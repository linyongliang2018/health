package com.itheima;

import com.itheima.entity.PageResult;
import com.itheima.pojo.Package;
import com.itheima.pojo.QueryPageBean;

import java.util.List;
import java.util.Map;

public interface PackageService {
    PageResult<Package> findPage(QueryPageBean queryPageBean);

    void add(Package aPackage, Integer[] checkgroupIds);

    List<Package> findAll();

    Package getPackageDetail(int id);

    Package findById(int id);

    List<Map<String, Object>> getPackageReport();

}
