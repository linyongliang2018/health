package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.itheima.RoleService;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.RoleDao;
import com.itheima.entity.PageResult;
import com.itheima.pojo.QueryPageBean;
import com.itheima.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = RoleService.class)
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Override
    public PageResult<Role> findPage(QueryPageBean queryPageBean) {
        if (!StringUtil.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%" + queryPageBean + "%");
        }
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<Role> permissions = roleDao.findAllByCondition(queryPageBean.getQueryString());
        return new PageResult<Role>(permissions.getTotal(), permissions.getResult());
    }
    @Transactional
    @Override
    public void add(Role role, Integer[] permissionIds, Integer[] menuIds) {
        roleDao.add(role);
        Integer roleId = role.getId();
        if (roleId != null && permissionIds.length > 0) {
            for (Integer permissionId : permissionIds) {
                roleDao.addRoleAndPermission(roleId, permissionId);
            }
        }
        if (roleId != null && menuIds.length > 0) {
            for (Integer menuId : menuIds) {
                roleDao.addRoleAndMenu(roleId,menuId);
            }
        }
    }

    @Override
    public void deleteById(Integer id) {
        long count = roleDao.findCountByRoleId(id);
        if (count>0) {
            throw new RuntimeException(MessageConstant.ROLE_WAS_USED);
        }
        roleDao.deleteById(id);
    }

    @Override
    public Role findById(Integer id) {
        return roleDao.findById(id);
    }

    @Override
    public void edit(Role role) {
        roleDao.edit(role);
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }
}
