package io.github.kamarias.filter;

import io.github.kamarias.context.HandlerContext;
import org.springframework.core.Ordered;
import reactor.core.publisher.Mono;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2024/1/19 12:45
 */
public class OrderedHandlerFilter implements HandlerFilter, Ordered {


    private final HandlerFilter delegate;

    private final int order;

    public OrderedHandlerFilter(HandlerFilter delegate, int order) {
        this.delegate = delegate;
        this.order = order;
    }

    public HandlerFilter getDelegate() {
        return delegate;
    }

    @Override
    public Mono<Void> filter(HandlerContext context, HandlerFilterChain chain) {
        return this.delegate.filter(context, chain);
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    @Override
    public String toString() {
        return "[" + delegate + ", order = " + order + "]";
    }


}
