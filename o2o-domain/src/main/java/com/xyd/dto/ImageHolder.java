package com.xyd.dto;

import java.io.InputStream;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.dto
 * @date 2020/3/31
 * @description
 */
public class ImageHolder {
    private InputStream image;
    private String imageName;

    public ImageHolder() {
    }

    public ImageHolder(InputStream image, String imageName) {
        this.image = image;
        this.imageName = imageName;
    }

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public String toString() {
        return "ImageHolder{" +
                "image=" + image +
                ", imageName='" + imageName + '\'' +
                '}';
    }
}
