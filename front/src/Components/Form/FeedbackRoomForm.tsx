import { RouteProp } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { AxiosResponse } from 'axios';
import React, { useEffect, useLayoutEffect, useState } from 'react';
import { StyleSheet, View } from 'react-native';
import { ScrollView } from 'react-native-gesture-handler';
import Spinner from 'react-native-loading-spinner-overlay';
import { Appbar, Divider, Surface, Title } from 'react-native-paper';
import Api from '../../Api';
import {
  formatDateTime,
  formatDateTimeStringToDate,
  nowMidnight,
  tomorrowMidnight
} from '../../Helper/DateHelper';
import useAlert from '../../Hooks/useAlert';
import { FeedbackRoomStages, ServerError } from '../../TypeChecking/Interfaces';
import { FeedbackRoomNavigatorParamsList } from '../../TypeChecking/Types';
import DateTimePickerRangeTitle from './DateTimePickerRangeTitle';

const styles = StyleSheet.create({
  container: {
    flex: 1
  },
  innerViewContainer: {
    justifyContent: 'center',
    alignItems: 'center'
  }
});

type Props = {
  navigation: StackNavigationProp<
    FeedbackRoomNavigatorParamsList,
    'CreateFeedbackRoom' | 'EditFeedbackRoom'
  >;
  route: RouteProp<
    FeedbackRoomNavigatorParamsList,
    'CreateFeedbackRoom' | 'EditFeedbackRoom'
  >;
};

const FeedbackRoomForm: React.FC<Props> = ({ navigation, route }) => {
  const feedbackRoomId = route.params?.feedbackRoomId;
  const isAddMode = !feedbackRoomId;
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [startDate, setStartDate] = useState<Date>(nowMidnight());
  const [endDate, setEndDate] = useState<Date>(tomorrowMidnight());
  const [writeStartDate, setWriteStartDate] = useState<Date>(nowMidnight());
  const [writeEndDate, setWriteEndDate] = useState<Date>(tomorrowMidnight());
  const [modifyStartDate, setModifyStartDate] = useState<Date>(nowMidnight());
  const [modifyEndDate, setModifyEndDate] = useState<Date>(tomorrowMidnight());
  const [evaluateStartDate, setEvaluateStartDate] = useState<Date>(
    nowMidnight()
  );
  const [evaluateEndDate, setEvaluateEndDate] = useState<Date>(
    tomorrowMidnight()
  );
  const [answerStartDate, setAnswerStartDate] = useState<Date>(nowMidnight());
  const [answerEndDate, setAnswerEndDate] = useState<Date>(tomorrowMidnight());
  const alert = useAlert();

  useEffect(() => {
    if (!isAddMode) {
      Api.get(`/feedbackRooms/${feedbackRoomId}/stages`)
        .then((response) => {
          const { data }: AxiosResponse<FeedbackRoomStages> = response;
          setStartDate(formatDateTimeStringToDate(data.startDate));
          setEndDate(formatDateTimeStringToDate(data.endDate));
          data.stages.forEach((stage) => {
            const tempStartDate = formatDateTimeStringToDate(stage.startDate);
            const tempEndDate = formatDateTimeStringToDate(stage.endDate);
            switch (stage.name) {
              case 'Rašyti':
                setWriteStartDate(tempStartDate);
                setWriteEndDate(tempEndDate);
                break;
              case 'Modifikuoti':
                setModifyStartDate(tempStartDate);
                setModifyEndDate(tempEndDate);
                break;
              case 'Vertinti':
                setEvaluateStartDate(tempStartDate);
                setEvaluateEndDate(tempEndDate);
                break;
              case 'Atsakyti':
                setAnswerStartDate(tempStartDate);
                setAnswerEndDate(tempEndDate);
                break;
              default:
                alert('Įvyko klaida, bandykite dar kartą');
            }
          });
          setIsLoading(false);
        })
        .catch((err) => {
          const error: ServerError = err.response.data;
          alert(error.errors.join('\n'));
        });
    } else {
      setIsLoading(false);
    }
  }, [alert, isAddMode, feedbackRoomId]);

  const createFeedbackRoom = (feedbackRoom: FeedbackRoomStages) => {
    Api.post('/feedbackRooms', feedbackRoom)
      .then(() => {
        navigation.goBack();
      })
      .catch((err) => {
        const error: ServerError = err.response.data;
        alert(error.errors.join('\n'));
      });
  };

  const updateFeedbackRoom = (feedbackRoom: FeedbackRoomStages) => {
    Api.put(`/feedbackRooms/${feedbackRoomId}`, feedbackRoom)
      .then(() => {
        navigation.goBack();
      })
      .catch((err) => {
        const error: ServerError = err.response.data;
        alert(error.errors.join('\n'));
      });
  };

  const onSubmit = () => {
    const feedbackRoom = {
      startDate: formatDateTime(startDate),
      endDate: formatDateTime(endDate),
      stages: [
        {
          name: 'Rašyti',
          startDate: formatDateTime(writeStartDate),
          endDate: formatDateTime(writeEndDate)
        },
        {
          name: 'Modifikuoti',
          startDate: formatDateTime(modifyStartDate),
          endDate: formatDateTime(modifyEndDate)
        },
        {
          name: 'Vertinti',
          startDate: formatDateTime(evaluateStartDate),
          endDate: formatDateTime(evaluateEndDate)
        },
        {
          name: 'Atsakyti',
          startDate: formatDateTime(answerStartDate),
          endDate: formatDateTime(answerEndDate)
        }
      ]
    };
    if (isAddMode) {
      createFeedbackRoom(feedbackRoom);
    } else {
      updateFeedbackRoom(feedbackRoom);
    }
  };

  useLayoutEffect(() => {
    navigation.setOptions({
      headerRight: () => (
        <Appbar.Action icon="content-save-outline" onPress={() => onSubmit()} />
      ),
      headerTitle: isAddMode ? 'Sukurti' : 'Atnaujinti'
    });
  });

  return (
    <Surface style={styles.container}>
      <Spinner visible={isLoading} />
      <ScrollView>
        <Divider />
        <View style={styles.innerViewContainer}>
          <Title>Atsiliepimų kambario laikotarpis</Title>
          <DateTimePickerRangeTitle
            startDate={startDate}
            setStartDate={setStartDate}
            endDate={endDate}
            setEndDate={setEndDate}
          />
        </View>
        <Divider />
        <View style={styles.innerViewContainer}>
          <Title>Etapai</Title>
          <DateTimePickerRangeTitle
            title="Rašyti"
            startDate={writeStartDate}
            setStartDate={setWriteStartDate}
            endDate={writeEndDate}
            setEndDate={setWriteEndDate}
          />
          <Divider />
          <DateTimePickerRangeTitle
            title="Modifikuoti"
            startDate={modifyStartDate}
            setStartDate={setModifyStartDate}
            endDate={modifyEndDate}
            setEndDate={setModifyEndDate}
          />
          <Divider />
          <DateTimePickerRangeTitle
            title="Vertinti"
            startDate={evaluateStartDate}
            setStartDate={setEvaluateStartDate}
            endDate={evaluateEndDate}
            setEndDate={setEvaluateEndDate}
          />
          <Divider />
          <DateTimePickerRangeTitle
            title="Atsakyti"
            startDate={answerStartDate}
            setStartDate={setAnswerStartDate}
            endDate={answerEndDate}
            setEndDate={setAnswerEndDate}
          />
        </View>
      </ScrollView>
    </Surface>
  );
};
export default FeedbackRoomForm;
