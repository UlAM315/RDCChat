package com.liangxiaolin.rdcchat.RDCChat1.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liangxiaolin.rdcchat.RDCChat1.entity.*;
import com.liangxiaolin.rdcchat.RDCChat1.service.UsersService;
import com.liangxiaolin.rdcchat.RDCChat1.service.impl.UsersServiceImpl;
import com.liangxiaolin.rdcchat.RDCChat1.util.MailUtils;
import com.liangxiaolin.rdcchat.RDCChat1.util.Md5Util;
import com.liangxiaolin.rdcchat.RDCChat1.util.SimpleUtils;
import com.liangxiaolin.rdcchat.RDCChat1.util.UuidUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@WebServlet("/Users/*")
public class UsersServlet extends BaseServlet{

    UsersService service = new UsersServiceImpl();

    public void checkUserName(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //校验用户名
        String userName = request.getParameter("userName");
        ResultInfo info = new ResultInfo();
        if (service.findByUserName(userName)) {
            //用户名存在
            info.setFlag(false);
            info.setErrorMsg("用户名存在!");
        }else {
            info.setFlag(true);
        }
        writeValue(info,response);
    }

    public void checkEmail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //校验邮箱
        String email = request.getParameter("email");
        ResultInfo info = new ResultInfo();
        if (service.findByEmail(email)){
            //邮箱存在
            info.setFlag(false);
            info.setErrorMsg("邮箱存在!");
        } else {
            info.setFlag(true);
        }
            //将info对象序列化为json
        writeValue(info,response);
    }

    public void checkTelephone(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //校验手机号
        String telephone = request.getParameter("telephone");
        ResultInfo info = new ResultInfo();
        if (service.findByTelephone(telephone)){
            //手机号存在
            info.setFlag(false);
            info.setErrorMsg("手机号存在!");
        } else {
            info.setFlag(true);
        }
        //将info对象序列化为json
        writeValue(info,response);
    }

    public void checkIdNumber(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //校验微信号
        String idNumber = request.getParameter("idNumber");
        ResultInfo info = new ResultInfo();
        if (service.findByIdNumber(idNumber)){
            //微信号存在
            info.setFlag(false);
            info.setErrorMsg("微信号存在!");
        }else {
            info.setFlag(true);
        }
        //将info对象序列化为json
        writeValue(info,response);
    }

    public void regist(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //验证校验
        String check = request.getParameter("check");
        //从sesion中获取验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//为了保证验证码只能使用一次
        //比较
        if(!checkcode_server.equalsIgnoreCase(check)){
            //验证码错误
            //注册失败
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误!");
            //将info对象序列化为json
            writeValue(info,response);
            return;
        }
        //1.获取数据
        Map<String, String[]> map = request.getParameterMap();

        //2.封装对象
        Users user = new Users();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
            System.out.println(user);


        //3.调用service完成注册
        boolean flag = service.regist(user);
        System.out.println(flag);
        ResultInfo info = new ResultInfo();
        //4.响应结果
        if(flag){
            //注册成功
            info.setFlag(true);
            info.setErrorMsg("快去激活邮箱8!");
        }else{
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("注册失败!是不是邮箱填错了呢？");
        }
        //将info对象序列化为json
        writeValue(info,response);
    }

    public void upLoad(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        String name=null;
        // 验证请求是否满足要求（post 请求 / enctype 是否以multipart打头
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        // 如果不满足要求就立即结束对该请求的处理
        if (!isMultipart) {
            return;
        }
        try {
            // FileItem 是表单中的每一个元素的封装
            // 创建一个 FileItem 的工厂类
            FileItemFactory factory = new DiskFileItemFactory();
            // 创建一个文件上传处理器（装饰设计模式）
            ServletFileUpload upload = new ServletFileUpload(factory);
            // 解析请求
            List<FileItem> items = upload.parseRequest(request);
            System.out.println(items);

            //获取登录用户的信息
            //从session中获取登录用户
            Users user = (Users) request.getSession().getAttribute("user");

            for (FileItem item : items) {
                // 文件类型
                // 获取文件后缀名
                if (!item.isFormField()&&item.getName()!=null&&!"".equals(item.getName())){
                    if("avatarImg".equals(item.getFieldName())){  //存到头像数据库中
                        String imgtype = item.getName().substring(item.getName().lastIndexOf("."));
                        // 给文件重新命名防止重复
                        String imgName = UUID.randomUUID() + imgtype;
                        String path= request.getSession().getServletContext().getRealPath("/img");
                        //"D:/RDCChat1/src/main/webapp/img";
                        System.out.println(path);
                        // 将上传的文件保存到服务器
                        item.write(new File(path, imgName));

                        /*String classpath = this.getClass().getResource("/").getPath().replaceFirst("/", "");
                        String webappRoot = classpath.replaceAll("WEB-INF/classes/", "");
                        System.out.println("webappRoot:"+webappRoot);
                        // 将上传的文件保存到服务器
                        item.write(new File(webappRoot, imgName));*/

                        // 把服务器中的路径添加到数据库中
                        String sqlPath = null;
                        sqlPath = "img/" + imgName;
                        System.out.println("访问路径：" + sqlPath);
                    //存入信息
                    user.setAvatarImg(sqlPath);
                    }

                    if("backgroundImg".equals(item.getFieldName())){  //存到背景图数据库中
                        String imgtype = item.getName().substring(item.getName().lastIndexOf("."));
                        // 给文件重新命名防止重复
                        String imgName = UUID.randomUUID() + imgtype;

                        String path= request.getSession().getServletContext().getRealPath("/img");
                                //"D:/RDCChat1/src/main/webapp/img";
                        System.out.println(path);
                        // 将上传的文件保存到服务器
                        item.write(new File(path, imgName));

                        // 把服务器中的路径添加到数据库中
                        String sqlPath=null;
                        sqlPath = "img/" + imgName;
                        System.out.println("访问路径：" + sqlPath);
                    //存入信息
                    user.setBackgroundImg(sqlPath);
                    }
                }
            }
            if (service.saveImg(user)){
                //2.跳转主页面
                response.sendRedirect(request.getContextPath()+"/Main.jsp");
            } else {
                //提示信息
                request.setAttribute("upload_msg","上传失败！");//3.转发到本页面
                request.getRequestDispatcher("/Upload.jsp").forward(request,response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void active(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.获取激活码
        String code = request.getParameter("code");
        if(code != null){
            //2.调用service完成激活
            boolean flag = service.active(code);

            //3.判断标记
            String msg = null;
            if(flag){
                //激活成功
                msg = "激活成功，快回去登录8~</a>";
            }else{
                //激活失败
                msg = "激活失败喔！！";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }

    public void logIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("content-type","text/html;charset=utf-8");
        String remember = request.getParameter("remember");
        //1.获取用户名和密码数据
        Map<String, String[]> map = request.getParameterMap();
        //2.封装User对象
        Users user = new Users();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        String telephone = user.getTelephone();
        String userPassword = user.getUserPassword();

        Users logInUser  = service.logIn(user);
        System.out.println(logInUser);

        ResultInfo info = new ResultInfo();

        //4.判断用户对象是否为null
        if(logInUser == null){
            //用户名或密码错误
            info.setFlag(false);
            info.setErrorMsg("用户名密码或错误");
        }
        //5.判断用户是否激活
        if(logInUser != null && !"Y".equals(logInUser.getActiveStatus())){
            //用户尚未激活
            info.setFlag(false);
            info.setErrorMsg("您尚未激活，请激活!");
        }
        //6.判断登录成功
        if(logInUser != null && "Y".equals(logInUser.getActiveStatus())){
            request.getSession().setAttribute("user",logInUser);//登录成功标记
            System.out.println("success!");
            //登录成功
            info.setFlag(true);
            if ("true".equals(remember)){
                Cookie ck1 = new Cookie("remember",remember);
                ck1.setMaxAge(60*60);
                response.addCookie(ck1);
                Cookie ck2 = new Cookie("userPassword",userPassword);
                ck2.setMaxAge(60*60);
                response.addCookie(ck2);
                Cookie ck3 = new Cookie("telephone",telephone);
                ck3.setMaxAge(60*60);
                response.addCookie(ck3);
            }else {
                //清楚cookie
                Cookie[] cks = request.getCookies();
                if (cks != null){
                    for (Cookie ck : cks) {
                        ck.setMaxAge(0);
                        response.addCookie(ck);
                    }
                }
            }
        }

        //响应数据
        writeValue(info,response);
    }

    public void exit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(request.getSession().getAttribute("user") != null){//已经登录
            //1.销毁session
            request.getSession().invalidate();
        }
        //2.跳转登录页面
        response.sendRedirect(request.getContextPath()+"/LogIn.jsp");
    }

    public void forgetPassword(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ResultInfo info = new ResultInfo();
        //获取邮箱验证码
        String emailCode = SimpleUtils.getCheckCode();
        System.out.println(emailCode);
        //获取填入的邮箱
        String email = request.getParameter("email");
        //发送验证码
        if(MailUtils.sendMail(email,"您的邮箱验证码是："+emailCode+"   <RDCChat>","RDCChat修改密码邮箱验证码")){
            info.setFlag(true);
            //2.将emailCode存入session域
            request.getSession().setAttribute("emailCode",emailCode);
            //2.将email存入session域
            request.getSession().setAttribute("email",email);
            System.out.println(111);
        }else {
            info.setFlag(false);
        }
        System.out.println(info.isFlag());
        //将info对象序列化为json
        writeValue(info,response);
    }

    public void updateUserPassword(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ResultInfo info = new ResultInfo();
        //获取填入的邮箱和修改的密码
        String email = (String) request.getSession().getAttribute("email");
        String userPassword = request.getParameter("userPassword");
        System.out.println("邮箱："+email+"密码："+userPassword);
        //把所有session销毁
        request.getSession().invalidate();
        if(service.updateUserPassword(email,userPassword)){
            info.setFlag(true);
        }else {
            info.setFlag(false);
        }
        //将info对象序列化为json
        writeValue(info,response);
    }

    public void updateUserMessage(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //1.获取数据
        Map<String, String[]> map = request.getParameterMap();

        //2.封装对象
        Users user0 = new Users();
        try {
            BeanUtils.populate(user0,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        Users user = (Users) request.getSession().getAttribute("user");

        user.setUserName(user0.getUserName());
        user.setUserId(user0.getUserId());
        user.setTelephone(user0.getTelephone());
        user.setGender(user0.getGender());

        ResultInfo info = new ResultInfo();

        if (service.updateUserMessage(user)){
            info.setFlag(true);
            //1.销毁session
            request.getSession().invalidate();
            request.getSession().setAttribute("user",user);//登录成功标记
        }else {
            info.setFlag(false);
        }

        //响应数据
        writeValue(info,response);
    }

    public void managerLogIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //1.获取用户名和密码数据
        Map<String, String[]> map = request.getParameterMap();
        //2.封装User对象
        Manager manager = new Manager();
        try {
            BeanUtils.populate(manager,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        ResultInfo info = new ResultInfo();

        if(service.managerLogIn(manager)!=null){
            info.setFlag(true);
        }else {
            info.setFlag(false);
        }
        //响应数据
        writeValue(info,response);
    }

    public void blockFriend(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("accusedId");
        ResultInfo info = new ResultInfo();
        if (service.blockFriend(userId)){
            info.setFlag(true);
        }else {
            info.setFlag(false);
        }
        //响应数据
        writeValue(info,response);
    }

    public void managerMain(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<ReportMessage> reportMessage = service.searchReportMessage();
        if (reportMessage!=null &&!reportMessage.isEmpty()){
            //2.将friendMessage存入request域
            request.setAttribute("reportMessage",reportMessage);
        }
        //3.转发到查看好友信息页面
        request.getRequestDispatcher("/ManagerMain.jsp").forward(request,response);
    }
}
