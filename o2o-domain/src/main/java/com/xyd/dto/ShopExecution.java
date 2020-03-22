package com.xyd.dto;

import com.xyd.entity.Shop;
import com.xyd.enums.ShopStateEnum;

import java.util.List;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.dto
 * @date 2020/3/22
 * @description
 */
public class ShopExecution {
    //结果状态
    private int state;
    //状态表示
    private String stateInfo;
    //店铺数量
    private int count;
    //店铺操作: shop 增删改
    private Shop shop;
    //店铺列表(查询店铺列表的时候使用 )
    private List<Shop> shopList;


    public ShopExecution(){

    }

    //店铺操作失败的构造器 只返回状态对象  并不返回shop对象
    public ShopExecution(ShopStateEnum stateEnum){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }
    //店铺操作成功的构造器 既返回状态对象 又返回shop对象
    public ShopExecution(ShopStateEnum stateEnum, Shop shop) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shop = shop;
    }
    //店铺操作成功的构造器 既返回状态对象 又返回shop列表
    public ShopExecution(ShopStateEnum stateEnum, List<Shop> shopList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shopList = shopList;
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

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }
}
