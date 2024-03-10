package io.github.kamarias.params;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import io.github.kamarias.exception.ParamVerifyException;

import java.io.Serializable;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2024/1/15 23:32
 */
public class ParamMap extends HashMap<String, Object> implements Serializable {

    public static final String PUBLIC = "public";

    public static final String APP_NAME = "appName";

    public static final String SERVICE_NAME = "serviceName";

    public static final String SERVICE_TYPE = "serviceType";

    public static final String PRIVATE = "private";


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    /**
     * 入参合法性校验
     */
    final public void verify() {
        if (!this.containsKey(PUBLIC)) {
            throw new ParamVerifyException(9010, "公共通讯区不能为空");
        }
        final Map<String, Object> publicParam = JSON.parseObject(JSON.toJSONString(this.get(PUBLIC)), new TypeReference<Map<String, Object>>() {
        });
        if (!publicParam.containsKey(APP_NAME)) {
            throw new ParamVerifyException(9012, "应用名称不能为空");
        }
        if (!publicParam.containsKey(SERVICE_NAME)) {
            throw new ParamVerifyException(9013, "服务名称不能为空");
        }
        if (!publicParam.containsKey(SERVICE_TYPE)) {
            throw new ParamVerifyException(9014, "服务类型不能为空");
        }
        if (!this.containsKey(PRIVATE)) {
            throw new ParamVerifyException(9010, "私有通讯区不能为空");
        }
    }

}
