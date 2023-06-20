package io.github.kamarias.query.system;

import com.alibaba.fastjson2.JSON;
import io.github.kamarias.form.PageForm;


import java.io.Serializable;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/30 14:27
 */
public class QueryRoleForm extends PageForm implements Serializable {

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
     * 权限编码
     */
    private String permissionCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
