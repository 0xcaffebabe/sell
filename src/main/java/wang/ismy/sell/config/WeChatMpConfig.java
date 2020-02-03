package wang.ismy.sell.config;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author MY
 * @date 2020/2/3 10:29
 */
@Configuration
public class WeChatMpConfig {

    @Bean
    public WxMpService wxMpService(WxMpConfigStorage config){
        WxMpService service = new WxMpServiceImpl();
        service.setWxMpConfigStorage(config);
        return service;
    }

    @Bean
    public WxMpConfigStorage wxMpConfigStorage(){

        WxMpDefaultConfigImpl config = new WxMpDefaultConfigImpl();
        config.setAppId("wx6173dec97fbca7cd");
        config.setSecret("9cc0624a5942f9993fb102edb40978d6");
        return config;
    }
}
