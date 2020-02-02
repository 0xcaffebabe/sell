package wang.ismy.sell.exception;

import wang.ismy.sell.enums.ResultEnum;

/**
 * @author MY
 * @date 2020/2/2 16:54
 */
public class SellException extends RuntimeException {

    private ResultEnum resultEnum;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.resultEnum = resultEnum;
    }
}
