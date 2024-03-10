package io.github.kamarias.api;

import io.github.kamarias.application.UserExecute;
import io.github.kamarias.model.UserDto;
import io.github.kamarias.api.sub.IDemoService;
import io.github.kamarias.vo.Request;
import io.github.kamarias.vo.Response;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/12/19 23:17
 */
@DubboService
public class DemoServiceImpl implements IDemoService {

    private final UserExecute userExecute;

    public DemoServiceImpl(UserExecute userExecute) {
        this.userExecute = userExecute;
    }

    @Override
    public Response sayHello(Request name) {
        UserDto userDto = new UserDto();
        userDto.setUserName("rpc");
        userDto.setPassWord("970699");
        userExecute.saveUser(userDto);
        Response response = new Response();
        response.setName("rpc");
        return response;
    }

    @Bean
    public NewTopic topicHello(){
        return TopicBuilder.name("test").build();
    }


}
