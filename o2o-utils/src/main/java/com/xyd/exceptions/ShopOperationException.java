package com.xyd.exceptions;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.exceptions
 * @date 2020/3/22
 * @description 店铺创建异常类
 */
public class ShopOperationException extends RuntimeException {
    public ShopOperationException(String msg){
        super(msg);
    }
}
