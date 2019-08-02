package com.itheima.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.PackageService;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.Result;
import com.itheima.pojo.Package;
import com.itheima.pojo.QueryPageBean;
import com.itheima.utils.QiNiuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/package")
public class PackageController {

    //细节,引入jedis
    @Autowired
    private JedisPool jedisPool;

    @Reference
    private PackageService packageService;

    @PostMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile) {
        //获取前端传过来的名字,并用uuid产生唯一名字
        String originalFilename = imgFile.getOriginalFilename();
        String extensionName = originalFilename.substring(originalFilename.lastIndexOf("."));
        String filename = UUID.randomUUID() + extensionName;
        try {
            QiNiuUtil.uploadViaByte(imgFile.getBytes(), filename);
            //上传成功后保存记录到 redis中
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES, filename);
            Map<String, String> imageMap = new HashMap<>();
            imageMap.put("domain", QiNiuUtil.DOMAIN);
            imageMap.put("imageName", filename);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, imageMap);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    @PostMapping("/findPage")
    @PreAuthorize("hasAuthority('SETMEAL_QUERY')")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult<Package> pageResult = packageService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS, pageResult);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Package aPackage, Integer[] checkgroupIds) {
        packageService.add(aPackage, checkgroupIds);
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, aPackage.getImg());
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }
}
