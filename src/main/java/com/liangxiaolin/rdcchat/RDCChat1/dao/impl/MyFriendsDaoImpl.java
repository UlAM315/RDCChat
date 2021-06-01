package com.liangxiaolin.rdcchat.RDCChat1.dao.impl;

import com.liangxiaolin.rdcchat.RDCChat1.dao.MyFriendsDao;
import com.liangxiaolin.rdcchat.RDCChat1.dao.base.ReflectCRUD;
import com.liangxiaolin.rdcchat.RDCChat1.dao.base.ReflectDimQuery;
import com.liangxiaolin.rdcchat.RDCChat1.dao.base.ReflectQueryByPage;
import com.liangxiaolin.rdcchat.RDCChat1.entity.*;

import java.util.ArrayList;
import java.util.List;

public class MyFriendsDaoImpl implements MyFriendsDao {

    @Override
    public List<Friend> getfriendsListsOne(int userId) {
        Friend friend = new Friend();
        friend.setMyId(userId);
        friend.setIfBlacklist("N");
        List<String> values = new ArrayList<>();
        values.add("myId");
        values.add("ifBlacklist");
        return ReflectCRUD.queryListAnd(friend,values);
    }

    @Override
    public List<Users> getfriendsListsTwo(List<Friend> friendList) {
        List<Users> usersList = new ArrayList<>();
        for (Friend friend : friendList) {
            int friendId = friend.getFriendId();
            Users users0 = new Users();
            users0.setUserId(friendId);
            Users users = ReflectCRUD.queryObject(users0,"userId");
            usersList.add(users);
        }
        return usersList;
    }

    @Override
    public List<MyGroups> getgroupsListsOne(int userId) {
        MyGroups myGroups = new MyGroups();
        myGroups.setMyId(userId);
        return ReflectCRUD.queryList(myGroups,"myId");
    }

    @Override
    public List<Groups> getgroupsListsTwo(List<MyGroups> myGroupsList) {
        List<Groups> groupsList = new ArrayList<>();
        for (MyGroups myGroups : myGroupsList) {
            int groupId = myGroups.getGroupId();
            Groups groups0 = new Groups();
            groups0.setGroupId(groupId);
            Groups groups = ReflectCRUD.queryObject(groups0,"groupId");
            groupsList.add(groups);
        }
        return groupsList;
    }

    @Override
    public List<Friend> getfriendsListsByPage(int userId, int currentPage) {
        Friend friend = new Friend();
        friend.setMyId(userId);
        return ReflectQueryByPage.QueryConditionListByPage(friend,"myId",currentPage);
    }

    @Override
    public List<Users> searchFriends(Users users, List<String> values) {
        return ReflectDimQuery.dimQueryList(users,values);
    }

    @Override
    public boolean searchIfFriend(int friendId, int myId) {
        Friend friend = new Friend();
        friend.setMyId(myId);
        friend.setFriendId(friendId);
        List<String> values = new ArrayList<>();
        values.add("friendId");
        values.add("myId");
        if (ReflectCRUD.queryObjectAnd(friend,values) != null){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteUser(int userId, int friendId1) {
        List<String> values = new ArrayList<>();
        values.add("myId");
        values.add("friendId");
        //单向删除
        Friend singled = new Friend();
        singled.setMyId(userId);
        singled.setFriendId(friendId1);
        boolean singleDel = ReflectCRUD.delete(singled,values);
        //双向删除
        Friend doubled = new Friend();
        doubled.setMyId(friendId1);
        doubled.setFriendId(userId);
        boolean doubleDel = ReflectCRUD.delete(doubled,values);
        return (singleDel && doubleDel);
    }

    @Override
    public boolean addUser(int userId, int friendId1,String content,String nickname) {
        Friend friend = new Friend();
        friend.setMyId(userId);
        friend.setFriendId(friendId1);
        friend.setNickname(nickname);
        friend.setContent(content);
        friend.setIfBlacklist("N");
        return ReflectCRUD.add(friend);
    }

    @Override
    public List<Friend> acceptNewFriendsOne(Friend friend1) {
        return ReflectCRUD.queryList(friend1,"friendId");
    }

    /**
     * 判断好友是否双向
     * @param friend2
     * @param values
     * @return
     */
    @Override
    public Friend acceptNewFriendsTwo(Friend friend2, List<String> values) {
        return ReflectCRUD.queryObjectAnd(friend2,values);
    }

    /**
     * 获取请求好友的信息
     * @param users
     * @return
     */
    @Override
    public Users acceptNewFriendsThree(Users users) {
        return ReflectCRUD.queryObject(users,"userId");
    }

    @Override
    public boolean changeNickname(Friend friend, List<String> values, List<String> conditions) {
        return ReflectCRUD.change(friend,values,conditions,friend);
    }

    @Override
    public boolean addToBlacklist(Friend friend, List<String> values, List<String> conditions) {
        return ReflectCRUD.change(friend,values,conditions,friend);
    }

    @Override
    public boolean report(Reporting reporting) {
        return ReflectCRUD.add(reporting);
    }

    @Override
    public boolean cancelBlacklist(Friend friend, List<String> values, List<String> conditions) {
        return ReflectCRUD.change(friend,values,conditions,friend);
    }

    @Override
    public List<Friend> getBlacklist(int userId) {
        Friend friend = new Friend();
        friend.setMyId(userId);
        friend.setIfBlacklist("Y");
        List<String> values = new ArrayList<>();
        values.add("myId");
        values.add("ifBlacklist");
        return ReflectCRUD.queryListAnd(friend,values);
    }

    @Override
    public boolean createGroup(int parseInt, String groupName,int ownerId) {
        Groups groups = new Groups(1,groupName,ownerId,"img/rdc.jpg");
        return ReflectCRUD.add(groups);
    }

    @Override
    public boolean addGroup(int parseInt, String groupName, int ownerId) {
        Groups groups = new Groups();
        groups.setGroupName(groupName);
        groups.setOwnerId(ownerId);
        List<String> values = new ArrayList<>();
        values.add("groupName");
        values.add("ownerId");
        int groupId = ReflectCRUD.queryObjectAnd(groups,values).getGroupId();
        MyGroups myGroups = new MyGroups(parseInt,groupId,groupName,"首次加入群聊");
        MyGroups myGroup = new MyGroups(ownerId,groupId,groupName,"群主加入群聊");
        return (ReflectCRUD.add(myGroups)&&ReflectCRUD.add(myGroup));
    }

    @Override
    public boolean findIfGroupOwner(Groups groups, List<String> values) {
        if (ReflectCRUD.queryObjectAnd(groups,values)!=null){
            return true;
        }
        return false;
    }

    @Override
    public boolean ownerChangeGroupName(Groups groups, List<String> values) {
        return ReflectCRUD.change(groups,values,"groupId",groups);
    }

    @Override
    public boolean memberChangeGroupName(MyGroups myGroups, List<String> values, List<String> conditions) {
        return ReflectCRUD.change(myGroups,values,conditions,myGroups);
    }

    @Override
    public boolean leaveGroup(MyGroups myGroups, List<String> values) {
        return ReflectCRUD.delete(myGroups,values);
    }

    @Override
    public boolean inviteToeGroup(int parseInt, String groupName, int groupId1) {
        MyGroups myGroups = new MyGroups(parseInt,groupId1,groupName,"首次加入群聊");
        return ReflectCRUD.add(myGroups);
    }

    @Override
    public boolean dismissGroup(Groups groups) {
        return ReflectCRUD.delete(groups,"groupId");
    }


}
