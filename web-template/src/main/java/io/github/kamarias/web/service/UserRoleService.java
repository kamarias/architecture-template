package io.github.kamarias.web.service;


import com.baomidou.mybatisplus.extension.service.IService;
import io.github.kamarias.entity.UserRoleEntity;

import java.util.List;

/**
 * 用户角色关系业务
 *
 * @author wangyuxing@gogpay.cn
 * @date 2023/6/5 11:24
 */
public interface UserRoleService extends IService<UserRoleEntity> {



    /**
     * 通过权限Id判断是否存在引用
     *
     * @param roleId 角色Id
     * @return 存在返回true , 不存在返回false
     */
    boolean existRefByRoleId(String roleId);

    /**
     * 通过角色Id集合批量保存 用户角色关系
     * @param userId 用户Id
     * @param roleIds 角色id
     * @return 添加结果
     */
    boolean saveUserRoleByRoleIds(String userId, List<String> roleIds);

    /**
     * 通过用户Id删除用户的角色关系
     * @param userId 用户Id
     * @return 删除结果
     */
    boolean removeUserRoleByUserId(String userId);

    /**
     * 通过用户Id获取拥有的角色编码
     * @param userId 路由Id
     * @return 返回的权限编码
     */
    List<String> getRoleCodeByUserId(String userId);

    /**
     * 通过用户Id获取拥有的角色Id
     * @param userId 用户Id
     * @return 返回角色Id集合
     */
    List<String> getRoleIdsByUserId(String userId);


    /**
     * 通过用户Id获取一个用户绑定的角色
     * @param userId 用户Id
     * @return 用户角色关系
     */
    UserRoleEntity getOneUserRoleByUserId(String userId);
}
