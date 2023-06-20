package io.github.kamarias.entity;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.kamarias.entity.base.BaseOperateEntity;

import java.io.Serializable;

/**
 * 路由信息表
 *
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/29 14:00
 */
@TableName("t_router")
public class RouterEntity extends BaseOperateEntity implements Serializable {


    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 重定向地址
     */
    private String redirect;

    /**
     * 组件名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 路由类型（0、目录，1、菜单）
     */
    private Integer type;

    /**
     * 是否路由启用状态（0、停用，1、启用）
     */
    private Integer state;

    /**
     * 父级路由Id
     */
    private String parentRouterId;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getRedirect() {
        return redirect;
    }

    public Integer getType() {
        return type;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentRouterId() {
        return parentRouterId;
    }

    public void setParentRouterId(String parentRouterId) {
        this.parentRouterId = parentRouterId;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
