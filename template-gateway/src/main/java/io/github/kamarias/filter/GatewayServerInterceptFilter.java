package io.github.kamarias.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关拦截过滤器
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/6 10:48
 */
public interface GatewayServerInterceptFilter {

    /**
     * 获取过滤器名称
     * @return 返回过滤器名称
     */
    String getFilterName();

    /**
     * 网关服务拦截器（默认拦截），需要重写 doExecute() 拦截才能生效，否则即使默认拦截依然不生效
     * @param exchange http 请求/响应实体
     * @return 是否拦截
     */
    default boolean intercept(ServerWebExchange exchange){
        return false;
    }

    /**
     * 拦截处理程序（默认放行）
     * @param exchange http 请求/响应实体
     * @param chain SpringCloudGateway 默认拦截器
     * @return 拦截处理结果
     */
    default Mono<Void> doExecute(ServerWebExchange exchange, GatewayFilterChain chain){
        return chain.filter(exchange);
    }

}
