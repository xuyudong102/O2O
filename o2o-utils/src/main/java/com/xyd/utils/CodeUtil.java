package com.xyd.utils;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.utils
 * @date 2020/3/24
 * @description 判断验证码是否正确
 */
public class CodeUtil {
    public static boolean checkVerifyCode(HttpServletRequest request){
        //获得图片里面的验证码
        String verifyCodeExpected = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        //获得输入的验证码
        String verifyCodeActual = HttpServletRequestUtil.getString(request,"verifyCodeActual");
        // 比较验证码是否相等
        if(verifyCodeActual==null ||!verifyCodeActual.equalsIgnoreCase(verifyCodeExpected)){
            //如果为空 或者验证码不相等的话
            return false;
        }
        return  true;
    }
}
