export function parseFromTimePickerToOffsetDateTime(date) {
  let day = date.getDate();
  if (day < 10) {
    day = '0' + day;
  }
  let month = date.getMonth() + 1;
  if (month < 10) {
    month = '0' + month;
  }
  const year = date.getFullYear();
  let hour = date.getHours();
  if (hour < 10) {
    hour = '0' + hour;
  }
  let minute = date.getMinutes();
  if (minute < 10) {
    minute = '0' + minute;
  }
  return `${year}-${month}-${day}T${hour}:${minute}:00+01:00`
}

export function parseFromOffsetDateTimeToLegibleFormat(date) {
  const year = date.slice(0,4);
  const month = date.slice(5, 7);
  const day = date.slice(8, 10);
  const hour = date.slice(11, 13);
  const minute = date.slice(14, 16);

  return `${day}-${month}-${year}, ${hour}:${minute}`
}