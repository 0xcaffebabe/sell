package wang.ismy.sell.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import wang.ismy.sell.pojo.entity.OrderDetail;

/**
 * @author MY
 * @date 2020/2/2 18:48
 */
@Data
@AllArgsConstructor
public class CartDTO {
    private String productId;
    private Integer productQuantity;

    public static CartDTO convert(OrderDetail orderDetail){
        return new CartDTO(orderDetail.getProductId(),orderDetail.getProductQuantity());
    }
}
