package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.CheckGroupService;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.QueryPageBean;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    @Reference
    private CheckGroupService checkGroupService;

    @PostMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
        try {
            checkGroupService.add(checkGroup, checkitemIds);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
    }

    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult<CheckGroup> pageResult = checkGroupService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, pageResult);
    }

    @GetMapping("/findById")
    public Result findById(Integer id) {
        CheckGroup checkGroup = checkGroupService.findById(id);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroup);
    }

    @GetMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(@RequestParam("id") Integer checkGroupId) {
        List<Integer> checkItemIds = checkGroupService.findCheckItemIdsByCheckGroupId(checkGroupId);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_CHECKITEM_SUCCESS, checkItemIds);
    }

    @PostMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupService.edit(checkGroup, checkitemIds);
        return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    @GetMapping("/findAll")
    public Result findAll() {
        List<CheckGroup> checkGroups = checkGroupService.findAll();
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroups);
    }


}
