import axios from 'axios'
import { ElMessage } from 'element-plus'

// 设置 axios 默认配置
axios.defaults.timeout = 10000
axios.defaults.baseURL = '/api' // 修正了 baseURL 的格式

// 设置请求转换函数，确保请求数据被正确转换为 JSON 格式
axios.defaults.transformRequest = [(data) => {
  return JSON.stringify(data)
}]

// 请求拦截器
axios.interceptors.request.use(
  (config) => {
    // 设置请求头的 Content-Type
    config.headers['Content-Type'] = 'application/json;charset=UTF-8'

    // 从 localStorage 中获取 token 并添加到请求头中
    try {
      const token = localStorage.getItem('token')
      if (token) {
        config.headers['token'] = token
      }
    } catch (error) {
      console.log('Error retrieving token:', error)
    }

    return config
  },
  (error) => {
    // 处理请求错误
    console.log('Request error:', error)
    return Promise.reject(error)
  }
)

// 响应拦截器
axios.interceptors.response.use(
  (response) => {
    // 如果响应成功且数据存在，直接返回数据
    return response.data || response
  },
  (error) => {
    // 处理响应错误
    let errorMessage = '请求失败，请稍后再试'

    if (error.response) {
      // 服务器返回的错误状态码
      switch (error.response.status) {
        case 400:
          errorMessage = '错误请求'
          break
        case 401:
          errorMessage = '未授权，请重新登录'
          // 清除本地存储的 token 并跳转到登录页面
          localStorage.removeItem('token')
          window.location.href = '/page/login'
          break
        case 403:
          errorMessage = '拒绝访问'
          break
        case 404:
          errorMessage = '请求资源未找到'
          break
        case 500:
          errorMessage = '服务器内部错误'
          break
        default:
          errorMessage = error.response.data.message || error.response.statusText
      }
    } else if (error.request) {
      // 请求已发出但没有收到响应
      errorMessage = '请求超时或网络错误'
    } else {
      // 其他错误
      errorMessage = error.message
    }

    // 显示错误消息
    ElMessage({
      showClose: true,
      message: errorMessage,
      type: 'error',
    })

    // 返回 Promise.reject，将错误传递给调用者
    return Promise.reject(error)
  }
)

export default axios
