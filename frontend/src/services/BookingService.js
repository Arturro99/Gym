import config from '../config.json';
import http from './HttpService'
import keys from "../errorKeys.json";
import { toast } from "react-toastify";

function bookingUrl(id) {
  return `${config.apiUrl}/bookings/${id}`;
}

function ownBookingUrl(id) {
  return `${config.apiUrl}/bookings/own/${id}`;
}

export async function getBookings() {
  const { data: bookings } = await http.get(`${config.apiUrl}/bookings`, {
    method: 'GET'
  });
  return bookings;
}

export async function getOwnBookings() {
  const { data: bookings } = await http.get(`${config.apiUrl}/bookings/own`, {
    method: 'GET'
  });
  return bookings;
}

export async function getBooking(id) {
  const { data: booking } = await http.get(`${bookingUrl(id)}`);
  return booking;
}

export async function getOwnBooking(id) {
  const { data: booking } = await http.get(`${ownBookingUrl(id)}`);
  return booking;
}

export async function updateBooking(booking, t) {
  return await http.put(bookingUrl(booking.number), booking).catch(ex => {
    if (ex.response.data.error.errorKey === keys.BOOKING_NOT_FOUND_ERROR) {
      toast.error(t('booking_notFound'));
    }
  }).then(response => {
    if (response.status === 200) {
      toast.success(t('booking_update_success'));
    }
  });
}

export async function createOwnBooking(activityNumber, login, t) {
  return await http.post(`${config.apiUrl}/bookings/own`,
      JSON.stringify({ activity: activityNumber, account: login })).catch(
      ex => {
        if (ex.response.data.error.errorKey === keys.BOOKING_CONFLICT_ERROR) {
          toast.error(t('booking_possessing_conflict'));
        } else if (ex.response.data.error.errorKey
            === keys.ACCESS_LEVEL_INAPPROPRIATE) {
          toast.error(t('accessLevel_inappropriate_client'));
        } else if (ex.response.data.error.errorKey
            === keys.BOOKING_CONFLICT_CANCELLATION_ERROR) {
          toast.error(t('booking_completion_cancellation_conflict'));
        } else if (ex.response.data.error.errorKey
            === keys.BOOKING_CONFLICT_CLIENT_TRAINER_ERROR) {
          toast.error(t('booking_completion_clientTrainer_conflict'));
        } else if (ex.response.data.error.errorKey
            === keys.BOOKING_CONFLICT_ACTIVITY_INACTIVE_ERROR) {
          toast.error(t('activity_inactive_conflict'));
        }
      }).then(response => {
    if (response && response.status === 200) {
      toast.success(t('booking_add_success'));
    }
  });
}

export async function createBooking(activityNumber, login, t) {
  return await http.post(`${config.apiUrl}/bookings`,
    JSON.stringify({ activity: activityNumber, account: login })).catch(
    ex => {
      if (ex.response.data.error.errorKey === keys.BOOKING_CONFLICT_ERROR) {
        toast.error(t('booking_possessing_conflict'));
      } else if (ex.response.data.error.errorKey
        === keys.ACCESS_LEVEL_INAPPROPRIATE) {
        toast.error(t('accessLevel_inappropriate_client'));
      } else if (ex.response.data.error.errorKey
        === keys.BOOKING_CONFLICT_CANCELLATION_ERROR) {
        toast.error(t('booking_completion_cancellation_conflict'));
      } else if (ex.response.data.error.errorKey
        === keys.BOOKING_CONFLICT_CLIENT_TRAINER_ERROR) {
        toast.error(t('booking_completion_clientTrainer_conflict'));
      } else if (ex.response.data.error.errorKey
        === keys.BOOKING_CONFLICT_ACTIVITY_INACTIVE_ERROR) {
        toast.error(t('activity_inactive_conflict'));
      }
    }).then(response => {
    if (response && response.status === 200) {
      toast.success(t('booking_add_success'));
    }
  });
}

export async function cancelBooking(number, t) {
  return await http.put(`${bookingUrl(number)}/cancel`).catch(ex => {
    if (ex.response.data.error.errorKey
        === keys.BOOKING_CONFLICT_CANCELLATION_ERROR) {
      toast.error(t('booking_cancellation_deadline_conflict'));
    } else if (ex.response.data.error.errorKey
        === keys.BOOKING_CONFLICT_COMPLETION_ERROR) {
      toast.error(t('booking_completion_cancellation_conflict'));
    }
    throw ex;
  }).then(response => {
    if (response && response.status === 200) {
      toast.success(t('booking_cancel_success'));
    }
    return response;
  });
}

export async function cancelOwnBooking(number, t) {
  return await http.put(`${ownBookingUrl(number)}/cancel`).catch(ex => {
    if (ex.response.data.error.errorKey
        === keys.BOOKING_CONFLICT_CANCELLATION_ERROR) {
      toast.error(t('booking_cancellation_deadline_conflict'));
    } else if (ex.response.data.error.errorKey
        === keys.BOOKING_CONFLICT_COMPLETION_ERROR) {
      toast.error(t('booking_completion_cancellation_conflict'));
    }
    throw ex;
  }).then(response => {
    if (response && response.status === 200) {
      toast.success(t('booking_cancel_success'));
    }
    return response;
  });
}

export async function completeBooking(number, t) {
  return await http.put(`${bookingUrl(number)}/complete`).catch(ex => {
    if (ex.response.data.error.errorKey
        === keys.BOOKING_CONFLICT_COMPLETION_ERROR) {
      toast.error(t('booking_completion_conflict'));
    }
    throw ex;
  }).then(response => {
    if (response && response.status === 200) {
      toast.success(t('booking_complete_success'));
    }
    return response;
  });
}