package wang.ismy.sell.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.sell.pojo.dto.OrderDTO;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WeChatMessageServiceTest {

    @Autowired
    WeChatMessageService service;

    @Autowired
    OrderService orderService;

    @Test
    public void orderStatusChanged() {
        OrderDTO orderDTO = orderService.find("73b8f75c3735417d986254b3e577f666");
        service.orderStatusChanged(orderDTO);
    }
}