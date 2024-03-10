package io.github.kamarias;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.TimeZone;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2024/1/15 22:20
 */
@SpringBootApplication
public class TemplateFixtureApplication {


    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateFixtureApplication.class);

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    }

    public static void main(String[] args) {
        SpringApplication.run(TemplateFixtureApplication.class, args);
        LOGGER.info(
                "\n\n" +
                        "███████╗██╗   ██╗ ██████╗ ██████╗███████╗███████╗███████╗  \n" +
                        "██╔════╝██║   ██║██╔════╝██╔════╝██╔════╝██╔════╝██╔════╝  \n" +
                        "███████╗██║   ██║██║     ██║     █████╗  ███████╗███████╗  \n" +
                        "╚════██║██║   ██║██║     ██║     ██╔══╝  ╚════██║╚════██║  \n" +
                        "███████║╚██████╔╝╚██████╗╚██████╗███████╗███████║███████║  \n" +
                        "╚══════╝ ╚═════╝  ╚═════╝ ╚═════╝╚══════╝╚══════╝╚══════╝  \n");
    }


}
