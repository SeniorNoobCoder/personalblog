package com.sdhsie.webService.toro.service;

import com.sdhsie.base.util.PageData;

/**
 * Created by zcx on 2016/12/14.
 */
public interface UserMessageService {

    /**
     * 添加电话记录
     */

    public void saveCallRecord(PageData pd);

    /**
     * 添加进出入安全岛记录
     * @param pd
     */
    public void saveInOutSafeArea(PageData pd);

    /**
     * 添加评价
      * @param pd
     */
    public void saveAssessment(PageData pd);

    /**
     *
     * @param pd
     * @return
     */
    public int findAssessmentNum(PageData pd);


}
