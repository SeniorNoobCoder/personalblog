package com.sdhsie.web.partners.service;

import java.util.List;

import com.sdhsie.base.util.Page;
import com.sdhsie.base.util.PageData;

public interface BusinessService {
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
	 * 无分页查询list
	 * @param pd
	 * @return
     */
	public List<PageData> findList(PageData pd);

	/**
	 * 查询工作中的符合分类的商户
	 * @param pd
	 * @return
     */
	public List<PageData> findBusinessIsWorkList(PageData pd);
	/**
	 * 
	 * 
	 * @Title: findInfo
	 * @Description: TODO
	 * @param @param pd
	 * @param @return 设定文件
	 * @return PageData 返回类型
	 * @throws
	 */
	public PageData findInfo(PageData pd);

	/**
	 * 
	 * 
	 * @Title: update
	 * @Description: 修改
	 * @param @param pd 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void update(PageData pd);

	/**
	 * 
	 * 
	 * @Title: delete
	 * @Description: 删除
	 * @param @param ids 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void delete(String[] ids);

	public PageData findAuthent(PageData pd);

	public List<PageData> findServerCategory(PageData pd);
	public int findNum(PageData pd);
}
