import React from 'react';
import { ColorValue, StyleSheet } from 'react-native';
import { Button, Caption, useTheme } from 'react-native-paper';
import { useFeedbackRoomContext } from '../../Contexts/FeedbackRoomProvider';
import { FeedbackRoomStage } from '../../TypeChecking/Enums';
import PrivateComponent from '../Privacy/PrivateComponent';

type Props = {
  permissions: string[];
  stages: FeedbackRoomStage[];
  onPress: () => void;
  text: string;
  color?: ColorValue;
};
const CommentButton: React.FC<Props> = ({
  permissions,
  stages,
  onPress,
  text,
  color
}) => {
  const { checkAllowedStages } = useFeedbackRoomContext();
  const isAllowed = checkAllowedStages(stages);
  const { colors } = useTheme();
  const styles = StyleSheet.create({
    buttonText: {},
    textSize: {
      fontSize: 16
    },
    Color: {
      color: isAllowed ? color || colors.black : colors.disabled
    }
  });

  return (
    <PrivateComponent permissions={permissions}>
      <Button
        disabled={!isAllowed}
        uppercase={false}
        onPress={onPress}
        labelStyle={[styles.buttonText]}>
        <Caption style={[styles.textSize, styles.Color]}>{text}</Caption>
      </Button>
    </PrivateComponent>
  );
};
export default CommentButton;
