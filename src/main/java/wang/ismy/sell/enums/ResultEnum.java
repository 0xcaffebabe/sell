package wang.ismy.sell.enums;

import lombok.Getter;

/**
 * @author MY
 * @date 2020/2/2 16:55
 */
@Getter
public enum ResultEnum {

    PARAM_ERROR(9001, "参数不正确"),
    PRODUCT_NOT_EXIST(1001,"商品不存在"),
    STOCK_NOT_ENOUGH(1002,"库存不足"),
    ORDER_NOT_EXIST(1003, "订单不存在"),
    ORDER_DETAIL_NOT_EXIST(1004, "订单详情不存在"),
    ORDER_STATUS_ERROR(1005, "订单状态不正确"),
    ORDER_UPDATE_FAIL(1006, "更新订单状态失败"),
    ORDER_DETAIL_EMPTY(1007, "订单中无订单详情"),
    ORDER_PAY_STATUS_ERROR(1008, "订单支付状态不正确"),
    CART_EMPTY(1009, "购物车为空"),
    OPENID_EMPTY(1010, "openid 为空"),
    PERMISSION_DENIED(1011, "无权限访问");

    private int code;

    private String msg;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
