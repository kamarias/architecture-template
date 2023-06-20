package io.github.kamarias.web.controller;


import io.github.kamarias.dto.AjaxResult;
import io.github.kamarias.form.LoginForm;
import io.github.kamarias.query.base.IdForm;
import io.github.kamarias.utils.TokenUtils;
import io.github.kamarias.web.service.LoginService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/28 16:29
 */
@RestController
public class LoginController {


    private final TokenUtils tokenUtils;

    private final LoginService loginService;

    public LoginController(TokenUtils tokenUtils, LoginService loginService) {
        this.tokenUtils = tokenUtils;
        this.loginService = loginService;
    }

    /**
     * 登录接口
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody @Validated LoginForm form){
        return AjaxResult.success("success",loginService.login(form));
    }

    /**
     * 切换角色登录 切换角色
     */
    @PostMapping("/switchRole")
    public AjaxResult switchRole(@RequestBody @Validated IdForm form){
        return AjaxResult.success("success",loginService.switchRole(form));
    }

    /**
     * 退出登录
     */
    @GetMapping("/loginOut")
    public AjaxResult logout(){
        return AjaxResult.success(loginService.removeToken());
    }


}
