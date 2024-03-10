package io.github.kamarias.infrastructure.domainimpl;

import io.github.kamarias.domain.User;
import io.github.kamarias.domain.repository.UserRepository;
import io.github.kamarias.infrastructure.entity.UserEntity;
import io.github.kamarias.infrastructure.repository.UserEntityRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/12/17 23:40
 */
@Service
public class UserRepositoryImpl implements UserRepository {


    private final UserEntityRepository userEntityRepository;

    public UserRepositoryImpl(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public void save(User user) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user,userEntity);
        userEntityRepository.save(userEntity);
    }

}
