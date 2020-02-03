package wang.ismy.sell.enums;

import lombok.Data;
import lombok.Getter;

/**
 * @author MY
 * @date 2020/2/2 16:05
 */
@Getter
public enum  OrderStatusEnum {
    NEW(0,"新订单"),
    FINISHED(1,"完成"),
    CANCELED(2,"取消");

    private int code;

    private String msg;

    OrderStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static OrderStatusEnum valueOf(Integer code){
        for (OrderStatusEnum value : values()) {
            if (value.code == code){
                return value;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return msg;
    }
}
