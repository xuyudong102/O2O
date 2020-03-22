package com.xyd.utils;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.utils
 * @date 2020/3/22
 * @description 为图片上传功能提供路径
 */
public class PathUtil {
    /**
     * 为所有上传图片提供根路径
     * @return
     */
    private  static  String separator = System.getProperty("file.separator");
    public static String getImgBasePath(){
        //先判断系统是windows 还是linux
        String os = System.getProperty("os.name");
        //判断系统分割符号  因为不同的系统分割符号不同

        String basePath="";
        if(os.toLowerCase().startsWith("win")){
            //如果是windows系统
            basePath="C:/projectdev/image/";
        }else{
            //如果是linux系统
            basePath="/home/o2o/image/";
        }
        basePath=basePath.replace("/",separator);
        return basePath;
    }

    /**
     * 获取上传商铺图片的根路径
     * @return
     */
    public static String getShopImgPath(long shopId){
        String imagePath="upload/item/shop/"+shopId+"/";
        //返回每一张图片的具体路径
        return imagePath.replace("/",separator);
    }
}
