package com.itheima;

import com.itheima.entity.PageResult;
import com.itheima.pojo.Menu;
import com.itheima.pojo.QueryPageBean;

import java.util.List;

public interface MenuService {
    PageResult<Menu> findPage(QueryPageBean queryPageBean);

    void add(Menu menu);

    void deleteById(Integer id);

    Menu findById(Integer id);

    void edit(Menu menu);

    List<Menu> findAll();

    List<Menu> queryByUsername(String username);
}
