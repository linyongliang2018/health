package com.itheima.jobs;

import com.itheima.constant.RedisConstant;
import com.itheima.utils.QiNiuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class CleanImgJob {
    @Autowired
    private JedisPool jedisPool;

    public void doJob() {
        Jedis jedis = jedisPool.getResource();
        //差集
        Set<String> need2del = jedis.sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        //差集转化成字符串数组,因为 removeFiles接收的是字符串
        String[] need2delArr = need2del.toArray(new String[]{});
        //调用七牛删除,传入的是字符串
        QiNiuUtil.removeFiles(need2delArr);
        //redis也删除
        jedis.srem(RedisConstant.SETMEAL_PIC_RESOURCES, need2delArr);
        //清空redis
        //jedis.del(RedisConstant.SETMEAL_PIC_RESOURCES,RedisConstant.SETMEAL_PIC_DB_RESOURCES);
    }
}
