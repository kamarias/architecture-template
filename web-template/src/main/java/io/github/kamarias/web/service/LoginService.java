package io.github.kamarias.web.service;


import io.github.kamarias.form.LoginForm;
import io.github.kamarias.query.base.IdForm;
import io.github.kamarias.uuid.LoginObject;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/29 10:19
 */
public interface LoginService {

    /**
     * 登录方法
     * @param form 登录表单
     * @return 返回结果
     */
    String login(LoginForm form);

    /**
     * 切换角色登录
     * @param form id表单
     * @return 返回令牌
     */
    String switchRole(IdForm form);


    /**
     * 生成授权token方法
     * @param t 继承UuidObject的实体类
     * @return 返回生成的token密钥
     * @param <T> 继承UuidObject的实体类
     */
    <T extends LoginObject> String generateToken(T t);

    /**
     * 移除登录token
     */
    boolean removeToken();


}
