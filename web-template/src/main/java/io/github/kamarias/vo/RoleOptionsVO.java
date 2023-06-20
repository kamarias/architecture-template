package io.github.kamarias.vo;

import com.alibaba.fastjson2.JSON;

import java.io.Serializable;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/6/8 20:31
 */
public class RoleOptionsVO implements Serializable {


    /**
     * 角色Id
     */
    private String id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 角色描述
     */
    private String description;

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

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
