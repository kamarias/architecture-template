package io.github.kamarias.utils;


import io.github.kamarias.dto.ResultDTO;

/**
 * 响应工具类
 *
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/15 9:52
 */
public class ResultDtoUtils {

    private ResultDtoUtils() {
    }

    /**
     * 请求未授权响应值
     *
     * @return 返回结果
     */
    public static ResultDTO unauthorized() {
        return new ResultDTO<>(401, "请求未授权");
    }


    /**
     * 网关上下文过滤器异常
     *
     * @return 返回结果
     */
    public static ResultDTO requestContext() {
        return new ResultDTO<>(500, "网关上下文过滤器异常");
    }

}
