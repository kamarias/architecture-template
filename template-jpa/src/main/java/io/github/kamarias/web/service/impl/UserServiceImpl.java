package io.github.kamarias.web.service.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.kamarias.entity.UserEntity;
import io.github.kamarias.query.QueryUserForm;
import io.github.kamarias.repository.UserRepository;
import io.github.kamarias.utils.PageUtil;
import io.github.kamarias.vo.PageVO;
import io.github.kamarias.web.service.UserService;
import io.github.kamarias.entity.QUserEntity;
import org.springframework.stereotype.Service;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/19 11:17
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final JPAQueryFactory jpaQueryFactory;

    public UserServiceImpl(UserRepository userRepository, JPAQueryFactory jpaQueryFactory) {
        this.userRepository = userRepository;
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public PageVO<UserEntity> queryUserList(QueryUserForm form) {
        QUserEntity userEntity = QUserEntity.userEntity;
        QueryResults<UserEntity> queryResults = jpaQueryFactory.select(Projections.bean(UserEntity.class,
                        userEntity.id,
                        userEntity.name,
                        userEntity.age,
                        userEntity.email
                )).from(userEntity)
                .offset(form.getOffset())
                .limit(form.getPageSize())
                .fetchResults();
        return PageUtil.handlerResult(queryResults);
    }

}
