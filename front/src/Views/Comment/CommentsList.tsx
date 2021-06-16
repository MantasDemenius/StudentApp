import {
  NavigationProp,
  RouteProp,
  useFocusEffect
} from '@react-navigation/native';
import { AxiosResponse } from 'axios';
import React, { useCallback, useEffect, useRef, useState } from 'react';
import {
  Dimensions,
  FlatList,
  KeyboardAvoidingView,
  StyleSheet,
  TextInput as RNTextInput
} from 'react-native';
import { TouchableOpacity } from 'react-native-gesture-handler';
import Spinner from 'react-native-loading-spinner-overlay';
import { Surface, TextInput } from 'react-native-paper';
import MaterialCommunityIcons from 'react-native-vector-icons/MaterialCommunityIcons';
import Api from '../../Api';
import CommentsListItem from '../../Components/ListItem/CommentsListItem';
import PrivateComponent from '../../Components/Privacy/PrivateComponent';
import { useFeedbackRoomContext } from '../../Contexts/FeedbackRoomProvider';
import extractIdFromHeader from '../../Helper/Header';
import useAlert from '../../Hooks/useAlert';
import { CommentEvent, FeedbackRoomStage } from '../../TypeChecking/Enums';
import { Comment, ServerError } from '../../TypeChecking/Interfaces';
import {
  CommentNavigatorParamList,
  FeedbackRoomNavigatorParamsList
} from '../../TypeChecking/Types';

const styles = StyleSheet.create({
  container: {
    flex: 1
  },
  flatlistStyle: {
    paddingBottom: 64
  },
  bottomContainer: {
    width: Dimensions.get('window').width,
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    position: 'absolute',
    bottom: 0
  },
  feedbackInput: {
    flex: 1,
    maxHeight: 130
  }
});

type Props = {
  route: RouteProp<CommentNavigatorParamList, 'CommentsList'>;
  navigation: NavigationProp<FeedbackRoomNavigatorParamsList, 'Comments'>;
};

