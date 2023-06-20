package io.github.kamarias.vo;

import com.alibaba.fastjson2.JSON;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * 角色信息
 * @author wangyuxing@gogpay.cn
 * @date 2023/6/5 11:33
 */
public class RoleVO implements Serializable {

    /**
     * Id
     */
    @NotBlank(message = "id不能为空")
    private String id;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    private String name;

    /**
     * 角色编码
     */
    @NotBlank(message = "角色编码不能为空")
    private String code;

    /**
     * 角色描述
     */
    private String description;


    /**
     * 权限集合
     */
    private List<String> permissions;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
