package io.github.kamarias.config.redis;

import io.github.kamarias.common.CaCheConstants;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/6/20 20:56
 */
@Component
public class RedisCacheManagerConfig {

    private final FastJson2JsonRedisSerializer<Object> serializer = new FastJson2JsonRedisSerializer<>(Object.class);

    private final RedisSerializer<String> stringRedisSerializer = RedisSerializer.string();

    @Bean
    public CacheManager cacheManager(final RedisConnectionFactory redisConnectionFactory) {
        Map<String, RedisCacheConfiguration> configurationMap = new HashMap<>(10);
        configurationMap.put(CaCheConstants.CACHE_30_SECOND, createCacheConfig(Duration.ofSeconds(30)));
        configurationMap.put(CaCheConstants.CACHE_1_MINUTE, createCacheConfig(Duration.ofMinutes(1)));
        configurationMap.put(CaCheConstants.CACHE_5_MINUTE, createCacheConfig(Duration.ofMinutes(5)));
        configurationMap.put(CaCheConstants.CACHE_10_MINUTE, createCacheConfig(Duration.ofMinutes(10)));
        RedisCacheConfiguration defaultConfig = createCacheConfig(Duration.ofDays(7));
        return RedisCacheManager.builder(redisConnectionFactory)
                .initialCacheNames(configurationMap.keySet())
                .withInitialCacheConfigurations(configurationMap)
                // 如果key不在configurationMap中，则使用此配置
                .cacheDefaults(defaultConfig)
                .build();
    }

    @Bean
    public KeyGenerator keyGenerator(){
        return (target, method, params) -> {
            /* 主键生成策略
             target 目标对象
             method 方法
             params 参数
             */
            return method.getName()+"["+ Arrays.asList(params) +"]";
        };
    }


    private RedisCacheConfiguration createCacheConfig(Duration duration) {
        return RedisCacheConfiguration.defaultCacheConfig()
                // 设置缓存有效期为7天
                .entryTtl(duration)
                // 设置键序列化器
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringRedisSerializer))
                // 设置值序列化器
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer))
                // 禁止缓存空值
                .disableCachingNullValues();
    }


}
