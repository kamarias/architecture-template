/**
 * 工具类
 */
import CryptoJS from 'crypto-js';

/**
 * 加密
 * @param content
 * @param key
 */
export function encrypt(content, key) {
    const contentUtf8 = CryptoJS.enc.Utf8.parse(content)
    const keyUtf8 = CryptoJS.enc.Utf8.parse(key)
    const cipherText = CryptoJS.AES.encrypt(contentUtf8, keyUtf8, {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7,
    }).toString();
    return cipherText;
}

/**
 * 解密
 * @param content
 * @param key
 */
export function decrypt(cipherText, key) {
    const keyUtf8 = CryptoJS.enc.Utf8.parse(key)
    const content = CryptoJS.enc.Utf8.stringify(CryptoJS.AES.decrypt(cipherText, keyUtf8, {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7,
    })).toString();
    return content;
}
