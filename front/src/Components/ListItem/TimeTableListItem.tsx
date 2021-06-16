import React from 'react';
import { Card } from 'react-native-paper';
import { TimeTable } from '../../TypeChecking/Interfaces';

type Props = {
  item: TimeTable;
};
const TimeTableListItem: React.FC<Props> = ({ item: timeTable }) => (
  <Card.Title
    title={`${timeTable.courseTitle}`}
    subtitle={`${timeTable.courseStartDate.split(' ')[1]} - ${
      timeTable.courseEndDate.split(' ')[1]
    }`}
  />
);

export default TimeTableListItem;
