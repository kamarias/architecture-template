package io.github.kamarias.utils;

import org.springframework.web.server.ServerWebExchange;

/**
 * 网络请求工具类
 *
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/11 12:02
 */
public class ServerWebUtils {

    /**
     * 获取网络请求属性
     *
     * @param exchange http 请求/响应实体
     * @param key      查找属性名字
     * @param <K>      key的类型
     * @param <V>      v 返回值类型
     * @return 返回查找到的值
     */
    public static <K, V> V getAttribute(ServerWebExchange exchange, K key) {
        Object o = exchange.getAttributes().get(key);
        if (o == null) {
            throw new IllegalStateException(String.format("attribute not exist：" + key, key));
        }
        return (V) o;
    }

    /**
     * 获取网络请求属性
     *
     * @param exchange http 请求/响应实体
     * @param key      查找属性名字
     * @param <V>      v 返回值类型
     * @return 返回查找到的值
     */
    public static <V> void putAttribute(ServerWebExchange exchange, String key, V value) {
        exchange.getAttributes().put(key, value);
    }

    /**
     * 获取网络请求头属性
     *
     * @param exchange
     * @param headerName 查找属性名字
     * @return 返回查找到的值
     */
    public static <K> String getHeader(ServerWebExchange exchange, String headerName) {
        return exchange.getRequest().getHeaders().getFirst(headerName);
    }

}
