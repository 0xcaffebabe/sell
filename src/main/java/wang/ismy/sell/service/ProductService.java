package wang.ismy.sell.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import wang.ismy.sell.enums.ProductStatusEnum;
import wang.ismy.sell.pojo.ProductInfo;
import wang.ismy.sell.repository.ProductInfoRepository;

import java.util.List;

/**
 * @author MY
 * @date 2020/2/2 14:05
 */
@Service
@AllArgsConstructor
public class ProductService {

    private ProductInfoRepository repository;

    public ProductInfo find(String productId){
        return repository.findById(productId).orElse(null);
    }

    /**
     * 查询上架商品列表
     * @return
     */
    public List<ProductInfo> findUp(){
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    public Page<ProductInfo> findAll(Pageable page){
        return repository.findAll(page);
    }

    public ProductInfo save(ProductInfo productInfo){
        return repository.save(productInfo);
    }


}
