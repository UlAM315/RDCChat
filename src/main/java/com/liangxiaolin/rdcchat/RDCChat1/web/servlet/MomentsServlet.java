package com.liangxiaolin.rdcchat.RDCChat1.web.servlet;

import com.liangxiaolin.rdcchat.RDCChat1.entity.*;
import com.liangxiaolin.rdcchat.RDCChat1.service.MomentsService;
import com.liangxiaolin.rdcchat.RDCChat1.service.MyFriendsService;
import com.liangxiaolin.rdcchat.RDCChat1.service.impl.MomentsServiceImpl;
import com.liangxiaolin.rdcchat.RDCChat1.service.impl.MyFriendsServiceImpl;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@WebServlet("/Moments/*")
public class MomentsServlet extends BaseServlet {

    MomentsService service = new MomentsServiceImpl();

    public void issueMoment(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        response.setContentType("application/json;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");


        //获取登录用户的信息
        //从session中获取登录用户
        Users user = (Users) request.getSession().getAttribute("user");

        //2.封装Moment对象
        Moment moment = new Moment();
        moment.setUserId(user.getUserId());
        moment.setIssueTime(new Date());

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

            // 文件类型
            for (FileItem item : items) {
                // 获取文件后缀名
                if (!item.isFormField() && item.getName() != null && !"".equals(item.getName())) {
                    if ("firstImg".equals(item.getFieldName())) {  //存到第一张照片数据库中
                        String imgtype = item.getName().substring(item.getName().lastIndexOf("."));
                        // 给文件重新命名防止重复
                        String imgName = UUID.randomUUID() + imgtype;
                        String path = request.getSession().getServletContext().getRealPath("/img");
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
                        moment.setFirstImg(sqlPath);
                    }

                    if ("secondImg".equals(item.getFieldName())) {  //存到第二张照片数据库中
                        String imgtype = item.getName().substring(item.getName().lastIndexOf("."));
                        // 给文件重新命名防止重复
                        String imgName = UUID.randomUUID() + imgtype;

                        String path = request.getSession().getServletContext().getRealPath("/img");
                        //"D:/RDCChat1/src/main/webapp/img";
                        System.out.println(path);
                        // 将上传的文件保存到服务器
                        item.write(new File(path, imgName));

                        // 把服务器中的路径添加到数据库中
                        String sqlPath = null;
                        sqlPath = "img/" + imgName;
                        System.out.println("访问路径：" + sqlPath);
                        //存入信息
                        moment.setSecondImg(sqlPath);
                    }

                    if ("thirdImg".equals(item.getFieldName())) {  //存到第二张照片数据库中
                        String imgtype = item.getName().substring(item.getName().lastIndexOf("."));
                        // 给文件重新命名防止重复
                        String imgName = UUID.randomUUID() + imgtype;

                        String path = request.getSession().getServletContext().getRealPath("/img");
                        //"D:/RDCChat1/src/main/webapp/img";
                        System.out.println(path);
                        // 将上传的文件保存到服务器
                        item.write(new File(path, imgName));

                        // 把服务器中的路径添加到数据库中
                        String sqlPath = null;
                        sqlPath = "img/" + imgName;
                        System.out.println("访问路径：" + sqlPath);
                        //存入信息
                        moment.setThirdImg(sqlPath);
                    }
                }

                //1.获取非文件数据
                if (item.isFormField()) {
                    if ("content".equals(item.getFieldName())) {
                        //解决乱码问题
                        String content = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
                        moment.setContent(content);
                    }
                    if ("ifOpen".equals(item.getFieldName())) {
                        moment.setIfOpen(item.getString());
                    }
                }

            }

            System.out.println(moment);

            if (service.issueMoment(moment)) {
                //提示信息
                request.setAttribute("issueMoment_msg", "发布成功！");//3.转发到本页面
                request.getRequestDispatcher("/IssueMoment.jsp").forward(request, response);
            } else {
                //提示信息
                request.setAttribute("issueMoment_msg", "发布失败！");//3.转发到本页面
                request.getRequestDispatcher("/IssueMoment.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getMoments(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Users user = (Users) request.getSession().getAttribute("user");

        if (user != null) {
            //1.接受参数
            String currentPageStr = request.getParameter("currentPage");
            int userId = user.getUserId();
            //处理
            int currentPage = 0;//当前页码，如果不传递，则默认为第一页
            if (currentPageStr != null && currentPageStr.length() > 0) {
                currentPage = Integer.parseInt(currentPageStr);
            } else {
                currentPage = 1;
            }

            //3. 调用service查询Page对象
            Page<MomentsDetail> page = service.getMoments(currentPage,userId);

            System.out.println(page);

            if (page != null) {
                //有朋友圈
                //将page对象序列化为json，返回
                writeValue(page, response);
            }

        }
    }

    public void cancelLikeIt(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取momentId
        String momentId = request.getParameter("momentId");
        //获取userId
        Users user = (Users) request.getSession().getAttribute("user");
        if (user != null){
            int userId = user.getUserId();
            ResultInfo info = new ResultInfo();
            if (service.cancelLikeIt(userId,momentId)){
                info.setFlag(true);
            }else {
                info.setFlag(false);
            }
            writeValue(info,response);
        }

    }

    public void likeIt(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取momentId
        String momentId = request.getParameter("momentId");
        //获取userId
        Users user = (Users) request.getSession().getAttribute("user");
        if (user != null){
            int userId = user.getUserId();
            ResultInfo info = new ResultInfo();
            if (service.likeIt(userId,momentId)){
                info.setFlag(true);
            }else {
                info.setFlag(false);
            }
            writeValue(info,response);
        }
    }

    public void postComment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取momentId
        String momentId = request.getParameter("momentId");
        //获取content
        String content = request.getParameter("commentValue");
        //获取userId
        Users user = (Users) request.getSession().getAttribute("user");
        if (user != null){
            int userId = user.getUserId();
            ResultInfo info = new ResultInfo();
            if (service.postComment(userId,momentId,content)){
                info.setFlag(true);
            }else {
                info.setFlag(false);
            }
            writeValue(info,response);
        }
    }
}
