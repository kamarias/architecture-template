package io.github.kamarias.vo;

import com.alibaba.fastjson.JSON;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/19 11:15
 */
public class UserVO {


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
