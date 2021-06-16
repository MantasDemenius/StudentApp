import React from 'react';
import { StyleSheet } from 'react-native';
import { Card, Divider, Headline, Paragraph, Title } from 'react-native-paper';
import { Lecture } from '../../TypeChecking/Interfaces';

type Props = {
  lecture: Lecture;
};
const LectureCard: React.FC<Props> = ({ lecture }) => {
  const styles = StyleSheet.create({
    card: {
      alignItems: 'center',
      width: 300,
      height: 300
    },
    cardText: {
      paddingTop: 10,
      fontSize: 20
    }
  });
  return (
    <Card style={styles.card}>
      <Card.Content>
        <Headline>
          {lecture.roomBuilding}&nbsp;
          {lecture.roomNumber}
        </Headline>
        <Title>Vieta: {lecture.seat}</Title>
        <Divider />
        <Paragraph style={styles.cardText}>{lecture.courseTitle}</Paragraph>
        <Paragraph style={styles.cardText}>{lecture.courseCode}</Paragraph>
        <Paragraph style={styles.cardText}>
          Vieta užimta: {lecture.userOccupyStartDate.split(' ')[1]}
        </Paragraph>
      </Card.Content>
    </Card>
  );
};
export default LectureCard;
