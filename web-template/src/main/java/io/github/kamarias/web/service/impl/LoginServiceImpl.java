package io.github.kamarias.web.service.impl;


import io.github.kamarias.entity.RoleEntity;
import io.github.kamarias.entity.UserEntity;
import io.github.kamarias.entity.UserRoleEntity;
import io.github.kamarias.enums.BoolFlagEnum;
import io.github.kamarias.exception.CustomException;
import io.github.kamarias.form.LoginForm;
import io.github.kamarias.mapstruct.UserMapStruct;
import io.github.kamarias.query.base.IdForm;
import io.github.kamarias.utils.SecurityContextUtils;
import io.github.kamarias.utils.TokenUtils;
import io.github.kamarias.utils.regular.RegularUtils;
import io.github.kamarias.utils.string.StringUtils;
import io.github.kamarias.uuid.LoginObject;
import io.github.kamarias.vo.LoginUser;
import io.github.kamarias.web.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/29 10:19
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class LoginServiceImpl implements LoginService {


    private final TokenUtils tokenUtils;

    private final UserService userService;

    private final UserMapStruct userMapStruct;

    private final UserRoleService userRoleService;

    private final RoleService roleService;

    private final RolePermissionService rolePermissionService;

    public LoginServiceImpl(TokenUtils tokenUtils, UserService userService, UserMapStruct userMapStruct, UserRoleService userRoleService, RoleService roleService, RolePermissionService rolePermissionService) {
        this.tokenUtils = tokenUtils;
        this.userService = userService;
        this.userMapStruct = userMapStruct;
        this.userRoleService = userRoleService;
        this.roleService = roleService;
        this.rolePermissionService = rolePermissionService;
    }


    @Override
    public String login(LoginForm form) {
        UserEntity user = getUserEntity(form.getUsername());
        if (!BoolFlagEnum.YES.getCode().equals(user.getStatus())) {
            throw new CustomException("该账号已停用，请联系管理员");
        }
        if (!userService.matchesPassword(form.getPassword(), user.getPassWord())) {
            throw new CustomException("用户密码错误，请重试");
        }
        return generateToken(getLoginUserInfoByUserEntity(user));
    }

    @Override
    public String switchRole(IdForm form) {
        UserEntity user = getUserEntity();
        if (!BoolFlagEnum.YES.getCode().equals(user.getStatus())) {
            throw new CustomException("该账号已停用，请联系管理员");
        }
        String token = generateToken(getLoginUserInfoByUserEntity(user, form.getId()));
        // 移除之前的登录
        removeToken();
        return token;
    }

    @Override
    public <T extends LoginObject> String generateToken(T t) {
        return tokenUtils.createToken(t);
    }

    @Override
    public boolean removeToken() {
        return tokenUtils.deleteToken();
    }

    /**
     * 通过登录用户实体获取登录用户信息
     *
     * @param user 用户信息
     * @return 返回结果
     */
    private LoginUser getLoginUserInfoByUserEntity(UserEntity user) {
        LoginUser loginUser = userMapStruct.toLoginUserByUserEntity(user);
        // 随机选取一个角色登录
        UserRoleEntity userRoleEntity = userRoleService.getOneUserRoleByUserId(user.getId());
        if (Objects.nonNull(userRoleEntity)) {
            // 如果在代码中使用 @RequiresRoles 或 @RequiresPermissions 注解，那么下面的角色和权限的设置是必要的
            String roleCode = roleService.getRoleCodeByRoleId(userRoleEntity.getRoleId());
            loginUser.setRoles(StringUtils.isEmpty(roleCode) ? new HashSet<>() : new HashSet<>(Collections.singletonList(roleCode)));
            Set<String> permissions = rolePermissionService.getPermissionCodeByRoleId(userRoleEntity.getRoleId());
            loginUser.setPermissions(CollectionUtils.isEmpty(permissions) ? new HashSet<>() : permissions);
        }
        return loginUser;
    }

    /**
     * 通过登录用户实体获取登录用户信息
     * @param roleId 需要登录的角色Id
     * @param user 用户信息
     * @return 返回结果
     */
    private LoginUser getLoginUserInfoByUserEntity(UserEntity user, String roleId) {
        LoginUser loginUser = userMapStruct.toLoginUserByUserEntity(user);
        RoleEntity userRoleEntity = roleService.getById(roleId);
        if (Objects.nonNull(userRoleEntity)) {
            // 如果在代码中使用 @RequiresRoles 或 @RequiresPermissions 注解，那么下面的角色和权限的设置是必要的
            loginUser.setRoles(new HashSet<>(Collections.singletonList(userRoleEntity.getCode())));
            Set<String> permissions = rolePermissionService.getPermissionCodeByRoleId(userRoleEntity.getId());
            loginUser.setPermissions(CollectionUtils.isEmpty(permissions) ? new HashSet<>() : permissions);
        }
        return loginUser;
    }


    /**
     * 获取登录的用户信息
     *
     * @param account 账号名称
     * @return 返回用户信息，不存在抛出异常
     */
    private UserEntity getUserEntity(String account) {
        UserEntity user;
        if (RegularUtils.verifyPhoneNumber(account)) {
            user = userService.selectUserByPhone(account);
        } else if (RegularUtils.verifyMail(account)) {
            user = userService.selectUserByEmail(account);
        } else {
            user = userService.selectUserByAccount(account);
        }
        if (Objects.isNull(user)) {
            throw new CustomException("登录用户：" + account + "不存在");
        }
        return user;
    }

    private UserEntity getUserEntity() {
        UserEntity user = userService.getById(SecurityContextUtils.getLoginUser().getId());
        if (Objects.isNull(user)) {
            throw new CustomException("登录用户已被删除");
        }
        return user;
    }
}
