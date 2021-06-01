package com.liangxiaolin.rdcchat.RDCChat1.entity;

public class Comments {

    private int momentId;
    private String content;
    private int reviewerId;

    public Comments() {
    }

    public Comments(int momentId, String content, int reviewerId) {
        this.momentId = momentId;
        this.content = content;
        this.reviewerId = reviewerId;
    }

    public int getMomentId() {
        return momentId;
    }

    public void setMomentId(int momentId) {
        this.momentId = momentId;
    }

    public int getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(int reviewerId) {
        this.reviewerId = reviewerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "momentId=" + momentId +
                ", reviewerId=" + reviewerId +
                ", content='" + content + '\'' +
                '}';
    }
}
