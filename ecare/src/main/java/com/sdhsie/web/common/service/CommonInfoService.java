package com.sdhsie.web.common.service;

import com.sdhsie.base.util.Page;
import com.sdhsie.base.util.PageData;

import java.util.List;

/**
 * Created by zcx on 2017/1/24.
 */
public interface CommonInfoService {
    /**
     * @Title: findList
     * @Description: 查询信息列表
     * @param @param pd
     * @param @return 设定文件
     * @return List<PageData> 返回类型
     * @throws
     */
    public List<PageData> findListPage(Page page);

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
     * @Title: save
     * @Description: 添加
     * @param @param pd 设定文件
     * @return void 返回类型
     * @throws
     */
    public void save(PageData pd);

    /**
     * @Title: update
     * @Description: 修改
     * @param @param pd 设定文件
     * @return void 返回类型
     * @throws
     */
    public void update(PageData pd);

    /**
     * @Title: delete
     * @Description: 删除
     * @param @param ids 设定文件
     * @return void 返回类型
     * @throws
     */
    public void delete(String ids);

}
