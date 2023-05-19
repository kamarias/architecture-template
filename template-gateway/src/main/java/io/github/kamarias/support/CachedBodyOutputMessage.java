package io.github.kamarias.support;

import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/6 16:09
 */
public class CachedBodyOutputMessage implements ReactiveHttpOutputMessage {

    private Boolean isCache = false;

    private final DataBufferFactory bufferFactory;

    private final HttpHeaders httpHeaders;

    // private Flux<DataBuffer> body = Flux.error(new IllegalStateException(
    //         "The body is not set. " + "Did handling complete with success?"));

    private Flux<DataBuffer> body = Flux.empty();

    /**
     * Instantiates a new Cached body output message.
     *
     * @param exchange    the exchange
     * @param httpHeaders the http headers
     */
    public CachedBodyOutputMessage(final ServerWebExchange exchange, final HttpHeaders httpHeaders) {
        this.bufferFactory = exchange.getResponse().bufferFactory();
        this.httpHeaders = httpHeaders;
    }

    @Override
    public void beforeCommit(final Supplier<? extends Mono<Void>> action) {
    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public HttpHeaders getHeaders() {
        return this.httpHeaders;
    }

    @Override
    public DataBufferFactory bufferFactory() {
        return this.bufferFactory;
    }

    /**
     * Is cached.
     *
     * @return boolean
     */
    public Boolean getCache() {
        return this.isCache;
    }

    /**
     * Return the request body, or an error stream if the body was never set or when.
     *
     * @return body as {@link Flux}
     */
    public Flux<DataBuffer> getBody() {
        return this.body;
    }

    /**
     * writeWith.
     *
     * @param body writeWith body
     * @return Mono
     */
    @Override
    public Mono<Void> writeWith(final Publisher<? extends DataBuffer> body) {
        this.isCache = true;
        this.body = Flux.from(body);
        return Mono.empty();
    }

    @Override
    public Mono<Void> writeAndFlushWith(final Publisher<? extends Publisher<? extends DataBuffer>> body) {
        return writeWith(Flux.from(body).flatMap(p -> p));
    }

    @Override
    public Mono<Void> setComplete() {
        return writeWith(Flux.empty());
    }
}
