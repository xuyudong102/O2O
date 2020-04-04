package com.xyd.enums;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.enums
 * @date 2020/3/28
 * @description
 */
public enum ProductStateEnum {
    SUCCESS(1,"创建成功"),INNER_ERROR(-1001,"操作失败"),
    EMPTY_ERROR(-1002,"图片不存在");
    //状态值
    private int state;
    //状态值
    private String stateInfo;

    //为什么设置成私有的 不希望外部访问 只能当做常量来访问
    private ProductStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static ProductStateEnum stateOf(int state){
        for (ProductStateEnum stateEnum:values()){
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
