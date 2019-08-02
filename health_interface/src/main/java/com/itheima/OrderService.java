package com.itheima;

import java.util.Map;

public interface OrderService {
    int addOrder(Map<String, String> orderInfo);

    Map<String, Object> findById(int id);
}
