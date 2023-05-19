package io.github.kamarias.filter.log;

import io.github.kamarias.enums.FilterTypeEnum;
import io.github.kamarias.filter.GatewayServerGlobalFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;


/**
 * 日志切面过滤器
 *
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/6 11:15
 */
@Component
public class LogAspectFilter extends GatewayServerGlobalFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogAspectFilter.class);


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String requestId = UUID.randomUUID().toString();
        LOGGER.info("---------------------->> 请求进入：{} <<----------------------", requestId);
        LOGGER.info("请求全路径：{}", request.getURI());
        LOGGER.info("请求路径：{}", request.getPath());
        return chain.filter(exchange)
                .doFinally(signalType -> {
                    switch (signalType) {
                        case ON_COMPLETE:
                            LOGGER.info("请求成功：{}", request.getPath());
                            break;
                        case ON_ERROR:
                            LOGGER.error("请求失败：{}，错误状态码：{}", request.getPath(), exchange.getResponse().getStatusCode());
                            break;
                        default:
                            break;
                    }
                    LOGGER.info("<<---------------------- 请求结束：{} ---------------------->>", requestId);
                });
    }

    @Override
    public String getFilterName() {
        return FilterTypeEnum.LOG_FILTER.getName();
    }

    @Override
    public int getOrder() {
        return FilterTypeEnum.LOG_FILTER.getSort();
    }

}
