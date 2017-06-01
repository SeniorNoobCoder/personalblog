package com.sdhsie.web.server.service.impl;

import com.sdhsie.base.dao.impl.IDaoImpl;
import com.sdhsie.base.util.Page;
import com.sdhsie.base.util.PageData;
import com.sdhsie.web.server.service.ServerInfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zcx on 2016/12/6.
 */
@Service
@Transactional
public class ServerInfoServiceImpl implements ServerInfoService{
    Logger logger = Logger.getLogger(ServerInfoServiceImpl.class);
    String mapperUrl = "com.ecare.web.server.info.";
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
     * 审核
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

    /**
     * 查询list
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
     * 清空数据
     * @param pd
     */
    public void clear(PageData pd) {

    }

    /**
     * 查询数据条数
     * @param pd
     * @return
     */
    public int findListNum(PageData pd) {
        Integer num = 0;
        try {
            num = (Integer) dao.findForObject(mapperUrl + "findListNum", pd);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
        return num;
    }
}
