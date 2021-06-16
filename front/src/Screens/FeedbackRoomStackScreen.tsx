import { createStackNavigator } from '@react-navigation/stack';
import React from 'react';
import TopHeader from '../Components/Navbar/TopHeader';
import { useFeedbackRoomContext } from '../Contexts/FeedbackRoomProvider';
import { FeedbackRoomNavigatorParamsList } from '../TypeChecking/Types';
import CommentModal from '../Views/Comment/CommentModal';
import FeedbackRoomCreateForm from '../Views/FeedbackRoom/FeedbackRoomCreateForm';
import FeedbackRoomEditForm from '../Views/FeedbackRoom/FeedbackRoomEditForm';
import FeedbackRooms from '../Views/FeedbackRoom/FeedbackRooms';
import CommentStackScreen from './CommentStackScreen';

const FeedbackRoomStack = createStackNavigator<FeedbackRoomNavigatorParamsList>();

const FeedbackRoomStackScreen: React.FC = () => {
  const { tabHeaderTitle } = useFeedbackRoomContext();
  return (
    <FeedbackRoomStack.Navigator
      headerMode="screen"
      screenOptions={{
        header: ({ scene, previous, navigation }) => (
          <TopHeader
            scene={scene}
            previous={previous}
            navigation={navigation}
          />
        )
      }}>
      <FeedbackRoomStack.Screen
        name="FeedbackRooms"
        component={FeedbackRooms}
        options={{ headerTitle: 'Atsiliepimų kambariai' }}
      />
      <FeedbackRoomStack.Screen
        name="CreateFeedbackRoom"
        component={FeedbackRoomCreateForm}
        options={{ headerTitle: 'Atsiliepimų kambarys' }}
      />
      <FeedbackRoomStack.Screen
        name="EditFeedbackRoom"
        component={FeedbackRoomEditForm}
        options={{ headerTitle: 'Atsiliepimų kambarys' }}
      />
      <FeedbackRoomStack.Screen
        name="Comments"
        component={CommentStackScreen}
        options={{ headerTitle: tabHeaderTitle ?? 'Atsiliepimai' }}
      />
      <FeedbackRoomStack.Screen
        name="CommentWriteModal"
        component={CommentModal}
      />
    </FeedbackRoomStack.Navigator>
  );
};
export default FeedbackRoomStackScreen;
