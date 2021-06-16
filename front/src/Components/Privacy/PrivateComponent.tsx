import React, { useContext } from 'react';
import { AuthContext } from '../../Contexts/UserProvider';
import checkAllowedPermissions from './checkAllowedPermissions';

interface Props {
  permissions: string[];
}

const PrivateComponent: React.FC<Props> = ({ permissions, children }) => {
  const { user } = useContext(AuthContext);

  return (
    <>
      {checkAllowedPermissions(user?.permissions, permissions) &&
        ({ ...(children as object) } as React.ReactNode)}
    </>
  );
};

export default PrivateComponent;
