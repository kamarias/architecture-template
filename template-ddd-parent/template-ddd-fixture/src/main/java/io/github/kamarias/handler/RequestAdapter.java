package io.github.kamarias.handler;

import io.github.kamarias.context.HandlerContext;
import io.github.kamarias.vo.Response;
import reactor.core.publisher.Mono;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2024/1/20 14:30
 */
public interface RequestAdapter {


    default boolean supports(HandlerContext handler) {
        return false;
    }


    Mono<Response> handle(HandlerContext context);


}
