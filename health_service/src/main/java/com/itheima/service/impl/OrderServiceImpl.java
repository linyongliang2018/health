package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.OrderService;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.OrderSettingDao;
import com.itheima.exception.HealthException;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    @Transactional
    public int addOrder(Map<String, String> orderInfo) {
        OrderSetting orderSetting = orderDao.findRestByOrderDate(orderInfo.get("orderDate"));
        if (orderSetting == null) {
            //休息的时间,不接客
            throw new HealthException(MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        } else {
            //正常工作时间
            int reservations = orderSetting.getReservations();
            int number = orderSetting.getNumber();
            Integer memberId = null;
            if (reservations >= number) {
                throw new HealthException(MessageConstant.ORDER_FULL);
            } else {
                //通过手机号查询是否为会员
                Member member = memberDao.findMemberByTelephone(orderInfo.get("telephone"));
                if (member == null) {
                    //创建会员
                    member = new Member();
                    member.setRegTime(new Date());
                    member.setName(orderInfo.get("name"));
                    member.setSex(orderInfo.get("sex"));
                    member.setPhoneNumber(orderInfo.get("telephone"));
                    member.setIdCard(orderInfo.get("idCard"));
                    memberDao.add(member);
                    //获取返回的id
                    memberId = member.getId();
                } else {
                    //会员已存在
                    memberId = member.getId();
                }
                //判断是否重复预约
                Order order = new Order();
                order.setMemberId(memberId);
                String orderDate = orderInfo.get("orderDate");
                try {
                    order.setOrderDate(DateUtils.parseString2Date(orderDate));
                    //通过预约日期和会员id来判断是不是有重复预约
                    List<Order> orders = orderDao.findOrderByCondition(order);
                    if (orders.size() == 0) {
                        //不存在则插入预约信息
                        order.setPackageId(Integer.valueOf(orderInfo.get("packageId")));
                        order.setOrderStatus(Order.ORDERSTATUS_NO);
                        order.setOrderType(orderInfo.get("orderType"));
                        //todo debug
                        orderDao.addOrder(order);
                        //更新已经预约的人数

                        orderSettingDao.editReservationsByOrderDate(1, orderDate);
                    } else {
                        //已存在了，报错
                        throw new HealthException(MessageConstant.HAS_ORDERED);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new HealthException("日期转换失败，请联系管理员解决");
                }
                Integer orderId = order.getId();
                return orderId;

            }
        }
    }

    @Override
    public Map<String, Object> findById(int id) {
        return orderDao.findById(id);
    }
}
