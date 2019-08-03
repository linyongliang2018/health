package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.PermissionService;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.Result;
import com.itheima.pojo.Permission;
import com.itheima.pojo.QueryPageBean;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Reference
    private PermissionService permissionService;

    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult<Permission> pageResult = permissionService.findPage(queryPageBean);
        return new Result(true, MessageConstant.GET_PERMISSIONLIST_SUCCESS, pageResult);
    }
    //todo add
    @PostMapping("/add")
    public Result add(@RequestBody Permission permission){
        permissionService.add(permission);
        return new Result(true, MessageConstant.ADD_PERMISSION_SUCCESS);
    }
    //todo delete
    @PostMapping("/deleteById")
    public Result deleteById(Integer id) {
        try {
            permissionService.deleteById(id);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_PERMISSION_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_PERMISSION_SUCCESS);
    }
    @GetMapping("/findById")
    public Result findById(Integer id) {
        Permission permission = permissionService.findById(id);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, permission);
    }

    @PostMapping("/edit")
    public Result edit(@RequestBody Permission permission){
        permissionService.edit(permission);
        return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }
    @GetMapping("/findAll")
    public Result findAll() {
        List<Permission> permissions = permissionService.findAll();
        return new Result(true, MessageConstant.QUERY_PERMISSION_SUCCESS, permissions);
    }
}
