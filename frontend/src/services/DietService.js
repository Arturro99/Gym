import config from '../config.json';
import http from './HttpService'
import keys from "../errorKeys.json";
import { toast } from "react-toastify";

function dietUrl(id) {
  return `${config.apiUrl}/diets/${id}`;
}

export async function getDiets() {
  const { data: diets } = await http.get(`${config.apiUrl}/diets`, {
    method: 'GET'
  });
  return diets;
}

export async function getDiet(id) {
  const { data: diet } = await http.get(`${dietUrl(id)}`);
  return diet;
}

export async function createDiet(diet, t) {
  return await http.post(`${config.apiUrl}/diets`, diet).catch(ex => {
    if (ex.response.data.error.errorKey === keys.DIET_CONFLICT_ERROR) {
      toast.error(t('diet_number_conflict'));
    }
  });
}

export async function updateDiet(diet, t) {
  return await http.put(dietUrl(diet.number), diet).then(response => {
    if (response.status === 200) {
      toast.success(t('diet_update_success'));
    }
  });
}

export async function deleteDiet(id) {
  return await http.delete(dietUrl(id));
}