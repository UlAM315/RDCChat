<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>RDCChat</title>
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" +
                request.getServerPort() + path + "/";
    %>
    <base href="<%=basePath%>">
    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/common.css">
    <link rel="stylesheet" type="text/css" href="css/index.css">

    <!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
    <script src="js/jquery-3.2.1.min.js"></script>
    <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
    <script src="js/bootstrap.min.js"></script>


    <script>

        function setReceiver(groupName) {
            document.getElementById("reply-to").innerHTML = "接收人：" + groupName;
        }

        function getFriendMessage(friendId){
            location.href="${pageContext.request.contextPath}/MyFriends/friendMessage?friendId="+friendId;
        }

        function getfriendsLists(uesrId){
            $.post("MyFriends/getfriendsLists",{userId: uesrId},function (info){
                var friends_lis = "";
                if(info.flag){
                    for (let i = 0; i < info.data.length; i++) {
                        if (info.data[i].nickname != null){
                            var friendsLists = '<li class="aFriend">'+
                                                '<a href="javascript:getFriendMessage('+info.data[i].friendId+');"><img src="/RDCChat1/'+info.data[i].avatarImg+'"></a>'+
                                                '<span class="friendName"><a href=# onclick=setReceiver(this)>'+info.data[i].nickname+'</a></span>'+
                                                '</li>';
                            friends_lis += friendsLists;
                        } else {
                            var friendsLists = '<li class="aFriend">'+
                                                '<a href="javascript:getFriendMessage('+info.data[i].friendId+');"><img src="/RDCChat1/'+info.data[i].avatarImg+'"></a>'+
                                                '<span class="friendName"><a href=# onclick=setReceiver(this)>'+info.data[i].userName+'</a></span>'+
                                                '</li>';
                            friends_lis += friendsLists;
                        }
                    }
                    //alert(info.data[i].friendId);
                    $(".friendsLists").html(friends_lis);
                } else {
                    $(".friendsLists").html("你还没有好友哦！");
                }
            });
        }

        function getgroupsLists(uesrId){
            $.post("MyFriends/getgroupsLists",{userId: uesrId},function (info){
                if(info.flag){
                    for (let i = 0; i < info.data.length; i++) {
                        if (info.data[i].myGroupName != null){
                            var chatsLists = '<li class="aChat">'+
                                '<img src="/RDCChat1/'+info.data[i].avatarImg+'">'+
                                '<span class="chatName">'+info.data[i].myGroupName+'</span>'+
                                '</li>';
                            $(".chatsLists").html(chatsLists);
                        } else {
                            var chatsLists = '<li class="aChat">'+
                                '<img src="/RDCChat1/'+info.data[i].avatarimg+'">'+
                                '<span class="chatName">'+info.data[i].groupName+'</span>'+
                                '</li>';
                            $(".chatsLists").html(chatsLists);
                        }
                    }
                } else {
                    $(".chatsLists").html("你还没有群聊哦！");
                }
            });
        }

        $(function (){
            //设置连接到的群聊
            var groupName = $("#groupName").val();
            setReceiver(groupName);

            var userId = $("#userId").val();

            getfriendsLists(userId);

            $("#friends").click(function (){
                $("#groupsdiv").css("display","none");
                $("#friendsdiv").css("display","block");
                getfriendsLists(userId);
            });

            $("#groups").click(function (){
                $("#friendsdiv").css("display","none");
                $("#groupsdiv").css("display","block");
                getgroupsLists(uesrId)
            });

            $("#exit").click(function (){
                if(confirm("您确定要退出登录吗？")){
                    location.href="${pageContext.request.contextPath}/Users/exit";
                }
            });

            $("#searchbutton").click(function (){
                var searchText = $("#searchText").val();
                if(searchText != ''){
                    $("#searchForm").submit();
                } else {
                    alert("搜索内容不能为空！")
                }
            });

            $("#goToMain").click(function (){
                if (confirm("确定要离开吗？")){
                    location.href="${pageContext.request.contextPath}/Main.jsp";
                }
            });

        });


    </script>

    <!-- css -->
    <style>
        /* 设置导航条内聊天区的样式,开启绝对定位 */
        .chatBox{
            margin-top: 2px;
            position: absolute;
            width: 100%;
            border: 1px solid pink;
            height: 600px;
        }
        /* 设置聊天内容的左侧,使用浮动 */
        .chatBox .chatLeft{
            width: 30%;
            float: left;
            background-color: rgba(46, 151, 199, 0.301);
        }
        /* 设置搜索框 */
        .searchFriends input{
            width: 80%;
            margin-left: 10%;
            border: 1px solid pink;
            outline: none;
        }
        /* 设置好友和群聊的样式和水平排列 */
        .myFs{
            display: flex;
            position: relative;
            padding: 0;
            justify-content: space-around;
            list-style-type: none;
        }
        .myFs li{
            width: 50%;
        }
        .myFs .oneTwo{
            border: 1px solid rgb(4, 27, 235);
            display: block;
            color: #fff;
            text-align: center;
            background-color: rgba(54, 106, 167, 0.753);
            line-height: 40px;
        }
        /* 设置好友和群聊的头像 */
        .chatBox img{
            width: 30px;
            height: 30px;
            border-radius: 50%;
        }
        /* 设置好友/群聊占满左侧 */
        .friendListsBox{
            width: 200%;
            padding: 0;
            margin: 0;
        }
        /* 使得左侧贴紧 */
        .friendListsBox .friendsLists{
            height: 528px;
            overflow-y: scroll;
            padding: 0;
        }
        .friendsLists .aFriend{
            list-style-type: none;
            display: flex;
            width: 100%;
            margin-bottom: 1px;
            border-bottom: 1px solid #888;
        }
        /* 设置好友名称以及超出省略 */
        .aFriend .friendName{
            width: 100%;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
        }
        /* 群聊设置同好友设置 */
        .chatsListsBox{
            width: 200%;
            padding: 0;
            margin-left: -100%;
        }
        .chatsListsBox .chatsLists{
            height: 528px;
            overflow-y: scroll;
            padding: 0;
        }
        .chatsLists .aChat{
            list-style-type: none;
            display: flex;
            width: 100%;
            margin-bottom: 1px;
            border-bottom: 1px solid #888;
        }
        .aChat .chatName{
            width: 100%;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
        }
        /* 设置聊天右侧 */
        .chatRight{
            width: 70%;
            height: 600px;
            float: right;
        }
        /* 聊天内容区 */
        .chatRight .chatContent{
            width: 100%;
            box-sizing: border-box;
            height: 400px;
            border-left: 1px solid pink;
            border-bottom: 2px dotted pink;
        }
        /* 输入内容区 */
        .inputChatContent{
            position: relative;
            width: 100%;
            height: 200px;
            background-color: rgba(27, 42, 180, 0.452);
        }
        .inputChatContent .inputContent{
            width: 100%;
            height: 200px;
            box-sizing: border-box;
            padding: 15px;
            border: 1px solid pink;
            outline: none;
        }
        /* 发送按钮 */
        .inputChatContent .sentContent{
            width: 70px;
            color: #fff;
            background-color: rgba(204, 12, 130, 0.281);
            line-height: 30px;
            position: absolute;
            bottom: 10px;
            right: 10px;
            border: none;
        }
    </style>
