import http from './HttpService'
import config from '../config.json';
import jwt from 'jwt-decode'
import keys from "../errorKeys.json";
import { toast } from "react-toastify";

export async function signIn(login, password, t) {
  return http.post(`${config.apiUrl}/authenticate`, {
    login,
    password
  }).catch(ex => {
    if (ex.response.status === 401) {
      toast.error(t('error_invalid_credentials'))
    } else if (ex.response.status === 403) {
      toast.error(t('error_account_inactive'))
    }
    throw ex;
  }).then(response => {
    if (response.data) {
      const values = jwt(response.data)
      localStorage.setItem('token', response.data);
      localStorage.setItem('userName', values.sub);
      localStorage.setItem('exp', values.exp);
      localStorage.setItem('roles', values.auth);
      localStorage.setItem('currentRole', values.auth.split(',')[0]);
    }
    toast.success(t('signIn_success') + getCurrentUser())
    return response.data;
  })
}

export function signOut() {
  localStorage.removeItem('token');
  localStorage.removeItem('userName');
  localStorage.removeItem('exp');
  localStorage.removeItem('roles');
  localStorage.removeItem('currentRole');
}

export function getCurrentUser() {
  return localStorage.getItem('userName') ? localStorage.getItem('userName')
      : '';
}

export function getCurrentRole() {
  return localStorage.getItem('currentRole') ? localStorage.getItem(
      'currentRole') : '';
}

export function getRoles() {
  return localStorage.getItem('roles') ? localStorage.getItem('roles').split(
      ',') : [];
}

export function switchCurrentRole(role) {
  localStorage.setItem('currentRole', role);
}

export function attachToken() {
  return localStorage.getItem('token');
}

export function confirmRegistration(token, t) {
  console.log(token)
  return http.get(`${config.apiUrl}/confirmRegistration/${token}`).catch(ex => {
    if (ex.response.data.error.errorKey === keys.URL_NOT_FOUND_ERROR) {
      toast.error(t('url_notFound'));
    } else if (ex.response.data.error.errorKey === keys.URL_GONE_ERROR) {
      toast.error(t('url_gone'));
    }
    throw ex;
  }).then(response => {
    if (response.status === 200) {
      toast.success(t('account_confirmation_success'))
    }
  })
}