const CommentsList: React.FC<Props> = ({ route, navigation }) => {
  const { commentSection, mode } = route.params;
  const [comments, setComments] = useState<Comment[] | null>(null);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const textInput = useRef<RNTextInput>(null);
  const [textInputText, setTextInputText] = useState<string>('');
  const { setTabHeaderTitle, checkAllowedStages } = useFeedbackRoomContext();
  const alert = useAlert();

  useEffect(() => {
    setTabHeaderTitle(commentSection.title);
    navigation.addListener('beforeRemove', () => {
      setTabHeaderTitle(undefined);
    });
  }, [commentSection.title, navigation, setTabHeaderTitle]);

  useFocusEffect(
    useCallback(() => {
      let cleanedUp = false;
      const callRoute =
        mode === 'course'
          ? `/feedbackRooms/${commentSection.feedbackRoomId}/courses/${commentSection.id}/courseComments`
          : `/feedbackRooms/${commentSection.feedbackRoomId}/users/${commentSection.id}/userComments`;
      Api.get(callRoute)
        .then((response: AxiosResponse<Comment[]>) => {
          if (cleanedUp) {
            return;
          }
          setComments(response.data);
          setIsLoading(false);
        })
        .catch((err) => {
          const error: ServerError = err.response.data;
          alert(error.errors.join('\n'));
        });
      return () => {
        cleanedUp = true;
      };
    }, [alert, commentSection.feedbackRoomId, commentSection.id, mode])
  );

  const submit = () => {
    if (!textInputText) {
      alert('Atsiliepimas negali būti tuščias');
      return;
    }
    setIsLoading(true);
    const callRoute =
      mode === 'course'
        ? `/feedbackRooms/${commentSection.feedbackRoomId}/courses/${commentSection.id}/courseComments`
        : `/feedbackRooms/${commentSection.feedbackRoomId}/users/${commentSection.id}/userComments`;
    Api.post(callRoute, { comment: textInputText })
      .then((response: AxiosResponse<any>) => {
        const newComment: Comment = {
          id: extractIdFromHeader(response),
          comment: textInputText,
          status: 'Neatsakytas',
          answer: null,
          answerer: null,
          votes: {
            id: 0,
            userVote: -2,
            votes: 0
          }
        };
        setComments((currentComments) => [
          ...(currentComments ?? []),
          newComment
        ]);
        setTextInputText('');
        textInput.current?.blur();
        setIsLoading(false);
      })
      .catch((err) => {
        const error: ServerError = err.response.data;
        if (error.errors) {
          alert(error.errors.join('\n'));
        } else {
          alert(
            'Įvyko klaida, atsiprašome už nesklandumus. Prašome kreiptis į sistemos administratorių.'
          );
        }
        setIsLoading(false);
      });
  };

  const removeComment = (commentId: number) => {
    const callRoute =
      mode === 'course'
        ? `/courseComments/${commentId}`
        : `/userComments/${commentId}`;

    Api.delete(callRoute)
      .then(() => {
        setComments(
          (currentComments) =>
            currentComments &&
            currentComments.filter((comment) => comment.id !== commentId)
        );
        setIsLoading(false);
      })
      .catch((err) => {
        const error: ServerError = err.response.data;
        if (error.errors) {
          alert(error.errors.join('\n'));
        } else {
          alert(
            'Įvyko klaida, atsiprašome už nesklandumus. Prašome kreiptis į sistemos administratorių.'
          );
        }
      });
  };

  const changeVote = (comment: Comment, vote: number) => {
    let changedVote = 0;
    if (comment?.votes.userVote === vote) {
      changedVote = 0;
    } else if (vote === 1) {
      changedVote = 1;
    } else {
      changedVote = -1;
    }

    let voteCount = 2;
    if (vote === comment?.votes.userVote) {
      voteCount = vote * -1;
    } else if (
      comment?.votes.userVote === 0 ||
      comment?.votes.userVote === -2
    ) {
      voteCount = vote;
    } else if (vote === -1 && comment?.votes.userVote === 1) {
      voteCount = -2;
    }

    const callRoute =
      mode === 'course'
        ? `/courseComments/${comment.id}/courseCommentVotes/${comment.votes.id}`
        : `/userComments/${comment.id}/userCommentVotes/${comment.votes.id}`;
    Api.patch(callRoute, { vote: changedVote })
      .then((response) => {
        if (!comments) {
          setComments(response.data);
          setIsLoading(false);
          return;
        }
        const newComments = comments.slice();
        const commentIndex = newComments.findIndex(
          (newComment) => newComment.id === comment.id
        );
        newComments[commentIndex].votes = {
          id: extractIdFromHeader(response),
          userVote: changedVote,
          votes: comment.votes.votes + voteCount
        };
        setComments(newComments);
        setIsLoading(false);
      })
      .catch((err) => {
        const error: ServerError = err.response.data;
        if (error.errors) {
          alert(error.errors.join('\n'));
        } else {
          alert(
            'Įvyko klaida, atsiprašome už nesklandumus. Prašome kreiptis į sistemos administratorių.'
          );
        }
      });
  };

  const commentEvent = (commentId: number, event: CommentEvent) => {
    setIsLoading(true);
    const foundComment = comments?.find((c) => c.id === commentId);
    if (!foundComment) {
      alert('Šis atsiliepimas jau neegzistuoja');
      return;
    }
    switch (event) {
      case CommentEvent.REMOVE:
        removeComment(commentId);
        break;
      case CommentEvent.EDIT:
        navigation.navigate('CommentWriteModal', {
          comment: foundComment,
          mode,
          action: CommentEvent.EDIT
        });
        setIsLoading(false);
        break;
      case CommentEvent.ANSWER:
        navigation.navigate('CommentWriteModal', {
          comment: foundComment,
          mode,
          action: CommentEvent.ANSWER
        });
        setIsLoading(false);
        break;
      case CommentEvent.AGREE:
        changeVote(foundComment, 1);
        break;
      case CommentEvent.DISAGREE:
        changeVote(foundComment, -1);
        break;
      default:
        alert('Įvyko klaida, prašome kreiptis į sistemos administratorių.');
        setIsLoading(false);
    }
  };

  return (
    <KeyboardAvoidingView style={styles.container}>
      {isLoading && <Spinner visible={isLoading} />}
      <FlatList
        contentContainerStyle={styles.flatlistStyle}
        data={comments}
        keyExtractor={(item) => String(item.id)}
        renderItem={({ item }) => (
          <CommentsListItem comment={item} mode={mode} event={commentEvent} />
        )}
      />
      <PrivateComponent
        permissions={
          mode === 'course'
            ? ['feedbackCourses:write']
            : ['feedbackUsers:write']
        }>
        <Surface style={styles.bottomContainer}>
          <TextInput
            style={styles.feedbackInput}
            label="Rašyti atsiliepimą..."
            ref={textInput}
            multiline
            disabled={!checkAllowedStages([FeedbackRoomStage.WRITE])}
            value={textInputText}
            onChangeText={(text) => setTextInputText(text)}
          />
          <TouchableOpacity
            onPress={() => submit()}
            disabled={!checkAllowedStages([FeedbackRoomStage.WRITE])}>
            <MaterialCommunityIcons name="arrow-right" size={48} />
          </TouchableOpacity>
        </Surface>
      </PrivateComponent>
    </KeyboardAvoidingView>
  );
};
export default CommentsList;
