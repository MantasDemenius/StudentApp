import { useContext } from 'react';
import { AlertContext } from '../Contexts/AlertProvider';

function useAlert() {
  const { alert } = useContext(AlertContext);

  return alert;
}
export default useAlert;
