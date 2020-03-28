package com.xyd.mapper;

import com.xyd.entity.ProductCategory;
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

@Repository("productCategoryMapper")
public interface ProductCategoryMapper {
    /**
     * 根据商品id 查询商品类别列表
     * @return
     */
    public List<ProductCategory> queryProductCategoryByShopId(@Param("shopId") long shopId);

    /**
     * 批量新增商品类别
     * @param productCategoryList
     * @return
     */
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);

    /**
     * 删除商铺商品类别
     * @param productCategoryId
     * @param shopId
     * @return
     */
    int deleteProductCategory(@Param("productCategoryId")long productCategoryId,@Param("shopId")long shopId);
}
