package io.github.kamarias.enums;

/**
 * 客户端枚举
 *
 * @author wangyuxing@gogpay.cn
 * @date 2023/5/11 15:57
 */
public enum ClientEnum {

    /**
     * 安卓
     */
    ANDROID("01", "android"),

    /**
     * 苹果
     */
    IOS("02", "ios"),

    /**
     * H5
     */
    H5("03", "h5");

    private final String id;
    private final String platform;

    ClientEnum(String id, String platform) {
        this.id = id;
        this.platform = platform;
    }
}
