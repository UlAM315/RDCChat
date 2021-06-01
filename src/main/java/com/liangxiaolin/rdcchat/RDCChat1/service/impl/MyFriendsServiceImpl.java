package com.liangxiaolin.rdcchat.RDCChat1.service.impl;

import com.liangxiaolin.rdcchat.RDCChat1.dao.MyFriendsDao;
import com.liangxiaolin.rdcchat.RDCChat1.dao.base.ReflectCRUD;
import com.liangxiaolin.rdcchat.RDCChat1.dao.impl.MyFriendsDaoImpl;
import com.liangxiaolin.rdcchat.RDCChat1.entity.*;
import com.liangxiaolin.rdcchat.RDCChat1.service.MyFriendsService;

import java.util.ArrayList;
import java.util.List;

public class MyFriendsServiceImpl implements MyFriendsService {

    MyFriendsDao dao = new MyFriendsDaoImpl();

    @Override
    public List<FriendAndUsers> getfriendsLists(int userId) {
        List<Friend> friendList = dao.getfriendsListsOne(userId);
        if(!friendList.isEmpty()){
            List<Users> usersList = dao.getfriendsListsTwo(friendList);
            List<FriendAndUsers> friendAndUsersList = new ArrayList<>();
            for (Friend friend : friendList) {
                for (Users users : usersList) {
                    if(friend.getFriendId() == users.getUserId()){
                        FriendAndUsers friendAndUsers = new FriendAndUsers(friend.getFriendId(),friend.getNickname(),friend.getIfBlacklist(),"",users.getUserName(),users.getAvatarImg());
                        friendAndUsersList.add(friendAndUsers);
                    }
                }
            }

            System.out.println(friendAndUsersList);
            return friendAndUsersList;
        }
        return null;
    }

    @Override
    public List<MyGroupsAndGroups> getgroupsLists(int userId) {
        List<MyGroups> myGroupsList = dao.getgroupsListsOne(userId);
        System.out.println("myGroupsList:"+myGroupsList);
        if(!myGroupsList.isEmpty()){
            List<Groups> groupsList = dao.getgroupsListsTwo(myGroupsList);
            System.out.println("groupsList:"+groupsList);
            List<MyGroupsAndGroups> myGroupsAndGroupsList = new ArrayList<>();
            for (MyGroups myGroups : myGroupsList) {
                for (Groups groups : groupsList) {
                    if(myGroups.getGroupId() == groups.getGroupId()){
                        MyGroupsAndGroups myGroupsAndGroups = new MyGroupsAndGroups(myGroups.getMyId(),myGroups.getGroupId(),myGroups.getMyGroupName(),myGroups.getMyContent(),groups.getGroupName(),groups.getAvatarImg());
                        System.out.println("myGroupsAndGroups:"+myGroupsAndGroups);
                        myGroupsAndGroupsList.add(myGroupsAndGroups);
                    }
                }
            }
            System.out.println(myGroupsAndGroupsList);
            return myGroupsAndGroupsList;
        }
        return null;
    }

    @Override
    public List<FriendAndUsers> getfriendsListsByPage(int userId, int currentPage) {
        List<Friend> friendList = dao.getfriendsListsByPage(userId,currentPage);
        System.out.println("friendList:"+friendList);
        if(!friendList.isEmpty() && friendList!=null){
            List<Users> usersList = dao.getfriendsListsTwo(friendList);
            System.out.println("usersList:"+usersList);
            List<FriendAndUsers> friendAndUsersList = new ArrayList<>();
            for (Friend friend : friendList) {
                for (Users users : usersList) {
                    if(friend.getFriendId() == users.getUserId()){
                        FriendAndUsers friendAndUsers = new FriendAndUsers(friend.getFriendId(),friend.getNickname(),friend.getIfBlacklist(),"",users.getUserName(),users.getAvatarImg());
                        friendAndUsersList.add(friendAndUsers);
                    }
                }
            }
            System.out.println(friendAndUsersList);
            return friendAndUsersList;
        }
        return null;
    }

