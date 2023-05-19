package io.github.kamarias.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关服务-网关过滤器
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/6 10:41
 */
public abstract class GatewayServerGatewayFilter implements GatewayFilter, Ordered, GatewayServerInterceptFilter {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (!this.intercept(exchange)){
            return doExecute(exchange, chain);
        }
        // 放行
        return chain.filter(exchange);
    }

}
