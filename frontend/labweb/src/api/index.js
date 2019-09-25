import ajax from './ajax';



//Home
export const getFooter = () => ajax('/api/footer', {}) //footer
export const getHome = () => ajax('/api/home', {}) //Home数据
export const getSlideshow = () => ajax('/api/home/slideshow', {}) //轮播图


//实验室简介

export const getLabAll = () => ajax('/api/lab/all/category', {}) //获取所有一级分类
export const getLabOneById = (id) => ajax(`api/lab/one/${id}`, {}) //通过一级分类的id查询一条数据（目前针对机构概况和研究方向）
export const getLabResearchTeam = () => ajax(`api/lab/researchTeam/3`, {}) //查询研究团队所有已发布数据
export const getLabId = (id,type) => ajax(`api/${type}/${id}`, {}) //通过id查询一条已发布数据
export const getLabLabCategoryId = (id,pageNum,pageSize,type) => ajax(`api/${type}/list/${id}?pageNum=${pageNum}&pageSize=${pageSize}`, {}) //通过一级分类的id查询列表

//news新闻

export const getNewAll = () => ajax('/api/news/category', {}) //获取所有一级分类

//通知

export const getNoticeAll = () => ajax('/api/notice/category', {}) //获取所有一级分类

//科研

export const getSciAll = () => ajax('/api/scientificResearch/all/category', {}) //获取所有一级分类
//资源发布

export const getResourceAll = () => ajax('/api/resource/all/category', {}) //获取所有一级分类