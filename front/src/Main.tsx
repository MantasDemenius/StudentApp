import { NavigationContainer } from '@react-navigation/native';
import { AxiosRequestConfig } from 'axios';
import createAuthRefreshInterceptor from 'axios-auth-refresh';
import React from 'react';
import Api from './Api';
import {
  getAccessToken,
  getRefreshToken,
  setAccessToken
} from './Helper/Tokens';
import { setAccessTokenStorageValue } from './Helper/TokenStorage';
import RootStackScreen from './Screens/RootStackScreen';

Api.interceptors.request.use((request: AxiosRequestConfig) => {
  if (!request.url?.includes('authenticate')) {
    const token = getAccessToken();
    request.headers.Authorization = `Bearer ${token}`;
  }
  console.log(request.url);
  return request;
});

const refreshAuthLogic = async () => {
  Api.get('/refresh_token', {
    headers: {
      refresh_token: getRefreshToken()
    }
  }).then(async (tokenRefreshResponse) => {
    setAccessToken(tokenRefreshResponse.data.token);
    setAccessTokenStorageValue(tokenRefreshResponse.data.token);
    return Promise.resolve();
  });
};

createAuthRefreshInterceptor(Api, refreshAuthLogic);

const Main: React.FC = () => (
  <NavigationContainer>
    <RootStackScreen />
  </NavigationContainer>
);

export default Main;
