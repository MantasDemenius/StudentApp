import { createStackNavigator } from '@react-navigation/stack';
import React, { useContext } from 'react';
import TopHeader from '../Components/Navbar/TopHeader';
import checkAllowedPermissions from '../Components/Privacy/checkAllowedPermissions';
import { AuthContext } from '../Contexts/UserProvider';
import { TimeTableNavigatorParamsList } from '../TypeChecking/Types';
import CourseManage from '../Views/TimeTable/CourseManage';
import TimeTables from '../Views/TimeTable/TimeTables';

const TimeTableStack = createStackNavigator<TimeTableNavigatorParamsList>();
const TimeTableStackScreen: React.FC = () => {
  const { user } = useContext(AuthContext);
  return (
    <TimeTableStack.Navigator
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
      {checkAllowedPermissions(user?.permissions, ['courses:timetable']) && (
        <TimeTableStack.Screen
          name="TimeTables"
          component={TimeTables}
          options={{ headerTitle: 'Tvarkaraštis' }}
        />
      )}
      {checkAllowedPermissions(user?.permissions, ['seats:usage']) && (
        <TimeTableStack.Screen
          name="CourseManage"
          component={CourseManage}
          options={({ route }) => ({ title: route.params.name })}
        />
      )}
    </TimeTableStack.Navigator>
  );
};
export default TimeTableStackScreen;
