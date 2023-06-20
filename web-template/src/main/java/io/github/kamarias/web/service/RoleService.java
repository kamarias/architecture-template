package io.github.kamarias.web.service;



import com.baomidou.mybatisplus.extension.service.IService;
import io.github.kamarias.entity.RoleEntity;
import io.github.kamarias.form.add.AddRoleForm;
import io.github.kamarias.form.update.UpdateRoleForm;
import io.github.kamarias.query.base.IdForm;
import io.github.kamarias.query.system.QueryRoleForm;
import io.github.kamarias.vo.PageVO;
import io.github.kamarias.vo.RoleOptionsVO;
import io.github.kamarias.vo.RoleVO;

import java.util.List;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/30 14:23
 */
public interface RoleService extends IService<RoleEntity> {


    /**
     * 翻页查询角色列表
     * @param form 查询表达
     * @return 返回结果
     */
    PageVO<RoleEntity> getList(QueryRoleForm form);


    /**
     * 更新角色状态
     * @param form id表单
     */
    boolean updateStatus(IdForm form);

    /**
     * 新增角色
     * @param form 表单
     * @return 返回结果
     */
    boolean addRole(AddRoleForm form);

    /**
     * 更新角色
     * @param form 表单
     * @return 更新结果
     */
    boolean updateRole(UpdateRoleForm form);


    /**
     * 删除角色
     * @param form 表单
     * @return 删除结果
     */
    boolean deleteRole(IdForm form);


    /**
     * 获取角色详细信息
     * @param form id表单
     * @return 返回结果
     */
    RoleVO detailById(IdForm form);


    /**
     * 获取可选的角色选项
     * @return 返回角色选项集合
     */
    List<RoleOptionsVO> getRoleOptions();

    /**
     * 通过用户id获取可选角色列表
     * @return 返回角色选项集合 返回可选角色列表
     */
    List<RoleOptionsVO> getRoleOptionsByUserId(String userId);

    /**
     * 通过角色编码获取角色信息
     * @param roleCode 角色编码
     * @return 角色信息实体
     */
    RoleEntity getRoleByCode(String roleCode);


    /**
     * 通过角色Id获取角色编码
     * @param roleId 角色Id
     * @return 返回角色编码
     */
    String getRoleCodeByRoleId(String roleId);

}
