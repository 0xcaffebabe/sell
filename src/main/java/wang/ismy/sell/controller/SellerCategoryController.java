package wang.ismy.sell.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import wang.ismy.sell.enums.ProductStatusEnum;
import wang.ismy.sell.pojo.entity.ProductCategory;
import wang.ismy.sell.pojo.entity.ProductInfo;
import wang.ismy.sell.pojo.form.CategoryForm;
import wang.ismy.sell.service.CategoryService;
import wang.ismy.sell.utils.KeyUtils;

import javax.validation.Valid;

/**
 * @author MY
 * @date 2020/2/4 10:17
 */
@Controller
@RequestMapping("seller/category")
@AllArgsConstructor
public class SellerCategoryController {

    private CategoryService categoryService;

    @GetMapping("list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("category/list");
        mav.addObject("categoryList", categoryService.findAll());
        return mav;
    }

    @GetMapping("index")
    public ModelAndView index(@RequestParam(required = false) Integer categoryId) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("category/index");
        if (categoryId != null && categoryId > 0) {
            ProductCategory productCategory = categoryService.find(categoryId);
            mav.addObject("category", productCategory);
        }
        return mav;
    }

    @PostMapping("save")
    public ModelAndView save(@Valid CategoryForm form, BindingResult bindingResult){
        ModelAndView mav = new ModelAndView();
        if (bindingResult.hasErrors()){
            mav.setViewName("common/error");
            mav.addObject("msg", bindingResult.getFieldError().getDefaultMessage());
            mav.addObject("url", "/seller/category/list");
            return mav;
        }

        ProductCategory productCategory = new ProductCategory();
        if (!StringUtils.isEmpty(form.getCategoryId())){
            productCategory = categoryService.find(form.getCategoryId());
        }
        BeanUtils.copyProperties(form,productCategory);

        try {
            ProductCategory save = categoryService.save(productCategory);
            mav.setViewName("common/success");
            mav.addObject("msg", "更新/保存成功");
            mav.addObject("url", "/seller/category/list");
        }catch (Exception e){
            mav.setViewName("common/error");
            mav.addObject("msg", e.getMessage());
            mav.addObject("url", "/seller/category/list");
        }
        return mav;
    }

}
