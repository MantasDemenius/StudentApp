import { RouteProp } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import React from 'react';
import FeedbackRoomForm from '../../Components/Form/FeedbackRoomForm';
import { FeedbackRoomNavigatorParamsList } from '../../TypeChecking/Types';

type Props = {
  navigation: StackNavigationProp<
    FeedbackRoomNavigatorParamsList,
    'EditFeedbackRoom'
  >;
  route: RouteProp<FeedbackRoomNavigatorParamsList, 'EditFeedbackRoom'>;
};
const FeedbackRoomEditForm: React.FC<Props> = ({ navigation, route }) => (
  <FeedbackRoomForm navigation={navigation} route={route} />
);
export default FeedbackRoomEditForm;
