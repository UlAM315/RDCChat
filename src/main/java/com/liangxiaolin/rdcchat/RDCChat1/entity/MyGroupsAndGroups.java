package com.liangxiaolin.rdcchat.RDCChat1.entity;

public class MyGroupsAndGroups {
    private int myId;
    private int groupId;
    private String myGroupName;
    private String myContent;
    private String groupName;
    private String avatarImg;

    public MyGroupsAndGroups() {
    }

    public MyGroupsAndGroups(int myId, int groupId, String myGroupName, String myContent, String groupName, String avatarImg) {
        this.myId = myId;
        this.groupId = groupId;
        this.myGroupName = myGroupName;
        this.myContent = myContent;
        this.groupName = groupName;
        this.avatarImg = avatarImg;
    }

    public int getMyId() {
        return myId;
    }

    public void setMyId(int myId) {
        this.myId = myId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getMyGroupName() {
        return myGroupName;
    }

    public void setMyGroupName(String myGroupName) {
        this.myGroupName = myGroupName;
    }

    public String getMyContent() {
        return myContent;
    }

    public void setMyContent(String myContent) {
        this.myContent = myContent;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getAvatarImg() {
        return avatarImg;
    }

    public void setAvatarImg(String avatarImg) {
        this.avatarImg = avatarImg;
    }

    @Override
    public String toString() {
        return "MyGroupsAndGroups{" +
                "myId=" + myId +
                ", groupId=" + groupId +
                ", myGroupName='" + myGroupName + '\'' +
                ", myContent='" + myContent + '\'' +
                ", groupName='" + groupName + '\'' +
                ", avatarImg='" + avatarImg + '\'' +
                '}';
    }
}
