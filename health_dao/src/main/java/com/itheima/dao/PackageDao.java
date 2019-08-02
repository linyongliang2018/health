package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Package;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PackageDao {
    Page<Package> findAllByCondition(String queryString);

    void add(Package aPackage);

    void addPackageAndCheckGroup(@Param("aPackageId") Integer aPackageId, @Param("checkgroupId") Integer checkgroupId);

    List<Package> findAll();

    Package getPackageDetail(int id);

    Package findById(int id);

    List<Map<String, Object>> getPackageReport();

}
