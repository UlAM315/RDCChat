<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>RDCChat登录中心</title>

    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">


    <!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
    <script src="js/jquery-3.2.1.min.js"></script>
    <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
    <script src="js/bootstrap.min.js"></script>

    <script>
        $(function (){

            function checkTelephone(){
                var telephone = $("#telephone").val();
                if(telephone == ""){
                    alert("输入内容不能为空！");
                    return false;
                } else return true;
            }

            function checkPassword(){
                var userPassword = $("#userPassword").val();
                if(userPassword == ""){
                    alert("密码不能为空！");
                    return false;
                } else return true;
            }

            $("#logInForm").submit(function (){
                if (checkTelephone() && checkPassword()){
                    $.post("Users/managerLogIn", $("#logInForm").serialize(), function (data) {

                        if (data.flag) {
                            //登录成功
                            //上传头像
                            alert("登录成功！");
                            location.href="${pageContext.request.contextPath}/Users/managerMain";
                        } else {
                            //登录失败,给errorMsg添加提示信息
                            alert("登陆失败！");
                            return false;
                        }
                    });
                }
                return false;
            });

        });
    </script>

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
        /*.input-box input{
            !*border: 1px solid pink;*!
            outline:none;
            width: 300px;
            height: 50px;
            border-radius: 10px;
            right: 0;
            margin-right: 0;
        }*/
        #rdcImg{
            width: 100px;
            height: 100px;
        }

        button{
            width: 60px;
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
    <span>RDCChat管理员登录中心</span>
    <form id="logInForm" action="#" method="post">
        <div class="form-group">
            <label for="managerName">管理员名字:</label>
            <input type="text" class="form-control" id="managerName" name="managerName" placeholder="请输入管理员名字">
        </div>
        <div class="form-group">
            <label for="managerPassword">密码:</label>
            <input type="password" class="form-control" id="managerPassword" name="managerPassword" placeholder="请输入密码">
        </div>
        <div>
            <a href="${pageContext.request.contextPath}/LogIn.jsp">返回</a><br>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <div class="checkbox">
                    <label>
                        <input type="checkbox">记住我
                    </label>
                </div>
            </div>
        </div>
        <button type="submit" class="btn btn-default">登录</button>
    </form>
</div>

</body>


</html>