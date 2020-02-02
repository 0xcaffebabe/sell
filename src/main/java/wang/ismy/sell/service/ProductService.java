package wang.ismy.sell.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import wang.ismy.sell.enums.ProductStatusEnum;
import wang.ismy.sell.enums.ResultEnum;
import wang.ismy.sell.exception.SellException;
import wang.ismy.sell.pojo.dto.CartDTO;
import wang.ismy.sell.pojo.entity.ProductInfo;
import wang.ismy.sell.repository.ProductInfoRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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

    public void increaseStock(List<CartDTO> cartDTOS){

    }

    @Transactional(rollbackOn = Exception.class)
    public void decreaseStock(List<CartDTO> cartDTOS){
        for (CartDTO cartDTO : cartDTOS) {
            Optional<ProductInfo> productOpt = repository.findById(cartDTO.getProductId());
            if (productOpt.isEmpty()){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            ProductInfo product = productOpt.get();
            int i = product.getProductStock() - cartDTO.getProductQuantity();
            if (i < 0 ){
                throw new SellException(ResultEnum.STOCK_NOT_ENOUGH);
            }
            product.setProductStock(i);
            repository.save(product);
        }
    }
}
