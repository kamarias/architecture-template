package io.github.kamarias.config.rabbitmq;

/**
 * rabbitmq 交换机、队列、配置常量 类
 * @author wangyuxing@gogpay.cn
 * @Date 2023/3/29 11:13
 */
public interface RabbitMqConstants  {


    /**
     * 直连交换机
     */
    String DIRECT_EXCHANGE = "direct_exchange";

    /**
     * 延时交换机
     */
    String DELAYED_EXCHANGE = "delayed_exchange";

    /**
     * 过期直连交换机
     */
    String TTL_DIRECT_EXCHANGE = "ttl_direct_exchange";

    /**
     * 死信直连交换机
     */
    String DLE_DIRECT_EXCHANGE = "dle_direct_exchange";


    /**
     * 应用队列
     */
    String APP_QUEUE = "app_queue";

    /**
     * 过期队列
     */
    String TTL_QUEUE = "ttl_queue";

    /**
     * 延时队列
     */
    String DELAY_QUEUE = "delayed_queue";

    /**
     * 延时队列
     */
    String DLE_QUEUE = "dle_queue";

}
