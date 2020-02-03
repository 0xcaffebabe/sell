package wang.ismy.sell.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wang.ismy.sell.enums.ResultEnum;
import wang.ismy.sell.exception.SellException;
import wang.ismy.sell.pojo.dto.OrderDTO;

/**
 * @author MY
 * @date 2020/2/3 9:20
 */
@Service
@AllArgsConstructor
public class BuyerService {

    private OrderService orderService;

    public OrderDTO findOrder(String openid, String orderId) {
        OrderDTO result = orderService.find(orderId);
        if (result != null && result.getBuyerOpenid().equals(openid)) {
            return result;
        }
        throw new SellException(ResultEnum.PERMISSION_DENIED);
    }

    public void cancelOrder(String openid, String orderId) {
        OrderDTO result = orderService.find(orderId);
        if (result != null && result.getBuyerOpenid().equals(openid)) {
            throw new SellException(ResultEnum.PERMISSION_DENIED);
        }
        throw new SellException(ResultEnum.PERMISSION_DENIED);
    }
}
