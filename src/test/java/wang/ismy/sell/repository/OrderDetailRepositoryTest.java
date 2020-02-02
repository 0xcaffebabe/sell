package wang.ismy.sell.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.sell.pojo.entity.OrderDetail;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderDetailRepositoryTest {

    @Autowired
    OrderDetailRepository repository;

    @Test
    @Transactional
    public void findByOrderId() {
        saveOne();
        List<OrderDetail> list = repository.findByOrderId("11");
        assertEquals("黄焖鸡米饭",list.get(0).getProductName());
    }

    private void saveOne(){
        OrderDetail order = new OrderDetail();
        order.setDetailId("123");
        order.setOrderId("11");
        order.setProductIcon("http:");
        order.setProductId("11");
        order.setProductName("黄焖鸡米饭");
        order.setProductPrice(new BigDecimal(14.5));
        order.setProductQuantity(100);
        repository.save(order);
    }
}