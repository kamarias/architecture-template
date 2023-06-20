package io.github.kamarias.entity;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.kamarias.entity.base.BaseIdEntity;

import java.io.Serializable;

/**
 * 用户角色关系表
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/29 11:27
 */
@TableName("t_user_role")
public class UserRoleEntity extends BaseIdEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 用户Id
     */
    private String userId;

    /**
     * 角色Id
     */
    private String roleId;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
