package io.github.kamarias.properties;

import com.alibaba.fastjson.JSON;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 网关服务配置
 *
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/11 11:21
 */
@Component
@ConfigurationProperties(prefix = GatewayServerProperties.PREFIX)
public class GatewayServerProperties {

    public final static String PREFIX = "spring.gateway.service";


    /**
     * 请求白名单
     */
    private List<String> tokenWhiteList;

    /**
     * 加解密白名单
     */
    private List<String> cryptoWhiteList;

    public List<String> getCryptoWhiteList() {
        return cryptoWhiteList;
    }

    public void setCryptoWhiteList(List<String> cryptoWhiteList) {
        this.cryptoWhiteList = cryptoWhiteList;
    }

    public List<String> getTokenWhiteList() {
        return tokenWhiteList;
    }

    public void setTokenWhiteList(List<String> tokenWhiteList) {
        this.tokenWhiteList = tokenWhiteList;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
