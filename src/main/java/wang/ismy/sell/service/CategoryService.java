package wang.ismy.sell.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wang.ismy.sell.pojo.ProductCategory;
import wang.ismy.sell.repository.ProductCategoryRepository;

import java.util.List;

/**
 * @author MY
 * @date 2020/2/2 13:37
 */
@Service
@AllArgsConstructor
public class CategoryService {

    private ProductCategoryRepository repository;

    public ProductCategory find(Integer categoryId){
        return repository.findById(categoryId).orElse(null);
    }

    public List<ProductCategory> findAll(){
        return repository.findAll();
    }

    public List<ProductCategory> findByCategoryType(List<Integer> typeList){
        return repository.findByCategoryTypeIn(typeList);
    }

    public ProductCategory save(ProductCategory category){
        return repository.save(category);
    }
}
