import React from 'react';
import { Card } from 'react-native-paper';
import { Room } from '../../TypeChecking/Interfaces';

interface Props {
  room: Room;
}

const RoomListItem: React.FC<Props> = ({ room }) => (
  <Card.Title
    title={`${room.roomNumber} ${room.type}`}
    subtitle={`${room.address} ${room.building}`}
  />
);
export default RoomListItem;
