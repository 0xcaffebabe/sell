package wang.ismy.sell.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import wang.ismy.sell.pojo.dto.OrderDTO;
import wang.ismy.sell.service.OrderService;

/**
 * @author MY
 * @date 2020/2/3 16:34
 */
@Controller
@RequestMapping("seller/order")
@AllArgsConstructor
public class SellerOrderController {

    private OrderService orderService;

    @GetMapping("list")
    public ModelAndView list(@RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "10") Integer size) {
        page = page - 1;
        Page<OrderDTO> list = orderService.findList(PageRequest.of(page, size));

        ModelAndView mav = new ModelAndView();
        mav.addObject("list",list);
        mav.addObject("currentPage",page+1);
        mav.addObject("size",size);
        mav.setViewName("order/list");
        return mav;
    }

    @GetMapping("cancel")
    public ModelAndView cancel(@RequestParam String orderId){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("common/success");
        mav.addObject("msg","操作成功");
        mav.addObject("url","/seller/order/list");
        try{
            orderService.cancel(orderId);
        }catch (Exception e){
            e.printStackTrace();
            mav.setViewName("common/error");
            mav.addObject("msg",e.getMessage());
            mav.addObject("url","/seller/order/list");
        }
        return mav;
    }
}

