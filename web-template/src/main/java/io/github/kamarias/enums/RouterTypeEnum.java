package io.github.kamarias.enums;

/**
 * 路由类型枚举
 *
 * @author wangyuxing@gogpay.cn
 * @date 2023/6/9 14:54
 */
public enum RouterTypeEnum {


    /**
     * 目录
     */
    DIRECTORY(0, "目录"),

    /**
     * 菜单
     */
    MENU(1, "菜单");

    /**
     * 编码
     */
    private final Integer code;

    /**
     * 描述
     */
    private final String desc;

    RouterTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
