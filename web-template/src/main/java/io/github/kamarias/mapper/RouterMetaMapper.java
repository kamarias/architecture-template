package io.github.kamarias.mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.kamarias.entity.RouterMetaEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/4 11:03
 */
@Mapper
public interface RouterMetaMapper extends BaseMapper<RouterMetaEntity> {


    /**
     * 通过路由Id查询路由源数据
     *
     * @param routerId 路由Id
     * @return 路由源数据
     */
    @Select("select id,router_id,title,icon,extra_icon,show_link,show_parent,keep_alive,frame_src,frame_loading,hidden_tag,dynamic_level,transition, `rank` " +
            "from t_router_meta " +
            "where router_id = #{routerId} " +
            "limit 1;")
    RouterMetaEntity findByRouterIdRouter(@Param("routerId") String routerId);

}
