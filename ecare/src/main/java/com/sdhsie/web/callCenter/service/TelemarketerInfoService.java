package com.sdhsie.web.callCenter.service;

import com.sdhsie.base.util.Page;
import com.sdhsie.base.util.PageData;

import java.util.List;

/**
 * Created by zcx on 2016/11/25.
 */
public interface TelemarketerInfoService {

    /**
     * @Title: findList
     * @Description: 查询信息列表
     * @param @param pd
     * @param @return 设定文件
     * @return List<PageData> 返回类型
     * @throws
     */
    public List<PageData> findListPage(Page page);

	public List<PageData> findList(PageData pd);

	public int findDelNum(PageData pd);

	public PageData findInfo(PageData pd);

	public void save(PageData pd);

	public void update(PageData pd);

	public List<PageData> findTrashPage(Page page);

	public void clearUser(PageData pd);

	public void revertUser(PageData pd);
}
