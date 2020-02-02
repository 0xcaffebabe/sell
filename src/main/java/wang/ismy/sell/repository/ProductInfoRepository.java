package wang.ismy.sell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.sell.pojo.entity.ProductInfo;

import java.util.List;

/**
 * @author MY
 * @date 2020/2/2 13:57
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {

    /**
     * 根据商品状态查询
     * @param status
     * @return
     */
    List<ProductInfo> findByProductStatus(Integer status);
}
