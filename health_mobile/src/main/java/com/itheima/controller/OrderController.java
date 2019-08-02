package com.itheima.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.OrderService;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    OrderService orderService;

    @PostMapping("/submit")
    public Result submitOrder(@RequestBody Map<String, String> orderInfo) {
        Jedis jedis = jedisPool.getResource();
        String key = RedisMessageConstant.SENDTYPE_ORDER + "_" + orderInfo.get("telephone");
        String codeInReids = jedis.get(key);
        if (codeInReids == null) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        } else {
            //订单编号
            int orderId;
            boolean bCode = codeInReids.equals(orderInfo.get("validateCode"));
            if (bCode) {
                orderInfo.put("orderType", Order.ORDERTYPE_WEIXIN);
                //调用业务层的方法预约
                orderId = orderService.addOrder(orderInfo);
                /*if (orderId==-123) {
                    return new Result(false, "系统异常,请联系管理员处理");
                }*/
            } else {
                //报错
                return new Result(false, MessageConstant.VALIDATECODE_ERROR);
            }
            return new Result(true, MessageConstant.ORDER_SUCCESS, orderId);
        }
    }

    @PostMapping("/findById")
    public Result findById(int id) {
        Map<String, Object> orderInfo = orderService.findById(id);
        return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, orderInfo);
    }
}
