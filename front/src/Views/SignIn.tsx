import { yupResolver } from '@hookform/resolvers/yup';
import React, { useContext } from 'react';
import { useForm } from 'react-hook-form';
import { Button, View } from 'react-native';
import FormWrapper from '../Components/Form/FormWrapper';
import TextInputValidated from '../Components/Form/TextInputValidated';
import { userValidation } from '../Components/Validations';
import { AuthContext } from '../Contexts/UserProvider';

interface FormData {
  username: string;
  password: string;
}

const SignIn: React.FC = () => {
  const { signIn } = useContext(AuthContext);
  const {
    handleSubmit,
    setValue,
    errors,
    trigger,
    control
  } = useForm<FormData>({
    resolver: yupResolver(userValidation),
    defaultValues: {
      username: '',
      password: ''
    }
  });

  return (
    <View>
      <FormWrapper
        setValue={setValue}
        errors={errors}
        trigger={trigger}
        control={control}>
        <TextInputValidated name="username" label="Prisijungimo vardas" />
        <TextInputValidated
          name="password"
          secureTextEntry
          label="Slaptažodis"
        />
        <Button title="prisijungti" onPress={handleSubmit(signIn)} />
      </FormWrapper>
    </View>
  );
};

export default SignIn;
