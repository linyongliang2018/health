package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.MemberService;
import com.itheima.OrderService;
import com.itheima.ReportService;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = ReportService.class)
public class ReportServiceImpl implements ReportService {

    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;

    @Override
    public Map<String, Object> getBusinessReportData() throws Exception {
        String today = DateUtils.parseDate2String(DateUtils.getToday());
        String thisWeekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
        String thisWeekSunday = DateUtils.parseDate2String(DateUtils.getSundayOfThisWeek(), "yyyy-MM-dd");
        //获得本月第一天的日期
        String firstDayOfThisMonth = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
        //获得本月最后一天的日期
        String lastDayOfThisMonth = DateUtils.parseDate2String(DateUtils.getLastDayOfThisMonth(), "yyyy-MM-dd");

        Map<String, Object> map = new HashMap<>();
        map.put("reportDate", today);
        Integer todayNewMember = memberDao.findMemberCountByDate(today);
        map.put("todayNewMember", todayNewMember);
        Integer totalMember = memberDao.findMemberTotalCount();
        map.put("totalMember", totalMember);
        //今天到星期一的
        //memberService.findMemberCountAfterDate(today,thisWeekMonday);
        //星期一以后的,也可以算本周
        Integer thisWeekNewMember = memberDao.findMemberCountAfterDate(thisWeekMonday);
        map.put("thisWeekNewMember", thisWeekNewMember);
        Integer thisMonthNewMember = memberDao.findMemberCountAfterDate(firstDayOfThisMonth);
        map.put("thisMonthNewMember", thisMonthNewMember);
        //今日预约数
        Integer todayOrderNumber = orderDao.findOrderCountByDate(today);
        map.put("todayOrderNumber", todayOrderNumber);
        //本周预约数
        Integer thisWeekOrderNumber = orderDao.findOrderCountBetweenDate(thisWeekMonday, thisWeekSunday);
        map.put("thisWeekOrderNumber", thisWeekOrderNumber);
        //本月预约数
        Integer thisMonthOrderNumber = orderDao.findOrderCountBetweenDate(firstDayOfThisMonth, lastDayOfThisMonth);
        map.put("thisMonthOrderNumber", thisMonthOrderNumber);
        //今日到诊数
        Integer todayVisitsNumber = orderDao.findVisitsCountByDate(today);
        map.put("todayVisitsNumber", todayVisitsNumber);
        //本周到诊数
        Integer thisWeekVisitsNumber = orderDao.findVisitsCountAfterDate(thisWeekMonday);
        map.put("thisWeekVisitsNumber", thisWeekVisitsNumber);
        //本月到诊数
        Integer thisMonthVisitsNumber = orderDao.findVisitsCountAfterDate(firstDayOfThisMonth);
        map.put("thisMonthVisitsNumber", thisMonthVisitsNumber);
        //热门套餐（取前4）
        List<Map> hotPackage = orderDao.findHotPackage();
        map.put("hotPackage", hotPackage);
        return map;
    }
}
