import React, { useState } from 'react';
import { Dimensions, StyleSheet, Text, View } from 'react-native';
import { Card, Paragraph, useTheme } from 'react-native-paper';
import DateTimePickerValidated from './DateTimePickerValidated';

type Props = {
  title?: String;
  startDate: Date;
  setStartDate: React.Dispatch<React.SetStateAction<Date>>;
  endDate: Date;
  setEndDate: React.Dispatch<React.SetStateAction<Date>>;
};

const DateTimePickerRangeTitle: React.FC<Props> = ({
  title,
  startDate,
  setStartDate,
  endDate,
  setEndDate
}) => {
  const [error, setError] = useState<String | null>(null);
  const onStartChange = (date: Date) => {
    setStartDate(date);
  };
  const { colors } = useTheme();
  const styles = StyleSheet.create({
    screenWidth: {
      width: Dimensions.get('window').width
    },
    cardTitle: {
      alignSelf: 'center'
    },
    textStyle: {
      alignSelf: 'center',
      color: colors.error,
      fontSize: 14
    }
  });

  const onEndChange = (date: Date) => {
    setEndDate(date);
    if (date <= startDate) {
      setError('Pabaigos data turi būti vėlesnė už pradžios datą');
      return;
    }
    setError(null);
  };

  return (
    <Card style={styles.screenWidth}>
      <Card.Content>
        {title && <Paragraph style={styles.cardTitle}>{title}</Paragraph>}
        <DateTimePickerValidated date={startDate} setDate={onStartChange} />
        <View>
          <DateTimePickerValidated date={endDate} setDate={onEndChange} />
        </View>
        {error && <Text style={styles.textStyle}>{error}</Text>}
      </Card.Content>
    </Card>
  );
};

export default DateTimePickerRangeTitle;
