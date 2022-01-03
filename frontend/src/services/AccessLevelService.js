import config from '../config.json';
import http from './HttpService'
import keys from "../errorKeys.json";
import { toast } from "react-toastify";

function accessLevelUrl(id) {
  return `${config.apiUrl}/accessLevels/${id}`;
}

export async function getAccessLevelsByAccount(login) {
  const { data: accessLevel } = await http.get(`${accessLevelUrl(login)}`);
  return accessLevel;
}

export async function addAccessLevel(level, login, t) {
  console.log(login, level)
  return await http.post(`${config.apiUrl}/accessLevels`,
      JSON.stringify({ account: login, level: level.toUpperCase() })).catch(
      ex => {
        if (ex.response.data.error.errorKey
            === keys.ACCOUNT_NOT_FOUND_ERROR) {
          toast.error(t('account_notFound'));
        }
      }).then(response => {
    if (response.status === 200) {
      toast.success(t('accessLevel_add_success'));
    }
  })
}

export async function removeAccessLevel(level, login, t) {
  return await http.delete(
      `${accessLevelUrl(login)}/${level.toUpperCase()}`).catch(ex => {
    if (ex.response.data.error.errorKey
        === keys.ACCOUNT_NOT_FOUND_ERROR) {
      toast.error(t('account_notFound'));
    } else if (ex.response.data.error.errorKey
        === keys.ACCOUNT_NOT_FOUND_ERROR) {
      toast.error(t('accessLevel_notFound'));
    }
  }).then(response => {
    if (response.status === 200) {
      toast.success(t('accessLevel_remove_success'));
    }
  })
}