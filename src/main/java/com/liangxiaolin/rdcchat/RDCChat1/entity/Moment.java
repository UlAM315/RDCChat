package com.liangxiaolin.rdcchat.RDCChat1.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Moment {
    private int momentId;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date issueTime;
    private String firstImg;
    private String secondImg;
    private String thirdImg;
    private int likeNumber;
    private String ifOpen;
    private String content;
    private int userId;

    public Moment() {
    }

    public Moment(int momentId, Date issueTime, String firstImg, String secondImg, String thirdImg, int likeNumber, String ifOpen, String content, int userId) {
        this.momentId = momentId;
        this.issueTime = issueTime;
        this.firstImg = firstImg;
        this.secondImg = secondImg;
        this.thirdImg = thirdImg;
        this.likeNumber = likeNumber;
        this.ifOpen = ifOpen;
        this.content = content;
        this.userId = userId;
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

    public String getIfOpen() {
        return ifOpen;
    }

    public void setIfOpen(String ifOpen) {
        this.ifOpen = ifOpen;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Moment{" +
                "momentId=" + momentId +
                ", issueTime=" + issueTime +
                ", firstImg='" + firstImg + '\'' +
                ", secondImg='" + secondImg + '\'' +
                ", thirdImg='" + thirdImg + '\'' +
                ", likeNumber=" + likeNumber +
                ", ifOpen='" + ifOpen + '\'' +
                ", content='" + content + '\'' +
                ", userId=" + userId +
                '}';
    }
}
