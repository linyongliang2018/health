package com.itheima;

import com.itheima.pojo.Member;

import java.util.Map;

public interface MemberService {
    Member findByPhone(String phoneNumber);

    void add(Member member);

    Map<String, Object> getMemberReport();
}
