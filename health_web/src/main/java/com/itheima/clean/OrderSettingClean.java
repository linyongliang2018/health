package com.itheima.clean;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.OrderSettingService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderSettingClean {
    @Reference
    private OrderSettingService orderSettingService;
    @Scheduled(cron = "0 0 2 1 1/1 ?")
    public void CleanOrder(){
        orderSettingService.cleanOrderSetting();
        System.out.println("正在清理上个月的预约数据");
    }
}
