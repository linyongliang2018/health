package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Permission;

public interface PermissionDao {
    Page<Permission> findAllByCondition(String queryString);

    void add(Permission permission);

    long findCountByPermissionId(Integer id);

    void deleteById(Integer id);

    Permission findById(Integer id);

    void edit(Permission permission);

}
