import React, { forwardRef } from 'react';
import { FieldError } from 'react-hook-form';
import {
  StyleSheet,
  Text,
  TextInput,
  TextInputProps,
  View
} from 'react-native';
import { useTheme } from 'react-native-paper';

interface Props extends TextInputProps {
  name: string;
  label?: string;
  error?: FieldError | undefined;
}

const TextInputValidated = forwardRef<any, Props>((props, ref) => {
  const { label, error, ...inputProps } = props;
  const { colors } = useTheme();

  const styles = StyleSheet.create({
    input: {
      borderStyle: 'solid',
      borderWidth: 1,
      borderRadius: 6,
      borderColor: colors.primary,
      paddingVertical: 4,
      paddingLeft: 4,
      fontSize: 16,
      height: 40
    },
    container: {
      marginHorizontal: 16
    },
    label: {
      paddingVertical: 4,
      fontSize: 14,
      fontWeight: 'bold'
    },
    textError: {
      color: '#fc6d47',
      fontSize: 14
    }
  });

  return (
    <View style={styles.container}>
      {label && <Text style={styles.label}>{label}</Text>}
      <TextInput
        autoCapitalize="none"
        ref={ref}
        style={[
          styles.input,
          { borderColor: error != null ? colors.error : colors.primary }
        ]}
        {...inputProps}
      />
      <Text style={styles.textError}>{error != null && error.message}</Text>
    </View>
  );
});

export default TextInputValidated;
