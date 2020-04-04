package com.xyd.dao;

import com.xyd.BaseTest;
import com.xyd.entity.ProductImg;
import com.xyd.mapper.ProductImgMapper;
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
 * @date 2020/3/31
 * @description
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImgDaoTest extends BaseTest {

    @Autowired
    ProductImgMapper productImgMapper;

    /* 测试批量添加图片*/
    @Test
    @Ignore
    public void testABatchInsertProductImg(){
        ProductImg productImg1 = new ProductImg();
        productImg1.setImgAddr("图片1");
        productImg1.setImgDesc("测试图片1");
        productImg1.setPriority(1);
        productImg1.setCreateTime(new Date());
        productImg1.setProductId(1);
        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("图片1");
        productImg2.setImgDesc("测试图片1");
        productImg2.setPriority(1);
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(1);
        List<ProductImg> productImgList1 = new ArrayList<>();
        productImgList1.add(productImg1);
        productImgList1.add(productImg2);
        int effectedNum = productImgMapper.batchInsertProductImg(productImgList1);
        Assert.assertEquals(2,effectedNum);
    }

    @Test
    public void testBQueryProductImgByProductId(){
        long productId = 10L;
        List<ProductImg>productImgList =  productImgMapper.queryProductImgByProductId(productId);
        Assert.assertEquals(2,productImgList.size());
    }
}
