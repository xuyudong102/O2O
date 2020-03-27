package com.xyd.utils;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.utils
 * @date 2020/3/27
 * @description 分页工具类
 */
public class PageCalculator {
    /**
     * 将页数 转换成行数
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public static int calculateRowIndex(int pageIndex,int pageSize){
        return (pageIndex>0)?((pageIndex-1)*pageSize):0;
    }
}
