<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>RDCMoment</title>

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



    <script>
        $(function (){

            //校验内容是否为空
            function checkContent() {
                //1.获取内容
                var content = $("#content").val();

                if(content != ''){
                    //内容非空,加一个绿色边框
                    $("#content").css("border","1px solid green");
                    return true;
                }else{
                    //内容为空,加一个红色边框
                    $("#content").css("border","1px solid red");
                    alert("内容不能为空！");
                    return false;
                }
            }

            $("#content").blur(checkContent);

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

            $("#publish").onclick(function (){
                alert(checkContent());
                if(checkContent()){
                    $("#issueMomentForm").submit();

                }else {
                    alert("发布内容不能为空哦！");
                }
            });
        })
    </script>

    <!-- css -->
    <style>
        /* 朋友圈 */
        .friendShows{
            width: 80%;
            background-color: rgba(104, 240, 176, 0.219);
            margin: 0 auto;
            padding: 40px;
            list-style-type: none;
            padding: 0;
        }
        .friendShows li{
            border: 2px solid rgba(136, 136, 136, 0.5);
            padding: 20px;
        }
        .friendShows img{
            width: 50px;
            height: 50px;
        }
        .friendShows li h2{
            width: 100%;
            background-color: #fff;
        }
        .friendShows li h3{
            width: 100%;
            font-size: 15px;
            background-color: #fff;
        }
        .friendShows li h4{
            width: 50px;
            height: 50px;
            font-size: 15px;
            background-color: #fff;
        }

        .inputContent{
            width: 100%;
            height: 200px;
            box-sizing: border-box;
            padding: 15px;
            border: 1px solid pink;
            outline: none;
        }

        .issue{
            width: 55px;
            line-height: 30px;
            border-radius: 10px;
            color: #fff;
            background-color: rgb(245, 121, 189);
            border: 1px solid pink;
            box-shadow: 2px 2px 5px rgba(54, 34, 38, 0.527);
        }

    </style>

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

<!-- 发朋友圈 -->
<ul class="friendShows">
    <li class="fshow">
        <form id="issueMomentForm" action="${pageContext.request.contextPath}/Moments/issueMoment" method="post" enctype="multipart/form-data">
            <div>
                <label for="content">内容:</label>
                <textarea name="content" id="content" class="inputContent" placeholder="请输入朋友圈文案..."></textarea>
            </div>
            <div>
                <label>请选择朋友圈的照片(最多可选择3张)</label>
                <input type="file" name="firstImg">
                <input type="file" name="secondImg">
                <input type="file" name="thirdImg">
            </div>
            <div>
                <label>是否公开:</label>
                <input type="radio" id="ifOpen" name="ifOpen" value="Y" checked> 是
                <input type="radio" name="ifOpen" value="N"> 否
            </div>
            <button type="submit" class="issue" id="publish">发布</button>
        </form>
        <br><br>
        <strong style="color: purple">${issueMoment_msg}</strong>
    </li>
</ul>

</body>

</html>