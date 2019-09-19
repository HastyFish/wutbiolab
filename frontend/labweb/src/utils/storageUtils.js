/*
进行local数据存储管理的工具模块
 */
import store from 'store'
const ROUTER_KEY = 'router_key'
export default {
    /*
  保存动态的一级路由
   */
  saveRouter (arr) {
    store.set(ROUTER_KEY, arr)
  },
  /*
  读取user
   */
  getRouter () {
    return store.get(ROUTER_KEY) || []
  },
}