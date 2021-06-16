import { createStackNavigator } from '@react-navigation/stack';
import React, { useContext } from 'react';
import TopHeader from '../Components/Navbar/TopHeader';
import checkAllowedPermissions from '../Components/Privacy/checkAllowedPermissions';
import { AuthContext } from '../Contexts/UserProvider';
import { CourseNavigatorParamsList } from '../TypeChecking/Types';
import CourseHistory from '../Views/Course/CourseHistory';
import Courses from '../Views/Course/Courses';

const CourseStack = createStackNavigator<CourseNavigatorParamsList>();
const CourseStackScreen: React.FC = () => {
  const { user } = useContext(AuthContext);
  return (
    <CourseStack.Navigator
      headerMode="screen"
      screenOptions={{
        header: ({ scene, previous, navigation }) => (
          <TopHeader
            scene={scene}
            previous={previous}
            navigation={navigation}
          />
        )
      }}>
      {checkAllowedPermissions(user?.permissions, ['courses:read']) && (
        <CourseStack.Screen
          options={{ headerTitle: 'Moduliai' }}
          name="Courses"
          component={Courses}
        />
      )}
      <CourseStack.Screen
        name="CourseDetails"
        component={CourseHistory}
        options={({ route }) => ({ title: route.params.name })}
      />
    </CourseStack.Navigator>
  );
};
export default CourseStackScreen;
