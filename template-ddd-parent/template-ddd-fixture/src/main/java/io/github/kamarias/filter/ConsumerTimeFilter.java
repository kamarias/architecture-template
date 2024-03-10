package io.github.kamarias.filter;

import io.github.kamarias.context.HandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 请求耗时过滤器
 * @author wangyuxing@gogpay.cn
 * @date 2024/1/19 22:03
 */
@Component
public class ConsumerTimeFilter implements Ordered,GlobalHandlerFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingRequestFilter.class);


    @Override
    public Mono<Void> filter(HandlerContext context, HandlerFilterChain chain) {
        context.setStarTime(System.currentTimeMillis());
        return chain.filter(context).doFinally(signalType -> {

            LOGGER.info("处理响应时长：{} 毫秒",System.currentTimeMillis() - context.getStarTime());
        });
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }


}
