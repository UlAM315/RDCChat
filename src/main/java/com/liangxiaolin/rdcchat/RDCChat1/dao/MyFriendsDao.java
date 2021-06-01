package com.liangxiaolin.rdcchat.RDCChat1.dao;

import com.liangxiaolin.rdcchat.RDCChat1.entity.*;

import java.util.List;

public interface MyFriendsDao {
    List<Friend> getfriendsListsOne(int userId);

    List<Users> getfriendsListsTwo(List<Friend> friendList);

    List<MyGroups> getgroupsListsOne(int userId);

    List<Groups> getgroupsListsTwo(List<MyGroups> myGroupsList);

    List<Friend> getfriendsListsByPage(int userId, int currentPage);


    List<Users> searchFriends(Users users, List<String> values);

    boolean searchIfFriend(int friendId, int myId);

    boolean deleteUser(int userId, int friendId1);

    boolean addUser(int userId, int friendId1,String content,String nickname);

    List<Friend> acceptNewFriendsOne(Friend friend1);

    Friend acceptNewFriendsTwo(Friend friend2, List<String> values);

    Users acceptNewFriendsThree(Users users);

    boolean changeNickname(Friend friend, List<String> values, List<String> conditions);

    boolean addToBlacklist(Friend friend, List<String> values, List<String> conditions);

    boolean report(Reporting reporting);

    boolean cancelBlacklist(Friend friend, List<String> values, List<String> conditions);

    List<Friend> getBlacklist(int userId);

    boolean createGroup(int parseInt, String groupName,int ownerId);

    boolean addGroup(int parseInt, String groupName, int ownerId);

    boolean findIfGroupOwner(Groups groups, List<String> values);

    boolean ownerChangeGroupName(Groups groups, List<String> values);

    boolean memberChangeGroupName(MyGroups myGroups, List<String> values, List<String> conditions);

    boolean leaveGroup(MyGroups myGroups, List<String> values);

    boolean inviteToeGroup(int parseInt, String groupName, int groupId1);

    boolean dismissGroup(Groups groups);
}
