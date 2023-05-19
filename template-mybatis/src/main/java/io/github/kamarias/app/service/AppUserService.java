package io.github.kamarias.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.kamarias.entity.UserEntity;
import io.github.kamarias.query.QueryUserForm;
import io.github.kamarias.vo.PageVO;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/19 11:40
 */
public interface AppUserService extends IService<UserEntity> {

    PageVO<UserEntity> queryUserList(QueryUserForm form);


}
