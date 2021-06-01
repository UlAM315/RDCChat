package com.liangxiaolin.rdcchat.RDCChat1.entity;

import java.io.Serializable;

public class FriendMessage implements Serializable {
    private int userId;
    private String userName;
    private String idNumber;
    private String gender;
    private String avatarImg;
    private String nickname;

    public FriendMessage() {
    }

    public FriendMessage(int userId, String userName, String idNumber, String gender, String avatarImg, String nickname) {
        this.userId = userId;
        this.userName = userName;
        this.idNumber = idNumber;
        this.gender = gender;
        this.avatarImg = avatarImg;
        this.nickname = nickname;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "FriendMessage{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", gender='" + gender + '\'' +
                ", avatarImg='" + avatarImg + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
