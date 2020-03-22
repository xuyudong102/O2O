package com.xyd.enums;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.enums
 * @date 2020/3/22
 * @description
 */
public enum ShopStateEnum {
    CHECK(0,"审核中"),OFFLINE(-1,"非法店铺"),SUCCESS(1,"操作成功"),
    PASS(2,"通过认证"),INNER_ERROR(-1001,"内部系统错误"),NULL_SHOPID(-1002,"ShopId为空"),
    NUll_SHOP(-1003,"shop信息为空");

    //状态值
    private int state;
    //状态值
    private String stateInfo;

    //为什么设置成私有的 不希望外部访问 只能当做常量来访问
    private ShopStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static ShopStateEnum stateOf(int state){
        for (ShopStateEnum stateEnum:values()){
            if(stateEnum.getState()==state){
                return  stateEnum;
            }
        }
        return null;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
}
