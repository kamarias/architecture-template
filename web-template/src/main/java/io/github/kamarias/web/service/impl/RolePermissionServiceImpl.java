package io.github.kamarias.web.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.kamarias.entity.RolePermissionEntity;
import io.github.kamarias.mapper.RolePermissionMapper;
import io.github.kamarias.web.service.RolePermissionService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/6/1 9:46
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermissionEntity> implements RolePermissionService {


    @Override
    public boolean existRefByPermissionId(String permissionId) {
        LambdaQueryWrapper<RolePermissionEntity> wrapper = Wrappers.lambdaQuery(RolePermissionEntity.class).eq(RolePermissionEntity::getPermissionId, permissionId);
        return this.baseMapper.exists(wrapper);
    }


    @Override
    public boolean deleteRolePermissionByRoleId(String roleId) {
        LambdaQueryWrapper<RolePermissionEntity> queryWrapper = Wrappers.lambdaQuery(RolePermissionEntity.class).eq(RolePermissionEntity::getRoleId, roleId);
        return this.remove(queryWrapper);
    }

    @Override
    public boolean saveBatchByRoleId(String roleId, List<String> permissions) {
        if (CollectionUtils.isEmpty(permissions)){
            return true;
        }
        List<RolePermissionEntity> entityList = permissions.stream().parallel().map(x -> {
            RolePermissionEntity entity = new RolePermissionEntity();
            entity.setRoleId(roleId);
            entity.setPermissionId(x);
            return entity;
        }).collect(Collectors.toList());
        return this.saveBatch(entityList);
    }

    @Override
    public List<String> getPermissionsByRoleId(String roleId) {
        LambdaQueryWrapper<RolePermissionEntity> wrapper = Wrappers.lambdaQuery(RolePermissionEntity.class).eq(RolePermissionEntity::getRoleId, roleId);
        return this.list(wrapper).stream().parallel().map(x -> x.getPermissionId()).collect(Collectors.toList());
    }


    @Override
    public Set<String> getPermissionCodeByRoleId(String roleId) {
        return this.baseMapper.getPermissionCodeByRoleId(roleId);
    }
}
