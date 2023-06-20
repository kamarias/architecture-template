package io.github.kamarias.form;

import com.alibaba.fastjson2.JSON;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 登陆表单
 * @author wangyuxing@gogpay.cn
 * @date 2023/6/16 15:26
 */
public class LoginForm implements Serializable {

    /**
     * 账号
     */
    @NotBlank(message = "账号不能为空")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 验证码
     */
    private String code;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
