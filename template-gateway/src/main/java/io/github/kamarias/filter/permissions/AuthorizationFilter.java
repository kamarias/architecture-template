package io.github.kamarias.filter.permissions;


import io.github.kamarias.constant.RequestAttributes;
import io.github.kamarias.constant.SignHeaderConstant;
import io.github.kamarias.enums.FilterTypeEnum;
import io.github.kamarias.filter.GatewayServerGlobalFilter;
import io.github.kamarias.filter.context.RequestContext;
import io.github.kamarias.properties.GatewayServerProperties;
import io.github.kamarias.utils.PathMatchUtils;
import io.github.kamarias.utils.ResultDtoUtils;
import io.github.kamarias.utils.ServerWebResponseUtils;
import io.github.kamarias.utils.ServerWebUtils;
import io.github.kamarias.utils.string.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 授权过滤器
 *
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/12 11:06
 */
@Component
public class AuthorizationFilter extends GatewayServerGlobalFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationFilter.class);

    private final GatewayServerProperties gatewayServerProperties;


    public AuthorizationFilter(GatewayServerProperties gatewayServerProperties) {
        this.gatewayServerProperties = gatewayServerProperties;
    }


    @Override
    public String getFilterName() {
        return FilterTypeEnum.AUTHORIZATION_FILTER.getName();
    }

    @Override
    public int getOrder() {
        return FilterTypeEnum.AUTHORIZATION_FILTER.getSort();
    }

    @Override
    public boolean intercept(ServerWebExchange exchange) {
        RequestContext requestContext = ServerWebUtils.getAttribute(exchange, RequestAttributes.REQUEST_CONTEXT);
        return PathMatchUtils.match(gatewayServerProperties.getTokenWhiteList(), requestContext.getPath());
    }

    @Override
    public Mono<Void> doExecute(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (StringUtils.isBlank(ServerWebUtils.getHeader(exchange, SignHeaderConstant.ACCESS_TOKEN_HEADER))) {
            LOGGER.info("请求头不存在：{}，请求未授权。", SignHeaderConstant.ACCESS_TOKEN_HEADER);
            return ServerWebResponseUtils.response(exchange, ResultDtoUtils.unauthorized());
        }
        ServerWebUtils.putAttribute(exchange, SignHeaderConstant.WARRANT, Boolean.TRUE);
        return chain.filter(exchange);
    }
}
