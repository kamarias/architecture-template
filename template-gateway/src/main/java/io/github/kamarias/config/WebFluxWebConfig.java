package io.github.kamarias.config;

import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * WebFlux 服务配置
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/11 11:10
 */
@Component
@EnableWebFlux
public class WebFluxWebConfig implements WebFluxConfigurer {


    /**
     * 重新设置 WebFlux 的最大内存限制，默认值为：256kb
     * @param configurer
     */
    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        configurer.defaultCodecs().maxInMemorySize(524288000);
    }

}
