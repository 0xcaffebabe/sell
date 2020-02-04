package wang.ismy.sell.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import wang.ismy.sell.exception.AuthException;

/**
 * @author MY
 * @date 2020/2/4 13:55
 */
@ControllerAdvice
public class ExceptionHandler {


    @org.springframework.web.bind.annotation.ExceptionHandler(AuthException.class)
    public ModelAndView handleAuthException(AuthException exception){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("common/error");
        mav.addObject("msg",exception.getMessage());
        mav.addObject("url","/alipay/login");
        return mav;
    }

}
