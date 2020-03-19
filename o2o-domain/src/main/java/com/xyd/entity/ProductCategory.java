package com.xyd.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.entity
 * @date 2020/3/18
 * @description 商品类别实体类
 */
public class ProductCategory implements Serializable {
    private long productCategoryId;
    private long shopId;
    private String productCategoryName;
    private Integer priority;
    private Date createTime;

    public ProductCategory() {
    }

    public ProductCategory(long productCategoryId, long shopId, String productCategoryName, Integer priority, Date createTime) {
        this.productCategoryId = productCategoryId;
        this.shopId = shopId;
        this.productCategoryName = productCategoryName;
        this.priority = priority;
        this.createTime = createTime;
    }

    public long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "productCategoryId=" + productCategoryId +
                ", shopId=" + shopId +
                ", productCategoryName='" + productCategoryName + '\'' +
                ", priority=" + priority +
                ", createTime=" + createTime +
                '}';
    }
}
