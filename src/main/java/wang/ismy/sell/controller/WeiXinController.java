package wang.ismy.sell.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author MY
 * @date 2020/2/3 9:58
 */
@Controller
@Slf4j
@RequestMapping("wechat")
@AllArgsConstructor
public class WeiXinController {

    private WxMpService wxMpService;

    @RequestMapping("auth")
    public String auth(){
        String url = wxMpService.oauth2buildAuthorizationUrl("http://r1495937a2.imwork.net/wechat/callback",WxConsts.OAuth2Scope.SNSAPI_USERINFO,"state");
        return "redirect:"+url;
    }

    @RequestMapping("callback")
    @ResponseBody
    public String callback(String code) throws WxErrorException {
        WxMpOAuth2AccessToken accessToken = wxMpService.oauth2getAccessToken(code);
        return accessToken.getOpenId();
    }
}
