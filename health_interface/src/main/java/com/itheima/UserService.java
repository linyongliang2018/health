package com.itheima;

import com.itheima.entity.PageResult;
import com.itheima.pojo.QueryPageBean;
import com.itheima.pojo.User;

import java.util.List;

public interface UserService {
    User findByUsername(String username);

    PageResult<User> findPage(QueryPageBean queryPageBean);

    void add(User user, Integer[] roleIds);

    void deleteById(Integer id);

    User findById(Integer id);

    List<Integer> findRoleIdsByUserId(Integer userId);

    void edit(User user, Integer[] roleIds);
}
