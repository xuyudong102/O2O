package com.xyd.service;

import com.xyd.dto.ProductCategoryExecution;
import com.xyd.entity.ProductCategory;
import com.xyd.exceptions.ProductCategoryOperationException;

import java.util.List;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.service
 * @date 2020/3/28
 * @description
 */
public interface ProductCategoryService {
    /**
     * 商品类别的查询
     * @return
     */
    public List<ProductCategory> getProductCategoryList(Long shopId);

    /**
     * 批量插入类别信息
     * @param productCategoryList
     */
    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)throws ProductCategoryOperationException;

    /**
     * 删除店铺商品类别信息 将此类别下的商品里的id置空 在删除掉该商品类别
     * @param productCategoryId
     * @param shopId
     * @return
     */
    ProductCategoryExecution deleteProductCategory(long productCategoryId,long shopId)throws ProductCategoryOperationException;
}
