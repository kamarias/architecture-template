package io.github.kamarias.infrastructure.repository;

import io.github.kamarias.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/12/17 23:45
 */
@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, String> {


}
