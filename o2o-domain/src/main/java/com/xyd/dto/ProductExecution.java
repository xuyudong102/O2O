package com.xyd.dto;

import com.xyd.entity.Product;
import com.xyd.enums.ProductStateEnum;

import java.util.List;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.dto
 * @date 2020/3/31
 * @description
 */
public class ProductExecution {
    private int state;
    private String stateInfo;
    private int count;
    private Product product;
    protected List<Product> productList;

    public ProductExecution() {
    }
    /*失败的构造方法*/
    public ProductExecution(ProductStateEnum productStateEnum){
        this.state = productStateEnum.getState();
        this.stateInfo = productStateEnum.getStateInfo();
    }
    /*成功的构造方法 增删改*/
    public ProductExecution(ProductStateEnum productStateEnum,Product product){
        this.state = productStateEnum.getState();
        this.stateInfo = productStateEnum.getStateInfo();
        this.product = product;
    }
    /*成功的构造方法 查询*/
    public ProductExecution(ProductStateEnum productStateEnum,List<Product> productList){
        this.state = productStateEnum.getState();
        this.stateInfo = productStateEnum.getStateInfo();
        this.productList = productList;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }



}
