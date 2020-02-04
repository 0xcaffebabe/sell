package wang.ismy.sell.pojo.form;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author MY
 * @date 2020/2/4 9:57
 */
@Data
public class ProductForm {

    private String productId;

    private String productName;

    private BigDecimal productPrice;

    private Integer productStock;

    private String productDescription;

    private String productIcon;

    private Integer categoryType;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
