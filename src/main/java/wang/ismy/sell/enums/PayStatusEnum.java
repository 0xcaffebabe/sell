package wang.ismy.sell.enums;

import lombok.Getter;

/**
 * @author MY
 * @date 2020/2/2 16:05
 */
@Getter
public enum PayStatusEnum {
    WAIT(0,"等待支付"),
    SUCCESS(1,"支付成功");

    private int code;

    private String msg;

    PayStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static PayStatusEnum valueOf(Integer payStatus) {
        for (PayStatusEnum value : values()) {
            if (value.code == payStatus){
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
