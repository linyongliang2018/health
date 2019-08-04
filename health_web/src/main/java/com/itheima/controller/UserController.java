package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.UserService;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.QueryPageBean;
import com.itheima.pojo.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Reference
    private UserService userService;

    @GetMapping("/getLoginUsername")
    public Result getLoginUsername() {
        // SecurityContextHolder, 存入上下文的内容，
        // getAuthentication.getPrincipal() 获取认证信息userDetail
        //getAuthentication().getName(); 获取登陆的用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        username = userDetails.getUsername();
        return new Result(true, MessageConstant.GET_USERNAME_SUCCESS, username);
    }

    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult<User> pageResult = userService.findPage(queryPageBean);
        return new Result(true, MessageConstant.GET_USER_SUCCESS, pageResult);
    }

    @PostMapping("/add")
    public Result add(@RequestBody User user, Integer[] roleIds) {
        try {
            userService.add(user, roleIds);
            return new Result(true, MessageConstant.ADD_USER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_USER_FAIL);
        }
    }

    @PostMapping("/deleteById")
    public Result deleteById(Integer id) {
        try {
            userService.deleteById(id);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_USER_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_USER_SUCCESS);
    }

    @GetMapping("/findById")
    public Result findById(Integer id) {
        User user = userService.findById(id);
        return new Result(true, MessageConstant.QUERY_USER_SUCCESS, user);
    }

    @GetMapping("/findroleIdsByUserId")
    public Result findCheckItemIdsByCheckGroupId(@RequestParam("id") Integer userId) {
        List<Integer> roleIds = userService.findRoleIdsByUserId(userId);
        return new Result(true, MessageConstant.QUERY_ROLEIDBYUSERID_SUCCESS, roleIds);
    }

    @PostMapping("/edit")
    public Result edit(@RequestBody User user, Integer[] roleIds) {
        userService.edit(user, roleIds);
        return new Result(true, MessageConstant.EDIT_USER_SUCCESS);
    }
}
