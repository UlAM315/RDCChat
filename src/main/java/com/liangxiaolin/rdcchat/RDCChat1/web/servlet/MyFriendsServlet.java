package com.liangxiaolin.rdcchat.RDCChat1.web.servlet;

import com.liangxiaolin.rdcchat.RDCChat1.entity.*;
import com.liangxiaolin.rdcchat.RDCChat1.service.MyFriendsService;
import com.liangxiaolin.rdcchat.RDCChat1.service.impl.MyFriendsServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@WebServlet("/MyFriends/*")
public class MyFriendsServlet extends BaseServlet{

    MyFriendsService service = new MyFriendsServiceImpl();

    public void getfriendsLists(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Users user = (Users) request.getSession().getAttribute("user");
        ResultInfo info = new ResultInfo();
        if (user!=null){
            int userId = user.getUserId();
            List<FriendAndUsers> friendAndUsers = service.getfriendsLists(userId);
            if (friendAndUsers != null && !friendAndUsers.isEmpty()) {
                //有好友，返回friendAndUsers集合
                info.setFlag(true);
                info.setData(friendAndUsers);
            }else {
                //没有好友
                info.setFlag(false);
            }
        } else {
            //没有好友
            info.setFlag(false);
        }

        System.out.println("friendflag:"+info.isFlag());
        writeValue(info,response);
    }

    public void getgroupsLists(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Users user = (Users) request.getSession().getAttribute("user");
        ResultInfo info = new ResultInfo();
        if (user!=null){
            int userId = user.getUserId();
            List<MyGroupsAndGroups> myGroupsAndGroups = service.getgroupsLists(userId);
            if (myGroupsAndGroups != null && !myGroupsAndGroups.isEmpty()) {
                //有好友，返回friendAndUsers集合
                info.setFlag(true);
                info.setData(myGroupsAndGroups);
            }else {
                //没有群聊
                info.setFlag(false);
            }
        } else {
            //没有群聊
            info.setFlag(false);
        }
        writeValue(info,response);
    }

