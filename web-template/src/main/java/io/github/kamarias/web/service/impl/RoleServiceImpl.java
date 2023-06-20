package io.github.kamarias.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.kamarias.entity.RoleEntity;
import io.github.kamarias.enums.BoolFlagEnum;
import io.github.kamarias.exception.CustomException;
import io.github.kamarias.form.add.AddRoleForm;
import io.github.kamarias.form.update.UpdateRoleForm;
import io.github.kamarias.mapper.RoleMapper;
import io.github.kamarias.mapstruct.RoleMapStruct;
import io.github.kamarias.query.base.IdForm;
import io.github.kamarias.query.system.QueryRoleForm;
import io.github.kamarias.utils.PageUtil;
import io.github.kamarias.utils.string.StringUtils;
import io.github.kamarias.vo.PageVO;
import io.github.kamarias.vo.RoleOptionsVO;
import io.github.kamarias.vo.RoleVO;
import io.github.kamarias.web.service.RolePermissionService;
import io.github.kamarias.web.service.RoleService;
import io.github.kamarias.web.service.RouterRoleService;
import io.github.kamarias.web.service.UserRoleService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/30 14:23
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleEntity> implements RoleService {

    private final RolePermissionService rolePermissionService;


    private final UserRoleService userRoleService;

    private final RouterRoleService routerRoleService;

    private final RoleMapStruct roleMapStruct;


    public RoleServiceImpl(RolePermissionService rolePermissionService, UserRoleService userRoleService, RouterRoleService routerRoleService, RoleMapStruct roleMapStruct) {
        this.rolePermissionService = rolePermissionService;
        this.userRoleService = userRoleService;
        this.routerRoleService = routerRoleService;
        this.roleMapStruct = roleMapStruct;
    }


    @Override
    public PageVO<RoleEntity> getList(QueryRoleForm form) {
        LambdaQueryWrapper<RoleEntity> query = Wrappers.lambdaQuery(RoleEntity.class);
        query.like(StringUtils.isNotBlank(form.getName()), RoleEntity::getName, form.getName())
                .eq(StringUtils.isNotBlank(form.getCode()), RoleEntity::getCode, form.getCode())
                .eq(Objects.nonNull(form.getStatus()), RoleEntity::getStatus, form.getStatus());

        if (StringUtils.isNotBlank(form.getPermissionCode())) {
            QueryWrapper<RoleEntity> wrapper = new QueryWrapper<>();
            wrapper.like(StringUtils.isNotBlank(form.getName()), "r.name", form.getName())
                    .eq(StringUtils.isNotBlank(form.getCode()), "r.code", form.getCode())
                    .eq(Objects.nonNull(form.getStatus()), "r.status", form.getStatus())
                    .eq(StringUtils.isNotBlank(form.getPermissionCode()), "p.code", form.getPermissionCode())
                    .eq("r.del_flag", BoolFlagEnum.NOT.getCode())
                    .eq("p.del_flag", BoolFlagEnum.NOT.getCode());
            return PageUtil.handlerResult(this.baseMapper.getList(new Page<>(form.getPageNum(), form.getPageSize()), wrapper));
        }
        return PageUtil.handlerResult(this.page(new Page<>(form.getPageNum(), form.getPageSize()), query));
    }

    @Override
    public boolean updateStatus(IdForm form) {
        RoleEntity entity = this.getById(form.getId());
        if (Objects.nonNull(entity)) {
            entity.setStatus(BoolFlagEnum.YES.getCode().equals(entity.getStatus()) ? BoolFlagEnum.NOT.getCode() : BoolFlagEnum.YES.getCode());
            return this.updateById(entity);
        }
        throw new CustomException("更新失败，Id异常");
    }

    @Override
    public boolean addRole(AddRoleForm form) {
        if (codeExists(form.getCode())) {
            throw new CustomException("角色编码已存在");
        }
        RoleEntity bean = roleMapStruct.toRoleEntityByAddRoleForm(form);
        if (CollectionUtils.isEmpty(form.getPermissions())) {
            return this.save(bean);
        }
        boolean save = this.save(bean);
        if (!save) {
            return false;
        }
        return rolePermissionService.saveBatchByRoleId(bean.getId(), form.getPermissions());
    }

    @Override
    public boolean updateRole(UpdateRoleForm form) {
        RoleEntity byId = this.getById(form.getId());
        if (codeExists(form.getCode()) && !form.getCode().equals(byId.getCode())) {
            throw new CustomException("角色编码已存在");
        }
        // 移除之前的关系
        rolePermissionService.deleteRolePermissionByRoleId(byId.getId());
        roleMapStruct.updateRoleEntityByUpdateRoleForm(form, byId);
        boolean save = this.updateById(byId);
        if (!save) {
            return false;
        }
        return rolePermissionService.saveBatchByRoleId(byId.getId(), form.getPermissions());
    }

    @Override
    public boolean deleteRole(IdForm form) {
        // 校验角色是否分配给用户
        if(userRoleService.existRefByRoleId(form.getId())){
            throw new CustomException("当前角色已分配给用户不可删除！");
        }
        // 校验角色是否分配给菜单
        if(routerRoleService.existRefByRoleId(form.getId())){
            throw new CustomException("当前角色已分配给菜单不可删除！");
        }
        rolePermissionService.deleteRolePermissionByRoleId(form.getId());
        return this.removeById(form.getId());
    }

    @Override
    public RoleVO detailById(IdForm form) {
        RoleEntity entity = this.getById(form.getId());
        RoleVO vo = roleMapStruct.toRoleVOByRoleEntity(entity);
        vo.setPermissions(rolePermissionService.getPermissionsByRoleId(entity.getId()));
        return vo;
    }

    @Override
    public List<RoleOptionsVO> getRoleOptions() {
        return this.baseMapper.getRoleOptions();
    }

    @Override
    public List<RoleOptionsVO> getRoleOptionsByUserId(String userId) {
        return this.baseMapper.getRoleOptionsByUserId(userId);
    }

    @Override
    public RoleEntity getRoleByCode(String roleCode) {
        return this.getOne(Wrappers.lambdaQuery(RoleEntity.class).eq(RoleEntity::getCode, roleCode));
    }

    /**
     * 判断输入编码是否存在
     *
     * @param code 编码
     * @return 存在返回 true，不存在返回 false
     */
    private boolean codeExists(String code) {
        LambdaQueryWrapper<RoleEntity> queryWrapper = Wrappers.lambdaQuery(RoleEntity.class).eq(RoleEntity::getCode, code);
        return this.baseMapper.exists(queryWrapper);
    }

    @Override
    public String getRoleCodeByRoleId(String roleId) {
        RoleEntity byId = this.getById(roleId);
        if (Objects.nonNull(byId)){
            return byId.getCode();
        }
        return null;
    }
}
