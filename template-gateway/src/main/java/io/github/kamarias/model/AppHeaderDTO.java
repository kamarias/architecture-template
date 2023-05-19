package io.github.kamarias.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 应用请求头实体
 *
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/11 15:09
 */
public class AppHeaderDTO implements Serializable {

    /**
     * 手机号
     */
    private String phoneNum;

    /**
     * 所属应用
     * 01：Android
     * 02：IOS
     * 03：h5
     */
    private String clientId;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 网络类型
     */
    private String netType;

    /**
     * 网络运营商
     */
    private String carrier;

    /**
     * 操作系统版本
     */
    private String osVersion;

    /**
     * 手机型号名
     */
    private String deviceName;

    /**
     * 请求流水号
     */
    private String nonce;

    /**
     * 请求头中的签名
     */
    private String sign;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
