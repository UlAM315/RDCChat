<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>RDCMoment</title>
    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">


    <!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
    <script src="js/jquery-3.2.1.min.js"></script>
    <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
    <script src="js/bootstrap.min.js"></script>

    <%-- js --%>
    <script>
        $(function (){

            //获取userId的参数值
            var userId = $("#userId").val();
            //当页码加载完成后，调用load方法，发送ajax请求加载数据
            load(null);

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
                    alert("搜索内容不能为空！");
                }
            });
        });




        //定义Format方法  dateTime--后台传输过来的Date类型  fmt--你要转换的格式
        //返回的是对应fmt时间格式的字符串
        function Format(datetime,fmt) {
            if (parseInt(datetime)==datetime) {
                if (datetime.length==10) {
                    datetime=parseInt(datetime)*1000;
                } else if(datetime.length==13) {
                    datetime=parseInt(datetime);
                }
            }
            datetime=new Date(datetime);
            var o = {
                "M+" : datetime.getMonth()+1,                 //月份
                "d+" : datetime.getDate(),                    //日
                "h+" : datetime.getHours(),                   //小时
                "m+" : datetime.getMinutes(),                 //分
                "s+" : datetime.getSeconds(),                 //秒
                "q+" : Math.floor((datetime.getMonth()+3)/3), //季度
                "S"  : datetime.getMilliseconds()             //毫秒
            };
            if(/(y+)/.test(fmt))
                fmt=fmt.replace(RegExp.$1, (datetime.getFullYear()+"").substr(4 - RegExp.$1.length));
            for(var k in o)
                if(new RegExp("("+ k +")").test(fmt))
                    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
            return fmt;
        }

            function load(currentPage){
            //发送ajax请求，请求route/pageQuery,传递cid
            $.post("Moments/getMoments",{currentPage:currentPage},function (page) {
                //解析page数据，展示到页面上

                //1.分页工具条数据展示
                //1.1 展示总页码和总记录数
                $("#totalPage").html(page.totalPage);
                $("#totalCount").html(page.totalCount);

                /*
                        <li><a href="">首页</a></li>
                        <li class="threeword"><a href="#">上一页</a></li>
                        <li class="curPage"><a href="#">1</a></li>
                        <li><a href="#">2</a></li>
                        <li><a href="#">3</a></li>
                        <li><a href="#">4</a></li>
                        <li><a href="#">5</a></li>
                        <li><a href="#">6</a></li>
                        <li><a href="#">7</a></li>
                        <li><a href="#">8</a></li>
                        <li><a href="#">9</a></li>
                        <li><a href="#">10</a></li>
                        <li class="threeword"><a href="javascript:;">下一页</a></li>
                        <li class="threeword"><a href="javascript:;">末页</a></li>


                 */
                var lis = "";

                var fristPage = '<li onclick="javascipt:load(1)"><a href="javascript:void(0)">首页</a></li>';

                //计算上一页的页码
                var beforeNum =  page.currentPage - 1;
                if(beforeNum <= 0){
                    beforeNum = 1;
                }

                var beforePage = '<li  onclick="javascipt:load('+beforeNum+')" class="threeword"><a href="javascript:void(0)">上一页</a></li>';

                lis += fristPage;
                lis += beforePage;
                //1.2 展示分页页码
                /*
                    1.一共展示10个页码，能够达到前5后4的效果
                    2.如果前边不够5个，后边补齐10个
                    3.如果后边不足4个，前边补齐10个
                */

                // 定义开始位置begin,结束位置 end
                var begin; // 开始位置
                var end ; //  结束位置


                //1.要显示10个页码
                if(page.totalPage < 10){
                    //总页码不够10页

                    begin = 1;
                    end = page.totalPage;
                }else{
                    //总页码超过10页

                    begin = page.currentPage - 5 ;
                    end = page.currentPage + 4 ;

                    //2.如果前边不够5个，后边补齐10个
                    if(begin < 1){
                        begin = 1;
                        end = begin + 9;
                    }

                    //3.如果后边不足4个，前边补齐10个
                    if(end > page.totalPage){
                        end = page.totalPage;
                        begin = end - 9 ;
                    }
                }


                for (var i = begin; i <= end ; i++) {
                    var li;
                    //判断当前页码是否等于i
                    if(page.currentPage == i){

                        li = '<li class="curPage" onclick="javascipt:load('+i+')"><a href="javascript:void(0)">'+i+'</a></li>';

                    }else{
                        //创建页码的li
                        li = '<li onclick="javascipt:load('+i+')"><a href="javascript:void(0)">'+i+'</a></li>';
                    }
                    //拼接字符串
                    lis += li;
                }

                /* for (var i = 1; i <= pb.totalPage ; i++) {
                     var li;
                     //判断当前页码是否等于i
                     if(pb.currentPage == i){

                         li = '<li class="curPage" onclick="javascipt:load('+cid+','+i+')"><a href="javascript:void(0)">'+i+'</a></li>';

                     }else{
                         //创建页码的li
                         li = '<li onclick="javascipt:load('+cid+','+i+')"><a href="javascript:void(0)">'+i+'</a></li>';
                     }
                     //拼接字符串
                     lis += li;
                 }*/
                var lastPage = '<li class="threeword"><a href="javascript:;">末页</a></li>';
                var nextPage = '<li class="threeword"><a href="javascript:;">下一页</a></li>';

                lis += nextPage;
                lis += lastPage;


                //将lis内容设置到 ul
                $("#pageNum").html(lis);



                /*
                    <li class="fshow">
                        <h1><img src="./tu/yanfa.jpg" alt=""></h1>
                        <h2>name</h2>
                        <h3>内容</h3>
                        <h4>发圈的照片</h4>
                        <button class="comment">评论</button>
                        <input type="text" class="gossip" placeholder="评论">
                        <h5 class="love">❤</h5>
                        <h6 class="loveNumber">0</h6>
                        <ul class="gossips">
                            评论区
                        <li>好秀啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊</li>
                        </ul>
                    </li>

                 */

                //2.列表数据展示
                var moment_lis = "";



                if (page.list != null){

                    for (var i = 0; i < page.list.length; i++) {
                        //获取
                        var momentsdetail = page.list[i];

                        var li =    '<h1><img src="'+momentsdetail.avatarImg+'" alt=""></h1>';

                        li +=      '<h2>'+momentsdetail.userName+'</h2>';//为什么undefined


                        li += '<h3>'+momentsdetail.content+'</h3>';

                        if(momentsdetail.firstImg != ''){
                            li += '<h4><img src="/RDCChat1/'+momentsdetail.firstImg+'" alt=""></h4>';
                        }
                        if(momentsdetail.secondImg != ''){
                            li += '<h4><img src="/RDCChat1/'+momentsdetail.secondImg+'" alt=""></h4>';
                        }
                        if(momentsdetail.thirdImg != ''){
                            li += '<h4><img src="/RDCChat1/'+momentsdetail.thirdImg+'" alt=""></h4>';
                        }

                        var it = Format(momentsdetail.issueTime,"yyyy-MM-dd");

                        li += '<h3>'+it+'</h3>';


                        li +=   '<button class="comment" value="'+momentsdetail.momentId+'">评论</button>'+
                            '<input type="text" class="gossip" placeholder="评论">';

                        if (momentsdetail.ifLike){
                            li +=  '<h5><a style="color: pink" href="javascript:likeIt('+momentsdetail.momentId+','+momentsdetail.ifLike+','+'this'+','+page.currentPage+');" class="love">❤</a></h5>';
                        }else {
                            li +=  '<h5><a style="color:rgba(0, 0, 0, 0.418)" href="javascript:likeIt('+momentsdetail.momentId+','+momentsdetail.ifLike+','+'this'+','+page.currentPage+');" class="love">❤</a></h5>';
                        }


                            li += '<h6 class="loveNumber">'+momentsdetail.likeNumber+'</h6>';

                        li +=    '<ul class="gossips">'+
                                        '评论区';
                        if (momentsdetail.comment!=null){//有评论
                            for (var j = 0; j < momentsdetail.comment.length; j++){
                                var comments = momentsdetail.comment[j];
                                li += '<li>'+comments+'</li>';
                            }
                        }
                        li +=   '</ul>';
                        moment_lis += li;
                    }
                    $(".fshow").html(moment_lis);
                } else {
                    $(".fshow").html("暂时还没有朋友圈喔！");
                }

                var love=document.getElementsByClassName('love');
                var comment=document.getElementsByClassName('comment');

                for(var i=0;i<love.length;i++){
                    /*var isLove=0;//是否点赞
                    var numLove=0;
                    love[i].onclick=function(){
                        if(isLove==0){
                            isLove=1;
                            numLove+=1;
                            this.nextElementSibling.innerHTML=numLove;
                            this.style.color='pink';
                        }else{
                            this.nextElementSibling.innerHTML=numLove-1;
                            this.style.color='rgba(0, 0, 0, 0.418)';
                            isLove=0;
                        }
                    }*/
                    comment[i].onclick=function(){
                        var li = document.createElement('li');
                        var commentValue=this.nextElementSibling.value;
                        var momentId = this.value;
                        li.innerHTML=commentValue;
                        var abc=this.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling;
                        if(commentValue!=''){

                            $.post("Moments/postComment",{commentValue:commentValue,momentId:momentId},function (info){
                                if (info.flag){
                                    alert("评论成功！");
                                    abc.insertBefore(li, abc.children[0]);
                                }else {
                                    alert("评论失败！");
                                }
                            });
                        }
                    }
                }

                //定位到页面顶部
                window.scrollTo(0,0);
            });

        }

        function likeIt(momentId,ifLike,obj,currentPage){

            //已经点赞了
            if (ifLike){
                $.post("Moments/cancelLikeIt",{momentId:momentId},function (info) {
                    if (info.flag){
                        alert("取消点赞成功！");
                        obj.style.color='rgba(0, 0, 0, 0.418)';
                        load(currentPage);
                    }else {
                        alert("取消点赞失败！");
                    }
                });
            }else {//还没点赞
                $.post("Moments/likeIt",{momentId:momentId},function (info) {
                    if (info.flag){
                        alert("点赞成功！");
                        obj.style.color='pink';
                        load(currentPage);
                    }else {
                        alert("点赞失败！");
                    }
                });
            }
        }



        function postComment(momentId){


        }


    </script>

    <!-- css -->
    <style>
        /* 朋友圈 */
        .pageNum li{
            list-style-type: none;
        }

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
        .friendShows li .gossip{
            width: 250px;
            height: 30px;
            border-radius: 10px;
            outline: none;
            border: 2px solid pink;
            box-shadow: rgb(184, 112, 124) 2px 2px 5px;
            background-color: #fff;
        }
        .friendShows li .comment{
            width: 55px;
            line-height: 30px;
            border-radius: 10px;
            color: #fff;
            background-color: rgb(245, 121, 189);
            border: 1px solid pink;
            box-shadow: 2px 2px 5px rgba(54, 34, 38, 0.527);
        }
        .friendShows li h5{
            width: 30px;
            line-height: 30px;
            font-size: 25px;
            text-align: center;
            color: rgba(0, 0, 0, 0.418);
            border-radius: 10px;
            background-color: rgba(235, 108, 167, 0.24);
            float: right;
            margin-top: 5px;
            margin-left: -40px;
            cursor: pointer;
        }
        .friendShows li h6{
            width: 15px;
            height: 15px;
            font-size: 15px;
            float: right;
            margin-top: 15px;
            color: #888;
            cursor: pointer;
            margin-right: -8px;
        }

        /*分页样式*/
        .pageNum {
            width: 100%;
            overflow: hidden;
        }

        .pageNum ul li {
            width: 40px;
            height: 40px;
            float: left;
            border: 1px solid #eee;
            margin-right: 10px;
            text-align: center;
            line-height: 40px;
        }
        .pageNum ul li.curPage {
            background-color: #e5a6e2;
        }
        .pageNum ul li a {
            width: 100%;
            height: 100%;
            color: #000;
            font-size: 14px;
        }
        .pageNum ul .threeword {
            width: 75px;
        }

        .page_num_inf {
            color: #878787;
            font-size: 19px;
            margin-bottom: 20px;
        }
        .page_num_inf i {
            float: left;
            width: 4px;
            background-color: #878787;
            height: 20px;
            margin-top: 5px;
            margin-right: 10px;
        }
        .page_num_inf span {
            color: #ff4848;
        }
    </style>

