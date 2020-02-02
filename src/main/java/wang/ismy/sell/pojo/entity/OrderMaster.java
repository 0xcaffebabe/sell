package wang.ismy.sell.pojo.entity;

import lombok.Data;
import wang.ismy.sell.enums.OrderStatusEnum;
import wang.ismy.sell.enums.PayStatusEnum;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author MY
 * @date 2020/2/2 16:02
 */
@Data
@Entity
public class OrderMaster {

    @Id
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
}
