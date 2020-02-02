package wang.ismy.sell.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.sell.pojo.entity.ProductCategory;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductCategoryRepositoryTest {

    @Autowired
    ProductCategoryRepository repository;

    @Test
    public void testQuery(){
        assertEquals(0,repository.findAll().size());
    }

    @Test
    @Transactional
    public void testSave(){
        ProductCategory category = new ProductCategory();
        category.setCategoryName("沙县小吃");
        category.setCategoryType(1);
        repository.save(category);

        assertEquals(1,repository.findAll().size());
        assertEquals("沙县小吃",repository.findAll().get(0).getCategoryName());
    }

    @Test
    public void testQueryBatch(){
        repository.findByCategoryTypeIn(List.of(1,2));
    }
}