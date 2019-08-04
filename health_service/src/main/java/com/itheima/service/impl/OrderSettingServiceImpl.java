package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.OrderSettingService;
import com.itheima.dao.OrderSettingDao;
import com.itheima.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public void cleanOrderSetting() {
        orderSettingDao.cleanOrderSetting();
    }

    @Override
    public void add(List<OrderSetting> orderSettingList) {
        if (orderSettingList != null && orderSettingList.size() > 0) {
            for (OrderSetting orderSetting : orderSettingList) {
                long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
                if (count > 0) {
                    orderSettingDao.editNumberByOrderDate(orderSetting);
                } else {
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }

    @Override
    public List<Map> getOrderSettingByMonth(String date) {
        String dateBegin = date + "-1";//2019-x-1
        String dateEnd = date + "-31";//2019-x-31
        Map map = new HashMap();
        map.put("dateBegin", dateBegin);
        map.put("dateEnd", dateEnd);
        //查询到的数据集合
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(map);
        //需要返回的参数集合
        List<Map> data = new ArrayList<>();
        for (OrderSetting orderSetting : list) {
            Map orderSettingMap = new HashMap();
            orderSettingMap.put("date", orderSetting.getOrderDate().getDate());//获得日期
            orderSettingMap.put("number", orderSetting.getNumber());//获得人数
            orderSettingMap.put("reservations", orderSetting.getReservations());//已预约人数
            data.add(orderSettingMap);
        }
        return data;
    }

    @Override
    public void editNumberByDate(String orderDate, int number) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        OrderSetting os = null;
        try {
            os = new OrderSetting(sdf.parse(orderDate), number);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        orderSettingDao.editNumberByOrderDate(os);
    }
}
