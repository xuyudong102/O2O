package com.xyd.service;

import com.xyd.dto.ShopExecution;
import com.xyd.entity.Shop;

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
     * @param shopImgInputSteam shop图片文件流
     * @return
     */
    ShopExecution addShop(Shop shop, InputStream shopImgInputSteam,String fileName);
}
