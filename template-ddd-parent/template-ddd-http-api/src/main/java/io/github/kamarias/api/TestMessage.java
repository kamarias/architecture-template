package io.github.kamarias.api;


import io.github.kamarias.event.MessageEvent;
import io.github.kamarias.observer.AbstractMessageObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2024/3/8 22:49
 */
@Component // 注入spring容器才能生效哦
public class TestMessage extends AbstractMessageObserver {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestMessage.class);


    @Transactional
    @Override
    public void handler(MessageEvent<?> event) {
        UserController source = (UserController) event.getSource();
        System.out.println(source.abc());
        System.out.println(event.getSource());
        System.out.println(event.getMessage());
        System.out.println("收到 本地 消息事件！！！");
        LOGGER.info("=================");
    }

    @Override
    public boolean check(MessageEvent<?> event) {
        // 接收到事件是否处理: true处理事件，false不处理事件
        return event.getMessage() instanceof String;
    }
}
