package wang.ismy.sell.service;

import com.alipay.api.AlipayApiException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import wang.ismy.sell.enums.OrderStatusEnum;
import wang.ismy.sell.enums.PayStatusEnum;
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
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2020/2/2 16:41
 */
@Service
@AllArgsConstructor
@Slf4j
public class OrderService {

    private OrderDetailRepository orderDetailRepository;
    private OrderMasterRepository orderMasterRepository;

    private ProductService productService;
    private AliPayService aliPayService;
    private WeChatMessageService weChatMessageService;

    /**
     * 创建一条订单
     * @param orderDTO
     * @return 订单号
     */
    @Transactional(rollbackOn = Exception.class)
    public String create(OrderDTO orderDTO) {

        String orderId = KeyUtils.generateUniqueKey();
        // 计算总价
        BigDecimal totalAmount = calculateAmount(orderDTO, orderId);
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(totalAmount);
        // 写入订单数据
        orderMasterRepository.save(orderMaster);
        // 扣库存
        productService.decreaseStock(
                orderDTO.getOrderDetailList()
                        .stream().map(CartDTO::convert).collect(Collectors.toList())
        );
        return orderId;
    }

    /**
     * 分页查找某用户的订单
     *
     * @param buyerOpenid
     * @param pageable
     * @return
     */
    public Page<OrderDTO> findByBuyer(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> page = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        return new PageImpl<>(
                page.getContent()
                        .stream().map(OrderDTO::convert)
                        .collect(Collectors.toList()), pageable, page.getTotalElements()
        );
    }

    public Page<OrderDTO> findList(Pageable pageable){
        Page<OrderMaster> page = orderMasterRepository.findAll(pageable);
        return new PageImpl<>(
                page.getContent()
                        .stream().map(OrderDTO::convert)
                        .collect(Collectors.toList()), pageable, page.getTotalElements()
        );
    }

    /**
     * 根据订单ID查询
     *
     * @param orderId
     * @return
     */
    public OrderDTO find(String orderId) {
        Optional<OrderMaster> opt = orderMasterRepository.findById(orderId);
        if (opt.isEmpty()) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(opt.get(), orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    /**
     * 取消订单
     *
     * @param orderId
     * @return
     */
    @Transactional(rollbackOn = Exception.class)
    public OrderDTO cancel(String orderId) {
        // 判断订单状态
        OrderDTO orderDTO = find(orderId);
        if (orderDTO.getOrderStatus().equals(OrderStatusEnum.CANCELED.getCode())) {
            log.error("订单无法取消,orderId={},orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        // 修改订单状态
        orderMaster.setOrderStatus(OrderStatusEnum.CANCELED.getCode());
        OrderMaster result = orderMasterRepository.save(orderMaster);
        if (!result.getOrderStatus().equals(OrderStatusEnum.CANCELED.getCode())) {
            log.error("取消订单 更新失败,orderMaster = {}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        // 返还库存
        if (StringUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("取消订单 订单中无商品详情 , {}", orderMaster);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        productService.increaseStock(orderDTO.getOrderDetailList().stream()
                .map(CartDTO::convert)
                .collect(Collectors.toList()));
        // 如果已支付，需要退款
        if (orderDTO.getOrderStatus().equals(OrderStatusEnum.FINISHED.getCode())) {
            try {
                aliPayService.cancel(orderDTO);
            } catch (AlipayApiException e) {
                throw new RuntimeException(e);
            }
        }
        orderDTO.setOrderStatus(result.getOrderStatus());
        // 微信消息通知
        weChatMessageService.orderStatusChanged(orderDTO);
        return orderDTO;
    }

    @Transactional(rollbackOn = Exception.class)
    public OrderDTO finish(String orderId) {
        // 判断状态
        OrderDTO orderDTO = find(orderId);
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("完成订单 订单状态不正确 ,{}",orderDTO);
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // 修改状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);

        OrderMaster result = orderMasterRepository.save(orderMaster);
        if (!result.getOrderStatus().equals(OrderStatusEnum.FINISHED.getCode())){
            log.error("完结订单 更新订单失败 {}",orderMaster);
        }
        orderDTO.setOrderStatus(result.getOrderStatus());
        // 微信消息通知
        weChatMessageService.orderStatusChanged(orderDTO);
        return orderDTO;
    }

    @Transactional(rollbackOn = Exception.class)
    public OrderDTO paid(String orderId) {
        // 判断订单状态
        OrderDTO orderDTO = find(orderId);
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("支付订单 订单状态不正确 ,{}",orderDTO);
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // 判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("支付订单 支付状态不正确 {}",orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        // 修改订单状态和支付状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);

        OrderMaster result = orderMasterRepository.save(orderMaster);
        if (!result.getOrderStatus().equals(OrderStatusEnum.FINISHED.getCode())){
            log.error("支付订单 更新订单失败 {}",orderMaster);
        }
        orderDTO.setOrderStatus(result.getOrderStatus());
        orderDTO.setPayStatus(result.getPayStatus());
        return orderDTO;
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
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetailRepository.save(orderDetail);
        }
        return totalAmount;
    }
}
