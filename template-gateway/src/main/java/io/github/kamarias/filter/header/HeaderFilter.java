package io.github.kamarias.filter.header;

import com.alibaba.fastjson.JSON;
import io.github.kamarias.constant.RequestAttributes;
import io.github.kamarias.constant.SignHeaderConstant;
import io.github.kamarias.enums.FilterTypeEnum;
import io.github.kamarias.filter.GatewayServerGlobalFilter;
import io.github.kamarias.filter.context.RequestContext;
import io.github.kamarias.utils.HttpHeaderUtils;
import io.github.kamarias.utils.ServerWebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 请求头过滤器
 *
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/11 18:18
 */
@Component
public class HeaderFilter extends GatewayServerGlobalFilter {

    private final Logger LOGGER = LoggerFactory.getLogger(HeaderFilter.class);

    @Override
    public String getFilterName() {
        return FilterTypeEnum.HEADER_FILTER.getName();
    }

    @Override
    public int getOrder() {
        return FilterTypeEnum.HEADER_FILTER.getSort();
    }

    @Override
    public Mono<Void> doExecute(ServerWebExchange exchange, GatewayFilterChain chain) {
        RequestContext requestContext = ServerWebUtils.getAttribute(exchange, RequestAttributes.REQUEST_CONTEXT);
        ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
        LOGGER.info("源请求头：{}", JSON.toJSONString(builder.build().getHeaders().toSingleValueMap()));
        ServerHttpRequest.Builder buildHeader = HttpHeaderUtils.buildHeader(builder, requestContext);
        // 清理请求头、减少数据传输量
        buildHeader.headers(h -> h.remove(SignHeaderConstant.ACCESS_TOKEN_HEADER));
        buildHeader.headers(h -> h.remove(SignHeaderConstant.SIGN));
        ServerHttpRequest httpRequest = buildHeader.build();
        HttpHeaders httpHeaders = httpRequest.getHeaders();
        String headerJson = JSON.toJSONString(httpHeaders.toSingleValueMap());
        LOGGER.info("构造后请求头：{}", headerJson);
        return chain.filter(exchange.mutate().request(httpRequest).build());
    }


}
