import jwtDecode from 'jwt-decode';
import {
  deleteAccessTokenStorageValue,
  deleteRefreshTokenStorageValue,
  getRefreshTokenStorageValue
} from './TokenStorage';

let accessToken = '';
let refreshToken = '';

export const setAccessToken = (value: string) => {
  accessToken = value;
};

export const getAccessToken = () => accessToken;

export const setRefreshToken = (value: string) => {
  refreshToken = value;
};

export const getRefreshToken = () => refreshToken;

export const validateRefreshToken = async () => {
  const storageRefreshToken = await getRefreshTokenStorageValue();
  if (!storageRefreshToken) {
    return false;
  }

  const { exp } = jwtDecode(storageRefreshToken);
  if (Date.now() >= exp * 1000) {
    await deleteRefreshTokenStorageValue();
    await deleteAccessTokenStorageValue();
    return false;
  }

  return true;
};
