package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckGroupDao {
    void add(CheckGroup checkGroup);

    void addCheckGroupAndCheckItem(@Param("id") Integer id, @Param("checkitemId") Integer checkitemId);

    Page<CheckGroup> findAllByCondition(String queryString);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer checkGroupId);

    void edit(CheckGroup checkGroup);

    void deleteAssociation(@Param("id") Integer checkGroupId);

    List<CheckGroup> findAll();
}
