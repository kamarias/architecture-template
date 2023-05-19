package io.github.kamarias.app.service;

import io.github.kamarias.entity.UserEntity;
import io.github.kamarias.query.QueryUserForm;
import io.github.kamarias.vo.PageVO;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/19 11:22
 */
public interface AppUserService {

    PageVO<UserEntity> queryUserList(QueryUserForm form);

}
