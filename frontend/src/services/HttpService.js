import axios from 'axios';
import { toast } from 'react-toastify'
import { attachToken } from "./AuthenticationService";

axios.interceptors.response.use(null, error => {
  const expectedError = error.response
      && error.response >= 400
      && error.response < 500;

  if (!expectedError) {
    toast.error(error);
  }

  return Promise.reject(error);
});

axios.defaults.headers.post['Content-Type'] = 'application/json'
axios.interceptors.request.use(function(config) {
  const token = attachToken();
  config.headers.Authorization = token ? `Bearer ${token}` : '';
  return config;
})

export default {
  get: axios.get,
  post: axios.post,
  put: axios.put,
  delete: axios.delete
}