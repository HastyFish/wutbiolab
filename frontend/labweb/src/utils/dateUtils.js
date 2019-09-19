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
  获取首页新闻需要的时间格式
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
  let date = new Date(time)
  return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate()
}