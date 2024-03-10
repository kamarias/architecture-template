package io.github.kamarias.api;

import io.github.kamarias.application.UserExecute;
import io.github.kamarias.event.MessageEvent;
import io.github.kamarias.infrastructure.entity.UserEntity;
import io.github.kamarias.infrastructure.repository.UserEntityRepository;
import io.github.kamarias.model.UserDto;
import io.github.kamarias.vo.Request;
import io.github.kamarias.vo.Response;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/12/17 23:02
 */

@RestController
public class UserController {

    private final UserExecute userExecute;

    private final UserEntityRepository userEntityRepository;

    private KafkaTemplate<String, Object> kafkaTemplate;

    private final ApplicationEventPublisher applicationEventPublisher;

    public UserController(UserExecute userExecute, UserEntityRepository userEntityRepository, KafkaTemplate<String, Object> kafkaTemplate, ApplicationEventPublisher applicationEventPublisher) {
        this.userExecute = userExecute;
        this.userEntityRepository = userEntityRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @PostMapping("/addUser")
    public Response test(@RequestBody Request request){
        UserDto userDto = new UserDto();
        userDto.setUserName("玩游戏");
        userDto.setPassWord("123456");
        userExecute.saveUser(userDto);
        Response response = new Response();
        response.setName("http");
        MessageEvent<String> testEvent = new MessageEvent<>(this, "测试！！！");
        applicationEventPublisher.publishEvent(testEvent);
        kafkaTemplate.send("test","你好").addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println(ex);
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                System.out.println(result);
            }
        });
        return response;
    }

    @Bean
    public NewTopic topicHello(){
        return TopicBuilder.name("test").build();
    }

    @PostMapping("/list")
    public Response getList(@RequestBody Request map){
        System.out.println(map);
        List<UserEntity> all = userEntityRepository.findAll();
        return new Response();
    }

    public String abc(){
        return "12345";
    }

}
