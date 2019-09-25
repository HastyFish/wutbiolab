/*
包含n个日期时间处理的工具函数模块
*/

/*
  格式化日期
*/
export function formateDate(time) {
  if (!time) return ''
  let date = new Date(time)
  return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate()
    + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds()
}
/*
  获取Home新闻需要的时间格式
*/
export function getSampleDay(time) {
  if (!time) return ''
  let date = new Date(time)
  return (date.getMonth() + 1) + '.' + date.getDate()
}
/*
  获取新闻需要的时间格式
*/
export function getNewsDay(time) {
  if (!time) return ''
  let date = new Date(time);
  let years = date.getFullYear();
  let months = String(date.getMonth() + 1);
  let days = String(date.getDate());
  return `${years}-${months.padStart(2, '0')}-${days.padStart(2, '0')}`
}