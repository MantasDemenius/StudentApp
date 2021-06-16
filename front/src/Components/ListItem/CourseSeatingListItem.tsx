import React from 'react';
import { Card, useTheme } from 'react-native-paper';
import { CourseSeating } from '../../TypeChecking/Interfaces';

type Props = {
  courseSeating: CourseSeating;
};
const CourseSeatingListItem: React.FC<Props> = ({ courseSeating }) => {
  const { colors } = useTheme();
  const styles = {
    card: {
      borderRadius: 0,
      borderBottomWidth: 1,
      borderColor: colors.border
    }
  };
  return (
    <Card style={styles.card}>
      <Card.Title
        title={`${courseSeating.user.name} ${courseSeating.user.surname}`}
        subtitle={`Vieta: ${courseSeating.seatName}`}
      />
    </Card>
  );
};
export default CourseSeatingListItem;
