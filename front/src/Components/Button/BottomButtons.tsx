import React from 'react';
import { Dimensions, StyleSheet, View } from 'react-native';
import {
  Modal,
  Portal,
  Title,
  TouchableRipple,
  useTheme
} from 'react-native-paper';

type Props = {
  visible: boolean;
  onDismiss: () => any;
  firstButtonText: string;
  firstButtonOnPress: () => any;
  secondButtonText: string;
  secondButtonOnPress: () => any;
};

const BottomButtons: React.FC<Props> = ({
  visible,
  onDismiss,
  firstButtonText,
  firstButtonOnPress,
  secondButtonText,
  secondButtonOnPress
}) => {
  const { colors } = useTheme();
  const styles = StyleSheet.create({
    container: {
      flex: 1,
      flexDirection: 'column',
      justifyContent: 'flex-end'
    },
    buttonContainer: {
      width: Dimensions.get('window').width,
      height: Dimensions.get('window').height / 10,
      flexDirection: 'row',
      backgroundColor: colors.primary
    },
    button: {
      flexGrow: 1,
      justifyContent: 'center',
      alignItems: 'center'
    },
    font: {
      color: colors.background
    }
  });
  return (
    <Portal>
      <Modal visible={visible} onDismiss={onDismiss} style={styles.container}>
        <View>
          <View style={styles.buttonContainer}>
            <TouchableRipple
              onPress={() => firstButtonOnPress()}
              rippleColor="rgba(0, 0, 0, .32)"
              style={styles.button}>
              <Title style={styles.font}>{firstButtonText}</Title>
            </TouchableRipple>
            <TouchableRipple
              onPress={() => secondButtonOnPress()}
              rippleColor="rgba(0, 0, 0, .32)"
              style={styles.button}>
              <Title style={styles.font}>{secondButtonText}</Title>
            </TouchableRipple>
          </View>
        </View>
      </Modal>
    </Portal>
  );
};

export default BottomButtons;
