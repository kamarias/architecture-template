package io.github.kamarias.mapstruct;

import io.github.kamarias.entity.PermissionEntity;
import io.github.kamarias.form.add.AddPermissionForm;
import io.github.kamarias.form.update.UpdatePermissionForm;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/6/15 9:48
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PermissionMapStruct {


    PermissionEntity toPermissionEntityByAddPermissionForm(AddPermissionForm form);


    void updatePermissionEntityByPermissionEntity(UpdatePermissionForm form, @MappingTarget PermissionEntity entity);


}
