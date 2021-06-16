import React, { createElement } from 'react';
import { Controller, FieldError, Control } from 'react-hook-form';
import { TextInput } from 'react-native';

interface ErrorMap {
  [key: string]: FieldError | undefined;
}

interface Props {
  children: JSX.Element | JSX.Element[];
  errors: ErrorMap;
  setValue: (name: any, value: string, config?: Object) => void;
  trigger: (payload?: any | string[]) => Promise<boolean>;
  control: Control;
}

const FormWrapper = ({
  errors,
  setValue,
  children,
  trigger,
  control,
}: Props) => {
  const Inputs = React.useRef<TextInput[]>([]);

  return (
    <>
      {(Array.isArray(children) ? [...children] : [children]).map((child, i) =>
        child.props.name
          ? createElement(Controller, {
              name: child.props.name,
              control,
              key: child.props.name,
              onFocus: () => {
                Inputs.current[i].focus();
              },
              render: () =>
                createElement(child.type, {
                  ...{
                    ...child.props,
                    ref: (e: TextInput) => {
                      Inputs.current[i] = e;
                    },
                    onChangeText: (v: string) =>
                      setValue(child.props.name, v, true),
                    onSubmitEditing: () => {
                      Inputs.current[i + 1]
                        ? Inputs.current[i + 1].focus()
                        : Inputs.current[i].blur();
                    },
                    onBlur: async () => trigger(child.props.name),
                    blurOnSubmit: false,
                    name: child.props.name,
                    error: errors[child.props.name],
                  },
                }),
            })
          : child,
      )}
    </>
  );
};
export default FormWrapper;
