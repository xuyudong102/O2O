package com.xyd.utils;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;


import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.utils
 * @date 2020/3/21
 * @description
 */

public class ImageUtil {
    public static void main(String[] args) throws IOException {
        //通过线程逆推绝对值路径 classpath
        String basePath= Thread.currentThread().getContextClassLoader().getResource("").getPath();
        System.out.println(basePath);
        //参数1:文件路径 size->图片大小 watermark水印图片的位置和图片路径和透明度     outputQuality->图片压缩一下
        try {
            Thumbnails.of(new File("E:/idea_workspace/o2o/o2o-web/src/main/resources/meinv.jpg")).size(960,540)
                    .watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath+"/watermark.jpg")),0.25f)
                    .outputQuality(0.8f).toFile("E:/idea_workspace/o2o/o2o-web/src/main/resources/meinvnew.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
