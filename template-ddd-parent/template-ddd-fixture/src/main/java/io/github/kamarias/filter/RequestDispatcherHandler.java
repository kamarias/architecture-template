package io.github.kamarias.filter;

import io.github.kamarias.context.HandlerContext;
import io.github.kamarias.handler.RequestAdapter;
import io.github.kamarias.vo.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2024/1/20 14:37
 */
@Component
public class RequestDispatcherHandler implements GlobalHandlerFilter, Ordered, ApplicationContextAware {


    private static final Logger LOGGER = LoggerFactory.getLogger(RequestDispatcherHandler.class);


    @Nullable
    private List<RequestAdapter> handlerAdapters;


    public RequestDispatcherHandler(@Nullable List<RequestAdapter> handlerAdapters) {
        this.handlerAdapters = handlerAdapters;
    }

    @Override
    public Mono<Void> filter(HandlerContext context, HandlerFilterChain chain) {
        context.setResponse(this.invokeHandler(context));
        return chain.filter(context);
    }

    private Mono<Response> invokeHandler(HandlerContext context) {
        if (this.handlerAdapters != null) {
            for (RequestAdapter handlerAdapter : this.handlerAdapters) {
                if (handlerAdapter.supports(context)) {
                    return handlerAdapter.handle(context);
                }
            }
        }
        LOGGER.error("No HandlerAdapter: " + context);
        throw new IllegalStateException("No HandlerAdapter: " + context);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.initStrategies(applicationContext);
    }

    protected void initStrategies(ApplicationContext context) {
        Map<String, RequestAdapter> adapterBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(
                context, RequestAdapter.class, true, false);
        this.handlerAdapters = new ArrayList<>(adapterBeans.values());
        AnnotationAwareOrderComparator.sort(this.handlerAdapters);
    }

}
