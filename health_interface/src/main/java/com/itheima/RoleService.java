package com.itheima;

import com.itheima.entity.PageResult;
import com.itheima.pojo.QueryPageBean;
import com.itheima.pojo.Role;

import java.util.List;

public interface RoleService {
    PageResult<Role> findPage(QueryPageBean queryPageBean);

    void add(Role role, Integer[] permissionIds, Integer[] menuIds);

    void deleteById(Integer id);

    Role findById(Integer id);

    void edit(Role role);

    List<Role> findAll();

}
