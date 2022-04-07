package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    UserDao userDao=new UserDaoImpl();
    @Override
    public boolean registUser(User user) {
        User byUsername = userDao.findByUsername(user.getUsername());
        if(byUsername == null){
            user.setCode(UuidUtil.getUuid());
            user.setStatus("N");
            userDao.save(user);
            String text="<a href='http://localhost:8080/travel/user/active?code="+user.getCode()+"'>点击激活《黑马旅游网》</a>";
            MailUtils.sendMail(user.getEmail(),text,"激活邮件");
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean activeUser(String code) {
        User user=userDao.findByCode(code);
        if(user==null){
            return false;
        }else{
            userDao.updateUser(code);
            return true;
        }
    }

    @Override
    public User loginUser(User user) {
        User user_sjk=userDao.loginUser(user.getUsername(),user.getPassword());
        return user_sjk;
    }
}
