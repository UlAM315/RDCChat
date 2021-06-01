<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>RDCChatInviteFriendsToGroup</title>
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" +
                request.getServerPort() + path + "/";
    %>
    <base href="<%=basePath%>">
    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">


    <!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
    <script src="js/jquery-3.2.1.min.js"></script>
    <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
    <script src="js/bootstrap.min.js"></script>

    <style type="text/css">
        td, th {
            text-align: center;
        }

        img{
            width: 50px;
            height: 50px;
        }
    </style>

    <script>

        $(function(){
            $("#form").submit(function(){
                if(confirm("您确定要邀请ta进群吗？")){
                        var flag = false;
                        //判断是否有选中条目
                        var cbs = $("#friendId");
                        for (var i = 0; i < cbs.length; i++) {
                            if(cbs[i].checked){
                                //有一个条目选中了
                                flag = true;
                                break;
                            }
                        }
                        if (!flag){
                            alert("选框不能为空！");
                        }
                        return flag;

                }else {
                    return false;
                }
            });


            //1.获取第一个cb
            $("#firstCb").click(function(){
                //2.获取下边列表中所有的cb
                var cbs = $("#friendId");
                //3.遍历
                for (var i = 0; i < cbs.length; i++) {
                    //4.设置这些cbs[i]的checked状态 = firstCb.checked
                    cbs[i].checked = this.checked;

                }

            });


        });


    </script>
</head>
<body>

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
                    <li><a href="${pageContext.request.contextPath}/MyFriends/acceptNewFriends">新的联系人</a></li>
                    <li class="active"><a href="${pageContext.request.contextPath}/Moment.jsp">朋友圈 <span class="sr-only">(current)</span></a></li>
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
                <form class="navbar-form navbar-left" id="searchForm" role="search" action="${pageContext.request.contextPath}/MyFriends/searchFriends">
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
<div class="container">
    <strong style="color: purple">${inviteToGroup_msg}</strong>
    <h3 style="text-align: center">邀请好友进群</h3>



    <form id="form" action="${pageContext.request.contextPath}/MyFriends/inviteFriendsToGroup" method="post">
        <input type="text" id="groupId" value="${groupId}" name="groupId" hidden="hidden">
        <div style="float: right;margin: 5px;">
            <button type="submit"  id="createGroup">确定邀请</button>
        </div>
        <table border="1" class="table table-bordered table-hover">
            <tr class="success">
                <th><input type="checkbox" id="firstCb"></th>
                <th>头像</th>
                <th>用户名</th>
                <th>爱的昵称</th>
            </tr>

            <c:forEach items="${friends}" var="user">
                <tr>
                    <td><input type="checkbox" name="friendId" id="friendId" value="${user.friendId}"></td>
                    <td><img src="${user.avatarImg}"></td>
                    <td>${user.userName}</td>
                    <td>${user.nickname}</td>
                </tr>

            </c:forEach>


        </table>
    </form>


</div>


</body>
</html>

