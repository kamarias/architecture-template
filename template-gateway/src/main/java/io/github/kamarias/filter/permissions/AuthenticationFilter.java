package io.github.kamarias.filter.permissions;

import io.github.kamarias.constant.RequestAttributes;
import io.github.kamarias.enums.FilterTypeEnum;
import io.github.kamarias.filter.GatewayServerGlobalFilter;
import io.github.kamarias.filter.context.RequestContext;
import io.github.kamarias.properties.GatewayServerProperties;
import io.github.kamarias.utils.PathMatchUtils;
import io.github.kamarias.utils.ServerWebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 认证过滤器
 *
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/12 11:13
 */
@Component
public class AuthenticationFilter extends GatewayServerGlobalFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);

    private final GatewayServerProperties gatewayServerProperties;

    public AuthenticationFilter(GatewayServerProperties gatewayServerProperties) {
        this.gatewayServerProperties = gatewayServerProperties;
    }


    @Override
    public String getFilterName() {
        return FilterTypeEnum.AUTHENTICATION_FILTER.getName();
    }

    @Override
    public int getOrder() {
        return FilterTypeEnum.AUTHENTICATION_FILTER.getSort();
    }

    @Override
    public boolean intercept(ServerWebExchange exchange) {
        RequestContext requestContext = ServerWebUtils.getAttribute(exchange, RequestAttributes.REQUEST_CONTEXT);
        return PathMatchUtils.match(gatewayServerProperties.getTokenWhiteList(), requestContext.getPath());
    }

    @Override
    public Mono<Void> doExecute(ServerWebExchange exchange, GatewayFilterChain chain) {

        return chain.filter(exchange);
    }
}
