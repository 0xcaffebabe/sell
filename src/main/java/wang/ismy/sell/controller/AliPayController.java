package wang.ismy.sell.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import wang.ismy.sell.pojo.dto.OrderDTO;
import wang.ismy.sell.service.AliPayService;
import wang.ismy.sell.service.OrderService;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author MY
 * @date 2020/2/3 14:17
 */
@Controller
@RequestMapping("alipay")
@AllArgsConstructor
@Slf4j
public class AliPayController {

    private AliPayService aliPayService;
    private OrderService orderService;

    @PostMapping("callback")
    @ResponseBody
    public String callback(@RequestParam("passback_params") String param,
                         @RequestParam("out_trade_no") String no,
                         @RequestParam("buyer_pay_amount") BigDecimal amount,
                         @RequestParam("trade_status") String status){
        OrderDTO orderDTO = orderService.find(no);
        if (orderDTO == null){
            log.error("支付宝回调无此订单");
            return "error";
        }
        // 验证支付状态
        if (aliPayService.validOrder(no,param)){

            // 验证支付金额
            if (!orderDTO.getOrderAmount().equals(amount)){
                log.error("支付宝回调金额校验不通过 {}",amount);
                return "error";
            }
            // 订单支付成功
            if (!"TRADE_SUCCESS".equals(status)){
                log.error("支付宝回调支付状态校验不通过");
                return "error";
            }
            orderService.paid(no);
            log.info("订单支付成功 :{}",no);
            // 通知微信处理成功
            return "success";
        }
        return "error";

    }

    @RequestMapping("login/callback")
    public ModelAndView loginCallback(@RequestParam("auth_code") String authCode, @RequestParam("app_id") String appId,
                                      @RequestParam("scope") String scope){
        log.info("支付宝登录回调 auth:{} id:{} scope:{}",authCode,appId,scope);
        String userId = aliPayService.getUserId(authCode);
        ModelAndView mav = new ModelAndView();
        if (!StringUtils.isEmpty(userId)){
            log.info("登录成功，userid :{}",userId);
            mav.setViewName("redirect:/seller/login?openid="+userId);
        }else {
            mav.setViewName("common/error");
            mav.addObject("msg","登录失败");
            mav.addObject("url","/seller/order/list");
        }
        return mav;
    }

    @RequestMapping("login")
    public String login(){
        return "redirect:"+aliPayService.generateLoginUrl();
    }


    @RequestMapping("create")
    @ResponseBody
    public String create(String title,String price,String orderId){
        return aliPayService.createPay(new BigDecimal(price),title,orderId);
    }
}
