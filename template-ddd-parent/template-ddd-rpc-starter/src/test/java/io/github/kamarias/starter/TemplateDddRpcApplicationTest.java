package io.github.kamarias.starter;

import io.github.kamarias.api.sub.IDemoService;
import io.github.kamarias.vo.Request;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/12/19 23:36
 */
@SpringBootTest
public class TemplateDddRpcApplicationTest {

    @DubboReference
    private IDemoService demoService;


    @Test
    void contextLoads() {
        System.out.println(demoService.sayHello(new Request()));
    }

}
