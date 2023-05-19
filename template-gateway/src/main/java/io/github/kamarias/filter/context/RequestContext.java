package io.github.kamarias.filter.context;

import com.alibaba.fastjson.JSON;
import io.github.kamarias.model.AppHeaderDTO;

/**
 * 请求上下文
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/11 14:37
 */
public class RequestContext {

    /**
     * 方法名
     */
    private String method;

    /**
     * 请求方法
     */
    private String httpMethod;

    /**
     * 签名值
     */
    private String sign;

    /**
     * timestamp .
     */
    private String timestamp;

    /**
     * appKey
     */
    private String appKey;

    /**
     * 请求路径
     */
    private String path;

    /**
     * 上下文路径
     */
    private String contextPath;

    /**
     * 真实请求地址
     */
    private String realUrl;

    /**
     * 移动客户端请求参数
     */
    private AppHeaderDTO appHeaderDTO;

    /**
     * 请求流水号
     */
    private String nonce;

    /**
     * 用户粒度AES key
     */
    private String aesKey;

    /**
     * 用户agent，判断用户所在的环境
     */
    private String userAgent;


    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getRealUrl() {
        return realUrl;
    }

    public void setRealUrl(String realUrl) {
        this.realUrl = realUrl;
    }

    public AppHeaderDTO getAppHeaderDTO() {
        return appHeaderDTO;
    }

    public void setAppHeaderDTO(AppHeaderDTO appHeaderDTO) {
        this.appHeaderDTO = appHeaderDTO;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
