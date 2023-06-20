package io.github.kamarias.mapstruct;


import io.github.kamarias.entity.UserEntity;
import io.github.kamarias.form.add.AddUserForm;
import io.github.kamarias.form.update.UpdateUserForm;
import io.github.kamarias.vo.LoginUser;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/6/15 9:07
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapStruct {

    UserEntity toUserEntityByAddUserForm(AddUserForm form);


    void updateRoleEntityByUpdateUserForm(UpdateUserForm form, @MappingTarget UserEntity userEntity);

    UpdateUserForm toUpdateUserFormByUserEntity(UserEntity userEntity, List<String> roleIds);

    LoginUser toLoginUserByUserEntity(UserEntity userEntity);

}
