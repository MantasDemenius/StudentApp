import { AxiosResponse } from 'axios';

const extractIdFromHeader = (response: AxiosResponse<any>) => {
  const { location } = response.headers;
  return location.split('/').pop();
};

export default extractIdFromHeader;
