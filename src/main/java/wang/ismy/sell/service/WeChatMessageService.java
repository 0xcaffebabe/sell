package wang.ismy.sell.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.stereotype.Service;
import wang.ismy.sell.pojo.dto.OrderDTO;

import java.util.List;

/**
 * @author MY
 * @date 2020/2/4 14:17
 */
@Service
@AllArgsConstructor
@Slf4j
public class WeChatMessageService {

    private WxMpService wxMpService;

    public void orderStatusChanged(OrderDTO orderDTO){
        WxMpTemplateMsgService templateMsgService = wxMpService.getTemplateMsgService();
        WxMpTemplateMessage message = new WxMpTemplateMessage();
        message.setTemplateId("dB-pVParH6pqpLcV_Usw195E60rY8nznHZeL9iURDbw");
        message.setToUser(orderDTO.getBuyerOpenid());
        message.setData(
                List.of(
                        new WxMpTemplateData("first","订单状态发生改变了"),
                        new WxMpTemplateData("keyword1",orderDTO.getBuyerName()+"的点餐订单"),
                        new WxMpTemplateData("keyword2","12345678901"),
                        new WxMpTemplateData("keyword3",orderDTO.getOrderId()),
                        new WxMpTemplateData("keyword4",orderDTO.getOrderStatusEnum().getMsg()),
                        new WxMpTemplateData("keyword5",orderDTO.getOrderAmount().toString()+"元"),
                        new WxMpTemplateData("remark","记得五星好评呀")
                )
        );
        try {
            templateMsgService.sendTemplateMsg(message);
        } catch (WxErrorException e) {
            throw new RuntimeException(e);
        }
    }
}
