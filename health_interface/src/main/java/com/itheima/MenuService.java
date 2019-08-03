package com.itheima;

import com.itheima.entity.PageResult;
import com.itheima.pojo.Menu;
import com.itheima.pojo.QueryPageBean;

public interface MenuService {
    PageResult<Menu> findPage(QueryPageBean queryPageBean);

    void add(Menu menu);

    void deleteById(Integer id);

    Menu findById(Integer id);

    void edit(Menu menu);
}
