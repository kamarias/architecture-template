package io.github.kamarias.mapstruct;


import io.github.kamarias.entity.RouterEntity;
import io.github.kamarias.entity.RouterMetaEntity;
import io.github.kamarias.form.add.AddRouterForm;
import io.github.kamarias.form.update.UpdateRouterForm;
import io.github.kamarias.vo.RouterManageVO;
import io.github.kamarias.vo.RouterMetaVO;
import io.github.kamarias.vo.RouterVO;
import org.mapstruct.*;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/6/15 10:22
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RouterMapStruct {


    RouterManageVO toRouterManageVOByRouterEntity(RouterEntity entity);

    RouterVO toRouterVOByRouterEntity(RouterEntity entity);

    RouterEntity toRouterEntityByAddRouterForm(AddRouterForm form);


    @Mappings({
            @Mapping(target = "routerMetaVO.id", ignore = true),
            @Mapping(target = "transition", ignore = true)
    })
    void updateRouterManageVOByRouterMetaVO(RouterMetaVO routerMetaVO, @MappingTarget RouterManageVO routerManageVO);

    void updateRouterEntityByUpdateRouterForm(UpdateRouterForm form, @MappingTarget RouterEntity entity);


    @Mappings({
            @Mapping(source = "routerEntity.id", target = "id"),
    })
    UpdateRouterForm toUpdateRouterFormByRouterEntityAndRouterMetaEntity(RouterEntity routerEntity, RouterMetaEntity routerMetaEntity);

}
