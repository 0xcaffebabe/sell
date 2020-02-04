package wang.ismy.sell.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.sell.pojo.entity.ProductInfo;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Test
    @Transactional
    public void find() {
        saveOne();
        ProductInfo product = productService.find("123");
        assertEquals("黄焖鸡米饭",product.getProductName());
    }

    @Test
    @Transactional
    public void findUp() {
        saveOne();
        List<ProductInfo> list = productService.findUp();
        assertEquals(1,list.size());
    }

    @Test
    @Transactional
    public void findAll() {
        saveOne();
        Page<ProductInfo> page = productService.findAll(PageRequest.of(0, 1));
        assertEquals(1,page.getContent().size());
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
        productService.save(product);
    }

    @Test
    public void testOnSale(){
        productService.onSale("1");
    }

    @Test
    public void testOffSale(){
        productService.offSale("1");
    }
}