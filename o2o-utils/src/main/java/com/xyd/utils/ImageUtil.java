package com.xyd.utils;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.utils
 * @date 2020/3/21
 * @description
 */

public class ImageUtil {
    //需要添加日志信息
    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    //类加载路径的绝对路径 (水印图片存储路径)
    private static String basePath=Thread.currentThread().getContextClassLoader().getResource("").getPath().substring(1);
    //simpleDateFormat 变量
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    //随机数变量
    private static Random  r = new Random();

    /**
     * 把前台表单传来的文件CommonsMultipartFile流转换成File io流
     * @param cFile 前台表单传来的文件流
     * @return File文件流
     */
    public static File transferCommonsMultipartFileToFile(CommonsMultipartFile cFile){
        File newFile = new File(cFile.getOriginalFilename());
        try {
            cFile.transferTo(newFile);
        }catch (IllegalStateException e) {
            logger.error(e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return newFile;
    }


    /**
     *处理缩略图 并返回目标图片的相对值路径
     * @param shopImgInputSteam 上传图片的文件流
     * @param targetAddr 新图片目标文件地址
     * @param fileName 传入文件名
     * @return 返回值不包含图片的基础路径  文件相对路径
     */
    //处理缩略图 门面照 和商品的小图
    public static String generateThumbnail(InputStream shopImgInputSteam,String targetAddr,String fileName){
        //获取新文件的随机名 和 拓展名
        String realFileName = getRandomFileName();
        String extension = getFileExtension(fileName);
        //创建 文件目标文件夹
        makeDirPath(targetAddr);
        //新文件地址:
        String relativeAddr = targetAddr +realFileName+extension;
        //将图片的相对路径输出到日志中
        System.out.println("水印文件路径为:"+basePath+"watermark.jpg");
        logger.debug("current realtiveAddr is"+relativeAddr);
        //创建新的图片 基础图片路径+图片目标路径+随机生成图片名
        File dest = new File(PathUtil.getImgBasePath()+relativeAddr);
        //将图片的全部路径输出到日志文件
        logger.debug("current completeAddr is"+PathUtil.getImgBasePath()+relativeAddr);
        try {
            //参数1:文件路径 size->图片大小 watermark水印图片的位置和图片路径和透明度     outputQuality->图片压缩一下
            //Thumbnails.of(thumbnail.getInputStream()).size(200,200).watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath+"watermark.jpg")),025f)
             // .outputQuality(0.8f).toFile(dest);
            Thumbnails.of(shopImgInputSteam).size(200,200).watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath+"watermark.jpg")),0.25f)
             .outputQuality(0.8f).toFile(dest);

        }catch (IOException e){
            //如果生成失败了 向日志中打印错误信息
            logger.error(e.toString());
            //向控制台输出错误信息
            e.printStackTrace();
        }
        //返回值不包含图片的基础路径
        return relativeAddr;
    }

    /**
     *处理详情图 并返回目标图片的相对值路径
     * @param shopImgInputSteam 上传图片的文件流
     * @param targetAddr 新图片目标文件地址
     * @param fileName 传入文件名
     * @return 返回值不包含图片的基础路径  文件相对路径
     */
    //处理详情图
    public static String generateNormalImg(InputStream shopImgInputSteam,String targetAddr,String fileName){
        //获取新文件的随机名 和 拓展名
        String realFileName = getRandomFileName();
        String extension = getFileExtension(fileName);
        //创建 文件目标文件夹
        makeDirPath(targetAddr);
        //新文件地址:
        String relativeAddr = targetAddr +realFileName+extension;
        //将图片的相对路径输出到日志中
        System.out.println("水印文件路径为:"+basePath+"watermark.jpg");
        logger.debug("current realtiveAddr is"+relativeAddr);
        //创建新的图片 基础图片路径+图片目标路径+随机生成图片名
        File dest = new File(PathUtil.getImgBasePath()+relativeAddr);
        //将图片的全部路径输出到日志文件
        logger.debug("current completeAddr is"+PathUtil.getImgBasePath()+relativeAddr);
        try {
            Thumbnails.of(shopImgInputSteam).size(337,640).watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath+"watermark.jpg")),0.25f)
                    .outputQuality(0.9f).toFile(dest);

        }catch (IOException e){
            //如果生成失败了 向日志中打印错误信息
            logger.error(e.toString());
            //向控制台输出错误信息
            e.printStackTrace();
        }
        //返回值不包含图片的基础路径
        return relativeAddr;
    }



    public static void main(String[] args) throws IOException {
        //参数1:文件路径 size->图片大小 watermark水印图片的位置和图片路径和透明度     outputQuality->图片压缩一下
        try {
            Thumbnails.of(new File("E:/idea_workspace/o2o/o2o-web/src/main/resources/meinv.jpg")).size(960,540)
                    .watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath+"/watermark.jpg")),0.25f)
                    .outputQuality(0.8f).toFile("E:/idea_workspace/o2o/o2o-web/src/main/resources/meinvnew.jpg");
        } catch (IOException e) {

            e.printStackTrace();
        }
    }


    /**
     * 生成随机的文件 并且返回路径
     * 当前年的年月日小时分钟秒钟+5位随机数
     * @return
     */
    public static String getRandomFileName(){
        //获取随机的五位数
        int randNum = r.nextInt(89999)+10000;
        String nowTimeStr = sdf.format(new Date());
        return nowTimeStr+"-"+randNum;
    }

    /**
     * 获取输入流文件的拓展名
     * @param fileName 传入文件名
     * @return
     */
    public static String getFileExtension(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 创建目标路径所涉及到的目录 即home/work/xiangze/xxx.jpg,
     * 那么 home work xiangze 这三个目录都得自动创建出来
     * @param targerAddr
     */
    public static  void makeDirPath(String targerAddr){
        //现获取基本路径
        String realFileParentPath = PathUtil.getImgBasePath()+ targerAddr;
        File dirPath = new File(realFileParentPath);
        //判断路径是否存在
        if(!dirPath.exists()){
            //如果不存在就递归的把路径创建出来
            dirPath.mkdirs();
        }

    }

    /**
     * storePath是文件的路径还是图片的路径.
     * 如果stroePath是文件的路径则删除文件
     * 如果stroePath是目录路径 则删除所有文件
     * @param storePath
     */
    public static void deleteFileOrPath(String storePath){
        File fileOrPath = new File(PathUtil.getImgBasePath()+storePath);
        if(fileOrPath.exists()){
            //存在
            if(fileOrPath.isDirectory()){
                //如果是目录就把下面的所有文件都删除
                File[] files = fileOrPath.listFiles();
                for(File file:files){
                    file.delete();
                }
            }
            //不是目录就直接删除 是目录删除完目录删除文件
            fileOrPath.delete();
        }
    }
}
