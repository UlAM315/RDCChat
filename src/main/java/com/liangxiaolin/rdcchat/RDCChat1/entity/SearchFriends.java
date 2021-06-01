package com.liangxiaolin.rdcchat.RDCChat1.entity;

import java.io.Serializable;

public class SearchFriends implements Serializable {
    private int userId;
    private String userName;
    private String idNumber;
    private String gender;
    private String telephone;
    private String avatarImg;
    private String ifBlock;
    private boolean isFriend;

    public SearchFriends() {
    }

    public SearchFriends(int userId, String userName, String idNumber, String gender, String telephone, String avatarImg, String ifBlock, boolean isFriend) {
        this.userId = userId;
        this.userName = userName;
        this.idNumber = idNumber;
        this.gender = gender;
        this.telephone = telephone;
        this.avatarImg = avatarImg;
        this.ifBlock = ifBlock;
        this.isFriend = isFriend;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAvatarImg() {
        return avatarImg;
    }

    public void setAvatarImg(String avatarImg) {
        this.avatarImg = avatarImg;
    }

    public String getIfBlock() {
        return ifBlock;
    }

    public void setIfBlock(String ifBlock) {
        this.ifBlock = ifBlock;
    }

    public boolean getIsFriend() {
        return isFriend;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
    }

    @Override
    public String toString() {
        return "SearchFriends{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", gender='" + gender + '\'' +
                ", telephone='" + telephone + '\'' +
                ", avatarImg='" + avatarImg + '\'' +
                ", ifBlock='" + ifBlock + '\'' +
                ", isFriend=" + isFriend +
                '}';
    }
}
