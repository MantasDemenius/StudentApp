import { Alert } from 'react-native';
import RNFetchBlob from 'rn-fetch-blob';
import { Room, Seat } from '../TypeChecking/Interfaces';
import { getAccessToken } from './Tokens';

const REMOTE_PATH = 'https://ktu-student-app.herokuapp.com/api/v1';

const downloadImage = (room: Room, seat: Seat) => {
  const { PictureDir } = RNFetchBlob.fs.dirs;
  const token = getAccessToken();
  const path = `${REMOTE_PATH}/rooms/${room.id}/seats/${seat.id}/qr`;
  const options = {
    fileCache: true,
    addAndroidDownloads: {
      useDownloadManager: true,
      notification: true,
      path: `${PictureDir}/qr_${seat.name}.png`,
      description: 'Image'
    }
  };
  RNFetchBlob.config(options)
    .fetch('GET', path, {
      authorization: `Bearer ${token}`
    })
    .then(() => {
      Alert.alert('Pavyko', 'QR kodas sėkmingai išsaugotas', [
        { text: 'Gerai' }
      ]);
    })
    .catch(() => {
      Alert.alert('Klaida', 'Nepavyko atsiųsti QR kodo', [{ text: 'Gerai' }]);
    });
};

export default downloadImage;
