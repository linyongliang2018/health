package com.itheima;

import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.QueryPageBean;

import java.util.List;

public interface CheckItemService {
    void addItem(CheckItem checkItem);

    PageResult findPage(QueryPageBean queryPageBean);

    void deleteById(Integer id);

    CheckItem findById(Integer id);

    void update(CheckItem checkItem);

    List<CheckItem> findAll();
}
