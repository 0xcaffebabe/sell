package wang.ismy.sell.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.sell.pojo.dto.OrderDTO;
import wang.ismy.sell.pojo.entity.OrderDetail;

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
    public void findByBuyer() {
    }

    @Test
    public void cancel() {
    }

    @Test
    public void finish() {
    }

    @Test
    public void paid() {
    }
}