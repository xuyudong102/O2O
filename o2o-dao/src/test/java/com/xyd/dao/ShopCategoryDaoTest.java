package com.xyd.dao;

import com.xyd.BaseTest;
import com.xyd.entity.ShopCategory;
import com.xyd.mapper.ShopCategoryMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.dao
 * @date 2020/3/21
 * @description
 */
public class ShopCategoryDaoTest extends BaseTest {
    @Autowired
    private ShopCategoryMapper shopCategoryMapper;
    @Test
    //@Ignore
    public void testQueryShopCategory(){

        ShopCategory testCategory = new ShopCategory();
        ShopCategory parentCategory = new ShopCategory();
        parentCategory.setShopCategoryId(1L);
        testCategory.setParent(parentCategory);
        List<ShopCategory> shopCategoryList = shopCategoryMapper.queryShopCategory(testCategory);
        Assert.assertEquals(1,shopCategoryList.size());
}


}