</head>
<body>

<%-- 引入头部 --%>
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
                            <li><a href="${pageContext.request.contextPath}//MyFriends/actionCreateGroup">发起群聊</a></li>
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

<input hidden="hidden" value="${user.userId}" id="userId">

<!-- 朋友圈层 -->
<div class="FShowBox" style="display: block;">
    <ul class="friendShows">
        <li class="fshow">
            <%--<h1><img src="./tu/yanfa.jpg" alt=""></h1>
            <h2>name</h2>
            <h3>内容</h3>
            <h4>发圈的照片</h4>
            <button class="comment">评论</button>
            <input type="text" class="gossip" placeholder="评论">
            <h5 class="love">❤</h5>
            <h6 class="loveNumber">0</h6>
            <ul class="gossips">
                评论区
                <li>好秀啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊</li>
            </ul>--%>
        </li>
    </ul>
    <br>
    <br>
    <div class="page_num_inf">
        <i></i> 共
        <span id="totalPage"><%--12--%></span>页<span id="totalCount"><%--132--%></span>条
    </div>
    <div class="pageNum">
        <ul id="pageNum">
            <%--<li><a href="">首页</a></li>
            <li class="threeword"><a href="#">上一页</a></li>
            <li class="curPage"><a href="#">1</a></li>
            <li><a href="#">2</a></li>
            <li><a href="#">3</a></li>
            <li><a href="#">4</a></li>
            <li><a href="#">5</a></li>
            <li><a href="#">6</a></li>
            <li><a href="#">7</a></li>
            <li><a href="#">8</a></li>
            <li><a href="#">9</a></li>
            <li><a href="#">10</a></li>
            <li class="threeword"><a href="javascript:;">下一页</a></li>
            <li class="threeword"><a href="javascript:;">末页</a></li>--%>
        </ul>
    </div>
    <br><br><br>
</div>

</body>



</html>