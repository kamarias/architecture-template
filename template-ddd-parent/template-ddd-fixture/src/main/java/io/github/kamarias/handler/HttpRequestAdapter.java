package io.github.kamarias.handler;

import com.alibaba.fastjson2.JSON;
import io.github.kamarias.bean.HttpFixtureProperties;
import io.github.kamarias.context.HandlerContext;
import io.github.kamarias.enums.RequestAdapterEnum;
import io.github.kamarias.exception.ParamVerifyException;
import io.github.kamarias.vo.Response;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;


/**
 * @author wangyuxing@gogpay.cn
 * @date 2024/1/20 15:36
 */
@Component
@Import(HttpFixtureProperties.class)
public class HttpRequestAdapter implements RequestAdapter, Ordered {


    private final ReactiveDiscoveryClient reactiveDiscoveryClient;

    private final HttpFixtureProperties httpFixtureProperties;


    public HttpRequestAdapter(ReactiveDiscoveryClient reactiveDiscoveryClient, HttpFixtureProperties httpFixtureProperties) {
        this.reactiveDiscoveryClient = reactiveDiscoveryClient;
        this.httpFixtureProperties = httpFixtureProperties;
    }


    @Override
    public boolean supports(HandlerContext handler) {
        return RequestAdapterEnum.HTTP.equals(handler.getRequestType());
    }

    @Override
    public Mono<Response> handle(HandlerContext context) {
        final HttpFixtureProperties.HttpParam httpParam = httpFixtureProperties.getRouter().get(context.getServiceName());
        if (Objects.isNull(httpParam)) {
            throw new ParamVerifyException(9001, "未找到可用服务配置");
        }
        return reactiveDiscoveryClient
                .getInstances(httpParam.getServiceId()).next()
                .flatMap(x -> WebClient.create()
                        .post().uri(x.getUri() + httpParam.getPath())
                        .headers(headers -> headers.set("Content-Type", "application/json"))
                        .bodyValue(JSON.toJSONString(context.getRequest()))
                        .retrieve()
                        .bodyToMono(Response.class));
    }


    @Override
    public int getOrder() {
        return 0;
    }


}
