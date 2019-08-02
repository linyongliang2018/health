package com.itheima;

import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.QueryPageBean;

import java.util.List;

public interface CheckGroupService {
    void add(CheckGroup checkGroup, Integer[] checkitemIds);


    PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer checkGroupId);

    void edit(CheckGroup checkGroup, Integer[] checkitemIds);

    List<CheckGroup> findAll();

}
