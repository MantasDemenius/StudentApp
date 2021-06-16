import { createStackNavigator } from '@react-navigation/stack';
import React, { useContext } from 'react';
import TopHeader from '../Components/Navbar/TopHeader';
import checkAllowedPermissions from '../Components/Privacy/checkAllowedPermissions';
import { AuthContext } from '../Contexts/UserProvider';
import { RoomNavigatorParamsList } from '../TypeChecking/Types';
import RoomDetails from '../Views/Room/RoomDetails';
import Rooms from '../Views/Room/Rooms';

const RoomStack = createStackNavigator<RoomNavigatorParamsList>();
const RoomStackScreen: React.FC = () => {
  const { user } = useContext(AuthContext);
  return (
    <RoomStack.Navigator
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
      {checkAllowedPermissions(user?.permissions, ['rooms:read']) && (
        <RoomStack.Screen
          name="Rooms"
          component={Rooms}
          options={{ headerTitle: 'Auditorijos' }}
        />
      )}
      {checkAllowedPermissions(user?.permissions, ['seats:read']) && (
        <RoomStack.Screen
          name="RoomDetails"
          component={RoomDetails}
          options={({ route }) => ({ title: route.params.name })}
        />
      )}
    </RoomStack.Navigator>
  );
};
export default RoomStackScreen;
