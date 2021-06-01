<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
  <title>RDCChatFriendMessage</title>
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

  <style>
    body{
      margin: 0;
      padding: 0;
    }
    /* 解决高度塌陷 */
    .clearfix::before,
    .clearfix::after {
      content: "";
      display: table;
      clear: both;
    }
    .messageBoxOuter{
      width: 100%;
      height: 1000px;
      background-color: rgba(136, 136, 136, 0.313);
    }
    .messageBox{
      width: 500px;
      height: 250px;
      border: 2px solid pink;
      position: relative;
      background-color: rgb(201, 245, 250);
      margin-top: 300px;
      /* 设置水平居中 */
      margin-left: 50%;
      transform: translate(-50%,-50%);
    }
    .messageBox .messageTop{
      width: 400px;
      height: 90px;
      border-bottom: 1px solid pink;
      display: flex;
      justify-content: space-between;
      margin: 0 auto;
    }
    /* <!-- 增加               1111111111111111111111111111 --> */
    .messageBox  .closeMessage{
      width: 40px;
      line-height: 40px;
      font-size: 17px;
      border-radius: 50%;
      margin-left: 450px;
      text-align: center;
      color: #888;
      cursor: pointer;
    }
    .messageBox .closeMessage:hover{
      background-color: rgb(168, 218, 243);
      transition: 0.5s;
      color: black;
      font-size: 20px;
    }
    /* <!-- 增加               1111111111111111111111111111 --> */
    .messageBox .messageTop .avatar img{
      width: 50px;
      height: 50px;
      margin-top: 20px;
    }
    .messageBox .messageTop .topLeft{
      color: #888;
    }
    .messageBox .messageTop .topLeft h1{
      font-size: 25px;
      line-height: 40px;
      margin: 0;
      color: black;
    }
    .messageBox .messageTop .topLeft span{
      font-size: 15px;
      line-height: 25px;
      color: #888;
    }
    .messageBox .messageBottom{
      color: #888;
      width: 400px;
      margin: 10px auto;
    }
    .messageBox .messageBottom input{
      color: black;
      width: 300px;
      line-height: 30px;
      display: inline-block;
      border: 1px solid rgba(0, 0, 0, 0);
      outline: none;
      background-color: transparent;
    }
    .messageBox .messageBottom .sign{
      border: 1px solid pink;
      border-radius: 5px;
    }
    .hateIt,.goDie{
      display: inline-block;
      line-height: 30px;
      width: 90px;
      color: rgb(255, 38, 0);
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
<input type="text" id="friendId" value="${friendMessages.userId}" hidden="hidden">

<div class="messageBoxOuter clearfix">
  <div class="messageBox">
    <div class="closeMessage">
      <strong>X</strong>
    </div>
    <div class="messageTop">
      <div class="topLeft">
        <h1>${friendMessages.userName}</h1><!-- 放名字 -->
        微信号：<span>${friendMessages.idNumber}</span><br>
        性别：<span>${friendMessages.gender}</span>
      </div>
      <div class="avatar">
        <img src="${friendMessages.avatarImg}">
      </div>
    </div>
    <div class="messageBottom">
      爱的昵称：<input type="text" class="signContent" id="nickname" value="${friendMessages.nickname}">
      <span class="hateIt"><a id="blacklist">加入黑名单</a></span>
      <span class="goDie"><a id="report">举报该好友</a></span>
    </div>
    <div>
      <br>

    </div>
  </div>
</div>


<script>
  var signContent=document.querySelector('.signContent');  //获取备注内容外的input
  var messageBox=document.querySelector('.messageBox');    //获取修改信息框
  var closeMessage=document.querySelector('.closeMessage');    //获取关闭按钮
  // 当备注内容input失去焦点时，如果输入是空值，则无变化。若输入不为空，则将其赋给placeholder
  // 同时删去sign类名以隐藏边框


  signContent.onblur=function(){
    this.className='signContent';
    var nickname = $("#nickname").val();
    var friendId = $("#friendId").val();
    if (nickname!=''){
      $.post("MyFriends/changeNickname",{nickname: nickname,friendId:friendId},function (info){
        if (info.flag){
          alert("修改昵称成功！");
        }else {
          alert("修改失败！");
        }
      });
    }
  }
  // 当备注内容input获得焦点时，赋予input新类名sign来显示外边框
  signContent.onfocus=function(){
    this.className='signContent sign';
  }

  closeMessage.onclick=function(){
    messageBox.style.display='none';
    location.href="${pageContext.request.contextPath}/Main.jsp";
  }

  $("#blacklist").click(function (){
    var friendId = $("#friendId").val();
    if (confirm("确定要把好友加入黑名单吗？")){
      $.post("MyFriends/addToBlacklist",{friendId:friendId},function (info){
        if (info.flag){
          alert("已添加！");
        }else {
          alert("添加失败！");
        }
      });
    }
  });

  $("#report").click(function (){
    var friendId = $("#friendId").val();
    var message = prompt("请输入举报内容:");
    if (message!=null&&message!=''){
      $.post("MyFriends/report",{friendId:friendId,message:message},function (info){
        if (info.flag){
          alert("举报成功！");
        }else {
          alert("举报失败！");
        }
      });
    }
  });

</script>

</body>
</html>