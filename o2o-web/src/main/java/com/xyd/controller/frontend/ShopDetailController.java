package com.xyd.controller.frontend;

import com.xyd.dto.ProductExecution;
import com.xyd.entity.Product;
import com.xyd.entity.ProductCategory;
import com.xyd.entity.Shop;
import com.xyd.service.ProductCategoryService;
import com.xyd.service.ProductService;
import com.xyd.service.ShopService;
import com.xyd.utils.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
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
public class ShopDetailController {
    @Autowired
    private ShopService shopService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;

    /*获取店铺信息和商品列表的信息*/
    @RequestMapping(value = "/listshopdetailpageinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map listShopDetailPageInfo(HttpServletRequest request) {
        Map modelMap = new HashMap();
        //接收shopId；
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        Shop shop = null;
        List<ProductCategory> productCategoryList = null;
        if (shopId > -1) {
            //如果能接受饿到shopId的话 就去数据库查询商品类别列表  商品详情  商铺基本信息
            shop = shopService.getByShopId(shopId);
            productCategoryList = productCategoryService.getProductCategoryList(shopId);
            modelMap.put("success", true);
            modelMap.put("shop", shop);
            modelMap.put("productCategoryList", productCategoryList);
        } else {
            modelMap.put("success", false);
        }
        return modelMap;
    }

    /*获取商品列表*/
    @RequestMapping(value = "listproductsbyshop", method = RequestMethod.GET)
    @ResponseBody
    private Map listProductsByShop(HttpServletRequest request) throws UnsupportedEncodingException {
        Map modelMap = new HashMap();
        //获取页码
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        //获取页面显示条数
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if (shopId > -1 && pageIndex > -1 && pageSize > -1) {
            //尝试获取商品类别id
            long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
            //尝试获取商品名字用于模糊查询
            String productName = HttpServletRequestUtil.getString(request, "productName");
            if (productName!=null){
                productName = new String(productName.getBytes("ISO-8859-1"),"utf-8");
            }
            Product productCondition = compactProductCondition4Search(shopId, productName, productCategoryId);
            ProductExecution productExecution = productService.getProductList(productCondition, pageIndex, pageSize);
            modelMap.put("productList", productExecution.getProductList());
            modelMap.put("count", productExecution.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
        }
        return modelMap;
    }

    //创建查询条件对象
    private Product compactProductCondition4Search(long shopId, String productName, long productCategoryId) {
        Shop shop = new Shop();
        shop.setShopId(shopId);
        Product productCondition = new Product();
        productCondition.setShop(shop);
        if (productCategoryId > -1) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        if (productName != null) {
            productCondition.setProductName(productName);
        }
        //只允许选出状态为上架的商品
        productCondition.setEnableStatus(1);
        return productCondition;
    }
}