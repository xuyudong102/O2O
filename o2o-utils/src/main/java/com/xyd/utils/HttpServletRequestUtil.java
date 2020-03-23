package com.xyd.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.utils
 * @date 2020/3/23
 * @description
 */
public class HttpServletRequestUtil {
    /**
     * 从request对象中提取一个整形  如果提取到了就转换成integer  提取不到就转换成-1
     * @param request
     * @param key
     * @return
     */
    public static int getInt(HttpServletRequest request,String key){
        try {
            return Integer.decode(request.getParameter(key));
        }catch (Exception e){
            return -1;
        }
    }

    public static long getLong(HttpServletRequest request,String key){
        try {
            return Long.valueOf(request.getParameter(key));
        }catch (Exception e){
            return -1l;
        }
    }

    public static double getDouble(HttpServletRequest request,String key){
        try {
            return Double.valueOf(request.getParameter(key));
        }catch (Exception e){
            return -1d;
        }
    }

    public static boolean getBoolean(HttpServletRequest request,String key){
        try {
            return Boolean.valueOf(request.getParameter(key));
        }catch (Exception e){
            return false;
        }
    }

    public static String getString (HttpServletRequest request,String key){
        try{
            String result = request.getParameter(key);
            if(result!=null){
                return result.trim();
            }
            if(result.equals("")){
                return  null;
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }
}
