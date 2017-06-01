package com.sdhsie.webService.toro.service.impl;

import com.sdhsie.base.dao.impl.IDaoImpl;
import com.sdhsie.base.util.Logger;
import com.sdhsie.base.util.PageData;
import com.sdhsie.webService.toro.service.UserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zcx on 2016/12/14.
 */
@Service
@Transactional
public class UserMessageServiceImpl implements UserMessageService{
    Logger logger = Logger.getLogger(UserMessageServiceImpl.class);
    String mapperUrl = "com.ecare.web.toro.userInfo.";
    @Autowired
    private IDaoImpl dao;

    /**
     * 添加通话记录
     * @param pd
     */
    public void saveCallRecord(PageData pd) {
        try {
            dao.save(mapperUrl + "saveCallRecord", pd);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    /**
     * 添加进出入安全岛记录
     * @param pd
     */
    public void saveInOutSafeArea(PageData pd) {
        try {
            dao.save(mapperUrl + "saveInOutSafeArea", pd);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    public void saveAssessment(PageData pd) {
        try {
            dao.save(mapperUrl + "saveAssessment", pd);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
    }
    public int findAssessmentNum(PageData pd) {
        Integer num = 0;
        try {
            num = (Integer) dao.findForObject(mapperUrl + "findAssessmentNum", pd);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
        return num;
    }
}
