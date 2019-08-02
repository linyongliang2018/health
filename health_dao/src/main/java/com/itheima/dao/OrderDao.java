package com.itheima.dao;

import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderDao {
    OrderSetting findRestByOrderDate(String orderDate);

    List<Order> findOrderByCondition(Order order);

    void addOrder(Order order);

    Map<String, Object> findById(int id);

    Integer findOrderCountByDate(String date);

    Integer findOrderCountBetweenDate(@Param("startDate") String thisWeekMonday, @Param("endDate") String thisWeekSunday);

    Integer findVisitsCountByDate(String date);

    Integer findVisitsCountAfterDate(String date);

    Integer findMemberCountBeforeDate(String date);

    List<Map> findHotPackage();
}
