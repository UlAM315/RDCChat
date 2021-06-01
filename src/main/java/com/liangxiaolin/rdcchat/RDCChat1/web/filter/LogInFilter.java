package com.liangxiaolin.rdcchat.RDCChat1.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class LogInFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //强制转换
        HttpServletRequest request = (HttpServletRequest) req;
        //获取资源请求路径
        String uri = request.getRequestURI();
        //判断是否包含登录相关的资源路径
        if (uri.contains("/LogIn.jsp") || uri.contains("/Users/") || uri.contains("Regist.jsp") || uri.contains("/css/") || uri.contains("/js/") || uri.contains("/fonts/") || uri.contains("CheckCodeServlet")){
            //包含，用户想登录 放行
            chain.doFilter(req, resp);
        } else {
            //不包含，需要验证用户是否登录
            //从session中获取user
            Object user = request.getSession().getAttribute("user");
            if (user != null){
                //登陆了，放行
                chain.doFilter(req,resp);
            }else {
                //没有登录。跳转登录页面
                request.setAttribute("login_msg","您尚未登陆，请先登录喔！");
                request.getRequestDispatcher("/LogIn.jsp").forward(request,resp);
            }
        }

    }
}
