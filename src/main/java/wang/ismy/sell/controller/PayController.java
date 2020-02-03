package wang.ismy.sell.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import wang.ismy.sell.enums.ResultEnum;
import wang.ismy.sell.exception.SellException;
import wang.ismy.sell.pojo.dto.OrderDTO;
import wang.ismy.sell.service.AliPayService;
import wang.ismy.sell.service.OrderService;

import java.math.BigDecimal;

/**
 * @author MY
 * @date 2020/2/3 13:34
 */
@Controller
@RequestMapping("pay")
@AllArgsConstructor
public class PayController {

    private OrderService orderService;
    private AliPayService aliPayService;

    @GetMapping("create")
    @ResponseBody
    public String create(@RequestParam String orderId){
        OrderDTO orderDTO = orderService.find(orderId);
        if (orderDTO == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        BigDecimal orderAmount = orderDTO.getOrderAmount();
        String title = orderDTO.getBuyerName()+"的点餐订单";
        return aliPayService.createPay(orderAmount,title,orderId);
    }
}
