import React from 'react';
import { StyleSheet } from 'react-native';
import { Card, useTheme } from 'react-native-paper';
import { formatDateStringToDateString } from '../../Helper/DateHelper';
import { FeedbackRoom } from '../../TypeChecking/Interfaces';

type Props = {
  feedbackRoom: FeedbackRoom;
  isSelected: boolean;
};

const FeedbackRoomListItem: React.FC<Props> = ({
  feedbackRoom,
  isSelected
}) => {
  const { colors } = useTheme();
  const styles = StyleSheet.create({
    title: {
      marginRight: 10
    },
    card: {
      backgroundColor: isSelected ? colors.primaryShade : colors.surface,
      borderRadius: 0,
      borderBottomWidth: 1,
      borderColor: colors.border
    }
  });
  return (
    <Card style={styles.card}>
      <Card.Title
        style={styles.title}
        title={`${formatDateStringToDateString(
          feedbackRoom.startDate
        )} - ${formatDateStringToDateString(feedbackRoom.endDate)}`}
      />
    </Card>
  );
};

export default FeedbackRoomListItem;
