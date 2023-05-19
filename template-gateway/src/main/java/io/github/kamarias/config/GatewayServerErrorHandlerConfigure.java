package io.github.kamarias.config;

import io.github.kamarias.handler.GatewayServerErrorWebExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.filter.reactive.HiddenHttpMethodFilter;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/10 17:43
 */
@Configuration
@Import({WebProperties.Resources.class})
public class GatewayServerErrorHandlerConfigure {

    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayServerErrorHandlerConfigure.class);


    private final ServerProperties serverProperties;

    private final ApplicationContext applicationContext;

    private final WebProperties.Resources resourceProperties;

    private final List<ViewResolver> viewResolvers;

    private final ServerCodecConfigurer serverCodecConfigurer;


    /**
     * Instantiates a new Error handler configuration.
     *
     * @param serverProperties      the server properties
     * @param resourceProperties    the resource properties
     * @param viewResolversProvider the view resolvers provider
     * @param serverCodecConfigurer the server codec configurer
     * @param applicationContext    the application context
     */
    public GatewayServerErrorHandlerConfigure(
            ServerProperties serverProperties,
            ApplicationContext applicationContext,
            WebProperties.Resources resourceProperties,
            final ObjectProvider<List<ViewResolver>> viewResolversProvider,
            ServerCodecConfigurer serverCodecConfigurer) {
        this.serverProperties = serverProperties;
        this.applicationContext = applicationContext;
        this.resourceProperties = resourceProperties;
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    /**
     * Error web exception handler error web exception handler.
     *
     * @param errorAttributes the error attributes
     * @return the error web exception handler
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ErrorWebExceptionHandler errorWebExceptionHandler(final ErrorAttributes errorAttributes) {
        GatewayServerErrorWebExceptionHandler exceptionHandler = new GatewayServerErrorWebExceptionHandler(
                errorAttributes,
                this.resourceProperties,
                this.serverProperties.getError(),
                this.applicationContext);
        exceptionHandler.setViewResolvers(this.viewResolvers);
        exceptionHandler.setMessageWriters(this.serverCodecConfigurer.getWriters());
        exceptionHandler.setMessageReaders(this.serverCodecConfigurer.getReaders());
        return exceptionHandler;
    }

    /**
     * https://github.com/spring-cloud/spring-cloud-gateway/issues/541
     * Hidden http method filter hidden http method filter.
     *
     * @return the hidden http method filter
     */
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter() {
            @Override
            public Mono<Void> filter(final ServerWebExchange exchange, final WebFilterChain chain) {
                return chain.filter(exchange);
            }
        };
    }

}
