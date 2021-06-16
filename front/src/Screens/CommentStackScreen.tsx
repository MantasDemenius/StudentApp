import { createStackNavigator } from '@react-navigation/stack';
import React from 'react';
import { CommentNavigatorParamList } from '../TypeChecking/Types';
import CommentsList from '../Views/Comment/CommentsList';
import CommentTabStackScreen from './CommentTabStackScreen';

type Props = {};
const CommentStack = createStackNavigator<CommentNavigatorParamList>();
const CommentStackScreen: React.FC<Props> = () => (
  <CommentStack.Navigator initialRouteName="CommentsTab" headerMode="none">
    <CommentStack.Screen name="CommentsTab" component={CommentTabStackScreen} />
    <CommentStack.Screen name="CommentsList" component={CommentsList} />
  </CommentStack.Navigator>
);
export default CommentStackScreen;
