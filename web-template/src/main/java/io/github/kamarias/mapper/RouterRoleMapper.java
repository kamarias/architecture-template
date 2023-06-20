package io.github.kamarias.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.kamarias.entity.RouterRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/4 11:03
 */
@Mapper
public interface RouterRoleMapper extends BaseMapper<RouterRoleEntity> {


    /**
     * 通过路由Id获取拥有的角色编码
     * @param routerId 路由Id
     * @return 返回的权限编码
     */
    @Select("select tr.code " +
            "from t_role tr join t_router_role trr on tr.id = trr.role_id " +
            "join t_router tru on trr.router_id = tru.id " +
            "where tru.del_flag = 0 and tru.id = #{routerId}")
    List<String> getRoleCodeByRouterId(@Param("routerId") String routerId);


    /**
     * 通过路由Id获取拥有的角色Id
     * @param routerId 路由Id
     * @return 返回角色Id集合
     */
    @Select("select tr.id " +
            "from t_role tr join t_router_role trr on tr.id = trr.role_id " +
            "join t_router tru on trr.router_id = tru.id " +
            "where tru.del_flag = 0 and tru.id = #{routerId}")
    List<String> getRoleIdsByRouterId(@Param("routerId") String routerId);
}
