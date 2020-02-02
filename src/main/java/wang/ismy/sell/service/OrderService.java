package wang.ismy.sell.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import wang.ismy.sell.enums.ResultEnum;
import wang.ismy.sell.exception.SellException;
import wang.ismy.sell.pojo.dto.CartDTO;
import wang.ismy.sell.pojo.dto.OrderDTO;
import wang.ismy.sell.pojo.entity.OrderDetail;
import wang.ismy.sell.pojo.entity.OrderMaster;
import wang.ismy.sell.pojo.entity.ProductInfo;
import wang.ismy.sell.repository.OrderDetailRepository;
import wang.ismy.sell.repository.OrderMasterRepository;
import wang.ismy.sell.utils.KeyUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2020/2/2 16:41
 */
@Service
@AllArgsConstructor
public class OrderService {

    private OrderDetailRepository orderDetailRepository;
    private OrderMasterRepository orderMasterRepository;

    private ProductService productService;

    @Transactional(rollbackOn = Exception.class)
    public OrderDTO create(OrderDTO orderDTO) {

        String orderId = KeyUtils.generateUniqueKey();
        // 计算总价
        BigDecimal totalAmount = calculateAmount(orderDTO, orderId);
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(totalAmount);
        // 写入订单数据
        orderMasterRepository.save(orderMaster);
        // 扣库存
        productService.decreaseStock(
                orderDTO.getOrderDetailList()
                        .stream().map(CartDTO::convert).collect(Collectors.toList())
        );
        return orderDTO;
    }

    public Page<OrderDTO> findByBuyer(String buyerOpenid, Pageable pageable) {
        return null;
    }

    public OrderDTO cancel(OrderDTO orderDTO) {
        return null;
    }

    public OrderDTO finish(OrderDTO orderDTO) {
        return null;
    }

    public OrderDTO paid(OrderDTO orderDTO) {
        return null;
    }

    private BigDecimal calculateAmount(OrderDTO orderDTO, String orderId) {
        BigDecimal totalAmount = new BigDecimal(0);
        // 查询商品
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productService.find(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            // 累加商品价格到总价
            totalAmount = totalAmount.add(
                    productInfo.getProductPrice()
                            .multiply(new BigDecimal(orderDetail.getProductQuantity()))
            );
            // 订单详情入库
            orderDetail.setDetailId(KeyUtils.generateUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailRepository.save(orderDetail);
        }
        return totalAmount;
    }
}
