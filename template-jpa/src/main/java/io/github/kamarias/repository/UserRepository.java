package io.github.kamarias.repository;

import io.github.kamarias.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wangyuxing@gogpay.cn
 * @date @DATE @TIME
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {



}
