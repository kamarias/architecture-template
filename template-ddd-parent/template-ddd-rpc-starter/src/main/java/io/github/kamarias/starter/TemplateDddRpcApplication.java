package io.github.kamarias.starter;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.TimeZone;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/12/19 22:29
 */
@SpringBootApplication(scanBasePackages = {"io.github.kamarias"})
@EntityScan("io.github.kamarias.infrastructure.entity")
@EnableJpaRepositories("io.github.kamarias.infrastructure.repository")
@EnableDubbo(scanBasePackages = {"io.github.kamarias.api"})
public class TemplateDddRpcApplication {


    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateDddRpcApplication.class);

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    }

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(TemplateDddRpcApplication.class, args);
        LOGGER.info(
                "\n\n" +
                        "███████╗██╗   ██╗ ██████╗ ██████╗███████╗███████╗███████╗  \n" +
                        "██╔════╝██║   ██║██╔════╝██╔════╝██╔════╝██╔════╝██╔════╝  \n" +
                        "███████╗██║   ██║██║     ██║     █████╗  ███████╗███████╗  \n" +
                        "╚════██║██║   ██║██║     ██║     ██╔══╝  ╚════██║╚════██║  \n" +
                        "███████║╚██████╔╝╚██████╗╚██████╗███████╗███████║███████║  \n" +
                        "╚══════╝ ╚═════╝  ╚═════╝ ╚═════╝╚══════╝╚══════╝╚══════╝  \n");
        Environment env = applicationContext.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        LOGGER.info("\n\t----------------------------------------------------------\n\t" +
                "Application  is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port  + "/\n\t" +
                "External: \thttp://" + ip + ":" + port + "/\n\t" +
                "----------------------------------------------------------");
    }


}
