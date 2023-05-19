package io.github.kamarias.utils;

import io.github.kamarias.dto.ResultDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * 服务响应工具类
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/11 17:28
 */
public class ServerWebResponseUtils {

    private ServerWebResponseUtils() {

    }


    /**
     * 原则上所有的响应http状态都使用该方法返回
     * @param exchange
     * @param result
     * @param <T>
     * @return
     */
    public static <T> Mono<Void> response(ServerWebExchange exchange, ResultDTO result) {
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        exchange.getResponse().setStatusCode(HttpStatus.OK);
        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse()
                .bufferFactory().wrap(Objects.requireNonNull(result.toString()).getBytes())));
    }

}
