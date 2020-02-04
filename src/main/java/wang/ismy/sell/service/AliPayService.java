package wang.ismy.sell.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.*;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import wang.ismy.sell.pojo.dto.AliPayUserInfo;
import wang.ismy.sell.pojo.dto.OrderDTO;
import wang.ismy.sell.utils.KeyUtils;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author MY
 * @date 2020/2/3 13:54
 */
@Service
public class AliPayService {

    private final String appId = "2016101200669406";
    private final String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCBtYHG++icMRH4rgHapnZeql3NzlgUmFAwtiaEVrskqhG1gSgAeVOYxTQ1y47gpOjIIj+itgM38HdHgH156IWyo1tCR8Orz8i1X/gkWBw4fs31SnHE+79WYIA22xfN45/sR02eUbMtGJN0wuuxrX+bln30vXdc2yynznMLUSjx2vp0WhUAXem1oz4oPia1La2Vlx66jWofx4BUzNGVysaLG6oDB6amiLcUqANY52V4rRJC+h4Uoy+QdP22H7L0A49XwJm5lH8hHtZB3kH66+ATBRhfwtk24x8lkRHEeCYx4qhj4OG24PqVONSZ/Rx2/fqNaqvGi6XhwII1BpXjzOVtAgMBAAECggEABKw+TaXbnmziIdxa8r6WexiWIojxgWRvVyHox1G8kjiSRMCvaTz1twY7uEhqEEvAmx0ov7rg6M3/7hKB8rdewa3C5uNg+DqXF3mqndySv7JJxzF0Yc1T+k+lrtQ7yk4N/9wdvrtgwholjcpL5W3DsB4+0WdV+oFm3XDBkkHNQNE972JAZQt7vP5PsBPZCPnlZcpQLaiQIx3i4Mgz6kxIQ1vWisgYnGZEDDi8NxQqL5kZdJSMi5GnbU04jYGi8uZVCZZRGVCdjlqTTzL1ik+NseaDWV93/il/WDNRs9KfUSn+Lfj0D5evtdIyuiOQYxSPiNI3IMfg3xDHyWqxwRxfgQKBgQD7DsXrsKtVgQ2Cf51tecgmMFBhZZ6+XjwKArD5E+5j73q9b4+CLkjg5fTQz7adybqqPH7vvoPGUxRL1loU00tKIlkHNS7D0eHCXHzBpkZDIuXHP8QXN0twR7pPXa6ORuvG0NWy26mIoQE5Tva8URhZUOAAPqg70bWkxHfQqfxJoQKBgQCEQy/HzUVqMRwcVbrzei8qZdmOKxtdnRw3CM1gUGVgzbYv19GNsz92ftovMd1C4k+mMI0O4RtKjyvMJLZHpuyKLY8uzYJe7oGIX3FAb9ozR35F+Bz4QLR4XBYXnGynqcPh50c/tkUTBh4sxIq1vJ5TV+SQGUpZzfBQ5RD5gnLATQKBgQDYxqzHEckfD0um/qHkQV88+kKtlp03Kw8UZbaBm2AgEkwc/hMgCO8MogJ9upyoKeNv0sSYJhyKRnoj8XXevYMpA/dzbcxvcO5KcKmYXAYXOi7FmJ2sniYZURXiKbXrS/mRtgiPY3RDcSIpZbFOcYdgt6BzFacV38HD/5ClL+GTgQKBgDpZrc23GOW1jtJNXDIoeIB6+JUJHYSaWKVLA9h2/5LKjpLi4vdh2iC77CQgGp50W3FVQBYNZ83oBgmFNMm9seNwIBgk4FxjWtBAUyV7c0dmRiY2RQmc+kGTVsfJ4gHCGsIiF8Pa9PRkoZFckFmHQk5Am3ybO7sM6PW3aVq6m0eNAoGABZFQOtn8LUoS/XL4RXYKGfguRqSES7RU60yaRr2SD8uXVWFqTCeGKdX4LcnbRizIoOK2+xkBmYYcEoqb2uLyetJ08c5YBW6/1hfkc5nnzOEvkAnF7aEkNv/YQmyZiPwvIWmT69P9ABybo4EnqKrTEKTQVWaAY+ZlVZ9tG1YChXg=";
    private final String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjmF5eeI9cLYeuSJjBdfXPdDmQfZNWNgWNOSLgB5S7Rz1OHNUyClqCrIOyfjYAx4NaeWWTXxz/lSu6ef5W/cz9tQ/pvBMlcm6Aj+Y68wnrkiLT65Q18/Uchqn07D5zJ6vSjGdrRS9DApoNo+Zj7TUNJiP8eOk1bc7u172Rmti1VsKogPELzQ/CduqhVJqIc/D3YZLz/BELHMpnZhwhEAHTsyzjWoNfBQYzazm3ezlV9YAGtUjAo1gBS3IcTWvq2CbJNABOwLU0WoftlMdzYRKh1tY5WNHy6PuGUbMmSLV1nCMKyqwDmQvBVxXNNdmbM4o7NJrCV0VPJXP5bWLA62LkwIDAQAB";

