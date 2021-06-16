import axios from 'axios';

const API_URL = 'http://192.168.0.101:5000/api/v1';

export default axios.create({
  withCredentials: true,
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json'
  }
});
