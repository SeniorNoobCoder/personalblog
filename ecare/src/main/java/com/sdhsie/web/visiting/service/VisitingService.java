package com.sdhsie.web.visiting.service;

import com.sdhsie.base.util.Page;
import com.sdhsie.base.util.PageData;

import java.util.List;

/**
 * Created by zcx on 2016/12/15.
 */
public interface VisitingService {

    /**
     * 分页查询
     */
    public List<PageData> findListPage(Page page);

    /**
     * 添加
     */
    public void save(PageData pd);

    /**
     *查询详情
     */
    public PageData findInfo(PageData pd);

    /**
     * 删除
     */
    public void delete(String[] id);
    public int findNum(PageData pd);
}
