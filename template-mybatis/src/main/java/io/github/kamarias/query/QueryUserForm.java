package io.github.kamarias.query;

import com.alibaba.fastjson.JSON;
import io.github.kamarias.form.PageForm;

import java.io.Serializable;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/4 13:57
 */
public class QueryUserForm extends PageForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    private String name;


    /**
     * 年龄
     */
    private Integer age;

    /**
     * 邮箱
     */
    private String email;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
