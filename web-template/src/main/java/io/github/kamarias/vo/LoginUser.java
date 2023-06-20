package io.github.kamarias.vo;

import com.alibaba.fastjson2.JSON;
import io.github.kamarias.uuid.LoginObject;


import java.io.Serializable;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/28 16:31
 */
public class LoginUser extends LoginObject implements Serializable {


    /**
     * 登录用户名
     */
    private String name;

    /**
     * 登录邮箱地址
     */
    private String email;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
