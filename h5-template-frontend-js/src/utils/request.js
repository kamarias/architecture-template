import axios from 'axios'
import {getBaseApi, isCypto, isDevelopment} from "@/utils/envUtil.js";
import {getToken} from "@/utils/cookieUtil.js";
import {decrypt, encrypt} from "@/utils/aesUtil.js";
import {Notify} from "vant";

/**
 * 创建http请求实例
 */
const request = axios.create({
    // axios中请求配置有baseURL选项，表示请求URL公共部分
    baseURL: getBaseApi,
    // 超时
    timeout: 50000,
})

/**
 * 请求错误处理方法
 * @param error 错误信息
 */
const errorHandler = (error) => {
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

/**
 * 请求拦截器
 */
request.interceptors.request.use(config => {
    const token = getToken()
    const headers = {
        ...config.headers,
        // ...{authorization: getToken() || 'abc'}
    }
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
}, errorHandler)

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
}, errorHandler)


export default request
