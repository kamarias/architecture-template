package io.github.kamarias.filter.crypto;


import io.github.kamarias.constant.Constant;
import io.github.kamarias.constant.RequestAttributes;
import io.github.kamarias.enums.FilterTypeEnum;
import io.github.kamarias.filter.GatewayServerGlobalFilter;
import io.github.kamarias.filter.context.RequestContext;
import io.github.kamarias.model.BodyDTO;
import io.github.kamarias.properties.GatewayServerProperties;
import io.github.kamarias.support.ModifiedResponseDecorator;
import io.github.kamarias.support.RewriteConfig;
import io.github.kamarias.utils.PathMatchUtils;
import io.github.kamarias.utils.ServerWebUtils;
import io.github.kamarias.utils.encrypt.AesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
import org.springframework.http.codec.CodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * 加密拦截器
 *
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/6 11:40
 */
@Component
public class EncryptFilter extends GatewayServerGlobalFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptFilter.class);

    private final CodecConfigurer codecConfigurer;

    private final GatewayServerProperties gatewayServerProperties;

    public EncryptFilter(CodecConfigurer codecConfigurer, GatewayServerProperties gatewayServerProperties) {
        this.codecConfigurer = codecConfigurer;
        this.gatewayServerProperties = gatewayServerProperties;
    }

    @Override
    public String getFilterName() {
        return FilterTypeEnum.ENCRYPT_FILTER.getName();
    }

    @Override
    public int getOrder() {
        return FilterTypeEnum.ENCRYPT_FILTER.getSort();
    }

    @Override
    public boolean intercept(ServerWebExchange exchange) {
        RequestContext requestContext = ServerWebUtils.getAttribute(exchange, RequestAttributes.REQUEST_CONTEXT);
        return PathMatchUtils.match(gatewayServerProperties.getCryptoWhiteList(), requestContext.getPath());
    }

    @Override
    public Mono<Void> doExecute(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange.mutate().response(
                new ModifiedResponseDecorator(exchange, codecConfigurer, new RewriteConfig()
                        .setRewriteFunction(String.class, String.class, this.rewriteResponse()))).build());
    }

    /**
     * 重写响应参数
     *
     * @return 重写响应参数规则
     */
    private RewriteFunction rewriteResponse() {
        return (ex, response) -> {
            LOGGER.info("接口响应数据：{}", response);
            if (Objects.isNull(response)) {
                return Mono.just("");
            }
            // @todo 忽略加密
            if (false) {
                return Mono.just(response);
            }
            BodyDTO dto = new BodyDTO();
            try {
                dto.setInit(Constant.OFFSET_KEY);
                dto.setData(AesUtils.encrypt(response.toString(), Constant.OFFSET_KEY + "abcdfgf" ));
            } catch (Exception e) {
                LOGGER.error("接口响应数据加密异常：{}", response);
                throw new RuntimeException();
            }
            return Mono.just(dto.toString());
        };
    }

}
