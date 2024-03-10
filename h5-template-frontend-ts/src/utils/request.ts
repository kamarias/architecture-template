import axios, {AxiosRequestHeaders, CustomParamsSerializer} from 'axios'
import {getBaseApi, isCypto} from "@/utils/envUtil";
import {stringify} from "qs";
import {Notify} from "vant";
import {getToken} from "@/utils/cookieUtil";
import {decrypt, encrypt} from "@/utils/aesUtil";

/**
 * 创建http请求实例
 */
const request = axios.create({
  baseURL: getBaseApi,
  timeout: 50000,
  paramsSerializer: {
    serialize: stringify as unknown as CustomParamsSerializer
  }
})

/**
 * 请求拦截器
 */
request.interceptors.request.use(config => {
  // @ts-ignore
  const headers = {...config.headers, ...{"authorization": getToken() || "abc"}} as AxiosRequestHeaders
  // 开启加解密加密
  if (isCypto) {
    const data = {
      init: 'offsetKey',
      data: encrypt(JSON.stringify(config.data), 'offsetKeyabcdfgf')
    }
    config.data = data
  }
  config.headers = headers
  return {...config}
}, (error) => {
  return errorHandler(error)
})

/**
 * 响应拦截器
 */
request.interceptors.response.use(res => {
  let requestData = res.data
  if (isCypto) {
    const decryptData = JSON.parse(decrypt(requestData.data, requestData.init + 'abcdfgf'))
    console.log("解密后的数据：", decryptData)
    requestData = decryptData
  }
  const {code, msg} = requestData
  if (code === 401) {
    return errorHandler(new Error('请求未授权'))
  }
  if (code !== 200) {
    return errorHandler(new Error(msg))
  }
  return Promise.resolve(requestData)
}, (error) => {
  return errorHandler(error)
})

/**
 * 请求错误处理方法
 * @param error 错误信息
 */
const errorHandler = (error: Error): Promise<any> => {
  let {message} = error;
  if (message == "Network Error") {
    message = "网络异常，请稍后再试";
  }
  if (message.includes("timeout")) {
    message = "接口请求超时";
  }
  if (message.includes("Request failed with status code")) {
    message = "请求接口" + message.substr(message.length - 3) + "异常";
  }
  Notify({
    type: 'danger',
    message: message
  })
  return Promise.reject(error)
}

export default request
