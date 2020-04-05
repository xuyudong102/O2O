package com.xyd.dao;

import com.xyd.BaseTest;
import com.xyd.entity.Product;
import com.xyd.entity.ProductCategory;
import com.xyd.entity.Shop;
import com.xyd.mapper.ProductMapper;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Date;
import java.util.List;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.dao
 * @date 2020/3/31
 * @description
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDaoTest extends BaseTest {
    @Qualifier("productMapper")
    @Autowired
    ProductMapper productMapper;


    @Test
    @Ignore
    public void  testAInsertProduct(){
        //添加三个商品
        Product product1 = new Product();
        product1.setProductName("测试商品1");
        product1.setProductDesc("测试商品1");
        product1.setImgAddr("测试商品1");
        product1.setNormalPrice("1988");
        product1.setCreateTime(new Date());
        product1.setPromotionPrice("1655");
        product1.setPriority(1);
        product1.setEnableStatus(1);
        ProductCategory productCategory1 = new ProductCategory();
        productCategory1.setProductCategoryId(2L);
        product1.setProductCategory(productCategory1);
        Shop shop = new Shop();
        shop.setShopId(2L);
        product1.setShop(shop);

        Product product2 = new Product();
        product2.setProductName("测试商品2");
        product2.setProductDesc("测试商品2");
        product2.setImgAddr("测试商品2");
        product2.setNormalPrice("1987");
        product2.setCreateTime(new Date());
        product2.setPromotionPrice("1455");
        product2.setPriority(2);
        product2.setEnableStatus(1);
        product2.setProductCategory(productCategory1);
        product2.setShop(shop);

        int effectedNum = productMapper.insertProduct(product1);
        int effectedNum2 = productMapper.insertProduct(product2);
        Assert.assertEquals(1,effectedNum);
        Assert.assertEquals(1,effectedNum2);
    }

    @Test
    @Ignore
    public  void testBQueryProductByProductId(){
        long productId = 11l;
        Product product = productMapper.queryProductById(productId);
        System.out.println(product);
    }

    @Test
    @Ignore
    public void testCUpdateProduct(){
        Product product = new Product();
        product.setProductId(11l);
        product.setProductName("测试店铺3");
        product.setProductDesc("2222");
        product.setImgAddr("aaa");
        product.setNormalPrice("4");
        product.setPromotionPrice("2");
        product.setLastEditTime(new Date());
        Shop shop = new Shop();
        shop.setShopId(2);
        product.setShop(shop);
        ProductCategory productCategory  = new ProductCategory();
        productCategory.setProductCategoryId(3);
        product.setProductCategory(productCategory);
        int effectedNum = productMapper.updateProduct(product);
        Assert.assertEquals(1,effectedNum);
    }

    @Test
    @Ignore
    public  void testDDeleteProductImgByProductId(){
        long productId = 11l;
        int effectedNum = productMapper.deleteProductImgByProductId(productId);
        Assert.assertEquals(3,effectedNum);
    }

    @Test
    @Ignore
    public void testEQueryProductList(){
        Product productCondition = new Product();
        //分页查询 预期返回三个结果
        List<Product> productList = productMapper.queryProductList(productCondition,0,3);
        Assert.assertEquals(3,productList.size());
        //查询名称为测试的商品总数
        productCondition.setProductName("测试");
        int  count= productMapper.queryProductCount(productCondition);
        Assert.assertEquals(9,count);

    }

    @Test
    public void testFUpdateProductCategoryToNull(){
        long productCategoryId = 3L;
        int effectedNum = productMapper.updateProductCategoryToNull(productCategoryId);
        Assert.assertEquals(3,effectedNum);
    }
}
