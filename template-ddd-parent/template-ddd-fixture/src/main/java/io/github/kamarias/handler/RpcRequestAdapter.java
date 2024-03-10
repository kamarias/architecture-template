package io.github.kamarias.handler;

import io.github.kamarias.bean.RpcFixtureProperties;
import io.github.kamarias.context.HandlerContext;
import io.github.kamarias.enums.RequestAdapterEnum;
import io.github.kamarias.exception.ParamVerifyException;
import io.github.kamarias.utils.SpringBeanFactoryUtils;
import io.github.kamarias.vo.Request;
import io.github.kamarias.vo.Response;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.rpc.model.FrameworkModel;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2024/1/20 15:20
 */
@Component
@Import(RpcFixtureProperties.class)
public class RpcRequestAdapter implements RequestAdapter, Ordered {


    @Value("spring.application.name")
    private String appName;



    private final ConfigurableListableBeanFactory configurableListableBeanFactory;

    private final RpcFixtureProperties rpcFixtureProperties;


    public RpcRequestAdapter(ConfigurableListableBeanFactory configurableListableBeanFactory, RpcFixtureProperties rpcFixtureProperties) {
        this.configurableListableBeanFactory = configurableListableBeanFactory;
        this.rpcFixtureProperties = rpcFixtureProperties;
    }

    @Override
    public boolean supports(HandlerContext handler) {
        return RequestAdapterEnum.RPC.equals(handler.getRequestType());
    }


    @Override
    public Mono<Response> handle(HandlerContext context) {
        final RpcFixtureProperties.RpcParam rpcParam = rpcFixtureProperties.getRouter().get(context.getServiceName());
        if (Objects.isNull(rpcParam)){
            throw new ParamVerifyException(9001, "未找到可用服务配置");
        }
        Object objectBean;
        Class<?> aClass;
        try {
            aClass = Class.forName(rpcParam.getInterfaceName());
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        try {
            objectBean = SpringBeanFactoryUtils.getBean(aClass);
        } catch (NoSuchBeanDefinitionException e) {
            objectBean = getReference(aClass);
            configurableListableBeanFactory.registerSingleton(ClassUtils.getShortNameAsProperty(aClass), objectBean);
            objectBean = SpringBeanFactoryUtils.getBean(aClass);
        }
        Method sayHello = ReflectionUtils.findMethod(aClass, rpcParam.getMethod(), Request.class);
        if (Objects.isNull(sayHello)){
            throw new IllegalArgumentException("方法找不到异常");
        }
        Object finalObjectBean = objectBean;
        return Mono.defer(()-> Mono.justOrEmpty((Response) ReflectionUtils.invokeMethod(sayHello,
                finalObjectBean, new Request())));
    }


    @Override
    public int getOrder() {
        return 0;
    }


    private <T> T getReference(Class<T> interfaceClass) {
        ReferenceConfig<T> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface(interfaceClass);
        referenceConfig.setScopeModel(FrameworkModel.defaultModel().getParent());
        return referenceConfig.get();
    }

}
