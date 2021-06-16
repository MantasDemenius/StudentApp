import React, { useContext } from 'react';
import { Dimensions, StyleSheet, View } from 'react-native';
import { TouchableOpacity } from 'react-native-gesture-handler';
import {
  Avatar,
  Caption,
  Card,
  Menu,
  Paragraph,
  Text,
  useTheme
} from 'react-native-paper';
import MaterialCommunityIcons from 'react-native-vector-icons/MaterialCommunityIcons';
import { AuthContext } from '../../Contexts/UserProvider';
import useAlert from '../../Hooks/useAlert';
import { CommentEvent, FeedbackRoomStage } from '../../TypeChecking/Enums';
import { Comment } from '../../TypeChecking/Interfaces';
import CommentButton from '../Button/CommentButton';
import checkAllowedPermissions from '../Privacy/checkAllowedPermissions';
import PrivateComponent from '../Privacy/PrivateComponent';

type Props = {
  comment: Comment;
  mode: string;
  event: (commentId: number, event: CommentEvent) => void;
};

const CommentsListItem: React.FC<Props> = ({ comment, mode, event }) => {
  const [visible, setVisible] = React.useState(false);
  const { colors } = useTheme();
  const { user } = useContext(AuthContext);
  const alert = useAlert();
  const openMenu = () => setVisible(true);

  const closeMenu = () => setVisible(false);

  const onEvent = (action: CommentEvent) => {
    event(comment.id, action);
    closeMenu();
  };

  const styles = StyleSheet.create({
    cardContainer: {
      flexDirection: 'row',
      marginBottom: 10
    },
    container: {
      paddingTop: 10,
      paddingLeft: 15
    },
    commentContainer: {
      flexDirection: 'row'
    },
    comment: {
      width: Dimensions.get('window').width - 50,
      textAlign: 'justify'
    },
    textSize: {
      fontSize: 16
    },
    likesContainer: {
      flex: 1,
      flexDirection: 'row',
      justifyContent: 'flex-end',
      paddingRight: 20
    },
    likesItems: {
      fontSize: 20,
      justifyContent: 'flex-start'
    },
    resetPadding: {
      padding: 0
    },
    answerContainer: {
      flexDirection: 'row',
      padding: 10,
      paddingLeft: 15,
      width: Dimensions.get('window').width
    },
    boldText: {
      paddingLeft: 15,
      fontWeight: 'bold'
    }
  });

  return (
    <Card style={styles.cardContainer}>
      <View style={styles.container}>
        <View style={styles.commentContainer}>
          <Caption style={styles.comment}>{comment.status}</Caption>
          <PrivateComponent
            permissions={
              mode === 'course'
                ? ['feedbackCourses:delete', 'feedbackCourses:update']
                : ['feedbackUsers:delete', 'feedbackUsers:update']
            }>
            <Menu
              visible={visible}
              onDismiss={closeMenu}
              anchor={
                <MaterialCommunityIcons
                  onPress={openMenu}
                  name="dots-vertical"
                  size={24}
                />
              }>
              {checkAllowedPermissions(
                user?.permissions,
                mode === 'course'
                  ? ['feedbackCourses:delete']
                  : ['feedbackUsers:delete']
              ) && (
                <Menu.Item
                  onPress={() => onEvent(CommentEvent.REMOVE)}
                  title="Ištrinti"
                />
              )}
              {checkAllowedPermissions(
                user?.permissions,
                mode === 'course'
                  ? ['feedbackCourses:update']
                  : ['feedbackUsers:update']
              ) && (
                <Menu.Item
                  onPress={() => onEvent(CommentEvent.EDIT)}
                  title="Redaguoti"
                />
              )}
            </Menu>
          </PrivateComponent>
        </View>
        <Paragraph
          style={[
            styles.comment,
            styles.textSize
          ]}>{`${comment.comment}`}</Paragraph>
        <Card.Actions style={styles.resetPadding}>
          <CommentButton
            permissions={
              mode === 'course'
                ? ['feedbackCourses:vote']
                : ['feedbackUsers:vote']
            }
            stages={[FeedbackRoomStage.EVALUATION]}
            text="Sutinku"
            onPress={() => onEvent(CommentEvent.AGREE)}
            color={
              comment.votes.userVote === 1 ? colors.information : colors.black
            }
          />
          <CommentButton
            permissions={
              mode === 'course'
                ? ['feedbackCourses:vote']
                : ['feedbackUsers:vote']
            }
            stages={[FeedbackRoomStage.EVALUATION]}
            text="Nesutinku"
            onPress={() => onEvent(CommentEvent.DISAGREE)}
            color={comment.votes.userVote === -1 ? colors.error : colors.black}
          />
          <CommentButton
            permissions={
              mode === 'course'
                ? ['feedbackCourses:answer']
                : ['feedbackUsers:answer']
            }
            stages={[FeedbackRoomStage.ANSWER]}
            text="Atsakyti"
            onPress={() => onEvent(CommentEvent.ANSWER)}
          />
          <View style={styles.likesContainer}>
            <MaterialCommunityIcons
              name="thumb-up-outline"
              style={styles.likesItems}
            />
            <Text style={[styles.likesItems]}>{comment.votes.votes}</Text>
          </View>
        </Card.Actions>
      </View>
      {comment.answer && (
        <TouchableOpacity
          style={styles.answerContainer}
          onPress={() =>
            alert(
              `${comment?.answer}`,
              `${comment?.answerer?.name} ${comment.answerer?.surname}`,
              'Uždaryti'
            )
          }>
          <Avatar.Text
            size={24}
            label={`${comment.answerer?.name[0].toUpperCase()}${comment.answerer?.surname[0].toUpperCase()}`}
          />
          <Text style={styles.boldText}>
            {comment.answerer?.name} {comment.answerer?.surname}
          </Text>
          <Text> - Atsakyta</Text>
        </TouchableOpacity>
      )}
    </Card>
  );
};

export default CommentsListItem;
