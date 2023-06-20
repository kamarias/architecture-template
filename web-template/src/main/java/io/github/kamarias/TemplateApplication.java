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
 * @date 2023/6/20 20:50
 */
@SpringBootApplication
public class TemplateApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateApplication.class);

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    }

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(TemplateApplication.class, args);
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
        String path = env.getProperty("server.servlet.context-path");
        LOGGER.info("\n\t----------------------------------------------------------\n\t" +
                "Application  is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + path + "\n\t" +
                "External: \thttp://" + ip + ":" + port + path + "\n\t" +
                "----------------------------------------------------------");
    }


}
