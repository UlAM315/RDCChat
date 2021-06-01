<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>RDCChat注册中心</title>

    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">


    <!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
    <script src="js/jquery-3.2.1.min.js"></script>
    <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
    <script src="js/bootstrap.min.js"></script>

    <%-- 校验和上传 --%>
    <script>

        $(function () {

            var emailCode = $("#emailCode").val();
            
            $("#emailCodeCheckForm").submit(function(){
                var getEmailCode = $("#getEmailCode").val();
                if(getEmailCode == emailCode){
                    alert("验证成功！");
                    location.href="${pageContext.request.contextPath}/UpdateUserPassword.jsp";

                } else {
                    alert("邮箱验证码错误！");
                    location.href="${pageContext.request.contextPath}/ForgetPassword.jsp";
                    return false;
                }
                return false;
            });

        });
    </script>

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
            width: 100px;
            line-height: 30px;
            color: aliceblue;
            background-color: pink;
            border: 1px solid #888;
            border-radius: 10px;
            margin-left: 220px;
        }
    </style>


</head>
<body>
<div class="input-box">
    <img src="img/rdc.jpg" alt="" class="img-circle" id="rdcImg">
    <span>RDCChat修改密码中心</span>
    <form id="emailCodeCheckForm" action="#" method="post">
        <div class="form-group">
            <label for="getEmailCode">邮件验证码(区分大小写):</label>
            <input type="text" class="form-control" id="getEmailCode" name="getEmailCode" placeholder="请输入邮箱验证码<区分大小写>">
        </div>
        <button type="submit" class="btn btn-default" id="next">下一步</button><br>
    </form>
        <a href="${pageContext.request.contextPath}/LogIn.jsp">我不改密码啦</a><br>
</div>
<input id="emailCode" hidden="hidden" value="${sessionScope.emailCode}"> <br>

</body>
</html>