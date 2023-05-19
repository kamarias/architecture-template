package io.github.kamarias.filter;

import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/6 10:40
 */
public abstract class GatewayServerWebFilter implements WebFilter, Ordered, GatewayServerInterceptFilter {


    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (!this.intercept(exchange)) {
            return doFilter(exchange, chain).switchIfEmpty(Mono.just(false))
                    .flatMap(res -> res ? chain.filter(exchange) : doDenyResponse(exchange, chain));
        }
        // 放行
        return chain.filter(exchange);
    }

    /**
     * this is Template Method ,children Implement your own filtering logic.
     *
     * @param exchange the current server exchange
     * @param chain    provides a way to delegate to the next filter
     * @return {@code Mono<Boolean>} result：TRUE (is pass)，and flow next filter；FALSE (is not pass) execute doDenyResponse(ServerWebExchange exchange)
     */
    protected abstract Mono<Boolean> doFilter(ServerWebExchange exchange, WebFilterChain chain);

    /**
     * this is template method ,children implement your own and response client.
     *
     * @param exchange the current server exchange.
     * @param chain    provides a way to delegate to the next filter
     * @return {@code Mono<Void>} response msg.
     */
    protected abstract Mono<Void> doDenyResponse(ServerWebExchange exchange, WebFilterChain chain);

}
