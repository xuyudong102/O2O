package com.xyd.mapper;

import com.xyd.entity.ProductImg;
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
@Repository("productImgMapper")
public interface ProductImgMapper {
    /**
     * 批量导入图片
     * @param productImgList
     * @return
     */
    int batchInsertProductImg(List<ProductImg> productImgList);

    List<ProductImg> queryProductImgByProductId(@Param("productId") long productId);

    //删除原来数据库中的图片
    int deleteProductImgByProductId(@Param("productId") long productId);
}
