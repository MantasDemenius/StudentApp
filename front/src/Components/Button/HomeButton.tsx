import React from 'react';
import { StyleSheet } from 'react-native';
import { Button, Title, useTheme } from 'react-native-paper';
import MaterialCommunityIcons from 'react-native-vector-icons/MaterialCommunityIcons';

type Props = {
  onPress: () => void;
  icon: string;
  title: string;
  disabled?: boolean;
};
const HomeButton: React.FC<Props> = ({ onPress, icon, title, disabled }) => {
  const { colors } = useTheme();
  const styles = StyleSheet.create({
    buttons: {
      backgroundColor: colors.accent
    },
    innerStyle: {
      justifyContent: 'space-between'
    }
  });
  return (
    <Button
      disabled={disabled}
      style={styles.buttons}
      contentStyle={styles.innerStyle}
      mode="outlined"
      uppercase={false}
      onPress={onPress}>
      <MaterialCommunityIcons name={icon} size={20} />
      <Title>{title}</Title>
    </Button>
  );
};

export default HomeButton;
