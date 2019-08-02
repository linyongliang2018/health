package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.PackageService;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Package;
import com.itheima.utils.QiNiuUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/package")
public class PackageController {

    @Reference
    private PackageService packageService;

    @GetMapping("/getPackage")
    public Result getPackage() {
        List<Package> list = packageService.findAll();
        list.forEach(pkg -> {
            pkg.setImg(QiNiuUtil.DOMAIN + "/" + pkg.getImg());
        });
        return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS, list);
    }

    @GetMapping("/getPackageDetail")
    public Result getPackageDetail(int id) {
        Package pkg = packageService.getPackageDetail(id);
        pkg.setImg(QiNiuUtil.DOMAIN + "/" + pkg.getImg());
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, pkg);
    }

    @PostMapping("/findById")
    public Result findById(int id) {
        Package pkg = packageService.findById(id);
        pkg.setImg(QiNiuUtil.DOMAIN + "/" + pkg.getImg());
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, pkg);
    }
}
