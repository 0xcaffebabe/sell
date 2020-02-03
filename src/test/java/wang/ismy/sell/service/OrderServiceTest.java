package wang.ismy.sell.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.sell.enums.OrderStatusEnum;
import wang.ismy.sell.enums.PayStatusEnum;
import wang.ismy.sell.pojo.dto.OrderDTO;
import wang.ismy.sell.pojo.entity.OrderDetail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("蔡徐坤");
        orderDTO.setBuyerAddress("福建漳浦");
        orderDTO.setBuyerPhone("12345678901");
        orderDTO.setBuyerOpenid("123");

        OrderDetail o1 = new OrderDetail();
        o1.setProductId("1");
        o1.setProductQuantity(2);
        OrderDetail o2 = new OrderDetail();
        o2.setProductId("2");
        o2.setProductQuantity(2);
        orderDTO.setOrderDetailList(List.of(o1,o2));
        log.info("创建订单结果:{}",orderService.create(orderDTO));
    }

    @Test
    public void testFind(){
        OrderDTO dto = orderService.find("c885af2eec324b5b8570f2b0675ed967");
        assertEquals(new BigDecimal("42.00"),dto.getOrderAmount());
        assertEquals(2,dto.getOrderDetailList().size());
    }

    @Test
    public void findByBuyer() {

        Page<OrderDTO> page = orderService.findByBuyer("123", PageRequest.of(0, 2));
        assertEquals(1,page.getContent().size());
        assertEquals(new BigDecimal("42.00"),page.getContent().get(0).getOrderAmount());
    }

    @Test
    public void cancel() {
        String orderId = "c885af2eec324b5b8570f2b0675ed967";
        OrderDTO order = orderService.cancel(orderId);
        assertEquals(OrderStatusEnum.CANCELED.getCode(),(int)order.getOrderStatus());
    }

    @Test
    public void finish() {
        String orderId = "c885af2eec324b5b8570f2b0675ed967";
        OrderDTO order = orderService.finish(orderId);
        assertEquals(OrderStatusEnum.FINISHED.getCode(),(int)order.getOrderStatus());
    }

    @Test
    public void paid() {
        String orderId = "c885af2eec324b5b8570f2b0675ed967";
        OrderDTO order = orderService.paid(orderId);
        assertEquals(OrderStatusEnum.FINISHED.getCode(),(int)order.getOrderStatus());
        assertEquals(PayStatusEnum.SUCCESS.getCode(),(int)order.getPayStatus());
    }

    @Test
    public void findList(){
        Page<OrderDTO> list = orderService.findList(PageRequest.of(1, 2));
        assertEquals(2,list.getContent().size());
    }
}