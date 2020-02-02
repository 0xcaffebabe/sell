package wang.ismy.sell.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.sell.pojo.ProductCategory;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CategoryServiceTest {

    @Autowired
    CategoryService service;

    @Test
    public void find() {
        ProductCategory category = service.find(1);
        assertNull(category);
    }

    @Test
    public void findAll() {
        List<ProductCategory> list = service.findAll();
        assertEquals(0,list.size());
    }

    @Test
    @Transactional
    public void findByCategoryType() {
        ProductCategory category1 = new ProductCategory();
        category1.setCategoryName("1");
        category1.setCategoryType(1);

        ProductCategory category2 = new ProductCategory();
        category2.setCategoryName("2");
        category2.setCategoryType(2);

        service.save(category1);
        service.save(category2);

        List<ProductCategory> list = service.findByCategoryType(List.of(1, 2));
        assertEquals(2,list.size());
    }

    @Test
    public void save() {
    }
}