package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckItemDao {

    void addItem(CheckItem checkItem);

    Page<CheckItem> findAllByCondition(String queryString);

    long findCountByCheckItemid(Integer id);

    void deleteById(Integer id);

    CheckItem findById(Integer id);

    void update(CheckItem checkItem);

    void editcheckitem(CheckItem checkItem);

    List<CheckItem> findAll();
}
