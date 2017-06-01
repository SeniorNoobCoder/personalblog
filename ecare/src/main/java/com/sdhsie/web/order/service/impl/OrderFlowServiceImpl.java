package com.sdhsie.web.order.service.impl;

import com.sdhsie.base.dao.impl.IDaoImpl;
import com.sdhsie.base.util.PageData;
import com.sdhsie.web.order.service.OrderFlowService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zcx on 2016/12/12.
 */
@Service
@Transactional
public class OrderFlowServiceImpl implements OrderFlowService{
    Logger logger = Logger.getLogger(OrderFlowServiceImpl.class);
    String mapperUrl = "com.ecare.web.order.orderFlow.";
    @Autowired
    private IDaoImpl dao;

    /**
     * 分页查询
     * @param pd
     * @return
     */
    public List<PageData> findList(PageData pd) {
        List<PageData> list = null;
        try {
            list = (List<PageData>) dao.findForList(mapperUrl + "findList", pd);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 根据订单编号查询流程列表
     * @param order_no
     * @return
     */
    public List<PageData> findListByOrderNo(String order_no) {
        List<PageData> list = null;
        try {
            list = (List<PageData>) dao.findForList(mapperUrl + "findListByOrderNo", order_no);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
        return list;
    }
    /**
     * 保存
     * @param pd
     */
    public void save(PageData pd) {
        try {
            dao.save(mapperUrl + "save", pd);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
    }
}