    @Override
    public List<SearchFriends> searchFriends(String searchText, int myId) {
        Users users = new Users();
        users.setUserName(searchText);
        users.setTelephone(searchText);
        users.setIdNumber(searchText);
        List<String> values = new ArrayList<>();
        values.add("userName");
        values.add("telephone");
        values.add("idNumber");
        //返回搜到的用户
        List<Users> usersList = dao.searchFriends(users,values);
        System.out.println("usersList()service:"+usersList);
        if (usersList!=null&&!usersList.isEmpty()){
            List<SearchFriends> friends = new ArrayList<>();
            //遍历搜到的用户，判断是否为好友
            for (int i = 0; i < usersList.size(); i++) {
                Users users1 = usersList.get(i);
                //先把用户信息包装--------------------------------------
                SearchFriends friend = new SearchFriends();
                friend.setUserId(users1.getUserId());
                friend.setGender(users1.getGender());
                friend.setAvatarImg(users1.getAvatarImg());
                friend.setIdNumber(users1.getIdNumber());
                friend.setUserName(users1.getUserName());
                friend.setTelephone(users1.getTelephone());
                //-----------------------------------------------------

                //判断好友表是否有
                int friendId = users1.getUserId();
                if(dao.searchIfFriend(friendId,myId)){
                    friend.setFriend(true);
                }else {
                    friend.setFriend(false);
                }
                System.out.println("friend:"+friend);
                friends.add(friend);
            }
            if (friends!=null&&!friends.isEmpty()){
                return friends;
            }
        }
            return null;
    }

    @Override
    public boolean deleteUser(int userId,String friendId) {
        int friendId1 = Integer.parseInt(friendId);
        return dao.deleteUser(userId,friendId1);
    }

    @Override
    public boolean addUser(int userId, String friendId,String content) {
        int friendId1 = Integer.parseInt(friendId);
        Users users = new Users();
        users.setUserId(friendId1);
        String nickname = dao.acceptNewFriendsThree(users).getUserName();//ReflectCRUD.queryObject(users,"userId").getUserName();
        return dao.addUser(userId,friendId1,content,nickname);
    }

    @Override
    public List<NewFriends> acceptNewFriends(int myId) {
        Friend friend1 = new Friend();
        friend1.setFriendId(myId);
        List<Friend> friendList = dao.acceptNewFriendsOne(friend1);//ReflectCRUD.queryList(friend1,"friendId");
        if (friendList!=null&&!friendList.isEmpty()){//有请求消息
            //创建新好友集合
            List<NewFriends> newFriends = new ArrayList<>();
            //遍历取出请求用户的id
            for (Friend friend : friendList) {
                //判断好友是否双向
                int friendId = friend.getMyId();
                Friend me = new Friend();
                me.setMyId(myId);
                me.setFriendId(friendId);
                //where部分
                List<String> values = new ArrayList<>();
                values.add("myId");
                values.add("friendId");
                if (dao.acceptNewFriendsTwo(me,values) == null){//只是单向，即只是发送了好友请求ReflectCRUD.queryObjectAnd(friend2,values)==null
                    //则可以添加
                    //获取请求用户的信息
                    Users users = new Users();
                    users.setUserId(friendId);
                    Users user = dao.acceptNewFriendsThree(users);//ReflectCRUD.queryObject(users,"userId");
                    //创建显示的NewFriends
                    NewFriends newFriend = new NewFriends(friendId, user.getUserName(), user.getIdNumber(), user.getGender(), user.getAvatarImg(), friend.getContent());
                    newFriends.add(newFriend);
                }
            }
            return newFriends;
        }

        return null;
    }

    @Override
    public boolean addNewFriends(int userId, String friendId, String nickname) {
        int friendId1 = Integer.parseInt(friendId);
        Users users = new Users();
        users.setUserId(friendId1);
        String content = "我们已经是好友啦！";
        return dao.addUser(userId,friendId1,content,nickname);
    }

    @Override
    public FriendMessage searchFriendMessages(String friendId, int myId) {
        int friendId0 = Integer.parseInt(friendId);
        Friend friend = new Friend();
        friend.setMyId(myId);
        friend.setFriendId(friendId0);
        List<String> values = new ArrayList<>();
        values.add("myId");
        values.add("friendId");
        String nickname = dao.acceptNewFriendsTwo(friend,values).getNickname();
        //获取好友的信息
        Users users = new Users();
        users.setUserId(friendId0);
        Users user = dao.acceptNewFriendsThree(users);
        FriendMessage friendMessage = new FriendMessage(user.getUserId(), users.getUserName(), user.getIdNumber(), user.getGender(), users.getAvatarImg(), nickname);
        return friendMessage;
    }

    @Override
    public boolean changeNickname(int userId, String friendId, String nickname) {
        int friendId1 = Integer.parseInt(friendId);
        Friend friend = new Friend();
        friend.setNickname(nickname);
        friend.setFriendId(friendId1);
        friend.setMyId(userId);
        List<String> values = new ArrayList<>();
        values.add("nickname");
        List<String> conditions = new ArrayList<>();
        conditions.add("myId");
        conditions.add("friendId");
        return dao.changeNickname(friend,values,conditions);
    }

