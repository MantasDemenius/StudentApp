import { StackNavigationProp } from '@react-navigation/stack';
import React from 'react';
import { View } from 'react-native';
import CourseListItem from '../../Components/ListItem/CourseListItem';
import ListComponent from '../../Components/ReusableComponents/ListComponent';
import ListItemClick from '../../Components/ReusableComponents/ListItemClick';
import { Course } from '../../TypeChecking/Interfaces';
import { CourseNavigatorParamsList } from '../../TypeChecking/Types';

interface CoursesProps {
  navigation: StackNavigationProp<CourseNavigatorParamsList, 'Courses'>;
}

const Courses: React.FC<CoursesProps> = ({ navigation }) => {
  const onPress = (item: Course) =>
    navigation.push('CourseDetails', { name: item.title, id: item.id });
  const ClickableListItem = (item: Course) => (
    <ListItemClick onPress={onPress} item={item} permissions={['courses:read']}>
      <CourseListItem course={item} />
    </ListItemClick>
  );
  return (
    <View>
      <ListComponent route="/courses" renderItem={ClickableListItem} />
    </View>
  );
};
export default Courses;
