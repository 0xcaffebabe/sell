package wang.ismy.sell.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.sell.pojo.entity.ProductInfo;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductInfoRepositoryTest {

    @Autowired
    ProductInfoRepository repository;

    @Test
    @Transactional
    public void testSave(){
        saveOne();

        List<ProductInfo> list = repository.findAll();
        assertEquals(1, list.size());
        assertEquals("黄焖鸡米饭",list.get(0).getProductName());
    }

    private void saveOne() {
        ProductInfo product = new ProductInfo();
        product.setProductId("123");
        product.setProductName("黄焖鸡米饭");
        product.setProductPrice(BigDecimal.valueOf(14));
        product.setProductStock(999);
        product.setProductDescription("不能多吃");
        product.setProductIcon("http://img.com/x.jpg");
        product.setProductStatus(0);
        product.setCategoryType(1);
        repository.save(product);
    }

    @Test
    @Transactional
    public void testFindByStatus(){
        saveOne();
        List<ProductInfo> list = repository.findByProductStatus(0);
        assertEquals(1,list.size());
    }
}