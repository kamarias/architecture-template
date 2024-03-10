package io.github.kamarias.context;

import com.alibaba.fastjson2.JSON;
import io.github.kamarias.bean.AppInfo;
import io.github.kamarias.enums.RequestAdapterEnum;
import io.github.kamarias.params.ParamMap;
import io.github.kamarias.vo.Request;
import io.github.kamarias.vo.Response;
import reactor.core.publisher.Mono;

/**
 * 处理上下文对象
 *
 * @author wangyuxing@gogpay.cn
 * @date 2024/1/16 23:07
 */
public class HandlerContext {

    /**
     * 入参信息
     */
    private ParamMap inData;

    /**
     * 请求入参
     */
    private Request request = new Request();

    /**
     * 响应对象
     */
    private Mono<Response> response = Mono.empty();


    /**
     * 私有通讯区
     */
    private ParamMap publicParam;

    /**
     * 公共通讯区
     */
    private ParamMap privateParam;

    /**
     * 服务应用
     */
    private String appName;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 服务类型
     */
    private RequestAdapterEnum requestType;

    /**
     * 服务开始时间
     */
    private long starTime;

    public ParamMap getInData() {
        return inData;
    }

    public void setInData(ParamMap inData) {
        this.inData = inData;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Mono<Response> getResponse() {
        return response;
    }

    public void setResponse(Mono<Response> response) {
        this.response = response;
    }

    public long getStarTime() {
        return starTime;
    }

    public void setStarTime(long starTime) {
        this.starTime = starTime;
    }


    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public RequestAdapterEnum getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestAdapterEnum requestType) {
        this.requestType = requestType;
    }

    public ParamMap getPublicParam() {
        return publicParam;
    }

    public void setPublicParam(ParamMap publicParam) {
        this.publicParam = publicParam;
    }

    public ParamMap getPrivateParam() {
        return privateParam;
    }

    public void setPrivateParam(ParamMap privateParam) {
        this.privateParam = privateParam;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
