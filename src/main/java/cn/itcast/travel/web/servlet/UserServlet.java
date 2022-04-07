package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    //激活
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        UserService userService=new UserServiceImpl();
        boolean flag=userService.activeUser(code);
        String msg=null;
        if(flag){
//            激活成功;
            msg="激活成功，请<a href='/travel/login.html'>登录</a>";
        }else{
//            激活失败;
            msg="激活失败,请联系管理员";
        }
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(msg);
    }
    //退出
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath()+"/login.html");
    }
//查找用户，session
    public void findUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User sjk_user = (User)request.getSession().getAttribute("user");
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.writeValue(response.getWriter(),sjk_user);
    }
    //登录
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        User user=new User();
        try {
            BeanUtils.populate(user,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        UserService userService=new UserServiceImpl();
        User user_sjk=userService.loginUser(user);
        ResultInfo resultInfo=new ResultInfo();
        if(user_sjk == null){
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("用户名或密码错误");
        }
        if(user_sjk != null && !user_sjk.getStatus().equals("Y")){
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("账号未激活");
        }
        if(user_sjk != null && user_sjk.getStatus().equals("Y")){
            resultInfo.setFlag(true);
            request.getSession().setAttribute("user",user_sjk);
        }
        ObjectMapper objectMapper=new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        objectMapper.writeValue(response.getWriter(),resultInfo);
    }

    //注册用户
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String)session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if(check == null || !checkcode_server.equalsIgnoreCase(check)){
            ResultInfo resultInfo=new ResultInfo();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误");
            ObjectMapper objectMapper=new ObjectMapper();
            String s = objectMapper.writeValueAsString(resultInfo);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(s);
            return;
        }
        Map<String, String[]> parameterMap = request.getParameterMap();
        User user=new User();
        try {
            BeanUtils.populate(user,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        UserService userService=new UserServiceImpl();
        boolean b = userService.registUser(user);
        ResultInfo resultInfo=new ResultInfo();
        if(b){
            resultInfo.setFlag(true);
        }else{
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("注册失败");
        }
        ObjectMapper objectMapper=new ObjectMapper();
        String s = objectMapper.writeValueAsString(resultInfo);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(s);
    }
}
