import React from 'react';
import { Card } from 'react-native-paper';
import { Course } from '../../TypeChecking/Interfaces';

type Props = {
  course: Course;
};

const CourseListItem: React.FC<Props> = ({ course }) => (
  <Card.Title
    title={course.title}
    subtitle={course.code}
    titleNumberOfLines={2}
  />
);

export default CourseListItem;
