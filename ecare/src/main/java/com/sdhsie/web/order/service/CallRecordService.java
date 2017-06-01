package com.sdhsie.web.order.service;

import com.sdhsie.base.util.Page;
import com.sdhsie.base.util.PageData;

import java.util.List;

/**
 * Created by zcx on 2016/12/9.
 */
public interface CallRecordService {
    /**
     * 分页查询
     * @param page
     * @return
     */
    public List<PageData> findListPage(Page page);

    /**
     * 查询
     * @param pd
     * @return
     */
    public List<PageData> findList(PageData pd);

    /**
     * 查询详情
     * @param id
     * @return
     */
    public PageData findInfo(String id);

    /**
     * 修改状态
     */
    public void updateStatus(PageData pd);

    /**
     * 订单提交后电话记录删除
     */
    public void acceptedOrder(PageData pd);

    /**
     * 添加
     */
    public void save(PageData pd);
}
