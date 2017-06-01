package com.sdhsie.web.server.service;

import com.sdhsie.base.util.Page;
import com.sdhsie.base.util.PageData;
import java.util.List;

/**
 * Created by zcx on 2016/11/23.
 */
public interface ServerCategoryService {

    /**
     *
     * @Title: findList
     * @Description: 查询信息列表
     * @param @param pd
     * @param @return 设定文件
     * @return List<PageData> 返回类型
     * @throws
     */
    public List<PageData> findListPage(Page page);
    /**
     * @Title: save
     * @Description: 添加
     * @param @param pd 设定文件
     * @return void 返回类型
     * @throws
     */
    public void save(PageData pd);

    /**
     * @Title: findInfo
     * @Description: TODO
     * @param @param pd
     * @param @return 设定文件
     * @return PageData 返回类型
     * @throws
     */
    public PageData findInfo(PageData pd);

    /**
     * @Title: update
     * @Description: 修改
     * @param @param pd 设定文件
     * @return void 返回类型
     * @throws
     */
    public void update(PageData pd);

    /**
     * 审核
     * @param pd
     */
    public void updateStatus(PageData pd);
    /**
     * @Title: delete
     * @Description: 删除
     * @param @param ids 设定文件
     * @return void 返回类型
     * @throws
     */
    public void delete(String[] ids);

    /**
     * 查询
     * @param pd
     * @return
     */
    public List<PageData> findList(PageData pd);
    /**
     *
     * @Title: findListNum
     * @Description: 查询数量
     * @param @param pd
     * @param @return    设定文件
     * @return int    返回类型
     * @throws
     */
    public int findListNum(PageData pd);
}
