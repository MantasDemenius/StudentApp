import React from 'react';
import { View } from 'react-native';
import { Button, Dialog, Portal, Subheading } from 'react-native-paper';
import { Alert } from '../../TypeChecking/Interfaces';

const DialogAlert: React.FC<{ props: Alert; close: () => void }> = ({
  props,
  close
}) => {
  const { isVisible, buttonText, message, title } = props;
  return (
    <View>
      <Portal>
        <Dialog visible={isVisible} onDismiss={close}>
          <Dialog.Title>{title}</Dialog.Title>
          <Dialog.Content>
            <Subheading>{message}</Subheading>
          </Dialog.Content>
          <Dialog.Actions>
            <Button onPress={close}>{buttonText}</Button>
          </Dialog.Actions>
        </Dialog>
      </Portal>
    </View>
  );
};

export default DialogAlert;
