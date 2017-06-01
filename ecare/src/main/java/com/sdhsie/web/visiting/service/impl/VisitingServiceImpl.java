package com.sdhsie.web.visiting.service.impl;

import com.sdhsie.base.dao.impl.IDaoImpl;
import com.sdhsie.base.util.Page;
import com.sdhsie.base.util.PageData;
import com.sdhsie.web.visiting.service.VisitingService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Created by zcx on 2016/12/15.
 */
@Service
@Transactional
public class VisitingServiceImpl implements VisitingService{
    Logger logger = Logger.getLogger(VisitingServiceImpl.class);
    String mapperUrl = "com.ecare.web.visiting.visiting.";
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
     * 添加
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

    /**
     * 查询详情
     * @param pd
     * @return
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
     * 删除
     * @param id
     */
    public void delete(String[] id) {
        try {
            dao.deleteBatch(mapperUrl + "delete", id);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
    }
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
