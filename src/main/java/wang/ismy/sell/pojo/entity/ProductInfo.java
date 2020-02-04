package wang.ismy.sell.pojo.entity;

import lombok.Data;
import wang.ismy.sell.enums.ProductStatusEnum;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author MY
 * @date 2020/2/2 13:54
 */
@Entity
@Data
public class ProductInfo {

    @Id
    private String productId;

    private String productName;

    private BigDecimal productPrice;

    private Integer productStock;

    private String productDescription;

    private String productIcon;

    /**
     * 商品状态,0正常1下架
     */
    private Integer productStatus;

    private Integer categoryType;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public ProductStatusEnum getProductStatusEnum(){
        return ProductStatusEnum.valueOf(productStatus);
    }
}
