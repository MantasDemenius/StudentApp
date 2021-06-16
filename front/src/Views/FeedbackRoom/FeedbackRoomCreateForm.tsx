import { RouteProp } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import React from 'react';
import FeedbackRoomForm from '../../Components/Form/FeedbackRoomForm';
import { FeedbackRoomNavigatorParamsList } from '../../TypeChecking/Types';

type Props = {
  navigation: StackNavigationProp<
    FeedbackRoomNavigatorParamsList,
    'CreateFeedbackRoom'
  >;
  route: RouteProp<FeedbackRoomNavigatorParamsList, 'CreateFeedbackRoom'>;
};
const FeedbackRoomCreateForm: React.FC<Props> = ({ navigation, route }) => (
  <FeedbackRoomForm navigation={navigation} route={route} />
);

export default FeedbackRoomCreateForm;
