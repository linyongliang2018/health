package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.MemberService;
import com.itheima.dao.MemberDao;
import com.itheima.pojo.Member;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.*;

@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public Member findByPhone(String phoneNumber) {
        return memberDao.findByPhone(phoneNumber);
    }

    @Override
    public void add(Member member) {
        memberDao.add(member);
    }

    @Override
    public Map<String, Object> getMemberReport() {
        Calendar calendar = Calendar.getInstance();
        //获得去年的日期
        calendar.add(Calendar.YEAR, -1);
        //遍历12个月
        List<String> months = new ArrayList<>();
        //会员数
        List<Integer> memberCount = new ArrayList<>();
        //格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String month = "";
        for (int i = 0; i < 12; i++) {
            calendar.add(calendar.MONTH, 1);
            month = sdf.format(calendar.getTime());
            months.add(month);
            memberCount.add(memberDao.findMemberCountBeforeDate(month + "-31"));
        }
        Map<String, Object> result = new HashMap<>();
        result.put("months", months);
        result.put("memberCount", memberCount);
        return result;
    }
}