    private String serverUrl = "https://openapi.alipaydev.com/gateway.do";
    private AlipayClient client;

    private Map<String, String> orderMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        client = new DefaultAlipayClient(serverUrl,
                appId, privateKey, "json", "utf-8", publicKey, "RSA2");
    }

    /**
     * 创建支付订单
     *
     * @param price
     * @param title
     * @return 返回支付宝付款页面html源码
     * @throws AlipayApiException
     * @throws JsonProcessingException
     */
    public String createPay(BigDecimal price, String title, String orderId) {

        //创建API对应的request
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl("http://r1495937a2.imwork.net");
        alipayRequest.setNotifyUrl("http://r1495937a2.imwork.net/alipay/callback");
        // 业务参数

        String tradeParam = KeyUtils.generateUniqueKey();
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\"" + orderId + "\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":" + price.toString() + "," +
                "    \"subject\":\"" + title + "\"," +
                "    \"body\":\"" + title + "\"," +
                "    \"passback_params\":\"" + tradeParam + "\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088511833207846\"" +
                "    }" +
                "  }");
        String form = "";
        orderMap.put(orderId, tradeParam);
        try {
            //调用SDK生成表单
            form = client.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return form;
    }

    public boolean cancel(OrderDTO orderDTO) throws AlipayApiException {
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizContent("{" +
                "\"out_trade_no\":\"" + orderDTO.getOrderId() + "\"," +
                "\"refund_amount\":" + orderDTO.getOrderAmount() +
                "  }");
        AlipayTradeRefundResponse response = client.execute(request);
        if (response.isSuccess()) {
            return true;
        }
        return false;

    }

    public boolean validOrder(String tradeNo, String key) {
        String s = orderMap.get(tradeNo);
        if (!StringUtils.isEmpty(s)) {
            if (s.equals(key)) {
                orderMap.remove(tradeNo);
                return true;
            }
            return false;
        }
        return false;
    }

    public String generateLoginUrl() {
        try {
            String redirectUrl = URLEncoder.encode("http://r1495937a2.imwork.net/alipay/login/callback", "utf-8");
            return "https://openauth.alipaydev.com/oauth2/publicAppAuthorize.htm?app_id=" + appId + "&scope=auth_user&redirect_uri=" + redirectUrl + "&state=init";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;

    }

    public String getUserId(String authCode){
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setCode(authCode);
        request.setGrantType("authorization_code");
        try {
            AlipaySystemOauthTokenResponse oauthTokenResponse = client.execute(request);
            if (oauthTokenResponse.isSuccess()){
                String accessToken = oauthTokenResponse.getAccessToken();
                AlipayUserInfoShareRequest userInfoRequest = new AlipayUserInfoShareRequest();
                AlipayUserInfoShareResponse response = client.execute(userInfoRequest, accessToken);
                if (response.isSuccess()){
                    return response.getUserId();
                }
            }
            return null;
        } catch (AlipayApiException e) {
            //处理异常
            e.printStackTrace();
        }

        return null;
    }
}
