package io.github.kamarias.utils;


import io.github.kamarias.constant.HeaderConstant;
import io.github.kamarias.constant.SignHeaderConstant;
import io.github.kamarias.filter.context.RequestContext;
import io.github.kamarias.model.AppHeaderDTO;
import io.github.kamarias.utils.string.StringUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.Objects;

/**
 * 请求头工具类
 *
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/11 18:30
 */
public class HttpHeaderUtils {


    private HttpHeaderUtils() {

    }

    /**
     * http请求头构造
     *
     * @param requestContext 请求上下文
     * @return 返回结果
     */
    public static ServerHttpRequest.Builder buildHeader(ServerHttpRequest.Builder builder, RequestContext requestContext) {
        // @todo 构造的请求头需要根据业务来定义
        AppHeaderDTO headerDTO = requestContext.getAppHeaderDTO();
        if (Objects.nonNull(headerDTO)){
            if (StringUtils.isNotEmpty(headerDTO.getClientId())){
                builder.header(SignHeaderConstant.TIMESTAMP, headerDTO.getClientId());
            }
        }
        builder.header(HeaderConstant.CLIENT_ID, "客户端Id");
        return builder;
    }

}
