package com.xyd.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.entity
 * @date 2020/3/18
 * @description 本地账号实体类
 */
public class LocalAuth implements Serializable {
    private long localAuthId;
    private String username;
    private String password;
    private Date createTime;
    private Date lastEditTime;
    private PersonInfo personInfo;

    public LocalAuth(long localAuthId, String username, String password, Date createTime, Date lastEditTime, PersonInfo personInfo) {
        this.localAuthId = localAuthId;
        this.username = username;
        this.password = password;
        this.createTime = createTime;
        this.lastEditTime = lastEditTime;
        this.personInfo = personInfo;
    }

    public LocalAuth() {
    }

    public long getLocalAuthId() {
        return localAuthId;
    }

    public void setLocalAuthId(long localAuthId) {
        this.localAuthId = localAuthId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }

    @Override
    public String toString() {
        return "LocalAuth{" +
                "localAuthId=" + localAuthId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", createTime=" + createTime +
                ", lastEditTime=" + lastEditTime +
                ", personInfo=" + personInfo +
                '}';
    }
}

