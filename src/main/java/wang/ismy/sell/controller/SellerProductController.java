package wang.ismy.sell.controller;

import com.alipay.api.domain.Product;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import wang.ismy.sell.pojo.dto.OrderDTO;
import wang.ismy.sell.pojo.entity.ProductInfo;
import wang.ismy.sell.service.ProductService;

/**
 * @author MY
 * @date 2020/2/4 8:53
 */
@Controller
@RequestMapping("seller/product")
@AllArgsConstructor
public class SellerProductController {

    private ProductService productService;

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
}
