package io.github.kamarias.filter.context;

import io.github.kamarias.constant.Constant;
import io.github.kamarias.constant.HeaderConstant;
import io.github.kamarias.constant.SignHeaderConstant;
import io.github.kamarias.model.AppHeaderDTO;
import io.github.kamarias.properties.GatewayServerProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Objects;

/**
 * 默认的上下文构造器
 *
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/11 15:21
 */
@Component
public class DefaultContextBuilder implements ContextBuilder {


    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultContextBuilder.class);

    private static final String NONE = "none";

    private final GatewayServerProperties gatewayServerProperties;

    public DefaultContextBuilder(GatewayServerProperties gatewayServerProperties) {
        this.gatewayServerProperties = gatewayServerProperties;
    }

    @Override
    public RequestContext buildRequestContext(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        final String sign = headers.getFirst(SignHeaderConstant.SIGN);
        LOGGER.info("sign: {}", sign);
        final String timestamp = headers.getFirst(SignHeaderConstant.TIMESTAMP);
        LOGGER.info("timestamp: {}", timestamp);
        final String appKey = headers.getFirst(SignHeaderConstant.APP_KEY);
        LOGGER.info("appKey: {}", appKey);
        final String token = headers.getFirst(SignHeaderConstant.ACCESS_TOKEN_HEADER);
        LOGGER.info("token: {}", token);

        RequestContext requestContext = new RequestContext();
        String path = request.getURI().getPath();
        LOGGER.info("path: {}", path);

        String[] splitList = StringUtils.split(path, "/");
        // 应用上下文
        String contextPath = "";
        if (splitList.length > 0) {
            contextPath = "/" + splitList[0];
        }
        String realPath = path.substring(contextPath.length());
        requestContext.setRealUrl(realPath);
        requestContext.setPath(path);
        requestContext.setContextPath(contextPath);
        requestContext.setMethod(request.getMethodValue());
        requestContext.setSign(sign);
        requestContext.setTimestamp(timestamp);
        requestContext.setAppKey(appKey);
        requestContext.setUserAgent(request.getHeaders().getFirst(SignHeaderConstant.USER_AGENT));

        HttpMethod method = request.getMethod();
        if (Objects.nonNull(method)) {
            requestContext.setHttpMethod(method.name());
        }
        requestContext.setAppHeaderDTO(buildAppHeaderDTO(request));
        // @TODO 加解密AESkey，先默认写死，后期动态生成
        requestContext.setAesKey(Constant.OFFSET_KEY + "abcdfgf");
        // @TODO requestContext 可根据业务实际需要注入更多属性
        return requestContext;
    }


    @Override
    public AppHeaderDTO buildAppHeaderDTO(ServerHttpRequest request) {
        AppHeaderDTO appHeaderDTO = new AppHeaderDTO();
        String clientId = request.getHeaders().getFirst(HeaderConstant.CLIENT_ID);
        appHeaderDTO.setClientId(noneToEmptyStr(clientId));
        String deviceId = request.getHeaders().getFirst(HeaderConstant.DEVICE_ID);
        appHeaderDTO.setDeviceId(deviceId);
        String netType = request.getHeaders().getFirst(HeaderConstant.NET_TYPE);
        appHeaderDTO.setNetType(netType);
        // @todo 暂时注入这些，需要时在添加
        return appHeaderDTO;
    }


    /**
     * none转空字符串
     *
     * @param str String
     * @return String
     */
    private static String noneToEmptyStr(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        if (NONE.equalsIgnoreCase(str)) {
            return "";
        }
        return str.trim();
    }

}
