package wang.ismy.sell.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import wang.ismy.sell.enums.ResultEnum;
import wang.ismy.sell.exception.SellException;
import wang.ismy.sell.pojo.dto.OrderDTO;
import wang.ismy.sell.pojo.form.OrderForm;
import wang.ismy.sell.pojo.vo.Result;
import wang.ismy.sell.service.OrderService;

import javax.print.DocFlavor;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author MY
 * @date 2020/2/2 20:34
 */
@RestController
@RequestMapping("buyer/order")
@Slf4j
@AllArgsConstructor
public class BuyerOrderController {

    private OrderService orderService;

    /**
     * 创建订单
     * @return
     */
    @PostMapping("create")
    public Result<Map<String,String>> create(@Valid OrderForm form, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.error("表单校验出现错误 ，{}",form);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderDTO.convert(form);

        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("创建订单 购物车为空 {}",form);
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        String result = orderService.create(orderDTO);
        return Result.success(Map.of("orderId",result));
    }

    /**
     * 根据openid分页查询
     * @param openid
     * @param page
     * @param size
     * @return
     */
    @GetMapping("list")
    public Result<List<OrderDTO>> list(@RequestParam String openid,
                                       @RequestParam(defaultValue = "0") Integer page,
                                       @RequestParam(defaultValue = "10") Integer size){
        if (StringUtils.isEmpty(openid)){
            throw new SellException(ResultEnum.OPENID_EMPTY);
        }

        Page<OrderDTO> result = orderService.findByBuyer(openid, PageRequest.of(page, size));
        return Result.success(result.getContent());
    }
}
