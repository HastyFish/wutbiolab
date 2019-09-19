import ajax from './ajax';

export const getHeader = () => ajax('/api/common/head/product', {}) //header

export const getFooter = () => ajax('/api/common/footer', {}) //footer

//实验室简介

export const getLabAll = () => ajax('/api/lab/all/category', {}) //获取所有一级分类
export const getLabOneById = (id) => ajax(`api/lab/one/${id}`, {}) //通过一级分类的id查询一条数据（目前针对机构概况和研究方向）
export const getLabResearchTeam = () => ajax(`api/lab/researchTeam`, {}) //查询研究团队所有已发布数据
export const getLabId = (id) => ajax(`api/lab/${id}`, {}) //通过id查询一条已发布数据
export const getLabLabCategoryId = (id,pageNum,pageSize) => ajax(`api/lab/list/${id}?pageNum=${pageNum}&pageSize=${pageSize}`, {}) //通过一级分类的id查询列表


//关于古奥

export const getGooalIntro = () => ajax('/api/aboutCompany/introduction', {}) //获取公司介绍

export const getGooalHistory = () => ajax('/api/aboutCompany/history', {}) //获取公司历程

export const getGooalCulture = () => ajax('/api/aboutCompany/culture', {}) //获取公司文化

export const getGooalHonor = () => ajax('/api/aboutCompany/honor', {}) //获取公司荣誉icon

export const getGooalHonorImg = (id) => ajax('/api/aboutCompany/honor/img/' + id, {}) //获取公司荣誉image

export const getGooalJoin = () => ajax('/api/aboutCompany/join', {}) //获取加入我们

export const getGooalContact = () => ajax('/api/aboutCompany/contact', {}) //获取联系我们

//产品系列

export const getProductModule= (productId) => ajax(`/api/product/all/module/${productId}`, {})//通过产品id获取其所有子产品及对应子模块

//市场系列
export const getMarket= (category,pageNum,pageSize) => ajax(`/api/market/news/${category}?pageNum=${pageNum}&pageSize=${pageSize}`,{},)//获取市场活动其中一部分

export const getMarketNews= (id,time,category) => ajax(`/api/market/news/info/${id}?publishDate=${time}&category=${category}`,{},)//获取新闻
