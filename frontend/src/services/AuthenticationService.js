import http from './HttpService'
import config from '../config.json';
import jwt from 'jwt-decode'

export async function signIn(login, password) {
  return http.post(`${config.apiUrl}/authenticate`, {
    login,
    password
  }).then(response => {
    if (response.data) {
      const values = jwt(response.data)
      localStorage.setItem('token', response.data);
      localStorage.setItem('userName', values.sub);
      localStorage.setItem('exp', values.exp);
      localStorage.setItem('roles', values.auth);
      localStorage.setItem('currentRole', values.auth.split(',')[0]);
    }
    return response.data;
  })
}

export function signOut() {
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