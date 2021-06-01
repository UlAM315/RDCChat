<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>RDCChat修改个人信息</title>

    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">


    <!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
    <script src="js/jquery-3.2.1.min.js"></script>
    <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
    <script src="js/bootstrap.min.js"></script>

    <script>

        $(function () {

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

            //校验用户名
            //单词字符，长度8到20位
            function checkUsername() {
                //1.获取用户名值
                var userName = $("#userName").val();
                //2.定义正则
                var reg_username = /^\w{2,8}$/;

                //3.判断，给出提示信息
                var flag = reg_username.test(userName);

                if(flag){
                    $.post("Users/checkUserName",{userName:userName},function (data){

                        if(data.flag){
                            //用户名合法,加一个绿色边框
                            $("#userName").css("border","1px solid green");
                            return true;
                        }else{
                            //用户名非法,加一个红色边框
                            $("#userName").css("border","1px solid red");
                            alert(data.errorMsg);
                            return false;
                        }
                    });
                } else {
                    //用户名非法,加一个红色边框
                    $("#userName").css("border","1px solid red");
                    return false;
                }


            }

            //校验手机号和唯一
            function checkTelephone(){
                //1.获取手机号
                var telephone = $("#telephone").val();
                //2.定义正则
                var reg_telephone = /^1[3|4|5|7|8][0-9]\d{8,11}$/;

                //3.判断
                var flag = reg_telephone.test(telephone);

                if(flag){
                    $.post("Users/checkTelephone",{telephone:telephone},function (data){

                        if(data.flag){
                            //密码合法,加一个绿色边框
                            $("#telephone").css("border","1px solid green");
                            return true;
                        }else{
                            //密码非法,加一个红色边框
                            $("#telephone").css("border","1px solid red");
                            alert(data.errorMsg);
                            return false;
                        }
                    });
                } else {
                    //密码非法,加一个红色边框
                    $("#telephone").css("border","1px solid red");
                    return false;
                }


            }

            //校验微信号非空和唯一
            function checkIdNumber(){
                //1.获取微信号
                var idNumber = $("#idNumber").val();

                if(idNumber.length != 0){
                    $.post("Users/checkIdNumber",{idNumber:idNumber},function (data){

                        if(data.flag){
                            //密码合法,加一个绿色边框
                            $("#idNumber").css("border","1px solid green");
                            return true;
                        }else{
                            //密码非法,加一个红色边框
                            $("#idNumber").css("border","1px solid red");
                            alert(data.errorMsg);
                            return false;
                        }
                    });
                } else {
                    //密码非法,加一个红色边框
                    $("#idNumber").css("border","1px solid red");
                    return false;
                }


            }

            //当某一个组件失去焦点是，调用对应的校验方法
            $("#userName").blur(checkUsername);

            $("#telephone").blur(checkTelephone);

            $("#idNumber").blur(checkIdNumber);

            //当注册表单提交时，调用所有的校验方法
            $("#updateUserMessageForm").submit(function(){

                //1.发送数据到服务器
                if (confirm("确认修改吗？")){
                    $.post("Users/updateUserMessage", $("#updateUserMessageForm").serialize(), function (data) {
                        //处理服务器响应的数据

                        if (data.flag) {
                            //修改成功
                            alert("修改成功！");
                            location.href = "${pageContext.request.contextPath}/UpdateUserMessage.jsp";
                        } else {
                            //注册失败,给errorMsg添加提示信息
                            alert("修改失败！");
                        }
                    });
                }
            });

        });


    </script>
<%-- 校验和上传 --%>

    <%-- css --%>
    <style>
    .input-box{
        width: 500px;
        /* background-color: rgba(34, 173, 118, 0.286);
         */
        margin: 0 auto;
        border: 1px solid rgba(194, 13, 104, 0.463);
        box-shadow: 0.1px 0.1px 11px rgba(194, 13, 104, 0.463);
        border-radius: 10px;
    }

    #rdcImg{
        width: 100px;
        height: 100px;
    }

    button{
        width: 60px;
        line-height: 60px;
        color: aliceblue;
        background-color: pink;
        border: 1px solid #888;
        border-radius: 10px;
        margin-left: 220px;
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

<div class="input-box">
    <img src="img/rdc.jpg" alt="" class="img-circle" id="rdcImg">
    <span>RDCChat修改个人信息</span>
    <form id="updateUserMessageForm" action="#" method="post">
        <div class="form-group">
            <label for="userName">用户名:</label>
            <input type="text" class="form-control" id="userName" name="userName" value="${user.userName}">
        </div>
        <div class="form-group">
            <label for="idNumber">微信号:</label>
            <input type="text" class="form-control" id="idNumber" name="idNumber" value="${user.userId}">
        </div>
        <div>
            <label>性别:</label>
            <c:if test="${user.gender == '男'}">
                <input type="radio" id="gender" name="gender" value="男" checked> 男
                <input type="radio" name="gender" value="女"> 女
            </c:if>
            <c:if test="${user.gender == '女'}">
                <input type="radio" id="gender" name="gender" value="男"> 男
                <input type="radio" name="gender" value="女" checked> 女
            </c:if>
        </div>
        <div class="form-group">
            <label for="telephone">电话号码:</label>
            <input type="tel" class="form-control" id="telephone" name="telephone" value="${user.telephone}">
        </div>
        <button type="submit" class="btn btn-default" id="update">确认修改</button>
    </form>


</div>

</body>
</html>