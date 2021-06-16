import { StackNavigationProp } from '@react-navigation/stack';
import React from 'react';
import { View } from 'react-native';
import RoomListItem from '../../Components/ListItem/RoomListItem';
import ListComponent from '../../Components/ReusableComponents/ListComponent';
import ListItemClick from '../../Components/ReusableComponents/ListItemClick';
import { Room } from '../../TypeChecking/Interfaces';
import { RoomNavigatorParamsList } from '../../TypeChecking/Types';

interface RoomsProps {
  navigation: StackNavigationProp<RoomNavigatorParamsList, 'Rooms'>;
}

const Rooms: React.FC<RoomsProps> = ({ navigation }) => {
  const onPress = (room: Room) =>
    navigation.push('RoomDetails', {
      name: `${room.building} ${room.roomNumber}`,
      room
    });

  const ClickableListItem = (item: Room) => (
    <ListItemClick onPress={onPress} item={item} permissions={['rooms:read']}>
      <RoomListItem room={item} />
    </ListItemClick>
  );

  return (
    <View>
      <ListComponent route="/rooms" renderItem={ClickableListItem} />
    </View>
  );
};

export default Rooms;
