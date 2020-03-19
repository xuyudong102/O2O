package com.xyd.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.entity
 * @date 2020/3/18
 * @description 店铺类别实体类
 */
public class ShopCategory implements Serializable {
    //店铺类别id
    private long shopCategoryId;
    //店铺类别名称
    private String shopCategoryName;
    //店铺类别描述
    private String shopCategoryDesc;
    //店铺类别图片
    private String shopCategoryImg;
    //权重
    private Integer priority;
    //创建时间
    private Date createTime;
    //最后一次修改时间
    private Date lastEditTime;
    //上级id
    private ShopCategory parent;

    public ShopCategory() {
    }

    public ShopCategory(long shopCategoryId, String shopCategoryName, String shopCategoryDesc, String shopCategoryImg, Integer priority, Date createTime, Date lastEditTime, ShopCategory parent) {
        this.shopCategoryId = shopCategoryId;
        this.shopCategoryName = shopCategoryName;
        this.shopCategoryDesc = shopCategoryDesc;
        this.shopCategoryImg = shopCategoryImg;
        this.priority = priority;
        this.createTime = createTime;
        this.lastEditTime = lastEditTime;
        this.parent = parent;
    }

    public long getShopCategoryId() {
        return shopCategoryId;
    }

    public void setShopCategoryId(long shopCategoryId) {
        this.shopCategoryId = shopCategoryId;
    }

    public String getShopCategoryName() {
        return shopCategoryName;
    }

    public void setShopCategoryName(String shopCategoryName) {
        this.shopCategoryName = shopCategoryName;
    }

    public String getShopCategoryDesc() {
        return shopCategoryDesc;
    }

    public void setShopCategoryDesc(String shopCategoryDesc) {
        this.shopCategoryDesc = shopCategoryDesc;
    }

    public String getShopCategoryImg() {
        return shopCategoryImg;
    }

    public void setShopCategoryImg(String shopCategoryImg) {
        this.shopCategoryImg = shopCategoryImg;
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

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public ShopCategory getParent() {
        return parent;
    }

    public void setParent(ShopCategory parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "ShopCategory{" +
                "shopCategoryId=" + shopCategoryId +
                ", shopCategoryName='" + shopCategoryName + '\'' +
                ", shopCategoryDesc='" + shopCategoryDesc + '\'' +
                ", shopCategoryImg='" + shopCategoryImg + '\'' +
                ", priority=" + priority +
                ", createTime=" + createTime +
                ", lastEditTime=" + lastEditTime +
                ", parent=" + parent +
                '}';
    }
}
