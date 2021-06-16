export const now = () => new Date();

export const nowMidnight = () => {
  const d = new Date();
  d.setHours(8, 0, 0, 0);
  return d;
};

export const tomorrowMidnight = () => {
  const d = new Date();
  d.setDate(d.getDate() + 1);
  d.setHours(8, 0, 0, 0);
  return d;
};

export const formatDate = (d: Date) => {
  const date = d.toISOString().split('T')[0];
  return `${date}`;
};

export const formatTime = (d: Date) => {
  const time = d.toTimeString().split(' ')[0].split(':');
  return `${time[0]}:${time[1]}`;
};

export const formatDateTime = (d: Date) => {
  const date = d.toISOString().split('T')[0];
  const time = d.toTimeString().split(' ')[0];
  return `${date} ${time}`;
};

export const formatDateTimeStringToDate = (d: string) => {
  const date = d.split(' ')[0];
  const time = d.split(' ')[1];
  const dateParts = date.split('-').map(Number);
  const timeParts = time.split(':').map(Number);

  const correctDate = new Date(
    dateParts[0],
    dateParts[1] - 1,
    dateParts[2],
    timeParts[0],
    timeParts[1],
    timeParts[2]
  );
  return correctDate;
};

export const formatDateStringToDateString = (d: string) => d.split(' ')[0];
