package com.xyd.service;

import com.xyd.BaseTest;
import com.xyd.dto.ImageHolder;
import com.xyd.dto.ProductExecution;
import com.xyd.entity.Product;
import com.xyd.entity.ProductCategory;
import com.xyd.entity.ProductImg;
import com.xyd.entity.Shop;
import com.xyd.enums.ProductStateEnum;
import com.xyd.exceptions.ShopOperationException;
import com.xyd.mapper.ProductMapper;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.service
 * @date 2020/3/31
 * @description
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductServiceTest extends BaseTest {
    @Autowired
    private ProductService productService;
    //创建shopId为1 并且productCategory为1 的商品实例 并给成员变量赋值

    @Test
    @Ignore
    public void testAAddProduct() throws ShopOperationException, FileNotFoundException {
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(2);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(2);
        product.setShop(shop);
        product.setProductCategory(productCategory);
        product.setProductName("测试商品1");
        product.setProductDesc("测试商品1");
        product.setPriority(20);
        product.setCreateTime(new Date());
        product.setEnableStatus(1);
        //创建缩略图文件流
        File thumbnailFile = new File("C:\\Users\\XuYuDong\\Desktop\\damei.jpg");
        InputStream is = new FileInputStream(thumbnailFile);
        ImageHolder thumbnailImageHolder = new ImageHolder(is, thumbnailFile.getName());
        //创建两个详情图片的文件流
        File productImgFile = new File("C:\\Users\\XuYuDong\\Desktop\\kafei.jpg");
        File productImgFile2 = new File("C:\\Users\\XuYuDong\\Desktop\\maomi.jpg");
        List<ImageHolder> productImageHolderList = new ArrayList<>();
        InputStream is1 = new FileInputStream(productImgFile);
        InputStream is2 = new FileInputStream(productImgFile2);
        productImageHolderList.add(new ImageHolder(is1, productImgFile.getName()));
        productImageHolderList.add(new ImageHolder(is2, productImgFile2.getName()));
        ProductExecution pe = productService.addProduct(product, thumbnailImageHolder, productImageHolderList);
        Assert.assertEquals(1, ProductStateEnum.SUCCESS.getState());
    }

    @Test
    public void testBModifyProduct() throws FileNotFoundException {
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(2L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(3L);
        product.setShop(shop);
        product.setProductCategory(productCategory);
        product.setProductId(10L);
        product.setProductName("测试商品5");
        product.setProductDesc("测试商品5");
        //创建文件缩略图文件流
        File thumbnailFile = new File("C:\\Users\\XuYuDong\\Desktop\\maomi.jpg");
        InputStream is = new FileInputStream(thumbnailFile);
        ImageHolder thumbnail = new ImageHolder(is, thumbnailFile.getName());
        //创建 详情图片文件流
        File productImg1 = new File("C:\\Users\\XuYuDong\\Desktop\\damei.jpg");
        File productImg2 = new File("C:\\Users\\XuYuDong\\Desktop\\kafei.jpg");
        InputStream is1 = new FileInputStream(productImg1);
        InputStream is2 = new FileInputStream(productImg2);
        List<ImageHolder> productImgList = new ArrayList<>();
        productImgList.add(new ImageHolder(is1,productImg1.getName()));
        productImgList.add(new ImageHolder(is2,productImg2.getName()));
        ProductExecution productExecution =  productService.modifyProduct(product,thumbnail,productImgList);
        Assert.assertEquals(ProductStateEnum.SUCCESS.getState(),productExecution.getState());
    }

}
