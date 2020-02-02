package wang.ismy.sell.enums;

import lombok.Getter;

/**
 * 商品上架状态
 * @author MY
 * @date 2020/2/2 14:14
 */
@Getter
public enum  ProductStatusEnum {

    UP(0,"上架"),

    DOWN(1,"下架");

    private int code;
    private String msg;

    ProductStatusEnum(int code,String msg) {
        this.code = code;
        this.msg = msg;
    }
}
