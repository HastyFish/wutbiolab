/*
要求: 能根据接口文档定义接口请求
包含应用中所有接口请求函数的模块
每个函数的返回值都是promise

基本要求: 能根据接口文档定义接口请求函数
 */
import ajax from './ajax'


export const BASE = process.env.REACT_APP_BASE_API

// 登陆
export const reqLogin = (username, password) => ajax(BASE + '/login', {username, password}, 'POST')


export const logout = () => ajax( `/user/logout`, {}, 'POST') //退出登录