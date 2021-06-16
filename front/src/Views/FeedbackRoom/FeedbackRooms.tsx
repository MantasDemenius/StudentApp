import { useFocusEffect } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import React, { useCallback, useState } from 'react';
import { FlatList, StyleSheet, TouchableOpacity, View } from 'react-native';
import Spinner from 'react-native-loading-spinner-overlay';
import { FAB } from 'react-native-paper';
import Api from '../../Api';
import BottomButtons from '../../Components/Button/BottomButtons';
import FeedbackRoomListItem from '../../Components/ListItem/FeedbackRoomListItem';
import { useFeedbackRoomContext } from '../../Contexts/FeedbackRoomProvider';
import useAlert from '../../Hooks/useAlert';
import { FeedbackRoomStages, ServerError } from '../../TypeChecking/Interfaces';
import { FeedbackRoomNavigatorParamsList } from '../../TypeChecking/Types';

type FeedbackRoomsProps = {
  navigation: StackNavigationProp<
    FeedbackRoomNavigatorParamsList,
    'FeedbackRooms'
  >;
};

const styles = StyleSheet.create({
  container: {
    height: '100%'
  },
  fab: {
    position: 'absolute',
    margin: 16,
    right: 20,
    bottom: 40
  }
});

const FeedbackRooms: React.FC<FeedbackRoomsProps> = ({ navigation }) => {
  const [feedbackRooms, setFeedbackRooms] = useState<
    FeedbackRoomStages[] | null
  >(null);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [selectedItem, setSelectedItem] = useState<
    FeedbackRoomStages | undefined
  >(undefined);
  const [bottomButtonsVisible, setBottomButtonsVisible] = useState<boolean>(
    false
  );
  const alert = useAlert();
  const contextFeedbackRoom = useFeedbackRoomContext();

  const hideBottomButtons = () => {
    setSelectedItem(undefined);
    setBottomButtonsVisible(false);
  };

  const showBottomButtons = () => {
    setBottomButtonsVisible(true);
  };

  const selectItem = (item: FeedbackRoomStages) => {
    setSelectedItem(item);
    showBottomButtons();
  };

  useFocusEffect(
    useCallback(() => {
      let cleanedUp = false;

      Api.get(`/feedbackRooms`)
        .then((response) => {
          if (cleanedUp) {
            return;
          }
          setFeedbackRooms(response.data);
          setIsLoading(false);
        })
        .catch((err) => {
          const error: ServerError = err.response.data;
          alert(error.errors.join('\n'));
        });
      return () => {
        cleanedUp = true;
      };
    }, [alert])
  );

  const deleteFeedbackRoom = () => {
    Api.delete(`/feedbackRooms/${selectedItem?.id}`)
      .then(() => {
        setFeedbackRooms(
          (currentFeedbackRooms) =>
            currentFeedbackRooms &&
            currentFeedbackRooms.filter(
              (feedbackRoom) => feedbackRoom.id !== selectedItem?.id
            )
        );
        setIsLoading(false);
      })
      .catch((err) => {
        const error: ServerError = err.response.data;
        alert(error.errors.join('\n'));
      });
    hideBottomButtons();
  };

  const navigateToEdit = () => {
    hideBottomButtons();
    navigation.push('EditFeedbackRoom', {
      feedbackRoomId: (selectedItem as FeedbackRoomStages).id
    });
  };

  return (
    <View style={styles.container}>
      <Spinner visible={isLoading} />
      <FlatList
        data={feedbackRooms}
        extraData={feedbackRooms}
        keyExtractor={(item) => String(item.id)}
        renderItem={({ item }) => (
          <TouchableOpacity
            onPress={() => {
              contextFeedbackRoom.setContextFeedbackRoom(item);
              navigation.push('Comments', {
                screen: 'CommentsTab',
                params: {
                  screen: 'CourseComments'
                }
              });
            }}
            onLongPress={() => selectItem(item)}>
            <FeedbackRoomListItem
              feedbackRoom={item}
              isSelected={selectedItem?.id === item.id}
            />
          </TouchableOpacity>
        )}
      />
      <BottomButtons
        visible={bottomButtonsVisible}
        firstButtonText="Redaguoti"
        firstButtonOnPress={navigateToEdit}
        secondButtonText="Ištrinti"
        secondButtonOnPress={deleteFeedbackRoom}
        onDismiss={hideBottomButtons}
      />
      <FAB
        style={styles.fab}
        small
        icon="plus"
        onPress={() => navigation.push('CreateFeedbackRoom')}
      />
    </View>
  );
};

export default FeedbackRooms;
