package io.github.kamarias.enums;

import io.github.kamarias.exception.ParamVerifyException;
import io.github.kamarias.handler.HttpRequestAdapter;
import io.github.kamarias.handler.RpcRequestAdapter;

/**
 * 请求适配器枚举
 *
 * @author wangyuxing@gogpay.cn
 * @date 2024/1/20 16:30
 */
public enum RequestAdapterEnum {


    /**
     * RPC请求适配器
     */
    RPC("RPC", RpcRequestAdapter.class),


    /**
     * HTTP请求适配器
     */
    HTTP("HTTP", HttpRequestAdapter.class);

    /**
     * 适配器名称
     */
    private final String name;

    /**
     * 适配器类型
     */
    private final Class<?> classType;


    RequestAdapterEnum(String name, Class<?> classType) {
        this.name = name;
        this.classType = classType;
    }

    public String getName() {
        return name;
    }

    public Class<?> getClassType() {
        return classType;
    }

    public static RequestAdapterEnum getRequestEnumByName(String adapterType){
        for (RequestAdapterEnum value : RequestAdapterEnum.values()) {
            if (value.name.equals(adapterType.toUpperCase())) {
                return value;
            }
        }
        throw new ParamVerifyException(9097, "不支持的请求类型");
    }

}
