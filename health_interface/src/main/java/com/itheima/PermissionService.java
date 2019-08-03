package com.itheima;

import com.itheima.entity.PageResult;
import com.itheima.pojo.Permission;
import com.itheima.pojo.QueryPageBean;

import java.util.List;

public interface PermissionService {
    PageResult<Permission> findPage(QueryPageBean queryPageBean);

    void add(Permission permission);

    void deleteById(Integer id);

    Permission findById(Integer id);

    void edit(Permission permission);

    List<Permission> findAll();

}
