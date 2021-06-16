import { createStackNavigator } from '@react-navigation/stack';
import React from 'react';
import TopHeader from '../Components/Navbar/TopHeader';
import { AuthNavigatorParamsList } from '../TypeChecking/Types';
import SignIn from '../Views/SignIn';

const AuthStack = createStackNavigator<AuthNavigatorParamsList>();
const AuthStackScreen: React.FC = () => (
  <AuthStack.Navigator
    headerMode="screen"
    screenOptions={{
      header: ({ scene, previous, navigation }) => (
        <TopHeader scene={scene} previous={previous} navigation={navigation} />
      )
    }}>
    <AuthStack.Screen
      options={{ headerTitle: 'Prisijungti' }}
      name="SignIn"
      component={SignIn}
    />
  </AuthStack.Navigator>
);

export default AuthStackScreen;
