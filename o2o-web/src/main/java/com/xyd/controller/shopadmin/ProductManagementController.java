package com.xyd.controller.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyd.dto.ImageHolder;
import com.xyd.dto.ProductExecution;
import com.xyd.entity.Product;
import com.xyd.entity.ProductCategory;
import com.xyd.entity.Shop;
import com.xyd.enums.ProductStateEnum;
import com.xyd.exceptions.ProductOperationException;
import com.xyd.service.ProductCategoryService;
import com.xyd.service.ProductService;
import com.xyd.utils.CodeUtil;
import com.xyd.utils.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.controller.shopadmin
 * @date 2020/3/31
 * @description
 */
@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {
    @Autowired
    ProductService productService;
    @Autowired
    ProductCategoryService productCategoryService;
    //可以上传的最大图片数
    private static final int IMAGMAXCOUNT = 6;

    @RequestMapping(value = "/addproduct", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //先进行验证码校验
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码不正确!!");
            return modelMap;
        }
        //通过验证码校验
        //接受前端的变量的初始化 包括商品实体类 缩略图 详情图片
        ObjectMapper objectMapper = new ObjectMapper();
        Product product = null;
        String productStr = HttpServletRequestUtil.getString(request, "productStr");
        MultipartHttpServletRequest multipartRequest = null;
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgList = new ArrayList<>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        try {
            //把json字符串转换成java实体对象
            product = objectMapper.readValue(productStr, Product.class);
            //如果请求中存在文件流 就取出相关文件(包括缩略图和详情图)
            if (multipartResolver.isMultipart(request)) {
                //如果存在文件流 则取出相关文件(包括缩略图和详情图 )
                thumbnail = handleImage((MultipartHttpServletRequest) request, productImgList);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "文件上传不能为空");
                return modelMap;
            }

            //如果三个参数都具备的话
            if (product != null && thumbnail != null && productImgList.size() > 0) {
                //从session中获取shopId
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
                //执行添加操作
                ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            }
        } catch (ProductOperationException | IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value = "getproductbyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getProductById(@RequestParam Long productId) {
        Map<String, Object> modelMap = new HashMap<>();
        //非空判断
        if (productId > -1) {
            Product product = productService.getProductById(productId);
            if(product.getProductCategory()==null){
                //如果为null的话 随便给个值
                ProductCategory productCategory = new ProductCategory();
                productCategory.setProductCategoryId(2L);
                product.setProductCategory(productCategory);
            }
            //获取所有的商品类别信息 需要根据shopid
            List<ProductCategory> productCategoryList = productCategoryService.getProductCategoryList(product.getShop().getShopId());
            modelMap.put("product", product);
            modelMap.put("productCategoryList", productCategoryList);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty productId");
        }
        return modelMap;
    }

    //修改商品信息
    @RequestMapping(value = "/modifyproduct", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //先进行验证码校验
        //是商品编辑时候调用还是上架下架操作的时候调用
        //如果是前者 进行 验证码判断   否则不需要验证码判断
        boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");

        //验证码操作
        if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码不正确!!");
            return modelMap;
        }
        //通过验证码校验
        //接受前端的变量的初始化 包括商品实体类 缩略图 详情图片
        ObjectMapper objectMapper = new ObjectMapper();
        Product product = null;
        String productStr = HttpServletRequestUtil.getString(request, "productStr");
        MultipartHttpServletRequest multipartRequest = null;
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgList = new ArrayList<>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        try {
            //把json字符串转换成java实体对象
            product = objectMapper.readValue(productStr, Product.class);
            //如果请求中存在文件流 就取出相关文件(包括缩略图和详情图)
            //不存在也没关系
            if (multipartResolver.isMultipart(request)) {
                thumbnail = handleImage((MultipartHttpServletRequest) request, productImgList);
            }
            //如果三个参数都具备的话
            if (product != null) {
                //从session中获取shopId
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
                //执行添加操作
                ProductExecution pe = productService.modifyProduct(product, thumbnail, productImgList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            }
        } catch (ProductOperationException | IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    /*根据条件查询商品列表信息*/
    @RequestMapping(value = "/getproductlistbyshop", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getProductListByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //接取传过来的页码 和 每页数据大小
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        //从session中拿到currentShop
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        //控制判断
        if (pageIndex > -1 && pageSize > -1 && currentShop != null && currentShop.getShopId() != null) {
            //如果都不为空的话 就用shopid 和 传入的条件检索
            long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
            String productName = HttpServletRequestUtil.getString(request, "productName");
            Product productCondition = compactProductCondition(currentShop.getShopId(), productCategoryId, productName);
            //传入查询调价以及分页信息进行查询 返回相应的商品列表 以及总数
            ProductExecution productExecution = productService.getProductList(productCondition,pageIndex,pageSize);
            modelMap.put("success",true);
            modelMap.put("productList",productExecution.getProductList());
            modelMap.put("count",productExecution.getCount());
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex or ShopId");
        }
        return modelMap;
    }

    /**
     * 整合查询条件参数
     *
     * @param shopId
     * @param productCategoryId
     * @param productName
     * @return
     */
    private Product compactProductCondition(long shopId, long productCategoryId, String productName) {
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        if (productCategoryId != -1l) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        if (productName != null) {
            productCondition.setProductName(productName);
        }
        return productCondition;
    }


    private ImageHolder handleImage(MultipartHttpServletRequest request, List<ImageHolder> productImgList) throws IOException {
        MultipartHttpServletRequest multipartRequest;
        ImageHolder thumbnail;//如果存在文件流 则取出相关文件(包括缩略图和详情图 )
        multipartRequest = request;
        //取出缩略图病构建imageHolder对象
        CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
        thumbnail = new ImageHolder(thumbnailFile.getInputStream(), thumbnailFile.getOriginalFilename());
        for (int i = 0; i < IMAGMAXCOUNT; i++) {
            CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest.getFile("productImg" + i);
            if (productImgFile != null) {
                //证明有文件
                ImageHolder imageHolder = new ImageHolder(productImgFile.getInputStream(), productImgFile.getOriginalFilename());
                productImgList.add(imageHolder);
            } else {
                //若取出第i个值为空 则终止循环
                break;
            }
        }
        return thumbnail;
    }
}
