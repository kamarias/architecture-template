package io.github.kamarias.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.kamarias.entity.RolePermissionEntity;


import java.util.List;
import java.util.Set;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/6/1 9:45
 */
public interface RolePermissionService extends IService<RolePermissionEntity> {


    /**
     * 通过权限Id判断是否存在引用
     *
     * @param permissionId
     * @return 存在返回true , 不存在返回false
     */
    boolean existRefByPermissionId(String permissionId);


    /**
     * 通过角色id删除权限关联信息
     * @param roleId  角色ID
     * @return 操作结果
     */
    boolean deleteRolePermissionByRoleId(String roleId);


    /**
     * 批量保持角色权限信息
     * @param roleId 角色ID
     * @param permissions 权限信息
     * @return 返回结果
     */
    boolean saveBatchByRoleId(String roleId, List<String> permissions);


    /**
     * 通过角色Id获取权限id集合
     * @param roleId 角色Id
     * @return 返回权限id的集合
     */
    List<String> getPermissionsByRoleId(String roleId);


    /**
     * 通过角色Id获取权限编码集合
     * @param roleId 角色Id
     * @return 返回权限编码的集合
     */
    Set<String> getPermissionCodeByRoleId(String roleId);

}
