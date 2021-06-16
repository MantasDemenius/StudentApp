import { RouteProp } from '@react-navigation/native';
import { AxiosResponse } from 'axios';
import React, { useEffect, useState } from 'react';
import { FlatList, View } from 'react-native';
import Spinner from 'react-native-loading-spinner-overlay';
import { useTheme } from 'react-native-paper';
import Api from '../../Api';
import SeatUsageListItem from '../../Components/ListItem/SeatUsageListItem';
import ListItemClick from '../../Components/ReusableComponents/ListItemClick';
import { formatDateTime, now } from '../../Helper/DateHelper';
import useAlert from '../../Hooks/useAlert';
import { SeatUsage, ServerError } from '../../TypeChecking/Interfaces';
import { TimeTableNavigatorParamsList } from '../../TypeChecking/Types';

interface CourseManageProps {
  route: RouteProp<TimeTableNavigatorParamsList, 'CourseManage'>;
}

const CourseManage: React.FC<CourseManageProps> = ({ route }) => {
  const [seats, setSeats] = useState<SeatUsage[] | null>(null);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const alert = useAlert();
  const { colors } = useTheme();
  const { roomId } = route.params;

  useEffect(() => {
    Api.get(`/rooms/${roomId}/seatUsages`)
      .then((response: AxiosResponse<SeatUsage[]>) => {
        setSeats(response.data);
        setIsLoading(false);
      })
      .catch((err) => {
        const error: ServerError = err.response.data;
        alert(error.errors.join('\n'));
      });
  }, [alert, roomId]);

  const clearSeat = (item: SeatUsage) => {
    setIsLoading(true);
    Api.patch(`/seats/${item?.seatId}/clear`, {
      endDate: formatDateTime(now())
    })
      .then(() => {
        setSeats(
          (currentSeats) =>
            currentSeats &&
            currentSeats.map((seat) =>
              seat.seatId === item?.seatId
                ? {
                    ...seat,
                    userName: null,
                    userSurname: null,
                    startDate: null,
                    locked: false
                  }
                : seat
            )
        );
        setIsLoading(false);
      })
      .catch((err) => {
        const error: ServerError = err.response.data;
        alert(error.errors.join('\n'));
      });
  };

  const lockSeat = (item: SeatUsage) => {
    setIsLoading(true);
    Api.patch(`/seats/${item?.seatId}/lock?isLocked=${!item?.locked}`)
      .then(() => {
        setSeats(
          (currentSeats) =>
            currentSeats &&
            currentSeats.map((seat) =>
              seat.seatId === item?.seatId
                ? {
                    ...seat,
                    locked: !item.locked
                  }
                : seat
            )
        );
        setIsLoading(false);
      })
      .catch((err) => {
        const error: ServerError = err.response.data;
        alert(error.errors.join('\n'));
      });
  };

  return (
    <View>
      <Spinner visible={isLoading} />
      <FlatList
        data={seats}
        extraData={seats}
        keyExtractor={(item) => String(item.seatId)}
        renderItem={({ item }) => {
          let backgroundColor = colors.surface;
          if (item.userName) {
            backgroundColor = colors.primaryShade;
          } else if (item.locked) {
            backgroundColor = colors.redBackground;
          }
          return (
            <ListItemClick
              onPress={() => {}}
              item={item}
              customBackgroundColor={{
                backgroundColor
              }}
              permissions={['seats:clear', 'seats:lock']}>
              <SeatUsageListItem
                seat={item}
                onLock={() => lockSeat(item)}
                onClear={() => clearSeat(item)}
              />
            </ListItemClick>
          );
        }}
      />
    </View>
  );
};
export default CourseManage;
