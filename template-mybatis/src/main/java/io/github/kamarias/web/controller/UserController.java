package io.github.kamarias.web.controller;

import io.github.kamarias.web.annotation.WebLog;
import io.github.kamarias.dto.AjaxResult;
import io.github.kamarias.entity.UserEntity;
import io.github.kamarias.query.QueryUserForm;
import io.github.kamarias.vo.PageVO;
import io.github.kamarias.web.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/19 11:24
 */
@RestController
public class UserController {


    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    @WebLog("查询用户信息")
    @PostMapping("/list")
    public AjaxResult<PageVO<UserEntity>> getUser(@RequestBody @Validated QueryUserForm form) {
        return AjaxResult.success(userService.queryUserList(form));
    }


}
