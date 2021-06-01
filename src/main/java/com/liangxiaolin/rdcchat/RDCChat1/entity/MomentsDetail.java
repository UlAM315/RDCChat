package com.liangxiaolin.rdcchat.RDCChat1.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MomentsDetail implements Serializable {
    private int MyId; // 我的id
    private int momentId;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date issueTime;

    private String firstImg;
    private String secondImg;
    private String thirdImg;
    private int likeNumber;
    private  String content;
    private List<String> comment; // 该文章的评论
    private int userId; // 发文章的作者
    private String userName;
    private String avatarImg;
    private boolean ifLike;

    public MomentsDetail() {
    }

    public MomentsDetail(int myId, int momentId, Date issueTime, String firstImg, String secondImg, String thirdImg, int likeNumber, String content, List<String> comment, int userId, String userName, String avatarImg, boolean ifLike) {
        MyId = myId;
        this.momentId = momentId;
        this.issueTime = issueTime;
        this.firstImg = firstImg;
        this.secondImg = secondImg;
        this.thirdImg = thirdImg;
        this.likeNumber = likeNumber;
        this.content = content;
        this.comment = comment;
        this.userId = userId;
        this.userName = userName;
        this.avatarImg = avatarImg;
        this.ifLike = ifLike;
    }

    public int getMomentId() {
        return momentId;
    }

    public void setMomentId(int momentId) {
        this.momentId = momentId;
    }

    public Date getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(Date issueTime) {
        this.issueTime = issueTime;
    }

    public String getFirstImg() {
        return firstImg;
    }

    public void setFirstImg(String firstImg) {
        this.firstImg = firstImg;
    }

    public String getSecondImg() {
        return secondImg;
    }

    public void setSecondImg(String secondImg) {
        this.secondImg = secondImg;
    }

    public String getThirdImg() {
        return thirdImg;
    }

    public void setThirdImg(String thirdImg) {
        this.thirdImg = thirdImg;
    }

    public int getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(int likeNumber) {
        this.likeNumber = likeNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getComment() {
        return comment;
    }

    public void setComment(List<String> comment) {
        this.comment = comment;
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

    public String getAvatarImg() {
        return avatarImg;
    }

    public void setAvatarImg(String avatarImg) {
        this.avatarImg = avatarImg;
    }

    public int getMyId() {
        return MyId;
    }

    public void setMyId(int myId) {
        MyId = myId;
    }

    public boolean getIfLike() {
        return ifLike;
    }

    public void setIfLike(boolean ifLike) {
        this.ifLike = ifLike;
    }

    @Override
    public String toString() {
        return "MomentsDetail{" +
                "MyId=" + MyId +
                ", momentId=" + momentId +
                ", issueTime=" + issueTime +
                ", firstImg='" + firstImg + '\'' +
                ", secondImg='" + secondImg + '\'' +
                ", thirdImg='" + thirdImg + '\'' +
                ", likeNumber=" + likeNumber +
                ", content='" + content + '\'' +
                ", comment=" + comment +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", avatarImg='" + avatarImg + '\'' +
                ", ifLike=" + ifLike +
                '}';
    }
}
