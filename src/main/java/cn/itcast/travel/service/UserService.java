package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {

    public boolean registUser(User user);

    boolean activeUser(String code);

    User loginUser(User user);
}
