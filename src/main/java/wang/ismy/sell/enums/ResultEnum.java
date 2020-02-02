package wang.ismy.sell.enums;

import lombok.Getter;

/**
 * @author MY
 * @date 2020/2/2 16:55
 */
@Getter
public enum ResultEnum {

    PRODUCT_NOT_EXIST(1001,"商品不存在"),
    STOCK_NOT_ENOUGH(1002,"库存不足"),
    ;

    private int code;

    private String msg;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
