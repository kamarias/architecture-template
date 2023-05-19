package io.github.kamarias.filter.context;

import com.alibaba.fastjson.JSONObject;
import io.github.kamarias.constant.RequestAttributes;
import io.github.kamarias.dto.ResultDTO;
import io.github.kamarias.enums.FilterTypeEnum;
import io.github.kamarias.filter.GatewayServerWebFilter;
import io.github.kamarias.utils.ResultDtoUtils;
import io.github.kamarias.utils.ServerWebResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 上下文过滤器
 *
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/11 13:55
 */
@Component
public class ContextFilter extends GatewayServerWebFilter {

    private final Logger LOGGER = LoggerFactory.getLogger(ContextFilter.class);

    private final ContextBuilder builder;

    public ContextFilter(ContextBuilder builder) {
        this.builder = builder;
    }

    @Override
    public String getFilterName() {
        return FilterTypeEnum.CONTEXT_FILTER.getName();
    }

    @Override
    public int getOrder() {
        return FilterTypeEnum.CONTEXT_FILTER.getSort();
    }

    @Override
    public boolean intercept(ServerWebExchange exchange) {
        return super.intercept(exchange);
    }

    @Override
    protected Mono<Boolean> doFilter(ServerWebExchange exchange, WebFilterChain chain) {
        final ServerHttpRequest request = exchange.getRequest();
        final HttpHeaders headers = request.getHeaders();
        final String authorization = headers.getFirst(HttpHeaders.AUTHORIZATION);
        LOGGER.info("authorization: {}", authorization);
        RequestContext requestContext = builder.buildRequestContext(exchange);
        exchange.getAttributes().put(RequestAttributes.REQUEST_CONTEXT, requestContext);
        LOGGER.info("请求上下文信息：{}", JSONObject.toJSONString(requestContext));
        return Mono.just(true);
    }

    @Override
    protected Mono<Void> doDenyResponse(ServerWebExchange exchange, WebFilterChain chain) {
        ResultDTO dto = new ResultDTO(500, "网关上下文过滤器异常");
        LOGGER.error("上下文过滤器处理异常");
        return ServerWebResponseUtils.response(exchange, ResultDtoUtils.requestContext());
    }
}
