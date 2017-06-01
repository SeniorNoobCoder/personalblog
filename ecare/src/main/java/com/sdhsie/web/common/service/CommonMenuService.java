package com.sdhsie.web.common.service;

import java.util.List;

import com.sdhsie.base.util.Page;
import com.sdhsie.base.util.PageData;

public interface CommonMenuService {
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
	 * 
	  * @Title: findList
	  * @Description: 查询信息列表
	  * @param @param page
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findList(PageData pd);
	
	/**
	 * 
	 * 
	 * @Title: save
	 * @Description: 添加菜单
	 * @param @param pd 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void save(PageData pd);

	/**
	 * 
	 * 
	 * @Title: findInfo
	 * @Description: 查询详情
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
	 * @Description: 修改菜单
	 * @param @param pd 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void update(PageData pd);

	/**
	 * 
	 * 
	 * @Title: delete
	 * @Description: 删除菜单
	 * @param @param pd 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void delete(PageData pd);

	/**
	 * 
	  * @Title: deleteRoleMenu
	  * @Description: 删除角色菜单信息
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void deleteRoleMenu(PageData pd);

}
