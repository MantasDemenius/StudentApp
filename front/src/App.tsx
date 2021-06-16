import React from 'react';
import { DefaultTheme, Provider as PaperProvider } from 'react-native-paper';
import AlertProvider from './Contexts/AlertProvider';
import { AuthProvider } from './Contexts/UserProvider';
import Main from './Main';

declare global {
  namespace ReactNativePaper {
    interface ThemeColors {
      border: string;
      buttonCancel: string;
      buttonOk: string;
      black: string;
      unfocused: string;
      primaryShade: string;
      redBackground: string;
      information: string;
    }

    interface Theme {
      myOwnProperty: boolean;
    }
  }
}

const theme = {
  ...DefaultTheme,
  myOwnProperty: true,
  colors: {
    ...DefaultTheme.colors,
    primary: '#006666',
    accent: '#ffffff',
    border: 'rgb(211,211,211)',
    buttonCancel: '#9e9e9e',
    buttonOk: '#3f50b5',
    black: 'black',
    unfocused: 'rgba(0,0,0,0.7)',
    primaryShade: '#cce0e0',
    redBackground: '#FFEBEB',
    information: '#0569FF'
  }
};

const App: React.FC = () => (
  <PaperProvider theme={theme}>
    <AuthProvider>
      <AlertProvider>
        <Main />
      </AlertProvider>
    </AuthProvider>
  </PaperProvider>
);

export default App;
