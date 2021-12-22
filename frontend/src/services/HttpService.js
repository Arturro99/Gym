import axios from 'axios';
import {toast} from 'react-toastify'

axios.interceptors.response.use(null, error => {
  const expectedError = error.response
      && error.response >= 400
      && error.response < 500;

  if (!expectedError) {
    toast.error(error);
  }

  return Promise.reject(error);
});

export default {
  get: axios.get,
  post: axios.post,
  put: axios.put,
  delete: axios.delete
}

export function attachHeaders() {
  return {headers: {
    "Access-Control-Allow-Origin": "http://localhost:8080"
    }}
}