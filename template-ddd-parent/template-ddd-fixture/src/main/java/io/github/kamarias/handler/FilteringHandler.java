package io.github.kamarias.handler;

import io.github.kamarias.context.HandlerContext;
import io.github.kamarias.filter.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2024/1/19 12:35
 */
@Component
public class FilteringHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingRequestFilter.class);

    private final List<HandlerFilter> globalHandlerFilters;

    public FilteringHandler(List<GlobalHandlerFilter> globalFilters) {
        this.globalHandlerFilters = loadFilters(globalFilters);
    }

    /**
     * 加载过滤器
     * @param filters 过滤器
     * @return 返回排序后的过滤器链
     */
    private static List<HandlerFilter> loadFilters(List<GlobalHandlerFilter> filters) {
        return filters.stream().map(filter -> {
            HandlerFilterAdapter handlerFilter = new HandlerFilterAdapter(filter);
            if (filter instanceof Ordered) {
                int order = ((Ordered) filter).getOrder();
                return new OrderedHandlerFilter(handlerFilter, order);
            }
            return handlerFilter;
        }).collect(Collectors.toList());
    }


    public Mono<Void> handler(HandlerContext context) {
//        Route route = exchange.getRequiredAttribute(GATEWAY_ROUTE_ATTR);
//        List<GatewayFilter> gatewayFilters = route.getFilters();

        List<HandlerFilter> combined = new ArrayList<>(this.globalHandlerFilters);
//        combined.addAll(gatewayFilters);
        // TODO: needed or cached?
        AnnotationAwareOrderComparator.sort(combined);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Sorted gatewayFilterFactories: " + combined);
        }

        return new DefaultHandlerFilterChain(combined).filter(context);
    }


    /**
     * 默认处理器过滤器链实现
     */
    private static class DefaultHandlerFilterChain implements HandlerFilterChain {

        private final int index;

        private final List<HandlerFilter> filters;

        DefaultHandlerFilterChain(List<HandlerFilter> filters) {
            this.filters = filters;
            this.index = 0;
        }

        private DefaultHandlerFilterChain(DefaultHandlerFilterChain parent, int index) {
            this.filters = parent.getFilters();
            this.index = index;
        }

        public List<HandlerFilter> getFilters() {
            return filters;
        }

        @Override
        public Mono<Void> filter(HandlerContext context) {
            return Mono.defer(() -> {
                if (this.index < filters.size()) {
                    HandlerFilter filter = filters.get(this.index);
                    DefaultHandlerFilterChain chain = new DefaultHandlerFilterChain(this, this.index + 1);
                    return filter.filter(context, chain);
                }
                else {
                    return Mono.empty(); // complete
                }
            });
        }
    }


    /**
     * 全局处理器适配器
     */
    private static class HandlerFilterAdapter implements HandlerFilter {

        private final GlobalHandlerFilter delegate;

        HandlerFilterAdapter(GlobalHandlerFilter delegate) {
            this.delegate = delegate;
        }

        @Override
        public Mono<Void> filter(HandlerContext context, HandlerFilterChain chain) {
            return this.delegate.filter(context, chain);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("GatewayFilterAdapter{");
            sb.append("delegate=").append(delegate);
            sb.append('}');
            return sb.toString();
        }

    }

}
