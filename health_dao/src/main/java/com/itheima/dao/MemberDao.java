package com.itheima.dao;

import com.itheima.pojo.Member;

public interface MemberDao {

    Member findMemberByTelephone(String telephone);

    void add(Member member);

    Member findByPhone(String phoneNumber);

    Integer findMemberCountBeforeDate(String date);

    Integer findMemberCountByDate(String date);

    Integer findMemberTotalCount();

    Integer findMemberCountAfterDate(String date);
}
