package io.github.kamarias.config.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

/**
 * QueryDsl 配置类
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/4 19:41
 */
@Configuration
public class QueryDslConfig {

    private final EntityManager entityManager;


    public QueryDslConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Bean
    public JPAQueryFactory jpaQueryFactory(){
        return new JPAQueryFactory(entityManager);
    }

}
