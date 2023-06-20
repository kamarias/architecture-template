package io.github.kamarias.mapstruct;

import io.github.kamarias.entity.RouterMetaEntity;
import io.github.kamarias.form.add.AddRouterForm;
import io.github.kamarias.vo.RouterMetaVO;
import io.github.kamarias.vo.RouterTransitionVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/6/15 10:01
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RouterMetaMapStruct {

    @Mapping(target = "transition", ignore = true)
    RouterMetaVO toRouterMetaVOByRouterMetaEntity(RouterMetaEntity entity);


     RouterTransitionVO toRouterTransitionVOByExtendsAddRouterForm(AddRouterForm form);

    RouterMetaEntity toRouterMetaEntityByAddRouterForm(AddRouterForm form);



}
