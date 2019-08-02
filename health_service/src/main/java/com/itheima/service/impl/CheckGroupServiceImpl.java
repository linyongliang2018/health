package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.itheima.CheckGroupService;
import com.itheima.dao.CheckGroupDao;
import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.QueryPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.add(checkGroup);
        Integer id = checkGroup.getId();
        System.out.println(id);
        if (checkitemIds != null && checkitemIds.length > 0) {
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addCheckGroupAndCheckItem(id, checkitemId);
            }
        }
    }

    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {
        if (!StringUtil.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<CheckGroup> checkGroups = checkGroupDao.findAllByCondition(queryPageBean.getQueryString());
        return new PageResult<CheckGroup>(checkGroups.getTotal(), checkGroups.getResult());
    }

    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer checkGroupId) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(checkGroupId);
    }

    @Override
    @Transactional
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.edit(checkGroup);
        Integer checkGroupId = checkGroup.getId();
        checkGroupDao.deleteAssociation(checkGroupId);
        if (checkitemIds != null && checkitemIds.length > 0) {
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addCheckGroupAndCheckItem(checkGroupId, checkitemId);
            }
        }
    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }
}
