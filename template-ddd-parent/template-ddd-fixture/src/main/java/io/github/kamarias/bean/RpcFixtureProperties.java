package io.github.kamarias.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2024/1/29 22:27
 */
@ConfigurationProperties(RpcFixtureProperties.PREFIX)
public class RpcFixtureProperties {

    public static final String PREFIX = "fixture.rpc";

    private Map<String, RpcParam> router;


    public Map<String, RpcParam> getRouter() {
        return router;
    }

    public void setRouter(Map<String, RpcParam> router) {
        this.router = router;
    }

    public static class RpcParam {

        private String interfaceName;


        private String method;


        public String getInterfaceName() {
            return interfaceName;
        }

        public void setInterfaceName(String interfaceName) {
            this.interfaceName = interfaceName;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }
    }

}
