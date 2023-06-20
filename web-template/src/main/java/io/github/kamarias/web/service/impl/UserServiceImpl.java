package io.github.kamarias.web.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.kamarias.entity.UserEntity;
import io.github.kamarias.enums.BoolFlagEnum;
import io.github.kamarias.exception.CustomException;
import io.github.kamarias.form.add.AddUserForm;
import io.github.kamarias.form.update.UpdateUserForm;
import io.github.kamarias.mapper.UserMapper;
import io.github.kamarias.mapstruct.UserMapStruct;
import io.github.kamarias.query.base.IdForm;
import io.github.kamarias.query.system.QueryUserForm;
import io.github.kamarias.utils.PageUtil;
import io.github.kamarias.utils.SecurityContextUtils;
import io.github.kamarias.utils.string.StringUtils;
import io.github.kamarias.uuid.LoginObject;
import io.github.kamarias.vo.PageVO;
import io.github.kamarias.vo.RoleOptionsVO;
import io.github.kamarias.web.service.RoleService;
import io.github.kamarias.web.service.UserRoleService;
import io.github.kamarias.web.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/29 10:10
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {


    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRoleService userRoleService;

    private final UserMapStruct userMapStruct;

    private final RoleService roleService;


    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, UserRoleService userRoleService, UserMapStruct userMapStruct, RoleService roleService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRoleService = userRoleService;
        this.userMapStruct = userMapStruct;
        this.roleService = roleService;
    }

    @Override
    public PageVO<UserEntity> getList(QueryUserForm form) {
        LambdaQueryWrapper<UserEntity> query = Wrappers.lambdaQuery(UserEntity.class)
                .eq(StringUtils.isNotEmpty(form.getAccount()), UserEntity::getAccount, form.getAccount())
                .eq(StringUtils.isNotEmpty(form.getEmail()), UserEntity::getEmail, form.getEmail())
                .eq(StringUtils.isNotEmpty(form.getPhone()), UserEntity::getPhone, form.getPhone())
                .like(StringUtils.isNotEmpty(form.getNickName()), UserEntity::getNickName, form.getNickName())
                .eq(Objects.nonNull(form.getStatus()), UserEntity::getStatus, form.getStatus());
        return PageUtil.handlerResult(this.page(new Page<>(form.getPageNum(), form.getPageSize()), query));
    }

    @Override
    public boolean addUser(AddUserForm form) {
        if (phoneExists(form.getPhone())){
            throw new CustomException("电话号码已存在");
        }
        if (emailExists(form.getEmail())){
            throw new CustomException("邮箱号码已存在");
        }
        if (accountExists(form.getAccount())){
            throw new CustomException("账号已存在");
        }
        UserEntity userEntity = userMapStruct.toUserEntityByAddUserForm(form);
        userEntity.setPassWord(encryptPassword(userEntity.getPassWord()));
        boolean save = this.save(userEntity);
        if (CollectionUtils.isNotEmpty(form.getRoleIds())) {
            return userRoleService.saveUserRoleByRoleIds(userEntity.getId(), form.getRoleIds());
        }
        return save;
    }

    @Override
    public boolean updateUser(UpdateUserForm form) {
        UserEntity userEntity = this.getById(form.getId());
        if (phoneExists(form.getPhone()) && !form.getPhone().equals(userEntity.getPhone())) {
            throw new CustomException("电话号码已存在");
        }
        if (emailExists(form.getEmail()) && !form.getEmail().equals(userEntity.getEmail())) {
            throw new CustomException("邮箱号码已存在");
        }
        if (accountExists(form.getAccount()) && !form.getAccount().equals(userEntity.getAccount())) {
            throw new CustomException("账号已存在");
        }
        userMapStruct.updateRoleEntityByUpdateUserForm(form, userEntity);
        userEntity.setPassWord(userEntity.getPassWord());
        userRoleService.removeUserRoleByUserId(userEntity.getId());
        boolean update = this.updateById(userEntity);
        if (CollectionUtils.isNotEmpty(form.getRoleIds())) {
            return userRoleService.saveUserRoleByRoleIds(userEntity.getId(), form.getRoleIds());
        }
        return update;
    }

    @Override
    public boolean updateStatus(IdForm form) {
        UserEntity userEntity = this.getById(form.getId());
        userEntity.setStatus(BoolFlagEnum.YES.getCode().equals(userEntity.getStatus()) ? BoolFlagEnum.NOT.getCode() : BoolFlagEnum.YES.getCode());
        return this.updateById(userEntity);
    }

    @Override
    public boolean deleteUser(IdForm form) {
        userRoleService.removeUserRoleByUserId(form.getId());
        return this.removeById(form.getId());
    }

    @Override
    public UpdateUserForm getUserInfoById(IdForm form) {
        UserEntity userEntity = this.getById(form.getId());
        return userMapStruct.toUpdateUserFormByUserEntity(userEntity, userRoleService.getRoleIdsByUserId(form.getId()));
    }

    @Override
    public UserEntity selectUserByPhone(String phone) {
        return this.getOne(Wrappers.lambdaQuery(UserEntity.class).eq(UserEntity::getPhone, phone));
    }

    @Override
    public UserEntity selectUserByAccount(String userName) {
        return this.getOne(Wrappers.lambdaQuery(UserEntity.class).eq(UserEntity::getAccount, userName));
    }

    @Override
    public UserEntity selectUserByEmail(String email) {
        return this.getOne(Wrappers.lambdaQuery(UserEntity.class).eq(UserEntity::getEmail, email));
    }

    @Override
    public boolean emailExists(String email) {
        return this.baseMapper.exists(Wrappers.lambdaQuery(UserEntity.class).eq(UserEntity::getEmail, email));
    }

    @Override
    public boolean phoneExists(String phone) {
        return this.baseMapper.exists(Wrappers.lambdaQuery(UserEntity.class).eq(UserEntity::getPhone, phone));
    }

    @Override
    public boolean accountExists(String userName) {
        return this.baseMapper.exists(Wrappers.lambdaQuery(UserEntity.class).eq(UserEntity::getAccount, userName));
    }

    @Override
    public String encryptPassword(String passWord) {
        return bCryptPasswordEncoder.encode(passWord);
    }

    @Override
    public boolean matchesPassword(String loginPassWord, String databasePassWord) {
        return bCryptPasswordEncoder.matches(loginPassWord, databasePassWord);
    }

    @Override
    public boolean resetPassword(String userId, String passWord) {
        UserEntity userEntity = this.getById(userId);
        userEntity.setPassWord(encryptPassword(passWord));
        return this.updateById(userEntity);
    }


    @Override
    public <T extends LoginObject> T getLoginUser() {
        return SecurityContextUtils.getLoginUser();
    }


    @Override
    public List<RoleOptionsVO> getLoginUserRole() {
        return roleService.getRoleOptionsByUserId(getLoginUser().getId());
    }
}