    @Override
    public boolean addToBlacklist(int userId, String friendId) {
        int friendId1 = Integer.parseInt(friendId);
        Friend friend = new Friend();
        friend.setIfBlacklist("Y");
        friend.setFriendId(friendId1);
        friend.setMyId(userId);
        List<String> values = new ArrayList<>();
        values.add("ifBlacklist");
        List<String> conditions = new ArrayList<>();
        conditions.add("myId");
        conditions.add("friendId");
        return dao.addToBlacklist(friend,values,conditions);
    }

    @Override
    public boolean report(int userId, String friendId, String message) {
        int accusedId = Integer.parseInt(friendId);
        Reporting reporting = new Reporting(userId,accusedId,message);
        return dao.report(reporting);
    }

    @Override
    public boolean cancelBlacklist(int userId, String friendId) {
        int friendId1 = Integer.parseInt(friendId);
        Friend friend = new Friend();
        friend.setIfBlacklist("N");
        friend.setFriendId(friendId1);
        friend.setMyId(userId);
        List<String> values = new ArrayList<>();
        values.add("ifBlacklist");
        List<String> conditions = new ArrayList<>();
        conditions.add("myId");
        conditions.add("friendId");
        return dao.cancelBlacklist(friend,values,conditions);
    }

    @Override
    public List<FriendAndUsers> getBlacklist(int userId) {
        List<Friend> friendList = dao.getBlacklist(userId);
        if(!friendList.isEmpty()){
            List<Users> usersList = dao.getfriendsListsTwo(friendList);
            List<FriendAndUsers> friendAndUsersList = new ArrayList<>();
            for (Friend friend : friendList) {
                for (Users users : usersList) {
                    if(friend.getFriendId() == users.getUserId()){
                        FriendAndUsers friendAndUsers = new FriendAndUsers(friend.getFriendId(),friend.getNickname(),friend.getIfBlacklist(),"",users.getUserName(),users.getAvatarImg());
                        friendAndUsersList.add(friendAndUsers);
                    }
                }
            }

            System.out.println(friendAndUsersList);
            return friendAndUsersList;
        }
        return null;
    }

    @Override
    public boolean createGroup(String[] friendIds,String groupName,int ownerId) {
        boolean flag = true;
        if(friendIds != null && friendIds.length > 0){
            //1.遍历数组
            for (String id : friendIds) {
                //2.调用dao拉群
                if(!dao.createGroup(Integer.parseInt(id),groupName,ownerId)){
                    flag = false;
                }
            }
            for (String id : friendIds) {
                if (!dao.addGroup(Integer.parseInt(id),groupName,ownerId)){
                    flag = false;
                }
            }
        }
        return flag;
    }

    @Override
    public boolean findIfGroupOwner(int groupId, int userId) {
        List<String> values = new ArrayList<>();
        values.add("groupId");
        values.add("ownerId");
        Groups groups = new Groups();
        groups.setOwnerId(userId);
        groups.setGroupId(groupId);
        return dao.findIfGroupOwner(groups,values);
    }

    @Override
    public boolean ownerChangeGroupName(String groupId, String groupName) {
        int groupId1 = Integer.parseInt(groupId);
        Groups groups = new Groups();
        groups.setGroupName(groupName);
        groups.setGroupId(groupId1);
        List<String> values = new ArrayList<>();
        values.add("groupName");
        return dao.ownerChangeGroupName(groups,values);
    }

    @Override
    public boolean memberChangeGroupName(String groupId, String myGroupName,int myId) {
        int groupId1 = Integer.parseInt(groupId);
        MyGroups myGroups = new MyGroups();
        myGroups.setGroupId(groupId1);
        myGroups.setMyGroupName(myGroupName);
        myGroups.setMyId(myId);
        List<String> values = new ArrayList<>();
        values.add("myGroupName");
        List<String> conditions = new ArrayList<>();
        conditions.add("groupId");
        conditions.add("myId");
        return dao.memberChangeGroupName(myGroups,values,conditions);
    }

    @Override
    public boolean leaveGroup(String groupId, int myId) {
        int groupId1 = Integer.parseInt(groupId);
        MyGroups myGroups = new MyGroups();
        myGroups.setMyId(myId);
        myGroups.setGroupId(groupId1);
        List<String> values = new ArrayList<>();
        values.add("groupId");
        values.add("myId");
        return dao.leaveGroup(myGroups,values);
    }

