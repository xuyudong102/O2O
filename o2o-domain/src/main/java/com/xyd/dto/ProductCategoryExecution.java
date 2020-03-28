package com.xyd.dto;

import com.xyd.entity.ProductCategory;
import com.xyd.enums.ProductCategoryStateEnum;
import com.xyd.enums.ShopStateEnum;

import java.util.List;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.dto
 * @date 2020/3/28
 * @description
 */
public class ProductCategoryExecution {
    ;
    //结果状态
    private int state;
    //状态标识
    private String stateInfo;
    //商品类别列表
    private List<ProductCategory> productCategoryList;

    ProductCategoryExecution() {
    }

    //添加失败
     public ProductCategoryExecution(ProductCategoryStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }
    //添加成功
    public  ProductCategoryExecution(ProductCategoryStateEnum stateEnum,List<ProductCategory> productCategoryList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.productCategoryList = productCategoryList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public List<ProductCategory> getProductCategoryList() {
        return productCategoryList;
    }

    public void setProductCategoryList(List<ProductCategory> productCategoryList) {
        this.productCategoryList = productCategoryList;
    }
}
