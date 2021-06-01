package com.liangxiaolin.rdcchat.RDCChat1.entity;

public class Reporting {

    private int accuserId;
    private int accusedId; //被举报人id
    private String message;

    public Reporting() {
    }

    public Reporting(int accuserId, int accusedId, String message) {
        this.accuserId = accuserId;
        this.accusedId = accusedId;
        this.message = message;
    }

    public int getAccuserId() {
        return accuserId;
    }

    public void setAccuserId(int accuserId) {
        this.accuserId = accuserId;
    }

    public int getAccusedId() {
        return accusedId;
    }

    public void setAccusedId(int accusedId) {
        this.accusedId = accusedId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Reporting{" +
                "accuserId=" + accuserId +
                ", accusedId=" + accusedId +
                ", message='" + message + '\'' +
                '}';
    }
}
