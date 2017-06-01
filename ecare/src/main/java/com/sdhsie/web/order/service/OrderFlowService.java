package com.sdhsie.web.order.service;

import com.sdhsie.base.util.PageData;

import java.util.List;

/**
 * Created by zcx on 2016/12/12.
 */
public interface OrderFlowService {
    /**
     * 查询
     * @param pd
     * @return
     */
    public List<PageData> findList(PageData pd);

    /**
     * 添加
     * @param pd
     */
    public void save(PageData pd);

    /**
     * 根据订单编号查询流程
     */

    public List<PageData> findListByOrderNo(String order_no);
}
