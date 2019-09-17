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

// 退出
export const logout = () => ajax( `/user/logout`, {}, 'POST') //退出登录

//获取新闻列表
export const reqNewsList = (data) => ajax('/news',data, 'GET')

//获取新闻类型下拉列表
export const reqNewTypeList = () => ajax('/news/category', {}, 'GET')

//获取某条新闻
export const reqNewItem = (id) => ajax('/news/' + id, {}, 'GET')

//新增或更新新闻
export const reqsavePublishNews = (data) => ajax('/news', data, 'POST') 

//删除新闻
export const reqDeleteNew = (id) => ajax('/news/' + id, {}, 'DELETE') 

//获取通知列表
export const reqNoticeList = (data) => ajax('/notice',data, 'GET')

//获取通知类型下拉列表
export const reqNoticeTypeList = () => ajax('/notice/category', {}, 'GET')

//获取某条新闻
export const reqNoticeItem = (id) => ajax('/notice/' + id, {}, 'GET')

//新增或更新通知
export const reqsavePublishNotice = (data) => ajax('/notice', data, 'POST') 

//删除通知
export const reqDeleteNotice = (id) => ajax('/notice/' + id, {}, 'DELETE') 

//获取资源列表
export const reqSourceList = (data) => ajax('/resource',data, 'GET')

//获取资源类型下拉列表
export const reqSourceTypeList = () => ajax('/resource/category', {}, 'GET')

//获取某条资源
export const reqSourceItem = (id) => ajax('/resource/' + id, {}, 'GET')

//新增或更新资源
export const reqsavePublishSource = (data) => ajax('/resource', data, 'POST') 

//删除资源
export const reqDeleteSource = (id) => ajax('/resource/' + id, {}, 'DELETE') 

//获取机构概况
export const reqlabDes = () => ajax('/lab/one/12', {}, 'GET')

//保存机构概况
export const reqsavePublishlabDes = (data) => ajax('/lab', data, 'POST')

//获取研究方向
export const reqDeriection = () => ajax('/lab/one/13', {}, 'GET')

//保存研究方向
export const reqsavePublishDeriect = (data) => ajax('/lab', data, 'POST')

//获取研究团队数据
export const reqResearchTeam = () => ajax('/lab/researchTeam',{},'GET')

//研究研究团队数据排序
export const reqSortTeam = (data) => ajax('/lab/researchTeam/sort',data,'POST')

//新增研究团队一级分类
export const reqAddTeamClassifi = (data) => ajax('/lab/mentorCategorys',data, 'POST')

//获取研究团队一级分类
