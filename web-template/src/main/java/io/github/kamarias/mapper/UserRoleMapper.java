package io.github.kamarias.mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.kamarias.entity.UserRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/4 11:03
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRoleEntity> {


    /**
     * 通过用户Id获取拥有的角色编码
     *
     * @param userId 用户Id
     * @return 返回的权限编码
     */
    @Select("select tr.code " +
            "from t_user tu join t_user_role tur on tu.id = tur.user_id " +
            "join t_role tr on tur.role_id = tr.id " +
            "where tu.del_flag = 0 and tu.id = #{userId}")
    List<String> getRoleCodeByUserId(@Param("userId") String userId);


    /**
     * 通过用户Id获取拥有的角色Id
     *
     * @param userId 用户Id
     * @return 返回的权限Id
     */
    @Select("select tr.id " +
            "from t_user tu join t_user_role tur on tu.id = tur.user_id " +
            "join t_role tr on tur.role_id = tr.id " +
            "where tu.del_flag = 0 and tr.del_flag = 0 and tu.id = #{userId}")
    List<String> getRoleIdsByUserId(@Param("userId") String userId);

}
