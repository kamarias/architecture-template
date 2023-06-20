package io.github.kamarias.mapstruct;

import io.github.kamarias.entity.RoleEntity;
import io.github.kamarias.form.add.AddRoleForm;
import io.github.kamarias.form.update.UpdateRoleForm;
import io.github.kamarias.vo.RoleVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/6/14 11:36
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoleMapStruct {

    RoleEntity toRoleEntityByAddRoleForm(AddRoleForm form);


    void updateRoleEntityByUpdateRoleForm(UpdateRoleForm userDto, @MappingTarget RoleEntity target);

    RoleVO toRoleVOByRoleEntity(RoleEntity entity);

}
