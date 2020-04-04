package com.xyd.mapper;

import com.xyd.entity.Product;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.mapper
 * @date 2020/3/31
 * @description
 */
@Repository("productMapper")
public interface ProductMapper {
    /**
     * 添加商品信息
     * @param product
     * @return
     */
    int insertProduct(Product product);

    /**
     * 通过productId查询唯一的商品信息
     * @param productId
     * @return
     */
    Product queryProductById(@Param("productId") long productId);

    /**
     * 更新商品信息
     */
    int updateProduct(Product product);


    /**
     * 删除指定商品下所有的详情图片
     */
    int deleteProductImgByProductId(long productId);

    /**
     * 查询商品列表
     * @param productCondition 模糊查询条件 商品名(模糊) 商品状态  商品类别 店铺id
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Product> queryProductList(@Param("productCondition") Product productCondition,@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);

    /**
     * 安条件查询商品个数
     * @param productCondition 查询条件有 商品id 状态 商品名(模糊) 店铺id
     * @return
     */
    int queryProductCount(@Param("productCondition") Product productCondition);
}

