import React from 'react';
import { View } from 'react-native';
import CommentSectionList from '../../Components/ListComponent/CommentSectionList';

const UserComments: React.FC = () => (
  <View>
    <CommentSectionList mode="user" />
  </View>
);

export default UserComments;
