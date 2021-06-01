package com.liangxiaolin.rdcchat.RDCChat1.entity;

public class MyGroups {
    private int myId;
    private int groupId;
    private String myGroupName;
    private String myContent;

    public MyGroups() {
    }

    public MyGroups(int myId, int groupId, String myGroupName, String myContent) {
        this.myId = myId;
        this.groupId = groupId;
        this.myGroupName = myGroupName;
        this.myContent = myContent;
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

    @Override
    public String toString() {
        return "MyGroups{" +
                "myId=" + myId +
                ", groupId=" + groupId +
                ", myGroupName='" + myGroupName + '\'' +
                ", myContent='" + myContent + '\'' +
                '}';
    }
}
