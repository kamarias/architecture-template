package io.github.kamarias.query.system;

import com.alibaba.fastjson2.JSON;
import io.github.kamarias.form.PageForm;


import java.io.Serializable;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/31 17:24
 */
public class QueryPermissionForm  extends PageForm implements Serializable {

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限编码
     */
    private String code;


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

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
