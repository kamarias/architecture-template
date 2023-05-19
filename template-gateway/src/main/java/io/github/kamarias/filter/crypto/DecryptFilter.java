package io.github.kamarias.filter.crypto;

import com.alibaba.fastjson.JSON;
import io.github.kamarias.constant.RequestAttributes;
import io.github.kamarias.enums.FilterTypeEnum;
import io.github.kamarias.filter.GatewayServerGlobalFilter;
import io.github.kamarias.filter.context.RequestContext;
import io.github.kamarias.model.BodyDTO;
import io.github.kamarias.properties.GatewayServerProperties;
import io.github.kamarias.support.ModifiedRequestDecorator;
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
 * 解密拦截器
 *
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/6 11:40
 */
@Component
public class DecryptFilter extends GatewayServerGlobalFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(DecryptFilter.class);

    private final CodecConfigurer codecConfigurer;

    private final GatewayServerProperties gatewayServerProperties;

    public DecryptFilter(CodecConfigurer codecConfigurer, GatewayServerProperties gatewayServerProperties) {
        this.codecConfigurer = codecConfigurer;
        this.gatewayServerProperties = gatewayServerProperties;
    }

    @Override
    public String getFilterName() {
        return FilterTypeEnum.DECRYPT_FILTER.getName();
    }

    @Override
    public int getOrder() {
        return FilterTypeEnum.DECRYPT_FILTER.getSort();
    }

    @Override
    public boolean intercept(ServerWebExchange exchange) {
        RequestContext requestContext = ServerWebUtils.getAttribute(exchange, RequestAttributes.REQUEST_CONTEXT);
        return PathMatchUtils.match(gatewayServerProperties.getCryptoWhiteList(), requestContext.getPath());
    }

    @Override
    public Mono<Void> doExecute(ServerWebExchange exchange, GatewayFilterChain chain) {
        return new ModifiedRequestDecorator(codecConfigurer, new RewriteConfig()
                .setRewriteFunction(String.class, String.class, this.rewriteRequest()))
                .filter(exchange, chain);
    }

    /**
     * 重写请求参数
     *
     * @return 重写请求参数规则
     */
    private RewriteFunction rewriteRequest() {
        return (ex, request) -> {
            if (Objects.isNull(request)) {
                return Mono.just("");
            }
            BodyDTO dto = JSON.parseObject(request.toString(), BodyDTO.class);
            String decrypt = "";
            try {
                decrypt = AesUtils.decrypt(dto.getData(), dto.getInit() + "abcdfgf");
            } catch (Exception e) {
                LOGGER.error("请求数据解密异常：{}", dto);
                throw new RuntimeException();
            }
            LOGGER.info("接口请求参数：{}", decrypt);
            return Mono.just(decrypt);
        };
    }

}
