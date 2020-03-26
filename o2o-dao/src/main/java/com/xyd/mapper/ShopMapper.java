package com.xyd.mapper;

import com.xyd.entity.Shop;
import org.springframework.stereotype.Repository;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.mapper
 * @date 2020/3/21
 * @description
 */

@Repository("shopMapper")
public interface ShopMapper {
    /**
     * 新增店铺
     * 参数 店铺对象
     * 返回值为1 成功 -1失败
     */
    int insertShop(Shop shop);
    /**
     * 更新店铺信息
     */
    int updateShop(Shop shop);

    /**
     * 通过店铺id 查询店铺信息
     * @param shopId
     * @return
     */
    Shop queryByShopId(long shopId);
}
