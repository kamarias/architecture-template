package io.github.kamarias.filter;

import io.github.kamarias.context.HandlerContext;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 处理器过滤器链
 * @author wangyuxing@gogpay.cn
 * @date 2024/1/18 23:33
 */
public interface HandlerFilterChain {


    Mono<Void> filter(HandlerContext context);



}
