import React from 'react';
import { StyleSheet, View } from 'react-native';
import { Text, useTheme } from 'react-native-paper';

type Props = {
  title: string;
};
const SectionHeaderListItem: React.FC<Props> = ({ title }) => {
  const { colors } = useTheme();

  const styles = StyleSheet.create({
    container: {
      justifyContent: 'center',
      alignItems: 'center',
      backgroundColor: colors.surface,
      borderBottomWidth: 1,
      borderBottomColor: colors.border
    }
  });

  return (
    <View style={styles.container}>
      <Text>{title}</Text>
    </View>
  );
};
export default SectionHeaderListItem;
