/*
能发送异步ajax请求的函数模块
封装axios库
函数的返回值是promise对象
1. 优化1: 统一处理请求异常?
    在外层包一个自己创建的promise对象
    在请求出错时, 不reject(error), 而是显示错误提示
2. 优化2: 异步得到不是reponse, 而是response.data
   在请求成功resolve时: resolve(response.data)
 */

import axios from 'axios'
import {message} from 'antd'
import storageUtils from '../utils/storageUtils.js'



const service = axios.create({
  baseURL: process.env.REACT_APP_BASE_API,//默认添加baseurl
  // timeout: 5000 
})
service.interceptors.request.use(config => {
  let Token = storageUtils.getToken();
  config.headers['Token'] = Token;
  return config
})


export default function ajax(url, data={}, type='GET') {

  return new Promise((resolve, reject) => {
    let promise
    // 1. 执行异步ajax请求
    if(type==='GET') { // 发GET请求
      promise = service.get(url, { // 配置对象
        params: data // 指定请求参数
      })
    } else if(type === 'POST') { // 发POST请求
      promise = service.post(url, data)
    }else if(type === 'DELETE'){ //发DELETE请求
      promise = service.delete(url, data)
    }
    // 2. 如果成功了, 调用resolve(value)
    promise.then(response => {
      if([-100,-101].includes(response.data.code)){
        storageUtils.removeUser();//清空用户名
        storageUtils.removeToken();//清空token
        window.location.reload(true);//刷新页面
      }
      resolve(response.data)
    // 3. 如果失败了, 不调用reject(reason), 而是提示异常信息
    }).catch(error => {
      // reject(error)
      message.error('请求出错了: ' + error.message)
    })
  })

}

