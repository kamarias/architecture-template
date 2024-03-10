import request from "@/utils/request";
import {PageResponse} from "@/common/responseType";





/**
 * 登录方法
 */
export function login(params?: object): Promise<PageResponse<any>> {
  return request({
    url: '/template/list',
    method: 'post',
    params
  })
}
