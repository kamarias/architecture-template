package io.github.kamarias.entity;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.kamarias.entity.base.BaseOperateEntity;

import java.io.Serializable;

/**
 * 角色信息表
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/29 11:26
 */
@TableName("t_role")
public class RoleEntity extends BaseOperateEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 启用状态(1-启用，0-停用)
     */
    private Integer status;

    /**
     * 角色描述
     */
    private String description;


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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
