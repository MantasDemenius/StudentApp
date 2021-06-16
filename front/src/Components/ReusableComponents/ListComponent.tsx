import { useFocusEffect } from '@react-navigation/native';
import React, { useCallback, useState } from 'react';
import { FlatList, View } from 'react-native';
import Spinner from 'react-native-loading-spinner-overlay';
import Api from '../../Api';
import useAlert from '../../Hooks/useAlert';
import { ServerError } from '../../TypeChecking/Interfaces';

interface Props {
  route: string;
  renderItem: (item: any) => JSX.Element;
}

const ListComponent: React.FC<Props> = ({ route, renderItem }) => {
  const [items, setItems] = useState<any[] | null>(null);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const alert = useAlert();

  useFocusEffect(
    useCallback(() => {
      let cleanedUp = false;

      Api.get(route)
        .then((response) => {
          setIsLoading(false);
          if (cleanedUp) {
            return;
          }
          setItems(response.data);
        })
        .catch((err) => {
          const error: ServerError = err.response.data;
          if (error.errors) {
            alert(error.errors.join('\n'));
          } else {
            alert(
              'Įvyko klaida, atsiprašome už nesklandumus. Prašome kreiptis į sistemos administratorių.'
            );
          }
          setIsLoading(false);
        });
      return () => {
        cleanedUp = true;
      };
    }, [alert, route])
  );

  return (
    <View>
      <Spinner visible={isLoading} />
      <FlatList
        data={items}
        keyExtractor={(item) => String(item.id)}
        renderItem={({ item }) => renderItem(item)}
      />
    </View>
  );
};

export default ListComponent;
