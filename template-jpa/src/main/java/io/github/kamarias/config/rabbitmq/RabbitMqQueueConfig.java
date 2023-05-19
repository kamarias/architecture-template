package io.github.kamarias.config.rabbitmq;
//
//import com.google.common.collect.Maps;
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.amqp.support.converter.MessageConverter;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * rabbitMq 队列配置类
// * @author wangyuxing@gogpay.cn
// * @date 2023/3/29 11:11
// */
//@Configuration
//public class RabbitMqQueueConfig {
//
//    /**
//     * 直连交换机
//     */
//    @Bean
//    DirectExchange directExchange(){
//        return ExchangeBuilder.directExchange(RabbitMqConstants.DIRECT_EXCHANGE)
//                .durable(true)
//                .build();
//    }
//
//    /**
//     * 应用队列
//     */
//    @Bean
//    Queue appQueue(){
//        return QueueBuilder
//                .durable(RabbitMqConstants.APP_QUEUE)
//                .build();
//    }
//
//    /**
//     * 绑定队列（应用队列绑定到直连交换机）
//     */
//    @Bean
//    Binding appBindDirect(){
//        return BindingBuilder.bind(appQueue())
//                .to(directExchange()).withQueueName();
//    }
//
//    /**
//     * 过期直连交换机（只能在规定时间内被消费）
//     */
//    @Bean
//    DirectExchange ttlDirectExchange(){
//        return ExchangeBuilder.directExchange(RabbitMqConstants.TTL_DIRECT_EXCHANGE)
//                .durable(true)
//                .build();
//    }
//
//    /**
//     * 过期队列（）
//     */
//    @Bean
//    Queue ttlQueue(){
//        Map<String, Object> args = new HashMap<>(1);
//        // 该消息在次队列中可被消费的最大时间
//        args.put("x-message-ttl",5000);
//        return QueueBuilder
//                .durable(RabbitMqConstants.TTL_QUEUE)
//                .withArguments(args)
//                .build();
//    }
//
//    /**
//     * 过期队列绑定过期队列
//     */
//    @Bean
//    Binding ttlBindTtlDirect(){
//        return BindingBuilder.bind(ttlQueue()).to(ttlDirectExchange()).with(RabbitMqConstants.TTL_QUEUE);
//    }
//
//    /**
//     * 延时交换机 （需要安装插件 rabbitmq_delayed_message_exchange）
//     */
//    @Bean
//    CustomExchange delayedExchange(){
//        Map<String, Object> args = Maps.newHashMap();
//        args.put("x-delayed-type", "direct");
//        return new CustomExchange(RabbitMqConstants.DELAYED_EXCHANGE, "x-delayed-message", true, false, args);
//    }
//
//
//    /**
//     * 延时队列
//     */
//    @Bean
//    Queue delayQueue(){
//        return QueueBuilder
//                .durable(RabbitMqConstants.DELAY_QUEUE)
//                .build();
//    }
//
//    /**
//     * 延时队列绑定延时交换机
//     * @param queue 延时队列
//     * @param exchange 延时交换机
//     */
//    @Bean
//    Binding binding(@Qualifier("delayQueue") Queue queue, @Qualifier("delayedExchange") CustomExchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with(RabbitMqConstants.DELAY_QUEUE).noargs();
//    }
//
//    /**
//     * 死信直连交换机（过期 || 消息被拒 || 队列长度最大时）
//     */
//    @Bean
//    DirectExchange dleDirectExchange(){
//        return ExchangeBuilder.directExchange(RabbitMqConstants.DLE_DIRECT_EXCHANGE)
//                .durable(true)
//                .build();
//    }
//
//    /**
//     * 死信队列
//     */
//    @Bean
//    Queue dleQueue(){
//        return QueueBuilder
//                .durable(RabbitMqConstants.DLE_QUEUE)
//                .build();
//    }
//
//    /**
//     * 死信队列绑定死信交换机
//     * 使用方法：在创建队列时通过 args 参数配置死信交换机和路由key
//     *    HashMap<String, Object> args = new HashMap<>();
//     *    args.put("x-message-ttl",5000);     //过期时间
//     *    args.put("x-dead-letter-exchange", RabbitMqConstants.DLE_DIRECT_EXCHANGE);     //绑定的死信交换机
//     *    args.put("x-dead-letter-routing-key", RabbitMqConstants.DLE_QUEUE);    //  死信交换机的路由key
//     *    return new Queue("ttl.direct.queue",true,false,false,args);
//     */
//    @Bean
//    Binding dleBindDleDirect(){
//        // 绑定队列
//        return BindingBuilder.bind(dleQueue()).to(dleDirectExchange()).withQueueName();
//    }
//
//    /**
//     * 配置rabbitmq 消息序列化方式
//     * @return 返回rabbitMq的序列化方式
//     */
//    @Bean
//    public MessageConverter messageConverter(){
//        return new Jackson2JsonMessageConverter();
//    }
//
//}
