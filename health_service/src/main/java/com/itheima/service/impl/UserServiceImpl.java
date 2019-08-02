package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.UserService;
import com.itheima.dao.UserDao;
import com.itheima.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;

@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
