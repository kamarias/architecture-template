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
 * @date 2023/5/5 12:54
 */
@SpringBootApplication
public class GatewayServerApplication {


    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayServerApplication.class);

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    }

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(GatewayServerApplication.class, args);
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
