import axios from 'axios';
import { toast } from 'react-toastify'
import { attachToken } from "./AuthenticationService";
import jwt from "jwt-decode";

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
axios.interceptors.request.use(config => {
  const token = attachToken();
  config.headers.Authorization = token ? `Bearer ${token}` : '';
  return config;
});
axios.interceptors.response.use(config => {
  const token = config.headers["refresh-token"];
  if (token) {
    const values = jwt(token)
    localStorage.setItem('token', token);
    localStorage.setItem('userName', values.sub);
    localStorage.setItem('exp', values.exp);
    localStorage.setItem('roles', values.auth);
    localStorage.setItem('currentRole', values.auth.split(',')[0]);
  }
  return config;
})

export default {
  get: axios.get,
  post: axios.post,
  put: axios.put,
  delete: axios.delete
}