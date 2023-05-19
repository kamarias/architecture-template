package io.github.kamarias.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 网关通用响应实体
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/6 17:58
 */
public class BodyDTO implements Serializable {


    /**
     * 加密key
     */
    private String init;

    /**
     * 加密数据
     */
    private String data;

    public String getInit() {
        return init;
    }

    public void setInit(String init) {
        this.init = init;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
