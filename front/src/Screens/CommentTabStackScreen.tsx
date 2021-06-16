import { createMaterialTopTabNavigator } from '@react-navigation/material-top-tabs';
import React from 'react';
import { CommentTabNavigatorParamList } from '../TypeChecking/Types';
import CourseComments from '../Views/Comment/CourseComments';
import UserComments from '../Views/Comment/UserComments';

const CommentTabStack = createMaterialTopTabNavigator<CommentTabNavigatorParamList>();
const CommentTabStackScreen: React.FC = () => (
  <CommentTabStack.Navigator lazy>
    <CommentTabStack.Screen
      name="CourseComments"
      component={CourseComments}
      options={{ tabBarLabel: 'Moduliai' }}
    />
    <CommentTabStack.Screen
      name="UserComments"
      component={UserComments}
      options={{ tabBarLabel: 'Dėstytojai' }}
    />
  </CommentTabStack.Navigator>
);
export default CommentTabStackScreen;
