import config from '../config.json';
import http from './HttpService'
import keys from "../errorKeys.json";
import { toast } from "react-toastify";

function accountUrl(id) {
  return `${config.apiUrl}/accounts/${id}`;
}

export async function getAccounts() {
  const { data: accounts } = await http.get(`${config.apiUrl}/accounts`, {
    method: 'GET'
  });
  return accounts;
}

export async function getAccount(id) {
  const { data: account } = await http.get(`${accountUrl(id)}`);
  return account;
}

export async function register(account, t) {
  return http.post(`${config.apiUrl}/accounts`, account).catch(ex => {
    if (ex.response.data.error.errorKey === keys.ACCOUNT_CONFLICT_ERROR) {
      toast.error(t('account_login_conflict'));
    }
  });
}

export async function updateAccount(account, t) {
  return await http.put(accountUrl(account.number), account).then(response => {
    if (response.status === 200) {
      toast.success(t('account_update_success'));
    }
  });
}

export async function applyForDiet(number, login, t) {
  return await http.post(`${accountUrl(login)}/diets`,
      JSON.stringify({ number: number })).catch(ex => {
    if (ex.response.data.error.errorKey === keys.DIET_CONFLICT_ERROR) {
      toast.error(t('diet_possessing_conflict'));
    }
  }).then(response => {
    if (response && response.status === 200) {
      toast.success(t('diet_add_success'));
    }
  });
}

export async function removeDiet(number, login, t) {
  return await http.delete(`${accountUrl(login)}/diets/${number}`).catch(ex => {
    if (ex.response.data.error.errorKey === keys.DIET_NOT_FOUND_ERROR) {
      toast.error(t('diet_notFound'));
    }
    throw ex;
  }).then(response => {
    if (response && response.status === 200) {
      toast.success(t('diet_remove_success'));
    }
    return response;
  });
}

export async function getOwnDiets(login) {
  const { data: diets } = await http.get(
      `${config.apiUrl}/accounts/${login}/diets`, {
        method: 'GET'
      });
  return diets;
}

export async function applyForTrainingPlan(number, login, t) {
  return await http.post(`${accountUrl(login)}/trainingPlans`,
      JSON.stringify({ number: number })).catch(ex => {
    if (ex.response.data.error.errorKey === keys.TRAINING_PLAN_CONFLICT_ERROR) {
      toast.error(t('trainingPlan_possessing_conflict'));
    } else if (ex.response.data.error.errorKey === keys.TRAINING_PLAN_CONFLICT_TRAINER_ERROR) {
      toast.error(t('trainingPlan_trainer_conflict'));
    }
  }).then(response => {
    if (response && response.status === 200) {
      toast.success(t('trainingPlan_add_success'));
    }
  });
}

export async function removeTrainingPlan(number, login, t) {
  return await http.delete(`${accountUrl(login)}/trainingPlans/${number}`).catch(ex => {
    if (ex.response.data.error.errorKey === keys.TRAINING_PLAN_NOT_FOUND_ERROR) {
      toast.error(t('trainingPlan_notFound'));
    }
    throw ex;
  }).then(response => {
    if (response && response.status === 200) {
      toast.success(t('trainingPlan_remove_success'));
    }
    return response;
  });
}

export async function getOwnTrainingPlans(login) {
  const { data: trainingPlans } = await http.get(
      `${config.apiUrl}/accounts/${login}/trainingPlans`, {
        method: 'GET'
      });
  return trainingPlans;
}

export async function getOwnBookings(login) {
  const { data: bookings } = await http.get(
      `${config.apiUrl}/bookings/client/${login}`, {
        method: 'GET'
      });
  return bookings;
}