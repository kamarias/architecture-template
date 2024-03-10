package io.github.kamarias.controller;

import com.alibaba.fastjson2.JSON;
import io.github.kamarias.context.HandlerContext;
import io.github.kamarias.handler.FilteringHandler;
import io.github.kamarias.params.ParamMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;

/**
 * 请求服务入口
 *
 * @author wangyuxing@gogpay.cn
 * @date 2024/1/15 23:12
 */
@RestController
public class FixtureHandlerController {


    private final FilteringHandler handler;


    public FixtureHandlerController(FilteringHandler filteringHandler) {
        this.handler = filteringHandler;
    }

    /**
     * 接入层处理方法
     *
     * @param param 请求入参
     * @return 响应结果
     */
    @PostMapping("/")
    public Mono<Object> fixture(@RequestBody ParamMap param) {
        return Mono.defer(() -> Mono.just(param))
                .map(this::inputConvert)
                .flatMap(x -> handler.handler(x).then(Mono.just(x)))
                .flatMap(HandlerContext::getResponse)
                .map(response -> {
                    HashMap<String, Object> stringObjectHashMap = new HashMap<>();
                    stringObjectHashMap.put("name", response.getName());
                    stringObjectHashMap.put("age", 18);
                    return stringObjectHashMap;
                });
    }

    /**
     * 请求入参转换方法
     *
     * @param map 请求的json对象
     * @return 返回转换后的上下文对象
     */
    private HandlerContext inputConvert(ParamMap map) {
        map.verify();
        HandlerContext context = new HandlerContext();
        context.setInData(map);
        context.setPublicParam(JSON.parseObject(JSON.toJSONString(map.get(ParamMap.PUBLIC)), ParamMap.class));
        context.setPrivateParam(JSON.parseObject(JSON.toJSONString(map.get(ParamMap.PRIVATE)), ParamMap.class));
        return context;
    }


}
