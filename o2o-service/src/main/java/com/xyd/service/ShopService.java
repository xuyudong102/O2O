package com.xyd.service;

import com.xyd.dto.ImageHolder;
import com.xyd.dto.ShopExecution;
import com.xyd.entity.Shop;
import com.xyd.exceptions.ShopOperationException;

import java.io.File;
import java.io.InputStream;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.service
 * @date 2020/3/22
 * @description
 */
public interface ShopService {
    /**
     * 添加店铺信息
     * @param shop shop店铺实体
     * @param thumbnail shop图片文件流
     * @return
     */
    ShopExecution addShop(Shop shop, ImageHolder thumbnail)throws ShopOperationException;

    /**
     * 通过店铺id获取店铺信息
     * @param shopId
     * @return
     */
    Shop getByShopId(long shopId);

    /**
     * 跟新店铺信息包括对图片的处理
     * @param shop
     * @param thumbnail
     * @return
     */
    ShopExecution modifyShop(Shop shop,ImageHolder thumbnail)throws ShopOperationException;

    /**
     * 根据shopCondition 分页返回相应列表数据
     * @param shopCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);

}
