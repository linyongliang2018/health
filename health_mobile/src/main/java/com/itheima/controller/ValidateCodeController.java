package com.itheima.controller;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.utils.SMSUtils;
import com.itheima.utils.ValidateCodeUtils;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;

    @PostMapping("/send4Order")
    public Result send4Order(String telephone) {
        Jedis jedis = jedisPool.getResource();
        //生成key存放验证码
        String key = RedisMessageConstant.SENDTYPE_ORDER + "_" + telephone;
        //验证是否发送过验证码
        return getResult(telephone, jedis, key);
    }

    @PostMapping("/send4Login")
    public Result send4Login(String telephone) {
        Jedis jedis = jedisPool.getResource();
        //生成key存放验证码
        String key = RedisMessageConstant.SENDTYPE_LOGIN + "_" + telephone;
        //验证是否发送过验证码
        return getResult(telephone, jedis, key);
    }

    private Result getResult(String telephone, Jedis jedis, String key) {
        if (jedis.get(key) != null) {
            return new Result(false, MessageConstant.SENT_VALIDATECODE);
        }
        //生成验证码
        Integer code = ValidateCodeUtils.generateValidateCode(6);
        //发送验证码
        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, code + "");
            //redis验证码设置过期时间,这里设置长一点,不要一直发信息
            jedis.setex(key, 5 * 36000, code + "");
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }
}
