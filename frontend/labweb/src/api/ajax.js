import axios from 'axios'

const service = axios.create({
  baseURL: process.env.REACT_APP_BASE_API,//默认添加baseurl
})

export default function ajax(url, data={}, type='GET') {

  return new Promise((resolve, reject) => {
    let promise ;
    // 1. 执行异步ajax请求
    if(type==='GET') { // 发GET请求
      promise = service.get(url, { // 配置对象
        params: data // 指定请求参数
      })
    } else{ // 发POST请求
      promise = service.post(url, data)
    }
    // 2. 如果成功了, 调用resolve(value)
    promise.then(response => {
      resolve(response.data)
    // 3. 如果失败了, 不调用reject(reason), 而是提示异常信息
    }).catch(error => {
      // reject(error)
      console.log('请求出错了: ' + error.message)
    })
  })


}

