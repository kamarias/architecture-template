import Cookies from 'js-cookie'


const TokenKey = 'Token'

/**
 * 获取token
 * @returns string
 */
export function getToken() {
    return Cookies.get(TokenKey)
}

/**
 * 设置token
 * @returns string
 */
export function setToken(token) {
    return Cookies.set(TokenKey, token)
}

/**
 * 移除token
 */
export function removeToken() {
    Cookies.remove(TokenKey)
}