    @Override
    public List<FriendAndUsers> getfriendsNotInGroup(int userId,String groupId) {
        int groupId1 = Integer.parseInt(groupId);
        //one找出所有好友
        List<Friend> friendList = dao.getfriendsListsOne(userId);
        if(!friendList.isEmpty()){
            //two找出所有好友的详细信息
            List<Users> usersList = dao.getfriendsListsTwo(friendList);
            //获取好友们加入的群,找出符合加群条件的好友
            List<Users> suitFriendList = new ArrayList<>();
            for (Users users : usersList) {
                int usersId = users.getUserId();
                List<MyGroups> friendGroups = dao.getgroupsListsOne(usersId);//ReflectCRUD.queryList(myGroups,"myId");
                if (friendGroups!=null&&!friendGroups.isEmpty()){//该好友有加群
                    boolean flag = true;
                    for (MyGroups friendGroup : friendGroups) { //遍历群聊看看有没有加此群
                        if (friendGroup.getGroupId() == groupId1){
                            flag = false;
                            break;
                        }
                    }
                    //假如还没加群，则可以邀请
                    if (flag){
                        suitFriendList.add(users);
                    }
                }else {//该好友还没有加入任何群聊
                    suitFriendList.add(users);
                }

            }
            //装载了合适的好友的所有信息
            List<FriendAndUsers> friendAndUsersList = new ArrayList<>();
            for (Users suitFriend : suitFriendList){
                for (Friend friend : friendList){
                    if(friend.getFriendId() == suitFriend.getUserId()){
                        FriendAndUsers friendAndUsers = new FriendAndUsers(friend.getFriendId(),friend.getNickname(),friend.getIfBlacklist(),"",suitFriend.getUserName(),suitFriend.getAvatarImg());
                        friendAndUsersList.add(friendAndUsers);
                    }
                }
            }

            System.out.println(friendAndUsersList);
            return friendAndUsersList;
        }
        return null;
    }

    @Override
    public boolean inviteToeGroup(String[] friendIds, String groupId) {
        int groupId1 = Integer.parseInt(groupId);
        Groups groups = new Groups();
        groups.setGroupId(groupId1);
        String groupName = ReflectCRUD.queryObject(groups,"groupId").getGroupName();
        boolean flag = true;
        if(friendIds != null && friendIds.length > 0){
            for (String id : friendIds) {
                if (!dao.inviteToeGroup(Integer.parseInt(id),groupName,groupId1)){
                    flag = false;
                }
            }
        }
        return flag;
    }

    @Override
    public List<FriendAndUsers> getfriendsInGroup(int userId, String groupId) {
        int groupId1 = Integer.parseInt(groupId);
        //one找出所有好友
        List<Friend> friendList = dao.getfriendsListsOne(userId);
        if(!friendList.isEmpty()){
            //two找出所有好友的详细信息
            List<Users> usersList = dao.getfriendsListsTwo(friendList);
            //获取好友们加入的群,找出符合加群条件的好友
            List<Users> suitFriendList = new ArrayList<>();
            for (Users users : usersList) {
                int usersId = users.getUserId();
                List<MyGroups> friendGroups = dao.getgroupsListsOne(usersId);//ReflectCRUD.queryList(myGroups,"myId");
                if (friendGroups!=null&&!friendGroups.isEmpty()){//该好友有加群
                    for (MyGroups friendGroup : friendGroups) { //遍历群聊看看有没有加此群
                        if (friendGroup.getGroupId() == groupId1){
                            //加了本群
                            suitFriendList.add(users);
                        }
                    }
                }

            }
            //装载了合适的好友的所有信息
            List<FriendAndUsers> friendAndUsersList = new ArrayList<>();
            for (Users suitFriend : suitFriendList){
                for (Friend friend : friendList){
                    if(friend.getFriendId() == suitFriend.getUserId()){
                        FriendAndUsers friendAndUsers = new FriendAndUsers(friend.getFriendId(),friend.getNickname(),friend.getIfBlacklist(),"",suitFriend.getUserName(),suitFriend.getAvatarImg());
                        friendAndUsersList.add(friendAndUsers);
                    }
                }
            }

            System.out.println(friendAndUsersList);
            return friendAndUsersList;
        }
        return null;
    }

    @Override
    public boolean dismissGroup(String groupId0) {
        int groupId = Integer.parseInt(groupId0);
        Groups groups = new Groups();
        groups.setGroupId(groupId);
        return dao.dismissGroup(groups);
    }

    @Override
    public boolean kickPeople(String[] friendIds, String groupId) {
        int groupId1 = Integer.parseInt(groupId);
        List<String> values = new ArrayList<>();
        values.add("groupId");
        values.add("myId");

        boolean flag = true;
        if(friendIds != null && friendIds.length > 0){
            for (String id : friendIds) {
                MyGroups myGroups = new MyGroups();
                myGroups.setMyId(Integer.parseInt(id));
                myGroups.setGroupId(groupId1);
                if (!dao.leaveGroup(myGroups,values)){
                    flag = false;
                }
            }
        }
        return flag;
    }

}
