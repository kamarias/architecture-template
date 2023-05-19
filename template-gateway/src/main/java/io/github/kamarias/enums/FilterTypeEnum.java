package io.github.kamarias.enums;

/**
 * 过滤器类型枚举
 *
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/6 11:15
 */
public enum FilterTypeEnum {

    /**
     * 日志切面过滤器
     */
    LOG_FILTER("logFilter", "日志切面过滤器", Integer.MIN_VALUE),

    /**
     * 上下文过滤器
     */
    CONTEXT_FILTER("contextFilter", "上下文过滤器", -100),

    /**
     * 响应加密过滤器
     */
    ENCRYPT_FILTER("encryptFilter", "响应加密过滤器", -40),

    /**
     * 授权过滤器
     */
    AUTHORIZATION_FILTER("authorizationFilter", "授权过滤器", -37),

    /**
     * 认证过滤器
     */
    AUTHENTICATION_FILTER("authenticationFilter", "认证过滤器", -35),

    /**
     * 请求解密过滤器
     */
    DECRYPT_FILTER("decryptFilter", "请求解密过滤器", 50),

    /**
     * 请求头过滤器
     */
    HEADER_FILTER("headerFilter", "请求头过滤器", 100);


    /**
     * 过滤器名称
     */
    private final String name;

    /**
     * 过滤器描述
     */
    private final String desc;

    /**
     * 过滤器排序（数值越低，优先级越高）
     */
    private final int sort;


    FilterTypeEnum(String name, String desc, int sort) {
        this.name = name;
        this.desc = desc;
        this.sort = sort;
    }

    public String getDesc() {
        return desc;
    }

    public int getSort() {
        return sort;
    }

    public String getName() {
        return name;
    }
}
