import config from '../config.json';
import http from './HttpService'
import keys from "../errorKeys.json";
import { toast } from "react-toastify";

function activityUrl(id) {
  return `${config.apiUrl}/activities/${id}`;
}

export async function getActivities() {
  const { data: activities } = await http.get(`${config.apiUrl}/activities`, {
    method: 'GET'
  });
  return activities;
}

export async function getActivity(id) {
  const { data: activity } = await http.get(`${activityUrl(id)}`);
  return activity;
}

export async function createActivity(activity, t) {
  return await http.post(`${config.apiUrl}/activities`, activity).catch(ex => {
    if (ex.response.data.error.errorKey === keys.ACTIVITY_CONFLICT_ERROR) {
      toast.error(t('activity_number_conflict'));
    } else if (ex.response.data.error.errorKey
        === keys.ACCOUNT_NOT_FOUND_ERROR) {
      toast.error(t('account_notFound'));
    } else if (ex.response.data.error.errorKey
        === keys.ACCESS_LEVEL_INAPPROPRIATE) {
      toast.error(t('accessLevel_inappropriate_trainer'));
    } else if (ex.response.data.error.errorKey
        === keys.CONSTRAINT_VIOLATION) {
      toast.error(t('activity_inappropriate_date'));
    }
  }).then(response => {
    if (response && response.status === 201) {
      toast.success(t('activity_add_success'));
    }
    return response;
  });
}

export async function updateActivity(activity, t) {
  return await http.put(activityUrl(activity.number), activity).then(response => {
    if (response.status === 200) {
      toast.success(t('activity_update_success'));
    }
  }).catch(ex => {
    if (ex.response.data.error.errorKey === keys.ACTIVITY_CONFLICT_INACTIVE_ERROR) {
      toast.error(t('activity_deactivate_conflict'));
    } else if (ex.response.data.error.errorKey === keys.ACTIVITY_CONFLICT_ERROR) {
      toast.error(t('activity_expired_conflict'));
    } else if (ex.response.data.error.errorKey === keys.ACTIVITY_CONFLICT_IN_USE_ERROR) {
      toast.error(t('activity_inUse_conflict'));
    } else if (ex.response.data.error.errorKey
      === keys.CONSTRAINT_VIOLATION) {
      toast.error(t('activity_inappropriate_date'));
    }
  });
}

export async function deactivateActivity(number, t) {
  return await http.delete(activityUrl(number)).catch(ex => {
    if (ex.response.data.error.errorKey === keys.ACTIVITY_CONFLICT_ERROR) {
      toast.error(t('activity_deactivate_conflict'));
    } else if (ex.response.data.error.errorKey === keys.ACTIVITY_NOT_FOUND_ERROR) {
      toast.error(t('activity_notFound'));
    } else if (ex.response.data.error.errorKey
        === keys.ACCESS_LEVEL_INAPPROPRIATE) {
      toast.error(t('accessLevel_inappropriate_trainer'));
    }
    throw ex;
  }).then(response => {
    if (response && response.status === 200) {
      toast.success(t('activity_deactivate_success'));
    }
    return response;
  });
}