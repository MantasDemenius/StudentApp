import { useFocusEffect } from '@react-navigation/native';
import { AxiosResponse } from 'axios';
import React, { useCallback, useState } from 'react';
import { Dimensions, FlatList, StyleSheet } from 'react-native';
import Spinner from 'react-native-loading-spinner-overlay';
import { Surface } from 'react-native-paper';
import Api from '../../Api';
import { useFeedbackRoomContext } from '../../Contexts/FeedbackRoomProvider';
import useAlert from '../../Hooks/useAlert';
import { CommentSection, ServerError } from '../../TypeChecking/Interfaces';
import CommentSectionListItem from '../ListItem/CommentSectionListItem';

export interface Response {
  id: number;
  title: string;
  commentCount: number;
}

type Props = {
  mode: string;
};

const styles = StyleSheet.create({
  container: {
    height: Dimensions.get('window').height
  }
});

const CommentSectionList: React.FC<Props> = ({ mode }) => {
  const [courseSections, setCourseSections] = useState<CommentSection[] | null>(
    null
  );
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const { feedbackRoom } = useFeedbackRoomContext();
  const alert = useAlert();

  useFocusEffect(
    useCallback(() => {
      let cleanedUp = false;
      if (feedbackRoom) {
        const callRoute =
          mode === 'course'
            ? `/feedbackRooms/${feedbackRoom?.id}/courses`
            : `/feedbackRooms/${feedbackRoom?.id}/users`;
        Api.get(callRoute)
          .then((response: AxiosResponse<Response[]>) => {
            if (cleanedUp) {
              return;
            }
            const commentSections: CommentSection[] = response.data.map(
              (course) => ({
                id: course.id,
                feedbackRoomId: feedbackRoom?.id,
                title: course.title,
                commentCount: course.commentCount
              })
            );
            setIsLoading(false);
            setCourseSections(commentSections);
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
      }
      return () => {
        cleanedUp = true;
      };
    }, [alert, feedbackRoom, mode])
  );

  return (
    <Surface style={styles.container}>
      {isLoading && <Spinner visible={isLoading} />}
      <FlatList
        data={courseSections}
        keyExtractor={(item) => String(item.id)}
        renderItem={({ item }) => (
          <CommentSectionListItem commentSection={item} mode={mode} />
        )}
      />
    </Surface>
  );
};
export default CommentSectionList;
