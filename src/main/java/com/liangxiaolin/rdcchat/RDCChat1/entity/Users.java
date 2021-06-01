package com.liangxiaolin.rdcchat.RDCChat1.entity;

import java.io.Serializable;

public class Users implements Serializable {

    private int userId;
    private String userName;
    private String userPassword;
    private String idNumber;
    private String gender;
    private String telephone;
    private String email;
    private String avatarImg;
    private String backgroundImg;
    private String ifBlock;
    private String activeStatus;
    private String activeCode;

    public Users() {
    }

    public Users(int userId, String userName, String userPassword, String idNumber, String gender, String telephone, String email, String avatarImg, String backgroundImg, String ifBlock, String activeStatus, String activeCode) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.idNumber = idNumber;
        this.gender = gender;
        this.telephone = telephone;
        this.email = email;
        this.avatarImg = avatarImg;
        this.backgroundImg = backgroundImg;
        this.ifBlock = ifBlock;
        this.activeStatus = activeStatus;
        this.activeCode = activeCode;
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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarImg() {
        return avatarImg;
    }

    public void setAvatarImg(String avatarImg) {
        this.avatarImg = avatarImg;
    }

    public String getBackgroundImg() {
        return backgroundImg;
    }

    public void setBackgroundImg(String backgroundImg) {
        this.backgroundImg = backgroundImg;
    }

    public String getIfBlock() {
        return ifBlock;
    }

    public void setIfBlock(String ifBlock) {
        this.ifBlock = ifBlock;
    }

    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }

    @Override
    public String toString() {
        return "Users{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", gender='" + gender + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", avatarImg='" + avatarImg + '\'' +
                ", backgroundImg='" + backgroundImg + '\'' +
                ", ifBlock='" + ifBlock + '\'' +
                ", activeStatus='" + activeStatus + '\'' +
                ", activeCode='" + activeCode + '\'' +
                '}';
    }
}
