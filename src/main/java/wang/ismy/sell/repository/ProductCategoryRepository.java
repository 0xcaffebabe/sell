package wang.ismy.sell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.sell.pojo.ProductCategory;

import java.util.List;

/**
 * @author MY
 * @date 2020/2/2 11:37
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {

    /**
     * 根据目录类型批量查询
     * @param typeList 目录类型列表
     * @return 类目列表
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> typeList);
}
