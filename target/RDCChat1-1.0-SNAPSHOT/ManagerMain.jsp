<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>RDCManagerMain</title>

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


        function blockFriend(accusedId){
            if (confirm("确定要封号吗？")){
                $.post("Users/blockFriend", {accusedId:accusedId}, function (info){
                    if (info.flag){
                        alert("封号成功！");
                    }else {
                        alert("封号失败！");
                    }
                });
            }
        }

        $(function (){

        })
    </script>

    <!-- css -->
    <style>
        td, th {
          text-align: center;
        }

    </style>

</head>
<body>

<header id="header">
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li><a href="${pageContext.request.contextPath}/Users/managerMain">举报信息</a></li>
                    <li class="active"><a href="${pageContext.request.contextPath}/Moment.jsp">朋友圈 <span class="sr-only">(current)</span></a></li>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>
</header>

<!-- 显示举报人信息看要不要封号 -->
<div class="container">
    <h3 style="text-align: center">RDCChat举报人信息</h3>
    <table border="1" class="table table-bordered table-hover">
        <tr class="success">
            <th>举报人</th>
            <th>被举报人</th>
            <th>举报内容</th>
            <th>操作</th>
        </tr>

        <c:forEach items="${reportMessage}" var="reporting">
            <tr>
                <td>${reporting.accuserName}</td>
                <td>${reporting.accusedName}</td>
                <td>${reporting.message}</td>
                <td>
                    <a class="btn btn-default btn-sm" href="javascript:blockFriend(${reporting.accusedId});">封号</a>
                </td>
            </tr>
        </c:forEach>

    </table>
</div>

</body>

</html>