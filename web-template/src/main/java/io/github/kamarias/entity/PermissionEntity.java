package io.github.kamarias.entity;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.kamarias.entity.base.BaseOperateEntity;

import java.io.Serializable;

/**
 * 权限信息表
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/29 11:27
 */
@TableName("t_permission")
public class PermissionEntity extends BaseOperateEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限编码
     */
    private String code;

    /**
     * 权限描述
     */
    private String description;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
