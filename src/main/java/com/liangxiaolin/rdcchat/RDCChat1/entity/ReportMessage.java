package com.liangxiaolin.rdcchat.RDCChat1.entity;

import java.io.Serializable;

public class ReportMessage implements Serializable {
    private  int accusedId;
    private String accuserName;
    private String accusedName;
    private String message;

    public ReportMessage() {
    }

    public ReportMessage(int accusedId, String accuserName, String accusedName, String message) {
        this.accusedId = accusedId;
        this.accuserName = accuserName;
        this.accusedName = accusedName;
        this.message = message;
    }

    public String getAccuserName() {
        return accuserName;
    }

    public void setAccuserName(String accuserName) {
        this.accuserName = accuserName;
    }

    public String getAccusedName() {
        return accusedName;
    }

    public void setAccusedName(String accusedName) {
        this.accusedName = accusedName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getAccusedId() {
        return accusedId;
    }

    public void setAccusedId(int accusedId) {
        this.accusedId = accusedId;
    }

    @Override
    public String toString() {
        return "ReportMessage{" +
                "accusedId=" + accusedId +
                ", accuserName='" + accuserName + '\'' +
                ", accusedName='" + accusedName + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
