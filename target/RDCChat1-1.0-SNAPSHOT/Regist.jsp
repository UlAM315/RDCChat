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

    <%-- 验证码 --%>
    <script type="text/javascript">
        //切换验证码
        function refreshCode(){
            //1.获取验证码图片对象
            var vcode = document.getElementById("vcode");
            //2.设置其src属性，加时间戳
            vcode.src = "${pageContext.request.contextPath}/CheckCodeServlet?time="+new Date().getTime();
        }
    </script>

    <script>

        $(function () {

            /*
            表单校验：
                1.用户名：单词字符，长度2到8位
                2.密码：单词字符，长度6到20位
                3.email：邮件格式
                4.手机号：手机号格式
                5.微信号：非空
                6.头像：非空
                7.背景图：非空
                8.验证码：非空
         */



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

            //校验密码
            function checkPassword() {
                //1.获取密码值
                var password = $("#userPassword").val();
                //2.定义正则
                var reg_password = /^\w{6,20}$/;

                //3.判断，给出提示信息
                var flag = reg_password.test(password);

                if(flag){
                    //密码合法,加一个绿色边框
                    $("#userPassword").css("border","1px solid green");
                }else{
                    //密码非法,加一个红色边框
                    $("#userPassword").css("border","1px solid red");
                }
                return flag;
            }

            //校验邮箱和唯一
            function checkEmail(){
                //1.获取邮箱
                var email = $("#email").val();
                //2.定义正则
                var reg_email = /^\w+@\w+\.\w+$/;

                //3.判断
                var flag = reg_email.test(email);

                if(flag){
                    $.post("Users/checkEmail",{email:email},function (data){

                        if(data.flag){
                            //密码合法,加一个绿色边框
                            $("#email").css("border","1px solid green");
                            return true;
                        }else{
                            //密码非法,加一个红色边框
                            $("#email").css("border","1px solid red");
                            alert(data.errorMsg);
                            return false;
                        }
                    });
                } else {
                    //密码非法,加一个红色边框
                    $("#email").css("border","1px solid red");
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



            //校验验证码非空
            function checkCheckCode(){
                //1.获取验证码
                var checkcode = $("#check").val();
                //2.定义正则
                var reg_checkcode = /^\w{4,4}$/;
                var flag = reg_checkcode.test(checkcode);
                if(flag){
                    $("#check").css("border","1px solid green");
                }else{
                    $("#check").css("border","1px solid red");
                }
                return flag;
            }


            //当某一个组件失去焦点是，调用对应的校验方法
            $("#userName").blur(checkUsername);

            $("#userPassword").blur(checkPassword);

            $("#email").blur(checkEmail);

            $("#telephone").blur(checkTelephone);

            $("#idNumber").blur(checkIdNumber);

            $("#check").blur(checkCheckCode);

            //当注册表单提交时，调用所有的校验方法
            $("#registerForm").submit(function(){

                //1.发送数据到服务器
                //if(checkUsername() && checkPassword() && checkEmail() && checkTelephone() && checkIdNumber() && checkAvatarImg() && checkBackgroundImg() && checkCheckCode()){
                //校验通过,发送ajax请求，提交表单的数据

                $.post("Users/regist", $("#registerForm").serialize(), function (data) {
                    //处理服务器响应的数据  data  {flag:true,errorMsg:"注册失败"}

                    if (data.flag) {
                        //注册成功
                        //验证邮箱，跳转成功页面
                        alert(data.errorMsg);
                        return true;
                    } else {
                        //注册失败,给errorMsg添加提示信息
                        alert(data.errorMsg);
                        return false;
                    }
                });
                /* }else {
                     //2.不让页面跳转
                     alert(111);
                     return false;
                 }*/
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
    <span>RDCChat注册中心</span>
    <form id="registerForm" action="#" method="post">
        <div class="form-group">
            <label for="userName">用户名:</label>
            <input type="text" class="form-control" id="userName" name="userName" placeholder="请输入用户名(长度2到8位)">
        </div>
        <div class="form-group">
            <label for="userPassword">密码:</label>
            <input type="password" class="form-control" id="userPassword" name="userPassword" placeholder="请输入密码(长度6到20位)">
        </div>
        <div class="form-group">
            <label for="idNumber">微信号:</label>
            <input type="text" class="form-control" id="idNumber" name="idNumber" placeholder="请输入微信号">
        </div>
        <div>
            <label>性别:</label>
            <input type="radio" id="gender" name="gender" value="男" checked> 男
            <input type="radio" name="gender" value="女"> 女
        </div>
        <div class="form-group">
            <label for="telephone">电话号码:</label>
            <input type="tel" class="form-control" id="telephone" name="telephone" placeholder="请输入电话号码">
        </div>
        <div class="form-group">
            <label for="email">邮箱:</label>
            <input type="email" class="form-control" id="email" name="email" placeholder="请输入邮箱">
        </div>
        <div class="form-inline">
            <label for="check">验证码：</label>
            <input type="text" name="check" class="form-control" id="check" placeholder="请输入验证码" style="width: 120px;"/>
            <a href="javascript:refreshCode()"><img src="${pageContext.request.contextPath}/CheckCodeServlet?time=1" title="看不清点击刷新" id="vcode"/></a>
        </div>
        <button type="submit" class="btn btn-default" id="regist">注册</button>
        <a href="${pageContext.request.contextPath}/LogIn.jsp">我要去登录啦！</a><br>
    </form>


</div>

</body>
</html>