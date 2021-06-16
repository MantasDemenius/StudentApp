import { createStackNavigator } from '@react-navigation/stack';
import React, { useContext, useEffect, useState } from 'react';
import { ActivityIndicator } from 'react-native';
import { AuthContext } from '../Contexts/UserProvider';
import {
  setAccessToken,
  setRefreshToken,
  validateRefreshToken
} from '../Helper/Tokens';
import {
  getAccessTokenStorageValue,
  getRefreshTokenStorageValue
} from '../Helper/TokenStorage';
import { RootNavigatorParamsList } from '../TypeChecking/Types';
import AuthStackScreen from './AuthStackScreen';
import DrawerStackScreen from './DrawerStackScreen';

const RootStack = createStackNavigator<RootNavigatorParamsList>();

const RootStackScreen: React.FC = () => {
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const { user, userMe } = useContext(AuthContext);

  useEffect(() => {
    async function initTokens() {
      if (await validateRefreshToken()) {
        const accessToken = await getAccessTokenStorageValue();
        const refreshToken = await getRefreshTokenStorageValue();
        setAccessToken(accessToken as string);
        setRefreshToken(refreshToken as string);
        userMe();
      }
    }
    initTokens();
    setIsLoading(false);
  }, []);

  if (isLoading) {
    return <ActivityIndicator size="large" />;
  }

  return (
    <RootStack.Navigator headerMode="none">
      {user?.isLoggedIn ? (
        <RootStack.Screen name="App" component={DrawerStackScreen} />
      ) : (
        <RootStack.Screen name="SignIn" component={AuthStackScreen} />
      )}
    </RootStack.Navigator>
  );
};
export default RootStackScreen;
