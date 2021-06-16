import { StackNavigationProp } from '@react-navigation/stack';
import React, { useState } from 'react';
import { Dimensions, Linking, StyleSheet, View } from 'react-native';
import { BarCodeReadEvent, RNCamera } from 'react-native-camera';
import { useTheme } from 'react-native-paper';
import useAlert from '../Hooks/useAlert';
import { HomeNavigatorParamsList } from '../TypeChecking/Types';

interface BarcodeCameraProps {
  navigation: StackNavigationProp<HomeNavigatorParamsList, 'BarcodeCamera'>;
}

const BarcodeCamera: React.FC<BarcodeCameraProps> = ({ navigation }) => {
  const [scanned, setScanned] = useState<Boolean>(false);
  const alert = useAlert();
  const { colors } = useTheme();

  const styles = StyleSheet.create({
    container: {
      flex: 1,
      flexDirection: 'column',
      backgroundColor: colors.black,
      position: 'relative'
    },
    preview: {
      height: Dimensions.get('window').height,
      justifyContent: 'flex-end',
      alignItems: 'center'
    },
    overlay: {
      position: 'absolute',
      top: 0,
      left: 0,
      right: 0,
      bottom: 0
    },
    unfocusedContainer: {
      flex: 1,
      backgroundColor: colors.unfocused
    },
    middleContainer: {
      flexDirection: 'row',
      flex: 1.5
    },
    focusedContainer: {
      flex: 6
    }
  });

  const handleBarCodeScanned = async ({ data, type }: BarCodeReadEvent) => {
    if (!scanned) {
      setScanned(true);
      if (type === RNCamera.Constants.BarCodeType.qr) {
        const supported = await Linking.canOpenURL(data);
        if (supported) {
          Linking.openURL(data).then(() => {
            navigation.goBack();
          });
        } else {
          alert('Šio brūkšninio kodo atidaryti negaliu!');
        }
      }
    }
  };

  return (
    <View style={styles.container}>
      <RNCamera
        style={styles.preview}
        type={RNCamera.Constants.Type.back}
        androidCameraPermissionOptions={{
          title: 'Leidimas naudotis jūsų kamera',
          message: 'Mums reikia leidimo naudotis jūsų kamera',
          buttonPositive: 'Gerai',
          buttonNegative: 'Atšaukti'
        }}
        captureAudio={false}
        onBarCodeRead={scanned ? undefined : handleBarCodeScanned}
      />
      <View style={styles.overlay}>
        <View style={styles.unfocusedContainer} />
        <View style={styles.middleContainer}>
          <View style={styles.unfocusedContainer} />
          <View style={styles.focusedContainer} />
          <View style={styles.unfocusedContainer} />
        </View>
        <View style={styles.unfocusedContainer} />
      </View>
    </View>
  );
};

export default BarcodeCamera;
