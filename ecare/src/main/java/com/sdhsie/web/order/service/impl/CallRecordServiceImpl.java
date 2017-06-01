package com.sdhsie.web.order.service.impl;
import com.sdhsie.base.dao.impl.IDaoImpl;
import com.sdhsie.base.util.Page;
import com.sdhsie.base.util.PageData;
import com.sdhsie.web.order.service.CallRecordService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Created by zcx on 2016/12/9.
 */
@Service
@Transactional
public class CallRecordServiceImpl implements CallRecordService{
    Logger logger = Logger.getLogger(CallRecordServiceImpl.class);
    String mapperUrl = "com.ecare.web.order.callRecord.";
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
     * 查询列表
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
     * 查询详情
     * @param id
     * @return
     */
    public PageData findInfo(String id) {
        PageData pd = null;
        try {
            pd = (PageData) dao.findForObject(mapperUrl + "findInfo", id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pd;
    }

    /**
     * 修改
     * @param pd
     */
    public void updateStatus(PageData pd) {
        try {
            dao.update(mapperUrl + "updateStatus", pd);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
    }
    /**
     * 订单提交后电话记录删除
     * @param pd
     */
    public void acceptedOrder(PageData pd) {
        try {
            dao.update(mapperUrl + "acceptedOrder", pd);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
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
