package io.github.kamarias.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.kamarias.entity.base.BaseOperateEntity;

/**
 * 测试用户实体
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/4 10:58
 */
@TableName("t_user")
public class UserEntity extends BaseOperateEntity {

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
