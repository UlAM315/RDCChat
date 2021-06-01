package com.liangxiaolin.rdcchat.RDCChat1.entity;

public class FriendAndUsers {
    private int friendId;
    private String nickname; //爱的昵称
    private String ifBlacklist;
    private String content; //聊天内容
    private String userName;
    private String avatarImg;

    public FriendAndUsers() {
    }

    public FriendAndUsers(int friendId, String nickname, String ifBlacklist, String content, String userName, String avatarImg) {
        this.friendId = friendId;
        this.nickname = nickname;
        this.ifBlacklist = ifBlacklist;
        this.content = content;
        this.userName = userName;
        this.avatarImg = avatarImg;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatarImg() {
        return avatarImg;
    }

    public void setAvatarImg(String avatarImg) {
        this.avatarImg = avatarImg;
    }

    @Override
    public String toString() {
        return "FriendAndUsers{" +
                "friendId=" + friendId +
                ", nickname='" + nickname + '\'' +
                ", ifBlacklist='" + ifBlacklist + '\'' +
                ", content='" + content + '\'' +
                ", userName='" + userName + '\'' +
                ", avatarImg='" + avatarImg + '\'' +
                '}';
    }
}
