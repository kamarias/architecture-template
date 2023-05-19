package io.github.kamarias.app.controller;

import io.github.kamarias.annotation.WebLog;
import io.github.kamarias.app.service.AppUserService;
import io.github.kamarias.dto.AjaxResult;
import io.github.kamarias.entity.UserEntity;
import io.github.kamarias.query.QueryUserForm;
import io.github.kamarias.vo.PageVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/19 11:24
 */
@RestController("/api")
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @WebLog("查询用户信息")
    @PostMapping("/list")
    public AjaxResult<PageVO<UserEntity>> getUser(@RequestBody @Validated QueryUserForm form) {
        return AjaxResult.success(appUserService.queryUserList(form));
    }

}
