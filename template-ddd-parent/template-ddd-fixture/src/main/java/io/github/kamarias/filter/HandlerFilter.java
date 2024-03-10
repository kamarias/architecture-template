package io.github.kamarias.filter;

import io.github.kamarias.context.HandlerContext;
import reactor.core.publisher.Mono;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2024/1/18 23:35
 */
public interface HandlerFilter {



    Mono<Void> filter(HandlerContext context, HandlerFilterChain chain);




}
