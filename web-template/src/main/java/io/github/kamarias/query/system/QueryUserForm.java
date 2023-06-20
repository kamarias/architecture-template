package io.github.kamarias.query.system;

import com.alibaba.fastjson2.JSON;
import io.github.kamarias.form.PageForm;


import java.io.Serializable;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/6/19 10:19
 */
public class QueryUserForm extends PageForm implements Serializable {

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户账号
     */
    private String account;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 用户状态
     */
    private Integer status;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
