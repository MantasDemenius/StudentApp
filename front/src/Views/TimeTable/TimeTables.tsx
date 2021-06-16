import { StackNavigationProp } from '@react-navigation/stack';
import React, { useEffect, useState } from 'react';
import { SectionList, View } from 'react-native';
import Spinner from 'react-native-loading-spinner-overlay';
import Api from '../../Api';
import SectionHeaderListItem from '../../Components/ListItem/SectionHeaderListItem';
import TimeTableListItem from '../../Components/ListItem/TimeTableListItem';
import ListItemClick from '../../Components/ReusableComponents/ListItemClick';
import useAlert from '../../Hooks/useAlert';
import { ServerError, TimeTable } from '../../TypeChecking/Interfaces';
import { TimeTableNavigatorParamsList } from '../../TypeChecking/Types';

type RoomsProps = {
  navigation: StackNavigationProp<TimeTableNavigatorParamsList, 'TimeTables'>;
};

interface SectionType {
  title: string;
  data: TimeTable[];
}

const TimeTables: React.FC<RoomsProps> = ({ navigation }) => {
  const [timeTables, setTimeTables] = useState<SectionType[] | null>(null);
  const alert = useAlert();
  const [isLoading, setIsLoading] = useState<boolean>(true);

  const manageSeats = (course: TimeTable) =>
    navigation.push('CourseManage', {
      name: course.courseTitle,
      roomId: course.roomId
    });

  useEffect(() => {
    Api.get('/courses/timetable/10')
      .then((response) => {
        const titleSet = new Set<string>(
          response.data.map(
            (item: TimeTable) => item.courseStartDate.split(' ')[0]
          )
        );
        const sectionList: SectionType[] = [...titleSet].map((title) => ({
          title,
          data: response.data.filter(
            (timeTable: TimeTable) =>
              timeTable.courseStartDate.split(' ')[0] === title
          )
        }));

        setTimeTables(sectionList);
        setIsLoading(false);
      })
      .catch((err) => {
        const error: ServerError = err.response.data;
        alert(error.errors.join('\n'));
      });
  }, [alert]);

  return (
    <View>
      {isLoading && <Spinner visible={isLoading} />}
      {timeTables && (
        <SectionList
          sections={timeTables}
          renderSectionHeader={({ section }) => (
            <SectionHeaderListItem title={section.title} />
          )}
          keyExtractor={(item) => String(item.id)}
          renderItem={({ item }) => (
            <ListItemClick
              onPress={manageSeats}
              item={item}
              permissions={['seats:usage']}>
              <TimeTableListItem item={item} />
            </ListItemClick>
          )}
        />
      )}
    </View>
  );
};

export default TimeTables;
