import {
  createDrawerNavigator,
  DrawerContentComponentProps
} from '@react-navigation/drawer';
import React from 'react';
import { useWindowDimensions } from 'react-native';
import DrawerContent from '../Components/Navbar/DrawerContent';
import FeedbackRoomProvider from '../Contexts/FeedbackRoomProvider';
import { DrawerNavigatorParamsList } from '../TypeChecking/Types';
import CommentStackScreen from './CommentStackScreen';
import CourseStackScreen from './CourseStackScreen';
import FeedbackRoomStackScreen from './FeedbackRoomStackScreen';
import HomeStackScreen from './HomeStackScreen';
import RoomStackScreen from './RoomStackScreen';
import TimeTableStackScreen from './TimeTableStackScreen';

const Drawer = createDrawerNavigator<DrawerNavigatorParamsList>();

const DrawerStackScreen: React.FC = () => {
  const dimensions = useWindowDimensions();
  return (
    <FeedbackRoomProvider>
      <Drawer.Navigator
        drawerType={dimensions.width >= 768 ? 'permanent' : 'front'}
        drawerContent={(props: DrawerContentComponentProps) => (
          <DrawerContent
            state={props.state}
            navigation={props.navigation}
            descriptors={props.descriptors}
            progress={props.progress}
          />
        )}>
        <Drawer.Screen
          options={{ headerTitle: 'Pradžia' }}
          name="Home"
          component={HomeStackScreen}
        />
        <Drawer.Screen name="Courses" component={CourseStackScreen} />
        <Drawer.Screen name="Rooms" component={RoomStackScreen} />
        <Drawer.Screen name="TimeTables" component={TimeTableStackScreen} />
        <Drawer.Screen
          name="FeedbackRooms"
          component={FeedbackRoomStackScreen}
        />
      </Drawer.Navigator>
      <Drawer.Screen
        name="Comments"
        component={CommentStackScreen}
        options={{ headerTitle: 'Atsiliepimai' }}
      />
    </FeedbackRoomProvider>
  );
};
export default DrawerStackScreen;
