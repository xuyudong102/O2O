package com.xyd.service;

import com.xyd.dto.ImageHolder;
import com.xyd.dto.ProductExecution;
import com.xyd.entity.Product;
import com.xyd.exceptions.ProductOperationException;

import java.util.List;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.service
 * @date 2020/3/31
 * @description
 */
public interface ProductService {

    /**
     * 添加商品信息以及图片处理
     * @param product 商品图片
     * @param thumbnail 商品缩略图
     * @param productImgList 商品详情图片
     * @return
     */
    ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList)throws ProductOperationException;

    /**
     * 根据商品id 获取商品信息
     * @param productId
     * @return
     */
    Product getProductById(long productId);

    /**
     * 修改商品信息
     * @param product
     * @param thumbnail
     * @param productImgList
     * @return
     * @throws ProductOperationException
     */
    ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList)throws ProductOperationException;

    /**
     * 根据条件查询所有商品信息 商品名（模糊） 商铺id 商品类别 状态值
     * @param productCondition
     * @param pageIndex 页码
     * @param pageSize
     * @return
     */
    ProductExecution getProductList(Product productCondition,int pageIndex,int pageSize);
}
