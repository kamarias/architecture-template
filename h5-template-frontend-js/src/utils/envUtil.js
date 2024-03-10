/**
 * 开发环境判断
 */
export const isDevelopment = import.meta.env.VITE_ENV === 'development'

/**
 * 生产环境判断
 */
export const isProduction = import.meta.env.VITE_ENV === 'production'

/**
 * 测试环境判断
 */
export const isTest = import.meta.env.VITE_ENV === 'test'

/**
 * 模式
 */
export const envMODE = import.meta.env.MODE

/**
 * 获取Api请求基地址
 */
export const getBaseApi = import.meta.env.VITE_BASE_API


/**
 * 获取是否加解密
 */
export const isCypto = import.meta.env.VITE_CYPTO === 'true'
