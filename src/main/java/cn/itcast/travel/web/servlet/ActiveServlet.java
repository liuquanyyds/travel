package cn.itcast.travel.web.servlet;

import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/activeServlet")
public class ActiveServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        UserService userService=new UserServiceImpl();
        boolean flag=userService.activeUser(code);
        String msg=null;
        if(flag){
//            激活成功;
            msg="激活成功，请<a href='login.html'>登录</a>";
        }else{
//            激活失败;
            msg="激活失败,请联系管理员";
        }
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(msg);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }
}
