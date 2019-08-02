package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.CheckItemService;
import com.itheima.dao.CheckItemDao;
import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.QueryPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;

@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    CheckItemDao checkItemDao;

    @Override
    public void addItem(CheckItem checkItem) {
        checkItemDao.addItem(checkItem);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<CheckItem> page = checkItemDao.findAllByCondition(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void deleteById(Integer id) {
        //注意：不能直接删除，需要判断当前检查项是否和检查组关联，
        // 如果已经和检查组进行了关联则不允许删除
        long count = checkItemDao.findCountByCheckItemid(id);
        if (count > 0) {
            throw new RuntimeException("当前检查项被引用，不能删除");
        }
        checkItemDao.deleteById(id);
    }

    @Override
    public CheckItem findById(Integer id) {

        return checkItemDao.findById(id);
    }

    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.editcheckitem(checkItem);
    }

    @Override
    public List<CheckItem> findAll() {

        return checkItemDao.findAll();
    }

}
