package wang.ismy.sell.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import wang.ismy.sell.service.CategoryService;

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
        mav.addObject("categoryList",categoryService.findAll());
        return mav;
    }
}
