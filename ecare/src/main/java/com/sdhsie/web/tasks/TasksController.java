package com.sdhsie.web.tasks;
import com.sdhsie.base.util.DateTimeUtil;
import com.sdhsie.base.util.PageData;
import com.sdhsie.web.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zcx on 2017/3/2.
 */
@Controller
@RequestMapping(value = "/tasks/tasks")
public class TasksController {
    @Autowired
    private OrderService orderService;
    @Scheduled(cron = "0 0/30 * * * ?")
    public void orderWarning() {
       String warningTime = DateTimeUtil.getNowBeforeTime(-60);
        PageData pd = new PageData();
        pd.put("warningTime",warningTime);
        orderService.updateWarning(pd);//修改超时警告订单
        String timeoutTime = DateTimeUtil.getNowBeforeTime(-30);
        pd.put("timeoutTime",timeoutTime);//修改超时订单
        orderService.updateTimeoutOrder(pd);
        System.out.println("订单超时轮询检测已执行完毕！");
    }
}
