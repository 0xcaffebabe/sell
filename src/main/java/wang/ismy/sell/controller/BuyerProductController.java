package wang.ismy.sell.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.sell.pojo.entity.ProductCategory;
import wang.ismy.sell.pojo.entity.ProductInfo;
import wang.ismy.sell.pojo.vo.ProductInfoVO;
import wang.ismy.sell.pojo.vo.ProductVO;
import wang.ismy.sell.pojo.vo.Result;
import wang.ismy.sell.service.CategoryService;
import wang.ismy.sell.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2020/2/2 15:02
 */
@RestController
@RequestMapping("buyer/product")
@AllArgsConstructor
public class BuyerProductController {

    private ProductService productService;

    private CategoryService categoryService;

    @GetMapping("list")
    public Result<List<ProductVO>> list(){

        // 查询所有上架商品
        List<ProductInfo> productInfoList = productService.findUp();
        // 查询类目(一次性查询)
        List<Integer> collectTypeList =
                categoryService.findAll()
                        .stream()
                        .map(ProductCategory::getCategoryType)
                        .collect(Collectors.toList());
        List<ProductCategory> categoryList = categoryService.findByCategoryType(collectTypeList);
        // 数据拼装
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory category : categoryList) {
            ProductVO vo = new ProductVO();
            vo.setCategoryType(category.getCategoryType());
            vo.setCategoryName(category.getCategoryName());
            List<ProductInfoVO> foods = new ArrayList<>();
            vo.setFoods(foods);
            for (ProductInfo product : productInfoList) {
                if (product.getCategoryType().equals(category.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(product,productInfoVO);
                    foods.add(productInfoVO);
                }
            }
            productVOList.add(vo);
        }

        return Result.success(productVOList);
    }
}
