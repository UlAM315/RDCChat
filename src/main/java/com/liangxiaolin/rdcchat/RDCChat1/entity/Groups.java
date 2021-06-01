package com.liangxiaolin.rdcchat.RDCChat1.entity;

public class Groups {
    private int groupId;
    private String groupName;
    private int ownerId;
    private String avatarImg;

    public Groups() {
    }

    public Groups(int groupId, String groupName, int ownerId, String avatarImg) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.ownerId = ownerId;
        this.avatarImg = avatarImg;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getAvatarImg() {
        return avatarImg;
    }

    public void setAvatarImg(String avatarImg) {
        this.avatarImg = avatarImg;
    }

    @Override
    public String toString() {
        return "Groups{" +
                "groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", ownerId=" + ownerId +
                ", avatarImg='" + avatarImg + '\'' +
                '}';
    }
}
