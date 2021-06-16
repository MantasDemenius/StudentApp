import { MaterialTopTabNavigationProp } from '@react-navigation/material-top-tabs';
import {
  CompositeNavigationProp,
  useNavigation
} from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import React from 'react';
import { StyleSheet, TouchableOpacity } from 'react-native';
import { Card, Subheading, useTheme } from 'react-native-paper';
import { CommentSection } from '../../TypeChecking/Interfaces';
import {
  CommentTabNavigatorParamList,
  FeedbackRoomNavigatorParamsList
} from '../../TypeChecking/Types';

type Props = {
  commentSection: CommentSection;
  mode: string;
};

type CommentScreenProps = CompositeNavigationProp<
  MaterialTopTabNavigationProp<
    CommentTabNavigatorParamList,
    'CourseComments' | 'UserComments'
  >,
  StackNavigationProp<FeedbackRoomNavigatorParamsList>
>;

const CommentSectionListItem: React.FC<Props> = ({ commentSection, mode }) => {
  const { colors } = useTheme();
  const navigation = useNavigation<CommentScreenProps>();
  const styles = StyleSheet.create({
    card: {
      borderBottomWidth: 1,
      borderBottomColor: colors.border
    }
  });
  let feedbackText = 'atsiliepimų';
  const commentsLength = commentSection.commentCount;

  if (commentsLength === 1) {
    feedbackText = 'atsiliepimas';
  } else if (commentsLength >= 2 && commentsLength <= 9) {
    feedbackText = 'atsiliepimai';
  }

  return (
    <TouchableOpacity
      onPress={() =>
        navigation.push('Comments', {
          screen: 'CommentsList',
          params: {
            commentSection,
            mode
          }
        })
      }>
      <Card style={styles.card}>
        <Card.Title
          title={<Subheading>{commentSection.title}</Subheading>}
          subtitle={`${commentSection.commentCount} ${feedbackText}`}
          titleNumberOfLines={4}
        />
      </Card>
    </TouchableOpacity>
  );
};

export default CommentSectionListItem;
