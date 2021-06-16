import React from 'react';
import { Card } from 'react-native-paper';
import { Seat } from '../../TypeChecking/Interfaces';

interface Props {
  seat: Seat;
}

const SeatListItem: React.FC<Props> = ({ seat }) => (
  <Card.Title title={`${seat.name}`} subtitle={`${seat.code}`} />
);

export default SeatListItem;
