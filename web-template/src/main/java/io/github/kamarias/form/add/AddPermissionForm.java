package io.github.kamarias.form.add;

import com.alibaba.fastjson2.JSON;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/31 17:26
 */
public class AddPermissionForm implements Serializable {


    private static final long serialVersionUID = 1L;


    /**
     * 权限名称
     */
    @NotBlank(message = "权限名称不能为空")
    private String name;

    /**
     * 权限编码
     */
    @NotBlank(message = "权限编码不能为空")
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
