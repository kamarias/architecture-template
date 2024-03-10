package io.github.kamarias.vo;

import com.alibaba.fastjson2.JSON;

import java.io.Serializable;

/**
 * 响应对象Map
 * @author wangyuxing@gogpay.cn
 * @date 2024/1/28 23:36
 */
public class Response implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }


}
