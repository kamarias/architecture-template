package io.github.kamarias.filter;

import com.alibaba.fastjson2.JSON;
import io.github.kamarias.context.HandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2024/1/19 22:16
 */
@Component
public class LoggingRequestFilter implements GlobalHandlerFilter, Ordered {


    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingRequestFilter.class);


    @Override
    public Mono<Void> filter(HandlerContext context, HandlerFilterChain chain) {
        String requestId = UUID.randomUUID().toString().replaceAll("-", "");
        LOGGER.info("---------------------->> 请求进入：{} <<----------------------", requestId);
        return chain.filter(context)
                .doFinally((signalType) -> {
                    switch (signalType) {
                        case ON_COMPLETE:
                            break;
                        case ON_ERROR:
                            break;
                        default:
                            break;
                    }
                    LOGGER.info(JSON.toJSONString(context));
                    LOGGER.info("<<---------------------- 请求结束：{} ---------------------->>", requestId);
                });
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
