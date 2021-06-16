/* eslint-disable no-nested-ternary */
import { DrawerNavigationProp } from '@react-navigation/drawer';
import { ParamListBase, Route } from '@react-navigation/native';
import { Scene } from '@react-navigation/stack/lib/typescript/src/types';
import React, { useContext } from 'react';
import { StyleSheet, TouchableOpacity } from 'react-native';
import { Appbar } from 'react-native-paper';
import MaterialCommunityIcons from 'react-native-vector-icons/MaterialCommunityIcons';
import { AuthContext } from '../../Contexts/UserProvider';

interface Props {
  scene: Scene<Route<string, object | undefined>>;
  previous: Scene<Route<string, object | undefined>> | undefined;
  navigation: DrawerNavigationProp<ParamListBase, string>;
}

const styles = StyleSheet.create({
  iconSize: {
    margin: 6,
    width: 36,
    height: 36,
    justifyContent: 'center',
    alignItems: 'center'
  }
});

const TopHeader: React.FC<Props> = ({ scene, previous, navigation }) => {
  const { user } = useContext(AuthContext);
  const { options } = scene.descriptor;
  let title;

  if (options.headerTitle !== undefined) {
    title = options.headerTitle;
  } else if (options.title !== undefined) {
    title = options.title;
  } else {
    title = scene.route.name;
  }

  return (
    <Appbar.Header>
      {user?.isLoggedIn ? (
        previous != null ? (
          <Appbar.BackAction size={24} onPress={() => navigation.goBack()} />
        ) : (
          <TouchableOpacity
            style={styles.iconSize}
            onPress={() => navigation.toggleDrawer()}>
            <MaterialCommunityIcons name="menu" size={24} color="white" />
          </TouchableOpacity>
        )
      ) : null}
      <Appbar.Content title={title} />
      {options?.headerRight
        ? options.headerRight({ tintColor: undefined })
        : null}
    </Appbar.Header>
  );
};

export default TopHeader;
