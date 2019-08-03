package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.RoleService;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.QueryPageBean;
import com.itheima.pojo.Role;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Reference
    private RoleService roleService;

    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult<Role> pageResult = roleService.findPage(queryPageBean);
        return new Result(true, MessageConstant.GET_ROLELIST_SUCCESS, pageResult);
    }
    @PostMapping("/add")
    public Result add(@RequestBody Role role, @RequestParam("permissionIds") Integer[] permissionIds, @RequestParam("menuIds") Integer[] menuIds) {
        try {
            roleService.add(role, permissionIds,menuIds);
            return new Result(true, MessageConstant.ADD_ROLE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_ROLE_FAIL);
        }
    }
    @PostMapping("/deleteById")
    public Result deleteById(Integer id) {
        try {
            roleService.deleteById(id);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_ROLE_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_ROLE_SUCCESS);
    }

    @GetMapping("/findById")
    public Result findById(Integer id) {
        Role role = roleService.findById(id);
        return new Result(true, MessageConstant.QUERY_ROLE_SUCCESS, role);
    }

    @PostMapping("/edit")
    public Result edit(@RequestBody Role role){
        roleService.edit(role);
        return new Result(true, MessageConstant.EDIT_ROLE_SUCCESS);
    }
}
