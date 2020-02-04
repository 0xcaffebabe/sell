package wang.ismy.sell.aspect;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import wang.ismy.sell.constant.RedisConstant;
import wang.ismy.sell.enums.ResultEnum;
import wang.ismy.sell.exception.AuthException;
import wang.ismy.sell.exception.SellException;
import wang.ismy.sell.utils.CookieUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author MY
 * @date 2020/2/4 13:47
 */
@Aspect
@Component
@Slf4j
@AllArgsConstructor
public class SellerAuthAspect {

    private StringRedisTemplate redisTemplate;

    @Pointcut("execution(public * wang.ismy.sell.controller.Seller*.*(..)) && !execution(public * wang.ismy.sell.controller.SellerLoginController.*(..))")
    public void pointCut(){}

    @Before("pointCut()")
    public void before(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = CookieUtils.get(request, "token");
        if (StringUtils.isEmpty(token)){
            log.error("登录校验 cookie中无token");
            throw new AuthException(ResultEnum.PERMISSION_DENIED);
        }
        String openid = redisTemplate.opsForValue().get(RedisConstant.TOKEN_PREFIX + token);
        if (StringUtils.isEmpty(openid)){
            log.error("登录校验 token不正确");
            throw new AuthException(ResultEnum.PERMISSION_DENIED);
        }


    }
}
