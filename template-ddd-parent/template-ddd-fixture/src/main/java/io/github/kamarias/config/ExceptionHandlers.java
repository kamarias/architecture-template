package io.github.kamarias.config;

import io.github.kamarias.exception.ParamVerifyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2024/2/3 11:02
 */
@RestControllerAdvice
public class ExceptionHandlers {


    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlers.class);

//    @ExceptionHandler(RuntimeException.class)
//    public Mono<Object> serverExceptionHandler(Exception ex) {
//        return Mono.defer(() -> Mono.just(ex));
//    }

    @ExceptionHandler(ParamVerifyException.class)
    public Mono<Object> serverExceptionHandler(ParamVerifyException ex) {
        return Mono.defer(() -> {
            Map<String, Object> map = new LinkedHashMap<>(4);
            map.put("code", ex.getCode());
            map.put("msg", ex.getMessage());
            return Mono.justOrEmpty(map);
        });
    }


}
