<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>上传头像和背景图</title>
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
            //校验头像非空
            function checkAvatarImg() {
                var fileInput = $("#avatarImg").get(0).files[0];
                var flag = false;
                if (fileInput) {
                    flag = true;
                } else {
                    alert("请选择上传头像！");
                }
                return flag;
            }

            //校验背景图非空
            function checkBackgroundImg() {
                var fileInput = $("#backgroundImg").get(0).files[0];
                var flag = false;
                if (fileInput) {
                    flag = true;
                } else {
                    alert("请选择上传背景图！");
                }
                return flag;
            }

            //当上传图片表单提交时，调用的校验方法
            $("#fileForm").submit(function () {

                if (checkAvatarImg() || checkBackgroundImg()) {
                    return true;
                } else return false;

            });
        });

    </script>

</head>

<body>

<div>
    <form id="fileForm" action="${pageContext.request.contextPath}/Users/upLoad" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="avatarImg">头像:</label>
            <input type="file" class="form-control" id="avatarImg" name="avatarImg">
        </div>
        <div class="form-group">
            <label for="backgroundImg">选择背景图:</label>
            <input type="file" class="form-control" id="backgroundImg" name="backgroundImg">
        </div>
        <button type="submit" class="btn btn-default" id="finish">完成</button>
    </form>

    <a href="${pageContext.request.contextPath}/Main.jsp">进入主页面</a><br>
    <%-- 显示是否上传成功 若为null则直接跳转了页面 而没有显示任何结果 --%>
    <c:if test="${upload_msg != null}">
        <strong>${upload_msg}</strong>
    </c:if>
</div>


</body>

</html>