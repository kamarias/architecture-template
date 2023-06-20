package io.github.kamarias.enums;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/31 11:23
 */
public enum BoolFlagEnum {

    /**
     * 否
     */
    NOT(0, "否", false),

    /**
     * 是
     */
    YES(1, "是", true);

    private final Integer code;
    private final String desc;
    private final Boolean value;

    BoolFlagEnum(Integer code, String desc, Boolean value) {
        this.code = code;
        this.desc = desc;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public Boolean getValue() {
        return value;
    }
}