    public void addUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取friendId
        String friendId = request.getParameter("friendId");
        //获取userId
        Users user = (Users) request.getSession().getAttribute("user");
        int userId = user.getUserId();
        //获取申请内容
        String content = request.getParameter("content");
        ResultInfo info = new ResultInfo();
        if (service.addUser(userId,friendId,content)){
            info.setFlag(true);
        }else {
            info.setFlag(false);
        }
        writeValue(info,response);
    }

    public void searchFriends(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        //1.调用UserService完成查询searchText
        String searchText = request.getParameter("searchText");
        System.out.println("searchText:"+searchText);
        Users user = (Users) request.getSession().getAttribute("user");

        if (user!=null){
            int myId = user.getUserId();
            List<SearchFriends> friends = service.searchFriends(searchText,myId);
            System.out.println("List<SearchFriends>(servlet):"+friends);
            /*if (friends!=null&&!friends.isEmpty()){*/
                //2.将list存入request域
                request.setAttribute("friends",friends);
            /*}*/
            //3.转发到list.jsp
            request.getRequestDispatcher("/Search.jsp").forward(request,response);
        }

    }

    public void delUserServlet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.获取friendId
        String friendId = request.getParameter("friendId");
        //获取本人id
        Users user = (Users) request.getSession().getAttribute("user");
        int userId = user.getUserId();
        //2.调用service删除
        if (service.deleteUser(userId,friendId)){
            //3.跳转到主页面
            response.sendRedirect(request.getContextPath()+"/Main.jsp");
        }else {
            System.out.println("删除失败！");
        }

    }

    public void acceptNewFriends(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Users user = (Users) request.getSession().getAttribute("user");

        if (user!=null){
            int myId = user.getUserId();
            List<NewFriends> newFriends = service.acceptNewFriends(myId);
            System.out.println("List<NewFriends>(servlet):"+newFriends);
            if (newFriends!=null&&!newFriends.isEmpty()){
            //2.将list存入request域
            request.setAttribute("newFriends",newFriends);
            }
            //3.转发到NewFriends.jsp
            request.getRequestDispatcher("/NewFriends.jsp").forward(request,response);
        }
    }

    public void addNewFriends(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取friendId
        String friendId = request.getParameter("friendId");
        //获取userId
        Users user = (Users) request.getSession().getAttribute("user");
        int userId = user.getUserId();
        //获取昵称
        String nickname = request.getParameter("nickname");
        ResultInfo info = new ResultInfo();
        if (service.addNewFriends(userId,friendId,nickname)){
            info.setFlag(true);
        }else {
            info.setFlag(false);
        }
        System.out.println();
        writeValue(info,response);
    }

    public void friendMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取friendId
        String friendId = request.getParameter("friendId");
        //获取本人id
        Users user = (Users) request.getSession().getAttribute("user");
        //2.调用service查询
        if (user!=null){
            int myId = user.getUserId();
            FriendMessage friendMessage = service.searchFriendMessages(friendId,myId);
            System.out.println("friendMessage"+friendMessage);
            //2.将friendMessage存入request域
            request.setAttribute("friendMessages",friendMessage);
            //3.转发到查看好友信息页面
            request.getRequestDispatcher("/FriendMessage.jsp").forward(request,response);

        }else {
            System.out.println("查看失败！");
        }
    }

    public void changeNickname(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取friendId
        String friendId = request.getParameter("friendId");
        //获取userId
        Users user = (Users) request.getSession().getAttribute("user");
        int userId = user.getUserId();
        //获取昵称
        String nickname = request.getParameter("nickname");
        ResultInfo info = new ResultInfo();
        if (service.changeNickname(userId,friendId,nickname)){
            info.setFlag(true);
        }else {
            info.setFlag(false);
        }
        writeValue(info,response);
    }

    public void addToBlacklist(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取friendId
        String friendId = request.getParameter("friendId");
        //获取userId
        Users user = (Users) request.getSession().getAttribute("user");
        int userId = user.getUserId();
        ResultInfo info = new ResultInfo();
        if (service.addToBlacklist(userId,friendId)){
            info.setFlag(true);
        }else {
            info.setFlag(false);
        }
        writeValue(info,response);
    }

    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取friendId
        String friendId = request.getParameter("friendId");
        //获取userId
        Users user = (Users) request.getSession().getAttribute("user");
        int userId = user.getUserId();
        //获取举报内容
        String message = request.getParameter("message");
        ResultInfo info = new ResultInfo();
        if (service.report(userId,friendId,message)){
            info.setFlag(true);
        }else {
            info.setFlag(false);
        }
        writeValue(info,response);
    }

    public void cancelBlacklist(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取friendId
        String friendId = request.getParameter("friendId");
        //获取userId
        Users user = (Users) request.getSession().getAttribute("user");
        int userId = user.getUserId();
        ResultInfo info = new ResultInfo();
        if (service.cancelBlacklist(userId,friendId)){
            info.setFlag(true);
        }else {
            info.setFlag(false);
        }
        writeValue(info,response);
    }

    public void getBlacklist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Users user = (Users) request.getSession().getAttribute("user");
        ResultInfo info = new ResultInfo();
        if (user!=null){
            int userId = user.getUserId();
            List<FriendAndUsers> friendAndUsers = service.getBlacklist(userId);
            if (friendAndUsers != null && !friendAndUsers.isEmpty()) {
                //有黑名单，返回friendAndUsers集合
                //2.将friendMessage存入request域
                request.setAttribute("blacklist",friendAndUsers);
                //3.转发到查看好友信息页面
                request.getRequestDispatcher("/Blacklist.jsp").forward(request,response);
            }
        }
    }

    public void actionCreateGroup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Users user = (Users) request.getSession().getAttribute("user");
        ResultInfo info = new ResultInfo();
        if (user!=null){
            int userId = user.getUserId();
            List<FriendAndUsers> friendAndUsers = service.getfriendsLists(userId);
            if (friendAndUsers != null && !friendAndUsers.isEmpty()) {
                //2.将friendMessage存入request域
                request.setAttribute("friends",friendAndUsers);
                //3.转发到查看好友信息页面
                request.getRequestDispatcher("/CreateGroup.jsp").forward(request,response);
            }

        }
    }

    public void createGroup(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        //1.获取所有id
        String[] friendIds = request.getParameterValues("friendId");
        //获取群名
        String groupName = request.getParameter("groupName");
        Users user = (Users) request.getSession().getAttribute("user");
        if (user!=null){
            int ownerId = user.getUserId();
            //2.调用service拉群
            if (service.createGroup(friendIds,groupName,ownerId)){
                //提示信息
                request.setAttribute("createGroup_msg", "拉群成功！恭喜成为群主！");//3.转发到本页面
                request.getRequestDispatcher("/CreateGroup.jsp").forward(request, response);
            } else {
                //提示信息
                request.setAttribute("createGroup_msg", "拉群失败！");//3.转发到本页面
                request.getRequestDispatcher("/CreateGroup.jsp").forward(request, response);
            }
        }
    }

    public void singleChat(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取friendId
        String receiver = request.getParameter("receiver");
        System.out.println("receiver"+receiver);
        //2.将list存入request域
        request.setAttribute("receiver",receiver);
        //3.转发到SingleChat.jsp
        request.getRequestDispatcher("/SingleChat.jsp").forward(request,response);
    }

    public void groupChat(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取groupId/groupName
        String groupId = request.getParameter("groupId");
        String groupName = request.getParameter("groupName");
        //2.将list存入request域
        request.setAttribute("groupId",groupId);
        request.setAttribute("groupName",groupName);
        //3.转发到SingleChat.jsp
        request.getRequestDispatcher("/GroupChat.jsp").forward(request,response);
    }

    public void groupMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Users user = (Users) request.getSession().getAttribute("user");
        ResultInfo info = new ResultInfo();
        if (user!=null){
            //获取groupId
            String groupId0 = request.getParameter("groupId");
            int groupId = Integer.parseInt(groupId0);
            int userId = user.getUserId();
            List<MyGroupsAndGroups> myGroupsAndGroups = service.getgroupsLists(userId);

            if (myGroupsAndGroups!=null&&!myGroupsAndGroups.isEmpty()){
                MyGroupsAndGroups myGroupAndGroup = null;
                for (MyGroupsAndGroups myGroupsAndGroup : myGroupsAndGroups) {
                    if (groupId == myGroupsAndGroup.getGroupId()){
                        myGroupAndGroup = myGroupsAndGroup;
                    }
                }
                System.out.println("myGroupAndGroup:"+myGroupAndGroup);
                if (myGroupAndGroup != null) {
                    //有此群
                    // 2.将群信息存入request域
                   request.setAttribute("myGroupAndGroup",myGroupAndGroup);
                    //判断此用户是否为群主
                    if (service.findIfGroupOwner(groupId,userId)){//加入是群主，则跳转到群主看群信息的页面
                        //3.转发到GroupMessageOwner.jsp
                        request.getRequestDispatcher("/GroupMessageOwner.jsp").forward(request,response);
                    } else {//不是群主
                        //3.转发到GroupMessageMember.jsp
                        request.getRequestDispatcher("/GroupMessageMember.jsp").forward(request,response);
                    }
                }
            }

        }
    }

    public void ownerChangeGroupName(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取群id和要修改的群名
        String groupId = request.getParameter("groupId");
        String groupName = request.getParameter("groupName");
        //获取我的id
        Users users = (Users) request.getSession().getAttribute("user");
        int myId = users.getUserId();
        ResultInfo info = new ResultInfo();
        if (service.ownerChangeGroupName(groupId,groupName)&&service.memberChangeGroupName(groupId,groupName,myId)){
            info.setFlag(true);
        }else {
            info.setFlag(false);
        }
        writeValue(info,response);
    }

    public void memberChangeGroupName(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取群id和要修改的群名
        String groupId = request.getParameter("groupId");
        String myGroupName = request.getParameter("myGroupName");
        //获取我的id
        Users users = (Users) request.getSession().getAttribute("user");
        int myId = users.getUserId();
        ResultInfo info = new ResultInfo();
        if (service.memberChangeGroupName(groupId,myGroupName,myId)){
            info.setFlag(true);
        }else {
            info.setFlag(false);
        }
        writeValue(info,response);
    }

    public void leaveGroup(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取群id
        String groupId = request.getParameter("groupId");
        //获取我的id
        Users users = (Users) request.getSession().getAttribute("user");
        int myId = users.getUserId();
        ResultInfo info = new ResultInfo();
        if (service.leaveGroup(groupId,myId)){
            info.setFlag(true);
        }else {
            info.setFlag(false);
        }
        writeValue(info,response);
    }

    public void actionInviteFriendsToGroup(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //获取群id
        String groupId = request.getParameter("groupId");
        Users user = (Users) request.getSession().getAttribute("user");
        ResultInfo info = new ResultInfo();
        if (user!=null){
            int userId = user.getUserId();
            //首先搜出所有不在黑名单的好友
            List<FriendAndUsers> friendAndUsers = service.getfriendsNotInGroup(userId,groupId);
            if (friendAndUsers != null && !friendAndUsers.isEmpty()) {
                //2.将friendMessage存入request域
                //将groupId放入request
                request.setAttribute("groupId",groupId);
                request.setAttribute("friends",friendAndUsers);
            }
                //3.转发到查看好友信息页面
                request.getRequestDispatcher("/InviteFriendsToGroups.jsp").forward(request,response);


        }
    }

    public void inviteFriendsToGroup(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //1.获取所有id
        String[] friendIds = request.getParameterValues("friendId");
        String groupId = request.getParameter("groupId");
        //2.调用service邀请
        if (service.inviteToeGroup(friendIds,groupId)){
            //提示信息
            request.setAttribute("inviteToGroup_msg", "邀请成功！快进群聊天吧！");//3.转发到本页面
            request.getRequestDispatcher("/InviteFriendsToGroup.jsp").forward(request, response);
        } else {
            //提示信息
            request.setAttribute("inviteToGroup_msg", "邀请失败！");//3.转发到本页面
            request.getRequestDispatcher("/InviteFriendsToGroup.jsp").forward(request, response);
        }
    }

    public void actionGroupManagment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取群id
        String groupId = request.getParameter("groupId");
        Users user = (Users) request.getSession().getAttribute("user");
        ResultInfo info = new ResultInfo();
        if (user!=null){
            int userId = user.getUserId();
            //搜出所有不在此群的好友
            List<FriendAndUsers> friendAndUsers = service.getfriendsInGroup(userId,groupId);
            if (friendAndUsers != null && !friendAndUsers.isEmpty()) {
                //2.将friendMessage存入request域
                //将groupId放入request
                request.setAttribute("groupId",groupId);
                request.setAttribute("friends",friendAndUsers);
            }
            //3.转发到查看好友信息页面
            request.getRequestDispatcher("/GroupManagment.jsp").forward(request,response);


        }
    }

    public void dismissGroup(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String groupId0 = request.getParameter("groupId");
        ResultInfo info = new ResultInfo();
        if (groupId0!=null){
            if (service.dismissGroup(groupId0)){
                info.setFlag(true);
            }else {
                info.setFlag(false);
            }
        }
        writeValue(info,response);
    }

    public void kickPeople(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取所有id
        String[] friendIds = request.getParameterValues("friendId");
        String groupId = request.getParameter("groupId");
        //2.调用service踢人
        if (service.kickPeople(friendIds,groupId)){
            //提示信息
            request.setAttribute("kick_msg", "已踢！");//3.转发到本页面
            request.getRequestDispatcher("/GroupManagment.jsp").forward(request, response);
        } else {
            //提示信息
            request.setAttribute("kick_msg", "失败！");//3.转发到本页面
            request.getRequestDispatcher("/GroupManagment.jsp").forward(request, response);
        }
    }
}
