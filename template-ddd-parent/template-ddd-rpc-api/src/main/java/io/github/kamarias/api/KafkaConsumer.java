package io.github.kamarias.api;


import io.github.kamarias.event.KafkaMessageEvent;
import io.github.kamarias.message.KafkaMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {


    private final ApplicationEventPublisher applicationEventPublisher;

    public KafkaConsumer(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * 消费监听
     * @param record 读取的记录
     */
    @KafkaListener(topics = "test", groupId = "${spring.application.name}")
    public void listenAllTopics(ConsumerRecord<String, String> record) {
        applicationEventPublisher.publishEvent(new KafkaMessageEvent<>(this,
                new KafkaMessage<>(
                        record.topic(), record.partition(), record.offset(),  record.timestamp(),
                        record.timestampType(), record.serializedKeySize(), record.serializedValueSize(),
                        record.key(), record.value(), record.headers(), record.leaderEpoch()
                )));
    }

}
