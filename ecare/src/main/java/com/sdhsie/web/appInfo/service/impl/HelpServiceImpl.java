package com.sdhsie.web.appInfo.service.impl;

import com.sdhsie.base.dao.impl.IDaoImpl;
import com.sdhsie.base.util.Logger;
import com.sdhsie.base.util.Page;
import com.sdhsie.base.util.PageData;
import com.sdhsie.web.appInfo.service.HelpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zcx on 2017/1/16.
 */
@Service
@Transactional
public class HelpServiceImpl implements HelpService{
    Logger logger = Logger.getLogger(HelpServiceImpl.class);
    String mapperUrl = "com.ecare.web.appInfo.help.";
    @Autowired
    private IDaoImpl dao;

    /**
     * 分页查询列表
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
     * 修改
     * @param pd
     */
    public void update(PageData pd) {
        try {
            dao.update(mapperUrl + "update", pd);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    /**
     * 删除
     * @param id
     */
    public void delete(String id) {
        try {
            dao.delete(mapperUrl + "delete", id);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
    }
}
