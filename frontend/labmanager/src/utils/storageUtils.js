/*
进行local数据存储管理的工具模块
 */
import store from 'store'
const USER_KEY = 'user_key'
const ROUTER_KEY = 'router_key'
const TOKEN_KEY = 'token_key'
export default {
  /*
  保存user
   */
  saveUser (user) {
    // localStorage.setItem(USER_KEY, JSON.stringify(user))
    store.set(USER_KEY, user)
  },

  /*
  读取user
   */
  getUser () {
    // return JSON.parse(localStorage.getItem(USER_KEY) || '{}')
    return store.get(USER_KEY) || {}
  },

  /*
  删除user
   */
  removeUser () {
    store.remove(USER_KEY)
  },

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

   /*
  保存token
   */
  saveToken(str){
    store.set(TOKEN_KEY, str)
  },
  
  /*
  获取token
   */
  getToken(){
   return store.get(TOKEN_KEY)||''
  },

  /*
  获取token
   */
  removeToken(){
    store.remove(TOKEN_KEY)
  }
}