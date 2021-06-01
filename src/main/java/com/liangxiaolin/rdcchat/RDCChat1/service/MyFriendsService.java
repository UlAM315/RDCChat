package com.liangxiaolin.rdcchat.RDCChat1.service;

import com.liangxiaolin.rdcchat.RDCChat1.entity.*;

import java.util.List;

public interface MyFriendsService {
    List<FriendAndUsers> getfriendsLists(int userId);

    List<MyGroupsAndGroups> getgroupsLists(int userId);

    List<FriendAndUsers> getfriendsListsByPage(int userId, int currentPage);

    List<SearchFriends> searchFriends(String searchText, int myId);

    boolean deleteUser(int userId,String friendId);

    boolean addUser(int userId, String friendId,String content);

    List<NewFriends> acceptNewFriends(int myId);

    boolean addNewFriends(int userId, String friendId, String nickname);

    FriendMessage searchFriendMessages(String friendId, int myId);

    boolean changeNickname(int userId, String friendId, String nickname);

    boolean addToBlacklist(int userId, String friendId);

    boolean report(int userId, String friendId, String message);

    boolean cancelBlacklist(int userId, String friendId);

    List<FriendAndUsers> getBlacklist(int userId);


    boolean createGroup(String[] friendIds, String groupName,int ownerId);

    boolean findIfGroupOwner(int groupId, int userId);

    boolean ownerChangeGroupName(String groupId, String groupName);

    boolean memberChangeGroupName(String groupId, String myGroupName,int myId);

    boolean leaveGroup(String groupId, int myId);

    List<FriendAndUsers> getfriendsNotInGroup(int userId,String groupId);

    boolean inviteToeGroup(String[] friendIds, String groupId);

    List<FriendAndUsers> getfriendsInGroup(int userId, String groupId);

    boolean dismissGroup(String groupId0);

    boolean kickPeople(String[] friendIds, String groupId);
}