</head>
<body>
<!--引入头部-->
<header id="header">
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="${pageContext.request.contextPath}/Main.jsp">聊天</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="${pageContext.request.contextPath}/MyFriends/acceptNewFriends">新的联系人 <span class="sr-only">(current)</span></a></li>
                    <li><a href="${pageContext.request.contextPath}/Moment.jsp">朋友圈</a></li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">我 <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li role="separator" class="divider"></li>
                            <li><a href="${pageContext.request.contextPath}/UpdateUserMessage.jsp">修改个人信息</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="${pageContext.request.contextPath}/ForgetPassword.jsp">修改密码</a></li>
                        </ul>
                    </li>
                </ul>
                <form class="navbar-form navbar-left" id="searchForm" role="search" action="${pageContext.request.contextPath}/MyFriends/searchFriends" method="post">
                    <div class="form-group">
                        <input type="text" class="form-control" id="searchText" name="searchText" placeholder="请输入用户名/群聊名称">
                    </div>
                    <button type="submit" class="btn btn-default" id="searchbutton">搜索</button>
                </form>
                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">更多 <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li role="separator" class="divider"></li>
                            <li><a href="${pageContext.request.contextPath}/IssueMoment.jsp">发朋友圈</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="${pageContext.request.contextPath}/MyFriends/actionCreateGroup">发起群聊</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="${pageContext.request.contextPath}/MyFriends/getBlacklist">黑名单</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a id="exit">退出登录</a></li>
                        </ul>
                    </li>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>
