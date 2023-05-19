package io.github.kamarias.support;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.codec.CodecConfigurer;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

/**
 * 修改请求装饰器
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/6 12:57
 */
public class ModifiedRequestDecorator {


    private List<HttpMessageReader<?>> messageReaders;
    private final RewriteConfig config;

    public ModifiedRequestDecorator(CodecConfigurer codecConfigurer, RewriteConfig rewriteConfig) {
        this.config = rewriteConfig;
        this.messageReaders = codecConfigurer.getReaders();
    }

    public ModifiedRequestDecorator(ServerWebExchange exchange, RewriteConfig config) {
        this.config = config;
        this.messageReaders = HandlerStrategies.withDefaults().messageReaders();
    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Class inClass = config.getInClass();
        ServerRequest serverRequest = ServerRequest.create(exchange, messageReaders);

        // TODO: flux or mono
        Mono<?> modifiedBody = serverRequest.bodyToMono(inClass)
                .switchIfEmpty(Mono.defer(() -> (Mono) config.getRewriteFunction().apply(exchange, null)))
                .flatMap(originalBody -> config.getRewriteFunction().apply(exchange, originalBody));

        BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, config.getOutClass());
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(exchange.getRequest().getHeaders());

        // the new content type will be computed by bodyInserter
        // and then set in the request decorator
        headers.remove(HttpHeaders.CONTENT_LENGTH);

        // if the body is changing content types, set it here, to the bodyInserter
        // will know about it
        if (config.getContentType() != null) {
            headers.set(HttpHeaders.CONTENT_TYPE, config.getContentType());
        }
        CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);
        return bodyInserter.insert(outputMessage, new BodyInserterContext())
                // .log("modify_request", Level.INFO)
                .then(Mono.defer(() -> {
                    ServerHttpRequest decorator = decorate(exchange, headers, outputMessage);
                    return chain.filter(exchange.mutate().request(decorator).build());
                })).onErrorResume((Function<Throwable, Mono<Void>>) throwable -> release(exchange,
                        outputMessage, throwable));
    }

    protected Mono<Void> release(ServerWebExchange exchange, CachedBodyOutputMessage outputMessage,
                                 Throwable throwable) {
        return outputMessage.getBody().map(DataBufferUtils::release).then(Mono.error(throwable));
    }

    ServerHttpRequestDecorator decorate(ServerWebExchange exchange, HttpHeaders headers,
                                        CachedBodyOutputMessage outputMessage) {
        return new ServerHttpRequestDecorator(exchange.getRequest()) {
            @Override
            public HttpHeaders getHeaders() {
                long contentLength = headers.getContentLength();
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.putAll(headers);
                if (contentLength > 0) {
                    httpHeaders.setContentLength(contentLength);
                } else {
                    // TODO: this causes a 'HTTP/1.1 411 Length Required' // on
                    // httpbin.org
                    httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                }
                return httpHeaders;
            }

            @Override
            public Flux<DataBuffer> getBody() {
                return outputMessage.getBody();
            }
        };
    }


}
