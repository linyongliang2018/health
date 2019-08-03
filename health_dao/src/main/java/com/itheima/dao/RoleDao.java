package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Role;
import org.apache.ibatis.annotations.Param;

public interface RoleDao {
    Page<Role> findAllByCondition(String queryString);

    void add(Role role);

    void addRoleAndPermission(@Param("roleId") Integer roleId, @Param("permissionId") Integer permissionId);

    void addRoleAndMenu(@Param("roleId") Integer roleId,@Param("menuId")  Integer menuId);

    long findCountByRoleId(Integer id);

    void deleteById(Integer id);

    Role findById(Integer id);

    void edit(Role role);
}
