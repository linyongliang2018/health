package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    User findByUsername(String username);

    Page<User> findAllByCondition(String queryString);

    void add(User user);

    void addUserAndRole(@Param("id") Integer id, @Param("roleId")Integer roleId);

    long findCountByUserId(Integer id);

    void deleteById(Integer id);

    User findById(Integer id);

    List<Integer> findRoleIdsByUserId(Integer userId);

    void edit(User user);

    void deleteAssociation(Integer userId);
}
