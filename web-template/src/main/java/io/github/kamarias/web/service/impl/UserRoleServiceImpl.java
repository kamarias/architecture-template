package io.github.kamarias.web.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.kamarias.entity.UserRoleEntity;
import io.github.kamarias.mapper.UserRoleMapper;
import io.github.kamarias.web.service.UserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/6/5 11:25
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRoleEntity> implements UserRoleService {


    @Override
    public boolean existRefByRoleId(String roleId) {
        LambdaQueryWrapper<UserRoleEntity> wrapper = Wrappers.lambdaQuery(UserRoleEntity.class).eq(UserRoleEntity::getRoleId, roleId);
        return this.baseMapper.exists(wrapper);
    }


    @Override
    public boolean saveUserRoleByRoleIds(String userId, List<String> roleIds) {
        return this.saveBatch(roleIds.stream().parallel().map(x->{
            UserRoleEntity item = new UserRoleEntity();
            item.setUserId(userId);
            item.setRoleId(x);
            return item;
        }).collect(Collectors.toList()));
    }

    @Override
    public boolean removeUserRoleByUserId(String userId) {
        return this.remove(Wrappers.lambdaQuery(UserRoleEntity.class).eq(UserRoleEntity::getUserId, userId));
    }

    @Override
    public List<String> getRoleCodeByUserId(String userId) {
        return this.baseMapper.getRoleCodeByUserId(userId);
    }

    @Override
    public List<String> getRoleIdsByUserId(String userId) {
        return this.baseMapper.getRoleIdsByUserId(userId);
    }


    @Override
    public UserRoleEntity getOneUserRoleByUserId(String userId) {
        return this.getOne(Wrappers.lambdaQuery(UserRoleEntity.class).eq(UserRoleEntity::getUserId, userId), false);
    }
}
