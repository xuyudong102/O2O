package com.xyd.exceptions;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.exceptions
 * @date 2020/3/31
 * @description
 */
public class ProductOperationException extends RuntimeException {
    public ProductOperationException(String msg){
        super(msg);
    }
}
