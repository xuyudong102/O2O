package com.xyd.dao;

import com.xyd.BaseTest;
import com.xyd.entity.ProductCategory;
import com.xyd.entity.ShopCategory;
import com.xyd.mapper.ProductCategoryMapper;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.dao
 * @date 2020/3/28
 * @description
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductCategoryDaoTest extends BaseTest {
    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Test
    //@Ignore
    public void testBQueryProductCategoryByShopId(){
        long shopId=2L;
        List<ProductCategory> productCategories =productCategoryMapper.queryProductCategoryByShopId(shopId);
        System.out.println(productCategories);
    }
    @Test
    public void testABatchInsertProductCategory(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setPriority(1);
        productCategory.setProductCategoryName("商品类别1");
        productCategory.setCreateTime(new Date());
        productCategory.setShopId(2L);
        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setPriority(2);
        productCategory2.setProductCategoryName("商品类别2");
        productCategory2.setCreateTime(new Date());
        productCategory2.setShopId(2L);
        List<ProductCategory> productCategoryList = new ArrayList<>();
        productCategoryList.add(productCategory);
        productCategoryList.add(productCategory2);
        int effectdNum = productCategoryMapper.batchInsertProductCategory(productCategoryList);
        Assert.assertEquals(2,effectdNum);
    }
    @Test
    public void testCDeleteProductCategory(){
        long shopId=2L;
        List<ProductCategory> productCategoryList = productCategoryMapper.queryProductCategoryByShopId(2L);
        for(ProductCategory productCategory:productCategoryList){
            if(productCategory.getProductCategoryName().equals("商品类别1")||productCategory.getProductCategoryName().equals("商品类别2")){
                int effectedNum = productCategoryMapper.deleteProductCategory(productCategory.getProductCategoryId(),shopId);
                Assert.assertEquals(1,effectedNum);
            }
        }
    }
}
