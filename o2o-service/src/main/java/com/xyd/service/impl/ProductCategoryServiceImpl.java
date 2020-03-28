package com.xyd.service.impl;

import com.xyd.dto.ProductCategoryExecution;
import com.xyd.entity.ProductCategory;
import com.xyd.enums.ProductCategoryStateEnum;
import com.xyd.exceptions.ProductCategoryOperationException;
import com.xyd.mapper.ProductCategoryMapper;
import com.xyd.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.service.impl
 * @date 2020/3/28
 * @description
 */
@Service("productCategoryService")
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    /**
     * 商品类别的查询
     *
     * @return
     */
    @Override
    public List<ProductCategory> getProductCategoryList(Long shopId) {
        if (shopId != null) {
            List<ProductCategory> productCategoryList = productCategoryMapper.queryProductCategoryByShopId(shopId);
            return productCategoryList;
        } else {
            return null;
        }
    }

    @Override
    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException {
        if(productCategoryList!=null){
            try {
                int effectedNum = productCategoryMapper.batchInsertProductCategory(productCategoryList);
                if(effectedNum<=0){
                    //插入失败
                    throw new ProductCategoryOperationException("店铺类别创建失败");
                }else{
                    //插入成功
                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
                }
            }catch (Exception e){
                throw new ProductCategoryOperationException("batchAddProductCategory error" + e.getMessage());
            }
        }else {
            //如果传递的列表为null
            return (new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST));
        }
    }

    @Override
    @Transactional
    public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) throws ProductCategoryOperationException {
        //TODO 将此商品类别下的商品类别id 置空
        try{
            int effectedNum = productCategoryMapper.deleteProductCategory(productCategoryId,shopId);
            if(effectedNum<=0){
                throw new ProductCategoryOperationException("店铺商品类别删除失败");
            }else{
                return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            }
        }catch (Exception e){
            throw new ProductCategoryOperationException("店铺商品类别删除失败");
        }
    }
}
