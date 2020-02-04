package wang.ismy.sell.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author MY
 * @date 2020/2/4 12:30
 */
public class CookieUtils {

    public static String get(HttpServletRequest request, HttpServletResponse response,String name){
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(name)) {
                return cookie.getValue();
            }
        }
        return "";
    }
}
