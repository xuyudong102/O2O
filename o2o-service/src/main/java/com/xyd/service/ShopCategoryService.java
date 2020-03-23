package com.xyd.service;

import com.xyd.entity.Area;
import com.xyd.entity.ShopCategory;

import java.util.List;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.service
 * @date 2020/3/23
 * @description
 */
public interface ShopCategoryService {
    public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
