package wang.ismy.sell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.sell.pojo.ProductCategory;

/**
 * @author MY
 * @date 2020/2/2 11:37
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> { }
