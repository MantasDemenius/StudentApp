import React, { createContext, useState } from 'react';
import Api from '../Api';
import { setAccessToken, setRefreshToken } from '../Helper/Tokens';
import {
  deleteAccessTokenStorageValue,
  deleteRefreshTokenStorageValue,
  setAccessTokenStorageValue,
  setRefreshTokenStorageValue
} from '../Helper/TokenStorage';
import useAlert from '../Hooks/useAlert';
import { ServerError } from '../TypeChecking/Interfaces';

type User = null | {
  id: number;
  name: string;
  surname: string;
  permissions: string[];
  isLoggedIn: boolean;
};

interface FormData {
  username: string;
  password: string;
}

export const AuthContext = createContext<{
  user: User;
  userMe: () => void;
  signIn: (data: FormData) => void;
  signOut: () => void;
}>({
  user: null,
  userMe: () => {},
  signIn: () => {},
  signOut: () => {}
});

export const AuthProvider: React.FC = ({ children }) => {
  const [user, setUser] = useState<User>(null);
  const alert = useAlert();

  const userMe = async () => {
    Api.get('/users/me')
      .then((response) => {
        setUser({
          id: response.data.id,
          name: response.data.name,
          surname: response.data.surname,
          permissions: response.data.permissions,
          isLoggedIn: true
        });
      })
      .catch((err) => {
        const error: ServerError = err.response.data;
        if (error.errors) {
          alert(error.errors.join('\n'));
        } else {
          alert(
            'Įvyko klaida, atsiprašome už nesklandumus. Prašome kreiptis į sistemos administratorių.'
          );
        }
      });
  };
  const signIn = async (data: FormData) => {
    Api.post('/authenticate', { ...data, skipAuthRefresh: true })
      .then((response) => {
        setAccessTokenStorageValue(response.data.token);
        setRefreshTokenStorageValue(response.data.refresh_token);
        setAccessToken(response.data.token);
        setRefreshToken(response.data.refresh_token);
        userMe();
      })
      .catch((err) => {
        const error: ServerError = err.response.data;
        alert('klaida1');
        if (error.errors) {
          alert('klaida');
        } else {
          alert(
            'Įvyko klaida, atsiprašome už nesklandumus. Prašome kreiptis į sistemos administratorių.'
          );
        }
      });
  };

  const signOut = () => {
    deleteAccessTokenStorageValue();
    deleteRefreshTokenStorageValue();
    setUser(
      (currentUser) => currentUser && { ...currentUser, isLoggedIn: false }
    );
  };

  return (
    <AuthContext.Provider value={{ user, userMe, signIn, signOut }}>
      {children}
    </AuthContext.Provider>
  );
};
