package com.liangxiaolin.rdcchat.RDCChat1.entity;

public class Friend {

    private int myId;
    private int friendId;
    private String nickname;
    private String ifBlacklist;
    private String content;

    public Friend() {
    }

    public Friend(int myId, int friendId, String nickname, String ifBlacklist, String content) {
        this.myId = myId;
        this.friendId = friendId;
        this.nickname = nickname;
        this.ifBlacklist = ifBlacklist;
        this.content = content;
    }

    public int getMyId() {
        return myId;
    }

    public void setMyId(int myId) {
        this.myId = myId;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIfBlacklist() {
        return ifBlacklist;
    }

    public void setIfBlacklist(String ifBlacklist) {
        this.ifBlacklist = ifBlacklist;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "myId=" + myId +
                ", friendId=" + friendId +
                ", nickname='" + nickname + '\'' +
                ", ifBlacklist='" + ifBlacklist + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
