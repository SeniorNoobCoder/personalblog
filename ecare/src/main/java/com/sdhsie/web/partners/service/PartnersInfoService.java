package com.sdhsie.web.partners.service;

import com.sdhsie.base.util.Page;
import com.sdhsie.base.util.PageData;

import java.util.List;

/**
 * Created by zcx on 2016/11/28.
 */
public interface PartnersInfoService {
    /**
     * @param @param  pd
     * @param @return 设定文件
     * @return List<PageData> 返回类型
     * @throws
     * @Title: findList
     * @Description: 查询信息列表
     */
    public List<PageData> findListPage(Page page);

    public List<PageData> findList(PageData pd);
    /**
     * @param @param pd 设定文件
     * @return void 返回类型
     * @throws
     * @Title: save
     * @Description: 添加
     */
    public void save(PageData pd);

    /**
     * @param @param pd 设定文件
     * @return void 返回类型
     * @throws
     * @Title: update
     * @Description: 修改
     */
    public void update(PageData pd);

    /**
     * @param @param  pd
     * @param @return 设定文件
     * @return PageData 返回类型
     * @throws
     * @Title: findInfo
     * @Description: TODO
     */
    public PageData findInfo(PageData pd);

    /**
     * 根据登录账号查询
     * @param pd
     * @return
     */
    public PageData findInfoByLoginName(PageData pd);

    /**
     * 添加服务分类
     * @param pd
     */
    public void saveServerCategory(PageData pd);

    /**
     * 删除服务分类
     */
    public void delServerCategory(String user_id);

    /**
     * 查询服务分类
     * @param pd
     * @return
     */
    public List<PageData> findServerCategory(PageData pd);

	public int findDelNum(PageData pd);

	public List<PageData> findTrashPage(Page page);

	public void clearUser(PageData pd);

	public void revertUser(PageData pd);

	public PageData findAuthentInfo(PageData pd);

	public void updateAuthent(PageData pd);

	public void saveAuthent(PageData pd);
    public int findNum(PageData pd);
}
