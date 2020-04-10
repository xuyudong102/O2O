package com.xyd.controller.frontend;

import com.xyd.entity.Product;
import com.xyd.service.ProductService;
import com.xyd.utils.HttpServletRequestUtil;
import org.apache.ibatis.annotations.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.controller.frontend
 * @date 2020/4/9
 * @description
 */
@Controller
@RequestMapping(value = "/frontend", method = RequestMethod.GET)
public class ProductDetailController {
    @Autowired
    private ProductService productService;

    //根据商品id返回商品详情
    @RequestMapping(value = "/listproductdetailpageinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listProductDetailPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        long productId = HttpServletRequestUtil.getLong(request, "productId");
        if (productId > -1) {
            //根据productId 获取商品信息 包含 商品详情图列表
            Product product = productService.getProductById(productId);
            modelMap.put("success", true);
            modelMap.put("product",product);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty productId");
        }
        return modelMap;
    }
}
