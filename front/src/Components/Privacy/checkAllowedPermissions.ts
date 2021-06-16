function checkAllowedPermissions(
  userPermissions: string[] | undefined,
  allowedPermissions: string[] | undefined
) {
  if (Array.isArray(allowedPermissions) && allowedPermissions.length === 0) {
    return true;
  }
  return userPermissions?.some((r) => allowedPermissions?.includes(r));
}

export default checkAllowedPermissions;
