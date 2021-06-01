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


    <script>

        $(function () {


            //校验邮箱和存在
            function checkEmail(){
                //1.获取邮箱
                var email = $("#email").val();
                //2.定义正则
                var reg_email = /^\w+@\w+\.\w+$/;

                //3.判断
                var flag = reg_email.test(email);

                if(flag){
                    $.post("Users/checkEmail",{email:email},function (data){

                        if(!data.flag){
                            //密码合法,加一个绿色边框
                            $("#email").css("border","1px solid green");
                            return true;
                        }else{
                            //密码非法,加一个红色边框
                            $("#email").css("border","1px solid red");
                            alert("该用户不存在，快去注册吧！");
                            return false;
                        }
                    });
                } else {
                    //密码非法,加一个红色边框
                    $("#email").css("border","1px solid red");
                    return false;
                }


            }

            //当某一个组件失去焦点是，调用对应的校验方法
            $("#email").blur(checkEmail);

            //当注册表单提交时，调用所有的校验方法
            $("#emailForm").submit(function(){

                    //校验通过,发送ajax请求，提交表单的数据
                    var email = $("#email").val();

                    //发邮件
                $.post("Users/forgetPassword", {email:email}, function (data) {
                    //处理服务器响应的数据
                    if (data.flag) {
                        alert("注意查收邮箱验证码喔！");
                        location.href="${pageContext.request.contextPath}/EmailCodeCheck.jsp";
                    } else {
                        //修改失败
                        alert("邮箱有误！")
                        return false;
                    }
                });
                //如果这个方法没有返回值，或者返回为true，则表单提交，如果返回为false，则表单不提交
                return false;
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
    <form id="emailForm" action="#" method="post">
        <div class="form-group">
            <label for="email">邮箱:</label>
            <input type="email" class="form-control" id="email" name="email" placeholder="请输入邮箱">
        </div>
        <button type="submit" class="btn btn-default" id="update">下一步</button><br>
    </form>
        <a href="${pageContext.request.contextPath}/Users/exit">我要去登录啦！</a><br>
</div>

</body>
</html>