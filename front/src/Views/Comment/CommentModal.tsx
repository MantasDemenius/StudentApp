import { Picker } from '@react-native-picker/picker';
import { NavigationProp, RouteProp } from '@react-navigation/native';
import React, { useContext, useLayoutEffect, useState } from 'react';
import { ScrollView, StyleSheet, TextInput } from 'react-native';
import { Appbar, Divider, useTheme } from 'react-native-paper';
import Api from '../../Api';
import { AuthContext } from '../../Contexts/UserProvider';
import {
  capitalizeFirstLetter,
  getStatusFromStatusString
} from '../../Helper/StringHelper';
import useAlert from '../../Hooks/useAlert';
import { CommentEvent, CommentState } from '../../TypeChecking/Enums';
import { ServerError } from '../../TypeChecking/Interfaces';
import {
  FeedbackRoomNavigatorParamsList,
  HomeNavigatorParamsList
} from '../../TypeChecking/Types';

type Props = {
  route:
    | RouteProp<HomeNavigatorParamsList, 'CommentWriteModal'>
    | RouteProp<FeedbackRoomNavigatorParamsList, 'CommentWriteModal'>;
  navigation:
    | NavigationProp<FeedbackRoomNavigatorParamsList, 'CommentWriteModal'>
    | NavigationProp<HomeNavigatorParamsList, 'CommentWriteModal'>;
};

const CommentModal: React.FC<Props> = ({ route, navigation }) => {
  const { comment, mode, action } = route.params;

  const [selectStatus, setSelectStatus] = useState<CommentState>(
    getStatusFromStatusString(comment.status, 0)
  );
  const [selectSemester, setSelectSemester] = useState<CommentState>(
    getStatusFromStatusString(comment.status, 1)
  );
  const [textAreaText, setTextAreaText] = useState<string | undefined>(
    comment.answer ?? undefined
  );
  const [answerText, setAnswerText] = useState<string | undefined>(
    comment.comment ?? undefined
  );
  const { user } = useContext(AuthContext);
  const { colors } = useTheme();
  const isAnswer = action === CommentEvent.ANSWER;
  const alert = useAlert();

  const styles = StyleSheet.create({
    container: {
      flex: 1,
      backgroundColor: colors.surface
    },
    containerChild: {
      flexDirection: 'column'
    },
    backgroundColor: {
      backgroundColor: colors.surface
    },
    bottomInput: {
      textAlignVertical: 'top'
    }
  });

  const updateComment = () => {
    if (!textAreaText && isAnswer) {
      alert('Atsakymas turi būti užpildytas');
      return;
    }
    const callRoute =
      mode === 'course'
        ? `/courseComments/${route.params?.comment.id}?type=${action}`
        : `/userComments/${route.params?.comment.id}?type=${action}`;
    const statusText =
      selectStatus === CommentState.AGREE ||
      selectStatus === CommentState.PARTIALLY_AGREE
        ? ` - ${selectSemester}`
        : '';
    Api.put(callRoute, {
      answer: textAreaText,
      status: selectStatus + statusText,
      comment: answerText
    })
      .then(() => {
        navigation.goBack();
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

  useLayoutEffect(() => {
    navigation.setOptions({
      headerRight: () => (
        <Appbar.Action
          icon="content-save-outline"
          onPress={() => updateComment()}
        />
      ),
      title: isAnswer ? 'Atsakyti' : 'Redaguoti'
    });
  });

  return (
    <ScrollView
      style={styles.container}
      contentContainerStyle={styles.containerChild}>
      <TextInput
        style={styles.backgroundColor}
        value={answerText}
        onChangeText={setAnswerText}
        autoFocus={textAreaText === undefined}
        multiline
        editable={!isAnswer && user?.id === 9}
        underlineColorAndroid="transparent"
        placeholder="Atsiliepimas"
        placeholderTextColor="grey"
      />
      {user?.id !== 6 && (isAnswer || textAreaText) && (
        <>
          <Divider />
          <Divider />
          <Picker
            selectedValue={selectStatus}
            onValueChange={(itemValue) => setSelectStatus(itemValue)}>
            <Picker.Item
              label={CommentState.AGREE}
              value={CommentState.AGREE}
            />
            <Picker.Item
              label={CommentState.PARTIALLY_AGREE}
              value={CommentState.PARTIALLY_AGREE}
            />
            <Picker.Item
              label={CommentState.DISAGREE}
              value={CommentState.DISAGREE}
            />
            <Picker.Item
              label={CommentState.SPECIFY}
              value={CommentState.SPECIFY}
            />
          </Picker>
          {(selectStatus === CommentState.AGREE ||
            selectStatus === CommentState.PARTIALLY_AGREE) && (
            <Picker
              selectedValue={selectSemester}
              onValueChange={(itemValue) => setSelectSemester(itemValue)}>
              <Picker.Item
                label={capitalizeFirstLetter(CommentState.THIS_SEMESTER)}
                value={CommentState.THIS_SEMESTER}
              />
              <Picker.Item
                label={capitalizeFirstLetter(CommentState.NEXT_SEMESTER)}
                value={CommentState.NEXT_SEMESTER}
              />
            </Picker>
          )}
          <Divider />
          <Divider />
          <TextInput
            value={textAreaText}
            onChangeText={setTextAreaText}
            multiline
            autoFocus={isAnswer}
            style={[styles.backgroundColor, styles.bottomInput]}
            underlineColorAndroid="transparent"
            placeholder="Atsakymas"
            placeholderTextColor="grey"
            numberOfLines={10}
          />
        </>
      )}
    </ScrollView>
  );
};
export default CommentModal;
