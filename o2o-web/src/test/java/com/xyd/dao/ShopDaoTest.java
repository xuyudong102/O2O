package com.xyd.dao;

import com.xyd.BaseTest;
import com.xyd.entity.Area;
import com.xyd.entity.PersonInfo;
import com.xyd.entity.Shop;
import com.xyd.entity.ShopCategory;
import com.xyd.mapper.ShopMapper;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.dao
 * @date 2020/3/21
 * @description
 */
public class ShopDaoTest extends BaseTest {
    @Autowired
    private ShopMapper shopMapper;
    @Test
    @Ignore
    public void testInsertShop(){
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试的店铺");
        shop.setShopDesc("test");
        shop.setShopAddr("test");
        shop.setPhone("test");
        shop.setShopImg("test");
        shop.setCreatTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("审核中");
        shop.setPriority(1);
        shop.setLastEditTime(new Date());
        int effectedNum = shopMapper.insertShop(shop);
        Assert.assertEquals(1,effectedNum);
    }

    @Test
    public void testUpdateShop(){
        //区域信息不能更改
        Shop shop = new Shop();
        shop.setShopId(1);
        shop.setShopDesc("测试描述");
        shop.setShopAddr("测试地址");
        shop.setLastEditTime(new Date());
        int effectedNum = shopMapper.updateShop(shop);
        Assert.assertEquals(1,effectedNum);
    }
}
