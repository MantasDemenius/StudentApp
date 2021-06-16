import {
  DrawerContentComponentProps,
  DrawerContentOptions,
  DrawerContentScrollView,
  DrawerItem
} from '@react-navigation/drawer';
import React, { useContext } from 'react';
import { View } from 'react-native';
import {
  Avatar,
  Caption,
  Divider,
  Drawer,
  Text,
  Title,
  useTheme
} from 'react-native-paper';
import MaterialCommunityIcons from 'react-native-vector-icons/MaterialCommunityIcons';
import { AuthContext } from '../../Contexts/UserProvider';
import PrivateComponent from '../Privacy/PrivateComponent';

type DrawerContentProps = DrawerContentComponentProps<DrawerContentOptions>;

const DrawerContent: React.FC<DrawerContentProps> = ({ navigation }) => {
  const { user, signOut } = useContext(AuthContext);
  const { colors } = useTheme();
  const styles: any = {
    drawerContent: {
      flex: 1,
      display: 'flex',
      justifyContent: 'space-between'
    },
    userInfoSection: {
      paddingLeft: 20,
      backgroundColor: colors.primary,
      paddingBottom: 20
    },
    title: {
      marginTop: 20,
      fontWeight: 'bold'
    },
    caption: {
      fontSize: 14,
      lineHeight: 14
    },
    row: {
      marginTop: 20,
      flexDirection: 'row',
      alignItems: 'center'
    },
    section: {
      flexDirection: 'row',
      alignItems: 'center',
      marginRight: 15
    },
    paragraph: {
      fontWeight: 'bold',
      marginRight: 3
    },
    preference: {
      flex: 1,
      justifyContent: 'flex-end'
    },
    font: {
      color: colors.accent
    },
    avatar: {
      paddingTop: 10
    }
  };
  return (
    <DrawerContentScrollView>
      <View style={styles.drawerContent}>
        <View style={styles.userInfoSection}>
          <Avatar.Image
            style={styles.avatar}
            source={{
              uri:
                'https://pbs.twimg.com/profile_images/952545910990495744/b59hSXUd_400x400.jpg'
            }}
            size={50}
          />
          <Title style={styles.title}>
            <Text style={styles.font}>
              {user?.name} {user?.surname}
            </Text>
          </Title>
          <Caption style={styles.caption}>
            <Text style={styles.font}>
              {user?.name}.{user?.surname}@ktu.lt
            </Text>
          </Caption>
        </View>
        <Divider />
        <Drawer.Section>
          <DrawerItem
            icon={({ color, size }) => (
              <MaterialCommunityIcons name="home" color={color} size={size} />
            )}
            label="Pradžia"
            onPress={() => navigation.navigate('Home')}
          />
          <PrivateComponent permissions={['courses:read']}>
            <DrawerItem
              icon={({ color, size }) => (
                <MaterialCommunityIcons name="book" color={color} size={size} />
              )}
              label="Moduliai"
              onPress={() => navigation.navigate('Courses')}
            />
          </PrivateComponent>
          <PrivateComponent permissions={['rooms:read']}>
            <DrawerItem
              icon={({ color, size }) => (
                <MaterialCommunityIcons
                  name="office-building"
                  color={color}
                  size={size}
                />
              )}
              label="Auditorijos"
              onPress={() => navigation.navigate('Rooms')}
            />
          </PrivateComponent>
          <PrivateComponent permissions={['courses:timetable']}>
            <DrawerItem
              icon={({ color, size }) => (
                <MaterialCommunityIcons
                  name="timetable"
                  color={color}
                  size={size}
                />
              )}
              label="Tvarkaraštis"
              onPress={() => navigation.navigate('TimeTables')}
            />
          </PrivateComponent>
          <PrivateComponent permissions={['feedbackRooms:read']}>
            <DrawerItem
              icon={({ color, size }) => (
                <MaterialCommunityIcons
                  name="forum"
                  color={color}
                  size={size}
                />
              )}
              label="Atsiliepimų kambariai"
              onPress={() => navigation.navigate('FeedbackRooms')}
            />
          </PrivateComponent>
        </Drawer.Section>
        <Drawer.Section>
          <DrawerItem
            icon={({ color, size }) => (
              <MaterialCommunityIcons name="logout" color={color} size={size} />
            )}
            label="Atsijungti"
            onPress={() => signOut()}
          />
        </Drawer.Section>
      </View>
    </DrawerContentScrollView>
  );
};
export default DrawerContent;
