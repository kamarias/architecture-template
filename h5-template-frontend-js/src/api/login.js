import request from "@/utils/request.js";


/**
 * 登录方法
 */
export function login(data){
    return request({
        url: '/template/list',
        method: 'post',
        data
    })
}
