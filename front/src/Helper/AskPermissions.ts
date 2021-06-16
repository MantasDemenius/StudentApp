// eslint-disable-next-line react-native/split-platform-components
import { Alert, PermissionsAndroid, Platform } from 'react-native';

const askWriteExternalStoragePermission = async () => {
  if (Platform.OS === 'ios') {
    return true;
  }
  try {
    const granted = await PermissionsAndroid.request(
      PermissionsAndroid.PERMISSIONS.WRITE_EXTERNAL_STORAGE,
      {
        title: 'Išorinės atminties prieiga',
        message: 'Mums reikalinga prieiga prie išorinės atminties',
        buttonNegative: 'Atšaukti',
        buttonPositive: 'Gerai'
      }
    );
    if (granted === PermissionsAndroid.RESULTS.GRANTED) {
      return true;
    }
  } catch (err) {
    Alert.alert(err);
  }
  return false;
};

export default askWriteExternalStoragePermission;
