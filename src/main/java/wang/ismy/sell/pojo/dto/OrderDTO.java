package wang.ismy.sell.pojo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import wang.ismy.sell.enums.OrderStatusEnum;
import wang.ismy.sell.enums.PayStatusEnum;
import wang.ismy.sell.pojo.entity.OrderDetail;
import wang.ismy.sell.pojo.entity.OrderMaster;
import wang.ismy.sell.pojo.form.OrderForm;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author MY
 * @date 2020/2/2 16:45
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
        return OrderStatusEnum.valueOf(orderStatus);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return PayStatusEnum.valueOf(payStatus);
    }

    public static OrderDTO convert(OrderMaster orderMaster) {
        OrderDTO dto = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, dto);
        return dto;
    }

    public static OrderDTO convert(OrderForm form) {
        OrderDTO dto = new OrderDTO();
        dto.buyerName = form.getName();
        dto.buyerPhone = form.getPhone();
        dto.buyerAddress = form.getAddress();
        dto.buyerOpenid = form.getOpenid();
        dto.orderDetailList = new Gson().fromJson(form.getItems(), new TypeToken<List<OrderDetail>>() {
        }.getType());
        return dto;
    }
}
