package com.sdhsie.web.system.service;

import java.util.List;

import com.sdhsie.base.util.PageData;


public interface MainService {

	
	/**
	 * 
	  * @Title: findRoleList
	  * @Description: 获取用户角色信息
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	List<Integer> findRoleList(PageData pd);
	

	/**
	 * 
	  * @Title: findMenuList
	  * @Description: 获取菜单列表
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	List<PageData> findMenuList(PageData pd);

	/**
	 * 
	  * @Title: findButtonList
	  * @Description: 获取按钮列表
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	List<PageData> findButtonList(PageData pd);

	List<PageData> findRoleLs(PageData pd);



}
