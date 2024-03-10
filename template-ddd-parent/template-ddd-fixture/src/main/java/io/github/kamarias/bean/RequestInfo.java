package io.github.kamarias.bean;

import io.github.kamarias.enums.RequestAdapterEnum;

import java.io.Serializable;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2024/1/20 16:19
 */
public class RequestInfo implements Serializable {


    /**
     * 服务名称
     * RPC serviceName = 类名
     * HTTP serviceName = 微服务名称
     */
    private String  serviceName;


    /**
     * 请求方法
     * RPC method = 方法名
     * HTTP method = 对应请求路径
     */
    private String method;


    /**
     * 请求类型
     */
    private RequestAdapterEnum adapter;


    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public RequestAdapterEnum getAdapter() {
        return adapter;
    }

    public void setAdapter(RequestAdapterEnum adapter) {
        this.adapter = adapter;
    }
}
