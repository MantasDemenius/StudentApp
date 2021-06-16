import AsyncStorage from '@react-native-async-storage/async-storage';
import { Platform } from 'react-native';
import * as Keychain from 'react-native-keychain';

export const setAccessTokenStorageValue = (accessToken: string) => {
  AsyncStorage.setItem('tid', accessToken);
};

export const getAccessTokenStorageValue = async () => {
  const accessToken = await Promise.all([AsyncStorage.getItem('tid')]);
  return accessToken.find((x) => x != null);
};

async function setStorageValue(key: string, value: string) {
  if (Platform.OS === 'android') {
    await Keychain.setGenericPassword(key, value);
  }
}

async function getStorageValue() {
  if (Platform.OS === 'android') {
    const credentials = await Keychain.getGenericPassword();
    if (credentials) {
      return credentials.password;
    }
  }
  return null;
}

async function deleteStorageValue() {
  if (Platform.OS === 'android') {
    await Keychain.resetGenericPassword();
  }
}

export const setRefreshTokenStorageValue = (refreshToken: string) => {
  setStorageValue('refresh_token', refreshToken);
  AsyncStorage.setItem('rid', refreshToken);
};

export const getRefreshTokenStorageValue = async () => {
  const refreshToken = await Promise.all([
    getStorageValue(),
    AsyncStorage.getItem('rid')
  ]);
  return refreshToken.find((x) => x != null);
};

export const deleteAccessTokenStorageValue = async () => {
  await Promise.all([AsyncStorage.removeItem('tid')]);
};

export const deleteRefreshTokenStorageValue = async () => {
  await Promise.all([deleteStorageValue(), AsyncStorage.removeItem('rid')]);
};
