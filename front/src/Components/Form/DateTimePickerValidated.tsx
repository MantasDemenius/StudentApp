import RNDateTimePicker from '@react-native-community/datetimepicker';
import React, { useState } from 'react';
import { StyleSheet, View } from 'react-native';
import { Button, useTheme } from 'react-native-paper';
import { formatDate, formatTime } from '../../Helper/DateHelper';

const styles = StyleSheet.create({
  container: {
    flex: 1
  },
  picker: {
    flexGrow: 1,
    flexDirection: 'row',
    justifyContent: 'center'
  },
  innerViewContainer: {
    justifyContent: 'center',
    alignItems: 'center'
  }
});

type Props = {
  date: Date;
  setDate: (date: Date) => void;
};

const DateTimePickerValidated: React.FC<Props> = ({ date, setDate }) => {
  const [mode, setMode] = useState('date');
  const [show, setShow] = useState<boolean>(false);
  const today = new Date();
  const { colors } = useTheme();

  const onChange = (event, selectedDate) => {
    const currentDate = selectedDate || date;
    setShow(false);
    setDate(currentDate);
  };

  const showdate = () => {
    setMode('date');
    setShow(true);
  };

  const showStartTime = () => {
    setMode('time');
    setShow(true);
  };
  return (
    <>
      <View style={styles.picker}>
        <Button color={colors.black} onPress={showdate}>
          {formatDate(date)}
        </Button>
        <Button color={colors.black} onPress={showStartTime}>
          {formatTime(date)}
        </Button>
      </View>
      {show && (
        <RNDateTimePicker
          value={date}
          minimumDate={today}
          mode={mode}
          is24Hour
          display="default"
          onChange={onChange}
        />
      )}
    </>
  );
};

export default DateTimePickerValidated;
