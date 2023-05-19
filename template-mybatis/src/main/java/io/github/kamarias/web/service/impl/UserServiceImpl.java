package io.github.kamarias.web.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.kamarias.entity.UserEntity;
import io.github.kamarias.mapper.UserMapper;
import io.github.kamarias.query.QueryUserForm;
import io.github.kamarias.utils.PageUtil;
import io.github.kamarias.vo.PageVO;
import io.github.kamarias.web.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/4 11:06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Override
    public PageVO<UserEntity> queryUserList(QueryUserForm form) {
        Page page = new Page(form.getPageNum(), form.getPageSize());
        return PageUtil.handlerResult(this.page(page));
    }

}
