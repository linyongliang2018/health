package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.CheckItemService;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.QueryPageBean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
    @Reference
    private CheckItemService checkItemService;

    @PostMapping("/add.do")
    public Result addCheckItemService(@RequestBody CheckItem checkItem) {
        checkItemService.addItem(checkItem);
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    @RequestMapping("/findPage")
    @PreAuthorize("hasAnyAuthority('CHECKITEM_QUERY')")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = checkItemService.findPage(queryPageBean);
        return pageResult;
    }

    @PostMapping("/deleteById")
    public Result deleteById(Integer id) {
        try {
            checkItemService.deleteById(id);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    @PostMapping("/findById.do")
    public Result findById(Integer id) {
        CheckItem checkItem = checkItemService.findById(id);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItem);
    }

    @PostMapping("/update.do")
    public Result update(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.update(checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }

    @GetMapping("/findAll")
    public Result findAll() {
        List<CheckItem> checkItems = checkItemService.findAll();
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItems);
    }
}
