package io.github.kamarias.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.kamarias.entity.PermissionEntity;
import io.github.kamarias.exception.CustomException;
import io.github.kamarias.form.add.AddPermissionForm;
import io.github.kamarias.form.update.UpdatePermissionForm;
import io.github.kamarias.mapper.PermissionMapper;
import io.github.kamarias.mapstruct.PermissionMapStruct;
import io.github.kamarias.query.base.IdForm;
import io.github.kamarias.query.system.QueryPermissionForm;
import io.github.kamarias.utils.PageUtil;
import io.github.kamarias.utils.string.StringUtils;
import io.github.kamarias.vo.PageVO;
import io.github.kamarias.web.service.PermissionService;
import io.github.kamarias.web.service.RolePermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/31 17:08
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, PermissionEntity> implements PermissionService {

    private final RolePermissionService rolePermissionService;

    private final PermissionMapStruct permissionMapStruct;

    public PermissionServiceImpl(RolePermissionService rolePermissionService, PermissionMapStruct permissionMapStruct) {
        this.rolePermissionService = rolePermissionService;
        this.permissionMapStruct = permissionMapStruct;
    }

    @Override
    public PageVO<PermissionEntity> getList(QueryPermissionForm form) {
        LambdaQueryWrapper<PermissionEntity> query = Wrappers.lambdaQuery(PermissionEntity.class);
        query.like(StringUtils.isNotBlank(form.getName()), PermissionEntity::getName, form.getName())
                .eq(StringUtils.isNotBlank(form.getCode()), PermissionEntity::getCode, form.getCode())
                .orderByDesc(PermissionEntity::getCreateTime);
        return PageUtil.handlerResult(this.page(new Page<>(form.getPageNum(), form.getPageSize()), query));
    }

    @Override
    public boolean addPermission(AddPermissionForm form) {
        if (codeExists(form.getCode())) {
            throw new CustomException("权限编码已存在");
        }
        PermissionEntity entity = permissionMapStruct.toPermissionEntityByAddPermissionForm(form);
        return this.save(entity);
    }

    @Override
    public boolean updatePermission(UpdatePermissionForm form) {
        PermissionEntity byId = this.getById(form.getId());
        if (codeExists(form.getCode()) && !form.getCode().equals(byId.getCode())) {
            throw new CustomException("权限编码已存在");
        }
        permissionMapStruct.updatePermissionEntityByPermissionEntity(form, byId);
        return this.updateById(byId);
    }


    @Override
    public boolean deletePermission(IdForm form) {
        if (!rolePermissionService.existRefByPermissionId(form.getId())) {
            return this.removeById(form.getId());
        }
        throw new CustomException("当前权限已分配给角色不可删除！");
    }


    /**
     * 判断输入编码是否存在
     *
     * @param code 编码
     * @return 存在返回 true，不存在返回 false
     */
    private boolean codeExists(String code) {
        LambdaQueryWrapper<PermissionEntity> queryWrapper = Wrappers.lambdaQuery(PermissionEntity.class).eq(PermissionEntity::getCode, code);
        return this.baseMapper.exists(queryWrapper);
    }


    @Override
    public List<PermissionEntity> getListAll() {
        return this.list(Wrappers.lambdaQuery(PermissionEntity.class).orderByDesc(PermissionEntity::getCreateTime));
    }

}
