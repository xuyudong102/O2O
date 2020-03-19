package com.xyd.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.entity
 * @date 2020/3/18
 * @description 用户实体类
 */
public class PersonInfo implements Serializable {
    private long   userId;
    private String name;
    private String profileImg;
    private String email;
    private String gender;
    //0禁止使用本商城 1可以使用本商城
    private Integer enableStatus;
    //1.顾客 2.店家 3.超级管理员
    private Integer userType;
    private Date createTime;
    private Date lastEditTime;

    public PersonInfo() {
    }

    public PersonInfo(long userId, String name, String profileImg, String email, String gender, Integer enableStatus, Integer userType, Date createTime, Date lastEditTime) {
        this.userId = userId;
        this.name = name;
        this.profileImg = profileImg;
        this.email = email;
        this.gender = gender;
        this.enableStatus = enableStatus;
        this.userType = userType;
        this.createTime = createTime;
        this.lastEditTime = lastEditTime;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    @Override
    public String toString() {
        return "PersonInfo{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", profileImg='" + profileImg + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", enableStatus=" + enableStatus +
                ", userType=" + userType +
                ", createTime=" + createTime +
                ", lastEditTime=" + lastEditTime +
                '}';
    }
}
