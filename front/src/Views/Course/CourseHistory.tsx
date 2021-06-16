import { RouteProp } from '@react-navigation/native';
import React, { useEffect, useState } from 'react';
import { SectionList, View } from 'react-native';
import Spinner from 'react-native-loading-spinner-overlay';
import Api from '../../Api';
import CourseSeatingListItem from '../../Components/ListItem/CourseSeatingListItem';
import SectionHeaderListItem from '../../Components/ListItem/SectionHeaderListItem';
import useAlert from '../../Hooks/useAlert';
import { CourseSeating, ServerError } from '../../TypeChecking/Interfaces';
import { CourseNavigatorParamsList } from '../../TypeChecking/Types';

interface CourseDetailsProps {
  route: RouteProp<CourseNavigatorParamsList, 'CourseDetails'>;
}

interface SectionType {
  title: string;
  data: CourseSeating[];
}

const CourseHistory: React.FC<CourseDetailsProps> = ({ route }) => {
  const [seatings, setSeatings] = useState<SectionType[] | null>(null);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const { id } = route.params;
  const alert = useAlert();

  useEffect(() => {
    Api.get(`/courses/${id}/seatUsages`)
      .then((response) => {
        const titleSet = new Set<string>(
          response.data.map((item: CourseSeating) => item.title)
        );
        const sectionList: SectionType[] = [...titleSet].map((title) => ({
          title,
          data: response.data.filter(
            (courseSeating: CourseSeating) => courseSeating.title === title
          )
        }));

        setSeatings(sectionList);
        setIsLoading(false);
      })
      .catch((err) => {
        const error: ServerError = err.response.data;
        alert(error.errors.join('\n'));
      });
  }, [alert, id]);

  return (
    <View>
      <Spinner visible={isLoading} />
      {seatings && (
        <SectionList
          sections={seatings}
          renderSectionHeader={({ section }) => (
            <SectionHeaderListItem title={section.title} />
          )}
          keyExtractor={(item) =>
            `${item.title}_${item.seatName}_${item.user.name} ${item.user.surname}`
          }
          renderItem={({ item }) => (
            <CourseSeatingListItem courseSeating={item} />
          )}
        />
      )}
    </View>
  );
};
export default CourseHistory;
