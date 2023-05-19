package io.github.kamarias.query.base;

import com.alibaba.fastjson.JSONObject;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/4 14:06
 */
public class IdForm implements Serializable {

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
