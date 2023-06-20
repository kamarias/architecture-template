package io.github.kamarias.common;

/**
 * 缓存常量
 * @author wangyuxing@gogpay.cn
 * @date 2023/6/17 18:24
 */
public interface CaCheConstants {

    String CACHE_30_SECOND = "redis_cache_30_second";
    String CACHE_1_MINUTE = "redis_cache_1_minute";
    String CACHE_5_MINUTE = "redis_cache_5_minute";
    String CACHE_10_MINUTE = "redis_cache_10_minute";


    /**
     * 全局路由缓存名
     */
    String ROUTER_CACHE_NAME = "router_cache";



}
