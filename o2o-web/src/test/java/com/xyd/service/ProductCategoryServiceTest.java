package com.xyd.service;

import com.xyd.BaseTest;
import com.xyd.dto.ShopExecution;
import com.xyd.entity.Area;
import com.xyd.entity.PersonInfo;
import com.xyd.entity.Shop;
import com.xyd.entity.ShopCategory;
import com.xyd.enums.ShopStateEnum;
import com.xyd.exceptions.ShopOperationException;
import com.xyd.mapper.ProductCategoryMapper;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.service
 * @date 2020/3/20
 * @description
 */
public class ProductCategoryServiceTest extends BaseTest {
    @Autowired
    private ProductCategoryService productCategoryService;

    @Test
    public void testGetProductCategoryList(){
        Long shopId = 2L;
        System.out.println(productCategoryService.getProductCategoryList(shopId));
    }


}
