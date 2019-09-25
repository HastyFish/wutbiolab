/*
要求: 能根据接口文档定义接口请求
包含应用中所有接口请求函数的模块
每个函数的返回值都是promise

基本要求: 能根据接口文档定义接口请求函数
 */
import ajax from './ajax'


// 登陆
export const reqLogin = (username,passowrd) => ajax('/user/login?username=' + username + '&password=' + passowrd, {}, 'POST')

// 退出
export const logout = () => ajax('/user/logout', {}, 'POST') //退出登录

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
export const reqlabDes = () => ajax('/lab/one/1', {}, 'GET')

//保存机构概况
export const reqSavelabDes = (data) => ajax('/lab', data, 'POST')

//发布机构概况
export const reqPublishlabDes = (data) => ajax('/lab/publish', data, 'POST')

//获取研究方向
export const reqDeriection = () => ajax('/lab/one/2', {}, 'GET')

//保存研究方向
export const reqSaveDeriect = (data) => ajax('/lab', data, 'POST')

//发布研究方向
export const reqPublishDeriect = (data) => ajax('/lab/publish', data, 'POST')

//获取研究团队数据
export const reqResearchTeam = () => ajax('/lab/researchTeam',{},'GET')

//发布研究团队数据
export const reqPublishResearchTeam = () => ajax('/lab/publish/3',{},'POST')

//研究研究团队数据排序
export const reqSortTeam = (data) => ajax('/lab/researchTeam/sort',data,'POST')

//删除研究团队人员
export const reqDeleteTeam = (id) => ajax('/lab/' + id, {}, 'DELETE')

//获取研究团队一级分类
export const reqAllTeamClassifi = () => ajax('/lab/mentorCategorys',{},'GET')

//新增研究团队一级分类
export const reqAddTeamClassifi = (data) => ajax('/lab/mentorCategorys',data, 'POST')

//删除研究团队一级分类
export const reqDeleteTeamClassifi = (id) => ajax('/lab/mentorCategory/' + id, {}, 'DELETE')

//更新研究团队一级分类
export const reqUpdateTeamClassifi = (data) => ajax('/lab/mentorCategorys',data, 'POST')

//新增研究团队一级分类下的所属人员
export const reqAddPerson = (data) => ajax('/lab', data, 'POST')

//获取研究团队一级分类下的所属人员信息
export const reqGetPerson = (id) => ajax('/lab/' + id, {}, 'GET')

//保存研究团队一级分类下的所属人员信息
export const reqSavePerson = (data) => ajax('/lab', data, 'POST')

//发布研究团队一级分类下的所属人员信息
export const reqPublishPerson = (data) => ajax('/lab/publish', data, 'POST')

//获取毕业生列表
export const reqGraduatesList = (data) => ajax('/lab/graduate', data, 'GET')

//获取所有毕业生类型
export const reqGraduatesTypes = () => ajax('/lab/graduateCategorys', {}, 'GET')

//获取某个毕业生信息
export const reqGraduateData = (id) => ajax('/lab/' + id , {}, 'GET')

//保存某个毕业生信息
export const reqSaveGraduate = (data) => ajax('/lab', data, 'POST')

//发布某个毕业生信息
export const reqPublishGraduate = (data) => ajax('/lab/publish', data, 'POST')

//删除某条毕业生信息
export const reqDeleteGradute = (id) => ajax('/lab/' + id, {}, 'DELETE')

//获取论文总览列表
export const reqArticleList = (data) => ajax('/scientificResearch/list/10', data, 'GET')

//获取某个论文信息
export const reqArticleData = (id) => ajax('/scientificResearch/' + id, {}, 'GET')

//保存某个论文信息
export const reqSaveArticle = (data) => ajax('/scientificResearch/', data, 'POST')

//发布某个论文信息
export const reqPublishArticle = (data) => ajax('/scientificResearch/publish', data, 'POST')

//删除某个论文信息
export const reqDeleteArticle = (id) => ajax('/scientificResearch/' + id, {}, 'DELETE')

//获取学术总览列表
export const reqAcademicList = (data) => ajax('/scientificResearch/list/academic', data, 'GET')

//获取学术分类信息
export const reqAcademicCategory = () => ajax('/scientificResearch/all/academicCategory', {}, 'GET')

//获取某个学术信息
export const reqAcademicData = (id) => ajax('/scientificResearch/' + id, {}, 'GET')

//保存某个学术信息
export const reqSaveAcademic = (data) => ajax('/scientificResearch/', data, 'POST')

//发布某个学术信息
export const reqPublishAcademic = (data) => ajax('/scientificResearch/publish', data, 'POST')

//删除某个学术信息
export const reqDeleteAcademic = (id) => ajax('/scientificResearch/' + id, {}, 'DELETE')

//获取首页中图片
export const reqImageList = () => ajax('/home/image', {}, 'GET');

//发布首页中图片
export const reqPublishImg = (data) => ajax('/home/image', data, 'POST');

//获取友情链接
export const reqCooplink = () => ajax('/home/cooperation-link', {}, 'GET')

//删除一条友情链接
export const reqDeleteCooplink = (id) => ajax('/home/cooperation-link/' + id, {}, 'DELETE')

//发送友情链接
export const reqSendCooplink = (data) => ajax('/home/cooperation-link', data, 'POST')

//获取页脚链接
export const reqFooter = () => ajax('/home/footer', {}, 'GET')

//删除一条页脚
export const reqDeleteFooter = (id) => ajax('/home/footer/' + id, {}, 'DELETE')

//发送页脚
export const reqSendFooter = (data) => ajax('/home/footer', data, 'POST')