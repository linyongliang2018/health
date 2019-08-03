package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.MenuService;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.Result;
import com.itheima.pojo.Menu;
import com.itheima.pojo.QueryPageBean;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/menu")
public class MenuController {
    @Reference
    private MenuService menuService;

    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult<Menu> pageResult = menuService.findPage(queryPageBean);
        return new Result(true, MessageConstant.GET_MENULIST_SUCCESS, pageResult);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Menu menu){
        //todo 有外键前端传数据注意限制,待改进
        menuService.add(menu);
        return new Result(true, MessageConstant.ADD_PERMISSION_SUCCESS);
    }

    @PostMapping("/deleteById")
    public Result deleteById(Integer id) {
        try {
            menuService.deleteById(id);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_MENU_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_MENU_SUCCESS);
    }

    @GetMapping("/findById")
    public Result findById(Integer id) {
        Menu menu = menuService.findById(id);
        return new Result(true, MessageConstant.QUERY_MENU_SUCCESS, menu);
    }

    @PostMapping("/edit")
    public Result edit(@RequestBody Menu menu){
        menuService.edit(menu);
        return new Result(true, MessageConstant.EDIT_MENU_SUCCESS);
    }
}
