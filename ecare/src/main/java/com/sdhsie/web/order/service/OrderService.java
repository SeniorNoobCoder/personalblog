package com.sdhsie.web.order.service;

import com.sdhsie.base.util.Page;
import com.sdhsie.base.util.PageData;

import java.util.List;

/**
 * Created by zcx on 2016/12/8.
 */
public interface OrderService {
    /**
     * 分页查询
     * @param page
     * @return
     */
    public List<PageData> findListPage(Page page);

    /**
     * 电话记录填写订单
     */
    public void saveOrderByTelemarketer(PageData pd);

    /**
     * 查询订单详情
     */
    public PageData findInfo(PageData pd);

    /**
     * 处理订单状态
     */
    public void updateStatus(PageData pd);

    public void updateStatusByOrderNo(PageData pd);

    /**
     * 订单未结单警告
     * @param pd
     */
    public void updateWarning(PageData pd);
    /**
     * 订单超时关闭
     * @param pd
     */
    public void updateTimeoutOrder(PageData pd);

    public int findNum(PageData pd);
}
