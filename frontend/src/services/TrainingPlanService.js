import config from '../config.json';
import http from './HttpService'
import keys from "../errorKeys.json";
import { toast } from "react-toastify";

function trainingPlanUrl(id) {
  return `${config.apiUrl}/trainingPlans/${id}`;
}

export async function getTrainingPlans() {
  const { data: trainingPlans } = await http.get(
      `${config.apiUrl}/trainingPlans`, {
        method: 'GET'
      });
  return trainingPlans;
}

export async function getTrainingTypes() {
  const { data: trainingPlans } = await http.get(
      `${config.apiUrl}/trainingTypes`, {
        method: 'GET'
      });
  return trainingPlans;
}

export async function getTrainingPlan(id) {
  const { data: trainingPlan } = await http.get(`${trainingPlanUrl(id)}`);
  return trainingPlan;
}

export async function createTrainingPlan(trainingPlan, t) {
  return await http.post(`${config.apiUrl}/trainingPlans`, trainingPlan).catch(
      ex => {
        if (ex.response.data.error.errorKey
            === keys.TRAINING_PLAN_CONFLICT_ERROR) {
          toast.error(t('trainingPlan_number_conflict'));
        } else if (ex.response.data.error.errorKey
            === keys.ACCOUNT_NOT_FOUND_ERROR) {
          toast.error(t('account_notFound'));
        } else if (ex.response.data.error.errorKey
            === keys.ACCESS_LEVEL_INAPPROPRIATE) {
          toast.error(t('accessLevel_inappropriate_trainer'));
        }
      }).then(response => {
    if (response && response.status === 201) {
      toast.success(t('trainingPlan_add_success'));
    }
    return response;
  });
}

export async function updateTrainingPlan(trainingPlan, t) {
  return await http.put(trainingPlanUrl(trainingPlan.number),
      trainingPlan).then(response => {
    if (response.status === 200) {
      toast.success(t('trainingPlan_update_success'));
    }
  });
}

export async function deleteTrainingPlan(number, t) {
  return await http.delete(trainingPlanUrl(number)).catch(ex => {
    if (ex.response.data.error.errorKey === keys.TRAINING_PLAN_CONFLICT_ERROR) {
      toast.error(t('trainingPlan_remove_conflict'));
    } else if (ex.response.data.error.errorKey
        === keys.TRAINING_PLAN_NOT_FOUND_ERROR) {
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