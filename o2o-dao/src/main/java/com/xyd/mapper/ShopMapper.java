package com.xyd.mapper;

import com.xyd.entity.Shop;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    /**
     * 分页查询店铺列表 可输入:店铺名(模糊),店铺状态,区域id,owner
     * @param shopCondition 需要查询的店铺类别
     * @param rowIndex      从第几行开始取
     * @param pageSize      返回的条数
     * @return
     */
    List<Shop> queryShopList(@Param("shopCondition")Shop shopCondition,
                             @Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);

    /**
     * 查询店铺总数
     * @param shopCondition 查询信息实体
     * @return
     */
    int queryShopCount(@Param("shopCondition") Shop shopCondition);
}
