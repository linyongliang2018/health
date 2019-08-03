package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.itheima.UserService;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.UserDao;
import com.itheima.entity.PageResult;
import com.itheima.pojo.QueryPageBean;
import com.itheima.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public PageResult<User> findPage(QueryPageBean queryPageBean) {
        if (!StringUtil.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%" + queryPageBean + "%");
        }
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<User> permissions = userDao.findAllByCondition(queryPageBean.getQueryString());
        return new PageResult<User>(permissions.getTotal(), permissions.getResult());
    }

    @Override
    public void add(User user, Integer[] roleIds) {
        userDao.add(user);
        Integer id = user.getId();
        System.out.println(id);
        if (roleIds != null && roleIds.length > 0) {
            for (Integer roleId : roleIds) {
                userDao.addUserAndRole(id, roleId);
            }
        }
    }

    @Override
    public void deleteById(Integer id) {
        long count = userDao.findCountByUserId(id);
        if (count>0) {
            throw new RuntimeException(MessageConstant.USER_WAS_USED);
        }
        userDao.deleteById(id);
    }

    @Override
    public User findById(Integer id) {
        return userDao.findById(id);
    }

    @Override
    public List<Integer> findRoleIdsByUserId(Integer userId) {
        return userDao.findRoleIdsByUserId(userId);
    }

    @Override
    public void edit(User user, Integer[] roleIds) {
        userDao.edit(user);
        Integer userId = user.getId();
        userDao.deleteAssociation(userId);
        if (roleIds != null && roleIds.length > 0) {
            for (Integer checkitemId : roleIds) {
                userDao.addUserAndRole(userId, checkitemId);
            }
        }
    }


}
