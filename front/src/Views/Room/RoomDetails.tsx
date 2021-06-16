import { RouteProp } from '@react-navigation/native';
import React, { useEffect, useState } from 'react';
import { FlatList, StyleSheet, View } from 'react-native';
import Spinner from 'react-native-loading-spinner-overlay';
import { Button, Dialog, FAB, Portal, TextInput } from 'react-native-paper';
import Api from '../../Api';
import BottomButtons from '../../Components/Button/BottomButtons';
import SeatListItem from '../../Components/ListItem/SeatListItem';
import PrivateComponent from '../../Components/Privacy/PrivateComponent';
import ListItemClick from '../../Components/ReusableComponents/ListItemClick';
import askWriteExternalStoragePermission from '../../Helper/AskPermissions';
import downloadImage from '../../Helper/DownloadFile';
import useAlert from '../../Hooks/useAlert';
import { Seat, ServerError } from '../../TypeChecking/Interfaces';
import { RoomNavigatorParamsList } from '../../TypeChecking/Types';

const styles = StyleSheet.create({
  view: {
    height: '100%'
  },
  fab: {
    position: 'absolute',
    margin: 16,
    right: 20,
    bottom: 40
  },
  button: {
    height: '100%',
    justifyContent: 'center',
    alignItems: 'center'
  },
  dialog: {
    width: '70%',
    alignSelf: 'center'
  }
});

interface RoomDetailsProps {
  route: RouteProp<RoomNavigatorParamsList, 'RoomDetails'>;
}

const RoomDetails: React.FC<RoomDetailsProps> = ({ route }) => {
  const [seats, setSeats] = useState<Seat[] | null>(null);
  const [visible, setVisible] = useState<boolean>(false);
  const [bottomButtonsVisible, setBottomButtonsVisible] = useState<boolean>(
    false
  );
  const [selectedItem, setSelectedItem] = useState<Seat | null>(null);
  const alert = useAlert();
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [quantity, setQuantity] = useState<string | undefined>(undefined);
  const { room } = route.params;

  useEffect(() => {
    Api.get(`/rooms/${room.id}/seats`)
      .then((response) => {
        setSeats(response.data);
        setIsLoading(false);
      })
      .catch((err) => {
        const error: ServerError = err.response.data;
        alert(error.errors.join('\n'));
      });
  }, [alert, room.id]);

  const hideDialog = () => {
    setQuantity(undefined);
    setVisible(false);
  };

  const showDialog = () => setVisible(true);

  const hideBottomButtons = () => {
    setSelectedItem(null);
    setBottomButtonsVisible(false);
  };

  const showBottomButtons = () => {
    setBottomButtonsVisible(true);
  };

  const createSeats = () => {
    setIsLoading(true);
    if (quantity === undefined || parseInt(quantity, 10) <= 0) {
      alert('Įveskite daugiau negu 0 vietų!');
      hideDialog();
      return;
    }
    Api.post(`/rooms/${room.id}/seats`, { quantity })
      .then((response) => {
        setSeats((currentSeats) => [...(currentSeats ?? []), ...response.data]);
        setIsLoading(false);
      })
      .catch((err) => {
        const error: ServerError = err.response.data;
        alert(error.errors.join('\n'));
      });
    hideDialog();
  };

  const downloadQR = async () => {
    askWriteExternalStoragePermission()
      .then(() => {
        if (selectedItem) {
          downloadImage(room, selectedItem);
        }
      })
      .catch((err) => {
        const error: ServerError = err.response.data;
        alert(error.errors.join('\n'));
      });
    hideBottomButtons();
  };

  const deleteSeat = () => {
    setIsLoading(true);
    Api.delete(`/seats/${selectedItem?.id}`)
      .then(() => {
        setSeats(
          (currentSeats) =>
            currentSeats &&
            currentSeats.filter((seat) => seat.id !== selectedItem?.id)
        );
        setIsLoading(false);
      })
      .catch((err) => {
        const error: ServerError = err.response.data;
        alert(error.errors.join('\n'));
      });
    hideBottomButtons();
  };

  const selectItem = (item: Seat) => {
    setSelectedItem(item);
    showBottomButtons();
  };

  return (
    <View style={styles.view}>
      <Spinner visible={isLoading} />
      <View>
        <Portal>
          <Dialog
            style={styles.dialog}
            visible={visible}
            onDismiss={hideDialog}>
            <Dialog.Title>Sukurti papildomų vietų</Dialog.Title>
            <Dialog.Content>
              <TextInput
                keyboardType="numeric"
                placeholder="0"
                label="Kiekis"
                value={quantity}
                onChangeText={(text) =>
                  setQuantity(text.replace(/[^0-9]/g, ''))
                }
                maxLength={2}
              />
            </Dialog.Content>
            <Dialog.Actions>
              <Button color="#9e9e9e" onPress={hideDialog}>
                Atšaukti
              </Button>
              <Button color="#3f50b5" onPress={createSeats}>
                Sukurti
              </Button>
            </Dialog.Actions>
          </Dialog>
        </Portal>
      </View>
      <PrivateComponent permissions={['seats:create']}>
        <>
          {!seats?.length && (
            <View style={styles.button}>
              <Button mode="contained" onPress={showDialog}>
                Sukurti vietas
              </Button>
            </View>
          )}
        </>
      </PrivateComponent>
      <FlatList
        data={seats}
        extraData={seats}
        keyExtractor={(item) => String(item.id)}
        renderItem={({ item }) => (
          <ListItemClick
            item={item}
            onPress={selectItem}
            permissions={['seats:read']}
            isSelected={item.id === selectedItem?.id}>
            <SeatListItem seat={item} />
          </ListItemClick>
        )}
      />
      <PrivateComponent permissions={['seats:create']}>
        <FAB style={styles.fab} small icon="plus" onPress={showDialog} />
      </PrivateComponent>
      <PrivateComponent permissions={['seats:delete', 'seats:download']}>
        <BottomButtons
          visible={bottomButtonsVisible}
          firstButtonText="Atsisiųsti QR"
          firstButtonOnPress={downloadQR}
          secondButtonText="Ištrinti"
          secondButtonOnPress={deleteSeat}
          onDismiss={hideBottomButtons}
        />
      </PrivateComponent>
    </View>
  );
};

export default RoomDetails;
