package io.github.kamarias.entity;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.kamarias.entity.base.BaseIdEntity;

import java.io.Serializable;

/**
 * 路由角色关系表
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/29 14:10
 */
@TableName("t_router_role")
public class RouterRoleEntity extends BaseIdEntity implements Serializable {

    /**
     * 路由Id
     */
    private String routerId;

    /**
     * 角色Id
     */
    private String roleId;


    public String getRouterId() {
        return routerId;
    }

    public void setRouterId(String routerId) {
        this.routerId = routerId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
