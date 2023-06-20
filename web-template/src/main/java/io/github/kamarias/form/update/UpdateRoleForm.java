package io.github.kamarias.form.update;

import com.alibaba.fastjson2.JSONObject;
import io.github.kamarias.form.add.AddRoleForm;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/6/5 10:30
 */
public class UpdateRoleForm extends AddRoleForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @NotBlank(message = "id不能为空")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }


}
