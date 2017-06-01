package com.sdhsie.web.order.service.impl;

import com.sdhsie.base.dao.impl.IDaoImpl;
import com.sdhsie.base.util.Page;
import com.sdhsie.base.util.PageData;
import com.sdhsie.web.order.service.OrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zcx on 2016/12/8.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService{
    Logger logger = Logger.getLogger(OrderServiceImpl.class);
    String mapperUrl = "com.ecare.web.order.order.";
    @Autowired
    private IDaoImpl dao;
    /**
     * 分页查询
     * @param page
     * @return
     */
    public List<PageData> findListPage(Page page) {
        List<PageData> list = null;
        try {
            list = (List<PageData>) dao.findForList(mapperUrl + "findListPage", page);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 查看订单详情
     */
    public PageData findInfo(PageData pd) {
        try {
            pd = (PageData) dao.findForObject(mapperUrl + "findInfo", pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pd;
    }

    /**
     * 根据电话记录填写订单
     * @param pd
     */
    public void saveOrderByTelemarketer(PageData pd) {
        try {
            dao.save(mapperUrl + "saveOrderByTelemarketer", pd);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    /**
     * 修改订单状态
     * @param pd
     */
    public void updateStatus(PageData pd) {
        try {
            dao.save(mapperUrl + "updateStatus", pd);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    public void updateStatusByOrderNo(PageData pd) {
        try {
            dao.save(mapperUrl + "updateStatusByOrderNo", pd);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    public void updateWarning(PageData pd) {
        try {
            dao.save(mapperUrl + "updateWarning", pd);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
    }
    public void updateTimeoutOrder(PageData pd) {
        try {
            dao.save(mapperUrl + "updateTimeoutOrder", pd);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
    }
    /**
     * 查询订单数量
     * @param pd
     * @return
     */
    public int findNum(PageData pd) {
        Integer num = 0;
        try {
            num = (Integer) dao.findForObject(mapperUrl + "findNum", pd);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
        return num;
    }
}
