package com.xyd.service.impl;

import com.xyd.entity.ShopCategory;
import com.xyd.mapper.ShopCategoryMapper;
import com.xyd.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.service.impl
 * @date 2020/3/23
 * @description
 */
@Service("shopCategoryService")
public class ShopCategoryServiceImpl implements ShopCategoryService {
    @Autowired
    private ShopCategoryMapper shopCategoryMapper;
    @Override
    public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {
        return shopCategoryMapper.queryShopCategory(shopCategoryCondition);
    }
}
