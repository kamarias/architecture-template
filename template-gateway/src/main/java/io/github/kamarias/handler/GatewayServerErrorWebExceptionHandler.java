package io.github.kamarias.handler;

import com.alibaba.fastjson.JSON;
import io.github.kamarias.dto.ResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;

/**
 * 网关异常拦截器
 *
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/10 10:37
 */
public class GatewayServerErrorWebExceptionHandler extends DefaultErrorWebExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayServerErrorWebExceptionHandler.class);


    /**
     * Create a new {@code DefaultErrorWebExceptionHandler} instance.
     *
     * @param errorAttributes    the error attributes
     * @param resources          the resources configuration properties
     * @param errorProperties    the error configuration properties
     * @param applicationContext the current application context
     * @since 2.4.0
     */
    public GatewayServerErrorWebExceptionHandler(
            final ErrorAttributes errorAttributes,
            final WebProperties.Resources resources,
            final ErrorProperties errorProperties,
            final ApplicationContext applicationContext) {
        super(errorAttributes, resources, errorProperties, applicationContext);
    }


    /**
     * 请求错误路由处理器
     *
     * @param errorAttributes the {@code ErrorAttributes} instance to use to extract error
     *                        information
     * @return 返回匹配的响应错误值
     */
    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::errorResponseHandler);
    }

    @Override
    protected void logError(ServerRequest request, ServerResponse response, Throwable throwable) {
        LOGGER.error("网关异常：{}", formatError(throwable, request));
        if (Objects.nonNull(HttpStatus.resolve(response.rawStatusCode())) && response.statusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)){
            LOGGER.error("网关服务异常：{}", formatRequest(request), throwable);
        }
    }

    /**
     * 错误响应处理程序
     *
     * @param request
     * @return
     */
    private Mono<ServerResponse> errorResponseHandler(ServerRequest request) {
        Map<String, Object> attributes = getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.ALL));
        HttpStatus httpStatus = HttpStatus.resolve(getHttpStatus(attributes));
        ResultDTO dto;
        switch (httpStatus) {
            case NOT_FOUND:
                dto = new ResultDTO<>(httpStatus.value(), "接口不存在");
                break;
            case METHOD_NOT_ALLOWED:
                dto = new ResultDTO(httpStatus.value(), "请求方法不支持");
                break;
            case REQUEST_TIMEOUT:
                dto = new ResultDTO(httpStatus.value(), "请求超时");
                break;
            case UNAUTHORIZED:
                dto = new ResultDTO(httpStatus.value(), "请求未授权");
                break;
            case PAYMENT_REQUIRED:
                dto = new ResultDTO(httpStatus.value(), "请求参数错误");
                break;
            case INTERNAL_SERVER_ERROR:
                dto = new ResultDTO(httpStatus.value(), "服务异常");
                break;
            case BAD_GATEWAY:
                dto = new ResultDTO(httpStatus.value(), "网关异常");
                break;
            case SERVICE_UNAVAILABLE:
                dto = new ResultDTO(httpStatus.value(), "暂停服务");
                break;
            case GATEWAY_TIMEOUT:
                dto = new ResultDTO(httpStatus.value(), "网关超时");
                break;
            case HTTP_VERSION_NOT_SUPPORTED:
                dto = new ResultDTO(httpStatus.value(), "不支持的http协议");
                break;
            default:
                dto = new ResultDTO(httpStatus.value(), httpStatus.getReasonPhrase());
        }
        return ServerResponse.status(httpStatus.value()).contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(JSON.parseObject(dto.toString(), Map.class)));
    }



    private String formatError(Throwable ex, ServerRequest request) {
        String reason = ex.getClass().getName() + ": " + ex.getMessage();
        return "Resolved [" + reason + "] for HTTP " + request.methodName() + " " + request.path();
    }

    private String formatRequest(ServerRequest request) {
        String rawQuery = request.uri().getRawQuery();
        String query = StringUtils.hasText(rawQuery) ? "?" + rawQuery : "";
        return "HTTP " + request.methodName() + " \"" + request.path() + query + "\"";
    }

}
