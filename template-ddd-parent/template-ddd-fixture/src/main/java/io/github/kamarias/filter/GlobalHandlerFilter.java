package io.github.kamarias.filter;

import io.github.kamarias.context.HandlerContext;
import reactor.core.publisher.Mono;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2024/1/19 12:34
 */
public interface GlobalHandlerFilter {


    Mono<Void> filter(HandlerContext context, HandlerFilterChain chain);

}
