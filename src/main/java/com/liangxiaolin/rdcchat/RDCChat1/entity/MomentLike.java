package com.liangxiaolin.rdcchat.RDCChat1.entity;

public class MomentLike {

    private int momentId;
    private int userId;

    public MomentLike() {
    }

    public MomentLike(int momentId, int userId) {
        this.momentId = momentId;
        this.userId = userId;
    }

    public int getMomentId() {
        return momentId;
    }

    public void setMomentId(int momentId) {
        this.momentId = momentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "MomentLike{" +
                "momentId=" + momentId +
                ", userId=" + userId +
                '}';
    }
}
