import axios from 'axios';
import { toast } from 'react-toastify'
import { attachToken, signOut } from "./AuthenticationService";
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
  if (localStorage.token) {
    const token = config.headers["refresh-token"];
    if (token) {
      const values = jwt(token)
      localStorage.setItem('token', token);
      localStorage.setItem('userName', values.sub);
      localStorage.setItem('exp', values.exp);
      localStorage.setItem('roles', values.auth);
    }
  }
  return config;
}, ex => {
  if (ex.response.data.status === 403 || ex.response.status === 403) {
    // window.location.replace('/error403');
  } else if (ex.response.data.status === 404 || ex.response.status === 404) {
    // window.location.replace('/error404');
  } else if (ex.response.data.status === 410 || ex.response.status === 410) {
    window.location.replace('/');
    signOut();
    toast.error(`${localStorage.getItem('i18nextLng') === 'pl' ? "Sesja wygasła"
        : "Session expired"}`)
  }
  throw ex;
})

export default {
  get: axios.get,
  post: axios.post,
  put: axios.put,
  delete: axios.delete
}