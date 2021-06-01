package com.liangxiaolin.rdcchat.RDCChat1.entity;

public class GroupNoWord {

    private int groupId;
    private int userId;

    public GroupNoWord() {
    }

    public GroupNoWord(int groupId, int userId) {
        this.groupId = groupId;
        this.userId = userId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "GroupNoWord{" +
                "groupId=" + groupId +
                ", userId=" + userId +
                '}';
    }
}
