package io.github.kamarias.filter;

import io.github.kamarias.context.HandlerContext;
import io.github.kamarias.enums.RequestAdapterEnum;
import io.github.kamarias.params.ParamMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2024/2/3 16:07
 */
@Component
public class RequestAdapterFilter implements Ordered,GlobalHandlerFilter{

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestAdapterFilter.class);

    @Override
    public Mono<Void> filter(HandlerContext context, HandlerFilterChain chain) {
        this.inputConvert(context);
        return chain.filter(context);
    }

    /**
     * 请求入参转换方法
     *
     * @param context 请求上下文
     */
    private void inputConvert(HandlerContext context) {
        final ParamMap publicParam = context.getPublicParam();
        RequestAdapterEnum adapterEnum = RequestAdapterEnum.
                getRequestEnumByName(String.valueOf(publicParam.get(ParamMap.SERVICE_TYPE)));
        context.setServiceName(String.valueOf(publicParam.get(ParamMap.SERVICE_NAME)));
        switch (adapterEnum) {
            case RPC:
                context.setRequestType(RequestAdapterEnum.RPC);
                break;
            case HTTP:
                context.setRequestType(RequestAdapterEnum.HTTP);
                break;
            default:
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
