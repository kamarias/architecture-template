package io.github.kamarias.form;

import com.alibaba.fastjson2.JSON;
import io.github.kamarias.query.base.IdForm;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/6/19 16:04
 */
public class ResetPassWordForm extends IdForm implements Serializable {


    /**
     * 密码
     */
    @NotBlank(message = "重置密码不能为空")
    private String passWord;


    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
