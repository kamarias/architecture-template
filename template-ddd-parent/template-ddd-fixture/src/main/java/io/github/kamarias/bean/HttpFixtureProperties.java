package io.github.kamarias.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


/**
 * @author wangyuxing@gogpay.cn
 * @date 2024/1/29 22:27
 */
@ConfigurationProperties(HttpFixtureProperties.PREFIX)
public class HttpFixtureProperties {

    protected static final String PREFIX = "fixture.http";

    private Map<String, HttpParam> router;


    public Map<String, HttpParam> getRouter() {
        return router;
    }

    public void setRouter(Map<String, HttpParam> router) {
        this.router = router;
    }

    public static class HttpParam {

        private String serviceId;

        private String path;


        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }

}
