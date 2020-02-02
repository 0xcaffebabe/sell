package wang.ismy.sell.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.sell.pojo.entity.OrderMaster;

import javax.transaction.Transactional;
import java.math.BigDecimal;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderMasterRepositoryTest {

    @Autowired
    OrderMasterRepository repository;

    @Test
    @Transactional
    public void findByBuyerOpenid() {
        saveOne();
        Page<OrderMaster> orderMasterPage = repository.findByBuyerOpenid("4561", PageRequest.of(0,10));
        assertEquals("蔡徐坤",orderMasterPage.getContent().get(0).getBuyerName());
    }

    private void saveOne(){
        OrderMaster order = new OrderMaster();
        order.setOrderId("1");
        order.setBuyerName("蔡徐坤");
        order.setBuyerPhone("12345678901");
        order.setBuyerAddress("福建漳浦");
        order.setBuyerOpenid("4561");
        order.setOrderAmount(BigDecimal.valueOf(158));
        repository.save(order);
    }
}