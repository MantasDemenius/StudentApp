import React from 'react';
import { StyleSheet } from 'react-native';
import { Card } from 'react-native-paper';
import MaterialCommunityIcons from 'react-native-vector-icons/MaterialCommunityIcons';
import { SeatUsage } from '../../TypeChecking/Interfaces';

interface Props {
  seat: SeatUsage;
  onLock: () => void;
  onClear: () => void;
}

const SeatUsageListItem: React.FC<Props> = ({ seat, onLock, onClear }) => {
  let subtitle = 'Neužimta';
  if (seat.startDate) {
    subtitle = seat.startDate;
  } else if (seat.locked) {
    subtitle = 'Užrakinta';
  }

  const styles = StyleSheet.create({
    lock: {
      paddingRight: 15
    }
  });

  return (
    <Card.Title
      title={
        seat.userName
          ? `${seat.seatName} ${seat.userName} ${seat.userSurname}`
          : seat.seatName
      }
      subtitle={subtitle}
      right={() => (
        <>
          {seat.userName ? (
            <MaterialCommunityIcons
              style={styles.lock}
              name="account-remove-outline"
              size={32}
              onPress={onClear}
            />
          ) : (
            <MaterialCommunityIcons
              style={styles.lock}
              name={seat.locked ? 'lock-outline' : 'lock-open-variant-outline'}
              color={seat.locked ? 'red' : 'black'}
              size={32}
              onPress={onLock}
            />
          )}
        </>
      )}
    />
  );
};
export default SeatUsageListItem;
