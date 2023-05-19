package io.github.kamarias.web.service;

import io.github.kamarias.entity.UserEntity;
import io.github.kamarias.query.QueryUserForm;
import io.github.kamarias.vo.PageVO;

/**
 * @author wangyuxing@gogpay.cn
 * @date @DATE @TIME
 */
public interface UserService {


    PageVO<UserEntity> queryUserList(QueryUserForm form);

}
