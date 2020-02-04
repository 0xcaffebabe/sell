package wang.ismy.sell.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import wang.ismy.sell.constant.RedisConstant;
import wang.ismy.sell.pojo.entity.SellerInfo;
import wang.ismy.sell.service.AliPayService;
import wang.ismy.sell.service.SellerService;
import wang.ismy.sell.utils.CookieUtils;
import wang.ismy.sell.utils.KeyUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author MY
 * @date 2020/2/4 11:40
 */
@Controller
@RequestMapping("seller")
@AllArgsConstructor
public class SellerLoginController {

    private AliPayService aliPayService;
    private SellerService sellerService;
    private StringRedisTemplate redisTemplate;

    @RequestMapping("login")
    public ModelAndView login(@RequestParam String openid, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
        // 匹配openid
        if (sellerInfo == null) {
            mav.setViewName("common/error");
            mav.addObject("msg", "数据库中没有此openid");
            mav.addObject("url", "/seller/order/list");
            return mav;
        }
        // 设置token到redis
        String token = KeyUtils.generateUniqueKey();
        redisTemplate.opsForValue().set(RedisConstant.TOKEN_PREFIX + token,
                openid, RedisConstant.EXPIRE, TimeUnit.SECONDS);

        // 设置token到cookie
        Cookie cookie = new Cookie("token",token);
        cookie.setPath("/");
        cookie.setMaxAge(7200);
        response.addCookie(cookie);
        // 登录成功
        mav.setViewName("redirect:/seller/order/list?openid="+openid);
        return mav;
    }

    @RequestMapping("logout")
    public ModelAndView logout(HttpServletRequest request,
                       HttpServletResponse response){
        ModelAndView mav = new ModelAndView();
        String token = CookieUtils.get(request, "token");
        if (!StringUtils.isEmpty(token)){
            // 清除redis
            redisTemplate.delete(RedisConstant.TOKEN_PREFIX+token);
            // 清除cookie
            Cookie cookie = new Cookie("token",null);
            cookie.setPath("/");
            cookie.setMaxAge(7200);
            response.addCookie(cookie);
            mav.setViewName("redirect:/alipay/login");
        }
        return mav;
    }
}
