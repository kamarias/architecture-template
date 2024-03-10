package io.github.kamarias.exception;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2024/2/3 13:49
 */
public class ParamVerifyException extends RuntimeException{

    private Integer code;

    public ParamVerifyException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


}
