/**
 * 工具类
 */
import CryptoJS from 'crypto-js';

/**
 * 加解密模式配置
 */
const encDecMode = {
  mode: CryptoJS.mode.ECB,
  padding: CryptoJS.pad.Pkcs7,
} as unknown as BufferSource

/**
 * 加密
 * @param content 内容
 * @param key 加密key
 */
export function encrypt(content: string, key: string): string {
  const contentUtf8 = CryptoJS.enc.Utf8.parse(content);
  const keyUtf8 = CryptoJS.enc.Utf8.parse(key)
  return CryptoJS.AES.encrypt(contentUtf8, keyUtf8, encDecMode).toString();
}

/**
 * 解密
 * @param cipherText 密文
 * @param key 解密key
 */
export function decrypt(cipherText: string, key: string): string {
  const keyUtf8 = CryptoJS.enc.Utf8.parse(key)
  return CryptoJS.enc.Utf8.stringify(CryptoJS.AES.decrypt(cipherText, keyUtf8, encDecMode)).toString();
}
