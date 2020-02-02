package wang.ismy.sell.pojo.dto;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import wang.ismy.sell.enums.OrderStatusEnum;
import wang.ismy.sell.enums.PayStatusEnum;
import wang.ismy.sell.pojo.entity.OrderDetail;
import wang.ismy.sell.pojo.entity.OrderMaster;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author MY
 * @date 2020/2/2 16:45
 */
@Data
public class OrderDTO {
    private String orderId;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String buyerOpenid;
    private BigDecimal orderAmount;
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();
    private Integer payStatus = PayStatusEnum.WAIT.getCode();
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private List<OrderDetail> orderDetailList;

    public static OrderDTO convert(OrderMaster orderMaster){
        OrderDTO dto = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,dto);
        return dto;
    }
}
