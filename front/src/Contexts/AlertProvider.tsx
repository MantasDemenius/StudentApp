import React, { createContext, useState } from 'react';
import DialogAlert from '../Components/Alert/DialogAlert';
import { Alert } from '../TypeChecking/Interfaces';

const initialState = {
  isVisible: false,
  message: '',
  buttonText: '',
  title: ''
};

export const AlertContext = createContext<{
  alertState: Alert;
  alert: (message: string, title?: string, buttonText?: string) => void;
  close: () => void;
}>({
  alertState: initialState,
  alert: () => {},
  close: () => {}
});

const AlertProvider: React.FC = ({ children }) => {
  const [alertState, setAlertState] = useState<Alert>(initialState);

  const alert = (
    message: string,
    title: string = 'Klaida',
    buttonText: string = 'GERAI'
  ) => {
    setAlertState({
      isVisible: true,
      message,
      buttonText,
      title
    });
  };

  const close = () => {
    setAlertState(initialState);
  };

  return (
    <>
      <AlertContext.Provider value={{ alertState, alert, close }}>
        {children}
      </AlertContext.Provider>
      <DialogAlert props={alertState} close={close} />
    </>
  );
};

export default AlertProvider;
