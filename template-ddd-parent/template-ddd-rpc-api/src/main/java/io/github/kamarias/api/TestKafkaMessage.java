package io.github.kamarias.api;


import io.github.kamarias.event.KafkaMessageEvent;
import io.github.kamarias.observer.AbstractKafkaMessageMessageObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2024/3/8 22:46
 */
@Component // 注入spring容器才能生效哦
public class TestKafkaMessage extends AbstractKafkaMessageMessageObserver {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestKafkaMessage.class);


    @Override
    public void handler(KafkaMessageEvent<?, ?> event) {
        System.out.println(event.getSource());
        System.out.println(event.getMessage());
        System.out.println("收到 kafka消息事件！！！");
        LOGGER.info("==================================");
    }

    @Override
    public boolean check(KafkaMessageEvent<?, ?> event) {
        // 接收到事件是否处理: true处理事件，false不处理事件
        return true;
    }

}
