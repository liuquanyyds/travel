package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    public User findByUsername(String username);

    public void save(User user);

    User findByCode(String code);

    void updateUser(String code);

    User loginUser(String username, String password);
}