</header>

<div class="chatBox" style="display: block;">
    <input hidden="hidden" value="${user.userName}" id="userName" type="text">
    <input hidden="hidden" value="${user.userId}" id="userId">
    <input hidden="hidden" value="${groupName}" id="groupName">
    <input hidden="hidden" value="${groupId}" id="groupId">

    <div class="chatBox" style="display: block;">
    <div class="chatLeft">

        <ul class="myFs">

            <li class="myFriends">
                <span class="oneTwo myLovelyFriend" id="friends" onclick="javascript:getfriendsLists(${user.userId});">我的好友</span>
                <!-- 好友列表 -->
                <div class="friendListsBox"id="friendsdiv"  style="display: block;">
                    <ul class="friendsLists" >
                        <%--<li class="aFriend">
                            <img src="./tu/yanfa.jpg" alt="">
                            <span class="friendName">dava</span>
                        </li>
                        <li class="aFriend">
                            <img src="./tu/yanfa.jpg" alt="">
                            <span class="friendName">davasasassaasasasa</span>
                        </li>--%>
                    </ul>
                </div>

            </li>
            <li class="myChats">
                <span class="oneTwo myLovelyChat" id="groups" onclick="javascript:getgroupsLists(${user.userId})">我的群聊</span>
                <!-- 群聊列表 -->
                <div class="chatsListsBox" id="groupsdiv" style="display: none;">
                    <ul class="chatsLists">
                        <%--<li class="aChat">
                            <img src="img/rdc.jpg" alt="">
                            <span class="chatName">抓瓦</span>
                        </li>
                        <li class="aChat">
                            <img src="img/rdc.jpg" alt="">
                            <span class="chatName">抓121212121212121瓦</span>
                        </li>--%>
                    </ul>
                </div>
            </li>

        </ul>
    </div>
    </div>
    <div class="chatRight">
        <!-- 聊天内容框 -->
        <div class="chatContent" id="chatContent">

        </div>
        <!-- 内容输入框 -->
        <div class="inputChatContent">
            <div id="reply-to">接收人：  <span id="message"></span></div>
            <textarea name="" id="inputContent" class="inputContent"></textarea>
            <button class="sentContent" id="sentContent">发送</button>
            <button class="goToMain" id="goToMain" >退出聊天</button>
        </div>
    </div>
</div>

<script type="text/javascript">

    var websocket = null;
    var userName = document.getElementById('userName').value;
    var groupId = document.getElementById('groupId').value;
    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://localhost:8080/RDCChat1/webSocketGroupChat?userName="+userName+"&groupId="+groupId);
    }
    else {
        alert('当前浏览器 Not support websocket')
    }

    //连接发生错误的回调方法
    websocket.onerror = function () {
        setMessageInnerHTML("WebSocket连接发生错误");
    };

    //连接成功建立的回调方法
    websocket.onopen = function () {
        setMessageInnerHTML("连接成功，可以开始聊天了");
    }

    //接收到消息的回调方法
    websocket.onmessage = function (event) {
        document.getElementById('chatContent').innerHTML+= "<p style='float:left'>"+event.data+"</p></br>";

    }

    //连接关闭的回调方法
    websocket.onclose = function () {
        setMessageInnerHTML("WebSocket连接关闭");
    }

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        closeWebSocket();
    }

    //将消息显示在网页上
    function setMessageInnerHTML(innerHTML) {
        document.getElementById('message').innerHTML= innerHTML + '<br/>';
    }

    //关闭WebSocket连接
    function closeWebSocket() {
        websocket.close();
    }
    document.getElementById("sentContent").onclick=function(){
        var message = document.getElementById('inputContent').value;
        document.getElementById('inputContent').value="";
        websocket.send(message);
        /*document.getElementById('chatContent').innerHTML+= "<p style='float:right'>我说:"+message+"</p></br>";*/
    };
</script>

</body>

</html>