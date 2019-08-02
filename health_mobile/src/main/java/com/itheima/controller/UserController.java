package com.itheima.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.MemberService;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    MemberService memberService;

    @PostMapping("/phoneLogin")
    public Result phoneLogin(@RequestBody Map<String, String> loginInfo, HttpServletResponse response) {
        //首先校验验证码是否正确
        Jedis jedis = jedisPool.getResource();
        String phoneNumber = loginInfo.get("telephone");
        String key = RedisMessageConstant.SENDTYPE_LOGIN + "_" + phoneNumber;
        String password = loginInfo.get("validateCode");
        String codeInRedis = jedis.get(key);
        if (codeInRedis.equals(password)) {
            Cookie phoneNumberCookie = new Cookie("phoneNumber", phoneNumber);
            phoneNumberCookie.setMaxAge(7 * 24 * 60 * 60);
            phoneNumberCookie.setPath("/");
            response.addCookie(phoneNumberCookie);
            //密码正确,调用service下去查询是不是会员,不是则添加
            Member member = memberService.findByPhone(phoneNumber);
            if (member == null) {
                member = new Member();
                member.setRegTime(new Date());
                member.setPhoneNumber(phoneNumber);
                memberService.add(member);
                return new Result(true, MessageConstant.LOGIN_SUCCESS);
            } else {
                // 已经存在了
                return new Result(true, MessageConstant.LOGIN_SUCCESS);
            }
        } else {
            return new Result(false, "验证码错误");
        }
    }
}
