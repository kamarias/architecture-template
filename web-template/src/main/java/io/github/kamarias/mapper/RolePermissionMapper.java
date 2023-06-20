package io.github.kamarias.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.kamarias.entity.RolePermissionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/30 9:39
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermissionEntity> {


    /**
     * 通过角色Id获取拥有的权限编码
     * @param roleId 角色Id
     * @return 返回权限编码
     */
    @Select("select tp.code " +
            "from t_permission tp join t_role_permission trp on tp.id = trp.permission_id " +
            "join t_role tr on trp.role_id = tr.id " +
            "where tp.del_flag = 0 and tr.del_flag = 0 and tr.id = #{roleId}")
    Set<String> getPermissionCodeByRoleId(@Param("roleId") String roleId);


}
