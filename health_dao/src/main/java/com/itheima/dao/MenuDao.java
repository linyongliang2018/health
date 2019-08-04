package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Menu;

import java.util.List;

public interface MenuDao {
    Page<Menu> findAllByCondition(String queryString);

    void add(Menu menu);

    long findCountByMenuId(Integer id);

    void deleteById(Integer id);

    Menu findById(Integer id);

    void edit(Menu menu);

    List<Menu> findAll();

    List<Menu> queryByUsername(String username);
}
