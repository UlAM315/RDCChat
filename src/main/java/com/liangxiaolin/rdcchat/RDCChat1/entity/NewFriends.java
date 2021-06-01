package com.liangxiaolin.rdcchat.RDCChat1.entity;

import java.io.Serializable;

public class NewFriends implements Serializable {
    private int userId; //朋友的id
    private String userName;
    private String idNumber;
    private String gender;
    private String avatarImg;
    private String content;

    public NewFriends() {
    }

    public NewFriends(int userId, String userName, String idNumber, String gender, String avatarImg, String content) {
        this.userId = userId;
        this.userName = userName;
        this.idNumber = idNumber;
        this.gender = gender;
        this.avatarImg = avatarImg;
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatarImg() {
        return avatarImg;
    }

    public void setAvatarImg(String avatarImg) {
        this.avatarImg = avatarImg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "NewFriends{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", gender='" + gender + '\'' +
                ", avatarImg='" + avatarImg + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
