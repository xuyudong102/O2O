package com.xyd.mapper;

import com.xyd.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.mapper
 * @date 2020/3/23
 * @description
 */
@Repository("shopCategoryMapper")
public interface ShopCategoryMapper {
    /**
     * 获取商铺类别信息
     * @return
     */
    List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition") ShopCategory shopCategoryCondition);

}
