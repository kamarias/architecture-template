package io.github.kamarias.web.service.impl;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.kamarias.entity.RouterRoleEntity;
import io.github.kamarias.mapper.RouterRoleMapper;
import io.github.kamarias.web.service.RouterRoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/6/9 12:49
 */
@Service
public class RouterRoleServiceImpl extends ServiceImpl<RouterRoleMapper, RouterRoleEntity> implements RouterRoleService {


    @Override
    public boolean removeRouterRoleByRouterId(String routerId) {
        return this.remove(Wrappers.lambdaQuery(RouterRoleEntity.class).eq(RouterRoleEntity::getRouterId, routerId));
    }


    @Override
    public boolean existRefByRoleId(String roleId) {
        LambdaQueryWrapper<RouterRoleEntity> wrapper = Wrappers.lambdaQuery(RouterRoleEntity.class).eq(RouterRoleEntity::getRoleId, roleId);
        return this.baseMapper.exists(wrapper);
    }

    @Override
    public boolean saveRouterRoleByRoleIds(String routerId, List<String> roles) {
        return this.saveBatch(roles.stream().parallel().map(x->{
            RouterRoleEntity item = new RouterRoleEntity();
            item.setRouterId(routerId);
            item.setRoleId(x);
            return item;
        }).collect(Collectors.toList()));
    }


    @Override
    public List<String> getRoleCodeByRouterId(String routerId) {
        return this.baseMapper.getRoleCodeByRouterId(routerId);
    }

    @Override
    public List<String> getRoleIdsByRouterId(String routerId) {
        return this.baseMapper.getRoleIdsByRouterId(routerId);
    }
}
