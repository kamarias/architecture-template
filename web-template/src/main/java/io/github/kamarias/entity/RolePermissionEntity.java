package io.github.kamarias.entity;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.kamarias.entity.base.BaseIdEntity;


import java.io.Serializable;

/**
 * 角色权限关系表
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/29 11:27
 */
@TableName("t_role_permission")
public class RolePermissionEntity extends BaseIdEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 角色Id
     */
    private String roleId;

    /**
     * 权限Id
     */
    private String permissionId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }


}
