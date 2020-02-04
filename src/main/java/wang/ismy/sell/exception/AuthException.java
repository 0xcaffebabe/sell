package wang.ismy.sell.exception;

import wang.ismy.sell.enums.ResultEnum;

/**
 * @author MY
 * @date 2020/2/4 13:55
 */
public class AuthException extends RuntimeException {
    private ResultEnum resultEnum;

    public AuthException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.resultEnum = resultEnum;
    }

    public AuthException(int code, String message) {
        super(message);

    }
}
