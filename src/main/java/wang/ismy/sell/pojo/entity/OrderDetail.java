package wang.ismy.sell.pojo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author MY
 * @date 2020/2/2 16:11
 */
@Entity
@Data
public class OrderDetail {

    @Id
    private String detailId;
    private String orderId;
    private String productId;
    private String productName;
    private BigDecimal productPrice;
    private Integer productQuantity;
    private String productIcon;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
