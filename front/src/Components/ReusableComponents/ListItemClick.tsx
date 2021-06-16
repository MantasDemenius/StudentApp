import React, { useContext } from 'react';
import { StyleProp, ViewStyle } from 'react-native';
import { Card, useTheme } from 'react-native-paper';
import { AuthContext } from '../../Contexts/UserProvider';
import checkAllowedPermissions from '../Privacy/checkAllowedPermissions';

type Props = {
  item: any;
  onPress: (item: any) => void;
  permissions: string[];
  isSelected?: boolean;
  customBackgroundColor?: StyleProp<ViewStyle>;
};

const ListItemClick: React.FC<Props> = ({
  item,
  onPress,
  permissions,
  isSelected,
  customBackgroundColor,
  children
}) => {
  const { user } = useContext(AuthContext);
  const { colors } = useTheme();
  const styles = {
    card: {
      borderRadius: 0,
      borderBottomWidth: 1,
      borderColor: colors.border,
      backgroundColor: isSelected ? colors.primaryShade : colors.surface
    }
  };
  return (
    <Card
      style={[styles.card, customBackgroundColor]}
      onPress={() =>
        checkAllowedPermissions(user?.permissions, permissions) && onPress(item)
      }>
      {children}
    </Card>
  );
};

export default ListItemClick;
