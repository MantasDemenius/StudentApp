import { createStackNavigator } from '@react-navigation/stack';
import React from 'react';
import { Platform } from 'react-native';
import BarcodeCamera from '../Components/BarcodeCamera';
import TopHeader from '../Components/Navbar/TopHeader';
import { useFeedbackRoomContext } from '../Contexts/FeedbackRoomProvider';
import { HomeNavigatorParamsList } from '../TypeChecking/Types';
import CommentModal from '../Views/Comment/CommentModal';
import Home from '../Views/Home/Home';
import CommentStackScreen from './CommentStackScreen';

const HomeStack = createStackNavigator<HomeNavigatorParamsList>();
const HomeStackScreen: React.FC = () => {
  const { tabHeaderTitle } = useFeedbackRoomContext();
  return (
    <HomeStack.Navigator
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
      <HomeStack.Screen
        options={{ headerTitle: 'Pradžia' }}
        name="Home"
        component={Home}
      />
      {Platform.OS === 'android' && (
        <HomeStack.Screen
          options={{
            headerTitle: 'Dalyvauti paskaitoje'
          }}
          name="BarcodeCamera"
          component={BarcodeCamera}
        />
      )}
      <HomeStack.Screen
        name="Comments"
        component={CommentStackScreen}
        options={{ headerTitle: tabHeaderTitle ?? 'Atsiliepimai' }}
      />
      <HomeStack.Screen name="CommentWriteModal" component={CommentModal} />
    </HomeStack.Navigator>
  );
};

export default HomeStackScreen;
