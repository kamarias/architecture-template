package io.github.kamarias.filter.context;

import io.github.kamarias.model.AppHeaderDTO;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

/**
 * http请求上下文构造器
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/11 14:35
 */
public interface ContextBuilder {

    /**
     * 构建请求上下文
     *
     * @param exchange the exchange
     * @return the context
     */
    RequestContext buildRequestContext(ServerWebExchange exchange);

    /**
     * 构造移动应用请求上下文
     * @param request httpRequest 请求
     * @return 返回结果
     */
    AppHeaderDTO buildAppHeaderDTO(ServerHttpRequest request);

}
