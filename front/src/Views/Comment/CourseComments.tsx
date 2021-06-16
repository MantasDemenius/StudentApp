import React from 'react';
import { View } from 'react-native';
import CommentSectionList from '../../Components/ListComponent/CommentSectionList';

const CourseComments: React.FC = () => (
  <View>
    <CommentSectionList mode="course" />
  </View>
);
export default CourseComments;
