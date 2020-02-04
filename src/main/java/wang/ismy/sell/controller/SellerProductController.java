package wang.ismy.sell.controller;

import com.alipay.api.domain.Product;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import wang.ismy.sell.enums.ProductStatusEnum;
import wang.ismy.sell.pojo.dto.OrderDTO;
import wang.ismy.sell.pojo.entity.ProductCategory;
import wang.ismy.sell.pojo.entity.ProductInfo;
import wang.ismy.sell.pojo.form.ProductForm;
import wang.ismy.sell.service.CategoryService;
import wang.ismy.sell.service.ProductService;
import wang.ismy.sell.utils.KeyUtils;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author MY
 * @date 2020/2/4 8:53
 */
@Controller
@RequestMapping("seller/product")
@AllArgsConstructor
public class SellerProductController {

    private ProductService productService;
    private CategoryService categoryService;

    @GetMapping("list")
    public ModelAndView list(@RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "10") Integer size) {
        page = page - 1;
        Page<ProductInfo> list = productService.findAll(PageRequest.of(page, size));

        ModelAndView mav = new ModelAndView();
        mav.addObject("list", list);
        mav.addObject("currentPage", page + 1);
        mav.addObject("size", size);
        mav.setViewName("product/list");
        return mav;
    }

    @GetMapping("on_sale")
    @CacheEvict(value = "product",key = "123")
    public ModelAndView onSale(@RequestParam String productId) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("common/success");
        mav.addObject("msg", "上架成功");
        mav.addObject("url", "/seller/product/list");
        try {
            if (productService.onSale(productId) == null) {
                throw new Exception("未知错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            mav.setViewName("common/error");
            mav.addObject("msg", e.getMessage());
            mav.addObject("url", "/seller/product/list");
        }
        return mav;
    }

    @GetMapping("off_sale")
    @CacheEvict(value = "product",key = "123")
    public ModelAndView offSale(@RequestParam String productId) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("common/success");
        mav.addObject("msg", "下架成功");
        mav.addObject("url", "/seller/product/list");
        try {
            if (productService.offSale(productId) == null) {
                throw new Exception("未知错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            mav.setViewName("common/error");
            mav.addObject("msg", e.getMessage());
            mav.addObject("url", "/seller/product/list");
        }
        return mav;
    }

    @GetMapping("index")
    @CacheEvict(value = "product",key = "123")
    public ModelAndView index(@RequestParam(required = false) String productId, Map<String, Object> map) {
        if (!StringUtils.isEmpty(productId)){
            ProductInfo productInfo = productService.find(productId);
            map.put("productInfo",productInfo);
        }
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList",categoryList);
        return new ModelAndView("product/index",map);
    }

    @PostMapping("save")
    @CacheEvict(value = "product",key = "123")
    public ModelAndView save(@Valid ProductForm form, BindingResult bindingResult,Map<String,Object> map){
        ModelAndView mav = new ModelAndView();
        if (bindingResult.hasErrors()){
            mav.setViewName("common/error");
            mav.addObject("msg", bindingResult.getFieldError().getDefaultMessage());
            mav.addObject("url", "/seller/product/list");
            return mav;
        }

        ProductInfo productInfo = new ProductInfo();
        if (!StringUtils.isEmpty(form.getProductId())){
            productInfo = productService.find(form.getProductId());
        }else {
            form.setProductId(KeyUtils.generateUniqueKey());
            productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        }
        BeanUtils.copyProperties(form,productInfo);

        try {
            ProductInfo save = productService.save(productInfo);
            mav.setViewName("common/success");
            mav.addObject("msg", "更新/保存成功");
            mav.addObject("url", "/seller/product/list");
        }catch (Exception e){
            mav.setViewName("common/error");
            mav.addObject("msg", e.getMessage());
            mav.addObject("url", "/seller/product/list");
        }
        return mav;
    }

}
