package io.github.kamarias.web.service;



import com.baomidou.mybatisplus.extension.service.IService;
import io.github.kamarias.entity.RouterRoleEntity;

import java.util.List;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/6/9 12:48
 */
public interface RouterRoleService extends IService<RouterRoleEntity> {


    /**
     * 通过路由Id 删除路由角色关系
     * @param routerId 路由Id
     * @return 删除结果
     */
    boolean removeRouterRoleByRouterId(String routerId);

    /**
     * 通过权限Id判断是否存在引用
     *
     * @param roleId 角色Id
     * @return 存在返回true , 不存在返回false
     */
    boolean existRefByRoleId(String roleId);


    /**
     * 通过角色Id集合批量保存 路由角色关系
     * @param roles 角色id集合
     * @return 保存结果
     */
    boolean saveRouterRoleByRoleIds(String routerId, List<String> roles);

    /**
     * 通过路由Id获取拥有的角色编码
     * @param routerId 路由Id
     * @return 返回的权限编码
     */
    List<String> getRoleCodeByRouterId(String routerId);

    /**
     * 通过路由Id获取拥有的角色Id
     * @param routerId 路由Id
     * @return 返回角色Id集合
     */
    List<String> getRoleIdsByRouterId(String routerId);

}
