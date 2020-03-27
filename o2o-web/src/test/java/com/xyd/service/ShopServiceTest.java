package com.xyd.service;

import com.xyd.BaseTest;
import com.xyd.dto.ShopExecution;
import com.xyd.entity.Area;
import com.xyd.entity.PersonInfo;
import com.xyd.entity.Shop;
import com.xyd.entity.ShopCategory;
import com.xyd.enums.ShopStateEnum;
import com.xyd.exceptions.ShopOperationException;
import com.xyd.mapper.ShopMapper;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.service
 * @date 2020/3/20
 * @description
 */
public class ShopServiceTest extends BaseTest {
    @Autowired
    private ShopService shopService;

    @Test
    @Ignore
    public void testAddShop(){
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
        shop.setShopName("测试的店铺3");
        shop.setShopDesc("test3");
        shop.setShopAddr("test3");
        shop.setPhone("test3");
        shop.setCreatTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");
        shop.setPriority(1);
        shop.setLastEditTime(new Date());
        File file = new File("E:/idea_workspace/o2o/o2o-web/target/classes/meinv.jpg");
        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ShopExecution execution = shopService.addShop(shop,is,file.getName());
        System.out.println(execution.getState());
    }


    @Test
    @Ignore
    public void testModifyShop()throws ShopOperationException,FileNotFoundException {
        Shop shop = new Shop();
        shop.setShopName("修改后的店铺名称");
        shop.setShopId(3L);
        File file = new File("C:\\Users\\XuYuDong\\Desktop\\kafei.jpg");
        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ShopExecution execution = shopService.modifyShop(shop,is,file.getName());
        System.out.println(execution.getShop().getShopImg());
    }

    @Test
    public void testGetShopList(){
        Shop shopCondition = new Shop();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1L);
        shopCondition.setOwner(owner);
        ShopCategory sc = new ShopCategory();
        sc.setShopCategoryId(2);
        shopCondition.setShopCategory(sc);
        ShopExecution se = shopService.getShopList(shopCondition,1,2);
        System.out.println("店铺列表为:"+se.getShopList().size());
        System.out.println("店铺总是为:"+se.getCount());
    }
}
