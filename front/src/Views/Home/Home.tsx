import { CompositeNavigationProp } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { AxiosError, AxiosResponse } from 'axios';
import React, { useCallback, useContext, useEffect, useState } from 'react';
import { Image, Linking, StyleSheet, View } from 'react-native';
import Spinner from 'react-native-loading-spinner-overlay';
import Api from '../../Api';
import HomeButton from '../../Components/Button/HomeButton';
import checkAllowedPermissions from '../../Components/Privacy/checkAllowedPermissions';
import PrivateComponent from '../../Components/Privacy/PrivateComponent';
import { useFeedbackRoomContext } from '../../Contexts/FeedbackRoomProvider';
import { AuthContext } from '../../Contexts/UserProvider';
import useAlert from '../../Hooks/useAlert';
import {
  FeedbackRoomStages,
  Lecture,
  ServerError
} from '../../TypeChecking/Interfaces';
import {
  CourseNavigatorParamsList,
  HomeNavigatorParamsList
} from '../../TypeChecking/Types';
import LectureCard from './LectureCard';

const KTUImage = require('../../assets/images/KtuPNG.png');

type linkEvent = {
  url: string;
};

interface HomeProps {
  navigation: CompositeNavigationProp<
    StackNavigationProp<HomeNavigatorParamsList, 'Home'>,
    StackNavigationProp<CourseNavigatorParamsList, 'Courses'>
  >;
}

const Home: React.FC<HomeProps> = ({ navigation }) => {
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [lecture, setLecture] = useState<Lecture | null>(null);
  const { user } = useContext(AuthContext);
  const { setContextFeedbackRoom, feedbackRoom } = useFeedbackRoomContext();
  const link = true;
  const alert = useAlert();

  const occupySeat = useCallback(
    (event: linkEvent) => {
      if (event.url) {
        const route = event.url.replace(/.*?:\/\//g, '');
        const routeCheck = route.match(/\/([^/]+)\/?$/);
        if (!routeCheck) {
          return;
        }
        const code = routeCheck[1];
        Api.post<Lecture>(`/seats/occupy`, { code })
          .then((response) => {
            setLecture(response.data);
          })
          .catch((err) => {
            const error: ServerError = err.response.data;
            alert(error.errors.join('\n'));
          });
      }
      setIsLoading(false);
    },
    [alert]
  );

  const leaveSeat = () => {
    Api.get('/seats/leave')
      .then(() => {
        setLecture(null);
      })
      .catch((err) => {
        const error: ServerError = err.response.data;
        alert(error.errors.join('\n'));
      });
    setIsLoading(false);
  };

  useEffect(() => {
    if (link) {
      Linking.addEventListener('url', occupySeat);
    }
    setIsLoading(false);
    return () => {
      Linking.removeEventListener('url', occupySeat);
    };
  }, [link, occupySeat]);

  useEffect(() => {
    if (checkAllowedPermissions(user?.permissions, ['seats:occupy'])) {
      Api.get('/lecture/active')
        .then((response) => {
          if (response.data) {
            setLecture(response.data);
          }
        })
        .catch((err) => {
          const error: ServerError = err.response.data;
          alert(error.errors.join('\n'));
        });
    }
    if (checkAllowedPermissions(user?.permissions, ['feedbackRooms:active'])) {
      Api.get('/feedbackRooms/active')
        .then((response: AxiosResponse<FeedbackRoomStages>) => {
          if (response.data) {
            setContextFeedbackRoom(response.data);
          }
        })
        .catch((err: AxiosError) => {
          if (err.response?.status !== 404) {
            const error: ServerError = err.response?.data;
            alert(error.errors.join('\n'));
          }
        });
    }
    setIsLoading(false);
  }, [alert, setContextFeedbackRoom, user?.permissions]);

  const styles = StyleSheet.create({
    container: {
      flex: 1
    },
    centerContainer: {
      flex: 3,
      flexDirection: 'row',
      alignItems: 'center',
      justifyContent: 'center'
    },
    buttonContainer: {
      flex: 1,
      justifyContent: 'flex-end',
      paddingBottom: 36
    }
  });

  return (
    <View style={styles.container}>
      <Spinner visible={isLoading} />

      <View style={styles.centerContainer}>
        {lecture ? (
          <LectureCard lecture={lecture} />
        ) : (
          <Image source={KTUImage} />
        )}
      </View>
      <View style={styles.buttonContainer}>
        <PrivateComponent permissions={['seats:occupy']}>
          {lecture ? (
            <HomeButton
              onPress={() => leaveSeat()}
              icon="walk"
              title="Išeiti iš paskaitos"
            />
          ) : (
            <HomeButton
              onPress={() => navigation.push('BarcodeCamera')}
              icon="camera"
              title="Dalyvauti paskaitoje"
            />
          )}
        </PrivateComponent>
        <PrivateComponent permissions={['courses:read']}>
          <HomeButton
            onPress={() => navigation.navigate('Courses')}
            icon="book"
            title="Moduliai"
          />
        </PrivateComponent>
        <PrivateComponent permissions={['feedbackRooms:active']}>
          <HomeButton
            disabled={feedbackRoom === null}
            onPress={() => {
              navigation.navigate('Comments', {
                screen: 'CommentsTab',
                params: {
                  screen: 'CourseComments'
                }
              });
            }}
            icon="forum"
            title="Atsiliepimai"
          />
        </PrivateComponent>
      </View>
    </View>
  );
};

export default Home;
