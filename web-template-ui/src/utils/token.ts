import Cookies from "js-cookie";

export const TOKEN_KEY = "authorized-token";

export const HEADER_PREFIX = "Bearer ";

/**
 * 设置token
 */
export function getToken(): string {
  return Cookies.get(TOKEN_KEY);
}

/**
 * 移除token
 */
export function removeToken() {
  Cookies.remove(TOKEN_KEY);
}

/**
 * 设置token
 * @param token
 */
export function setToken(token: string) {
  Cookies.set(TOKEN_KEY, token);
}

/**
 * 格式化token
 * @param token
 */
export function formatToken(token: string): string {
  return HEADER_PREFIX + token;
}
