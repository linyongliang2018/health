package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.itheima.PackageService;
import com.itheima.dao.PackageDao;
import com.itheima.entity.PageResult;
import com.itheima.pojo.Package;
import com.itheima.pojo.QueryPageBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service
public class PackageServiceImpl implements PackageService {

    @Autowired
    private PackageDao packageDao;

    @Override
    public PageResult<Package> findPage(QueryPageBean queryPageBean) {
        if (!StringUtil.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<Package> page = packageDao.findAllByCondition(queryPageBean.getQueryString());
        return new PageResult<>(page.getTotal(), page.getResult());
    }

    @Override
    public void add(Package aPackage, Integer[] checkgroupIds) {
        packageDao.add(aPackage);
        Integer aPackageId = aPackage.getId();
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            for (Integer checkgroupId : checkgroupIds) {
                packageDao.addPackageAndCheckGroup(aPackageId, checkgroupId);
            }
        }
    }

    @Override
    public List<Package> findAll() {
        return packageDao.findAll();
    }

    @Override
    public Package getPackageDetail(int id) {
        return packageDao.getPackageDetail(id);
    }

    @Override
    public Package findById(int id) {
        return packageDao.findById(id);
    }

    @Override
    public List<Map<String, Object>> getPackageReport() {
        return packageDao.getPackageReport();
    }
}
