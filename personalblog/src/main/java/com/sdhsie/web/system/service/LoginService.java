package com.sdhsie.web.system.service;

import com.sdhsie.base.util.PageData;

public interface LoginService {

	
	/**
	 * 
	  * @Title: findInfo
	  * @Description: 员工登录
	  * @param @param pd
	  * @param @return    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
	public PageData findUserInfo(PageData pd);


	/**
	 * 
	  * @Title: findUserPath
	  * @Description: 判断用户是否拥有改路径的权限
	  * @param @param p
	  * @param @return    设定文件
	  * @return int    返回类型
	  * @throws
	 */
	public String findUserPath(PageData p);
	
	/**
	 * 
	  * @Title: findUserOrganize
	  * @Description: 查询用户组织
	  * @param @param pd
	  * @param @return    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
	public PageData findUserOrganize(PageData pd);
	

	/**
	 * 
	  * @Title: findUnitOrgId
	  * @Description: 获取施工单位组织ID
	  * @param @param pd
	  * @param @return    设定文件
	  * @return Integer    返回类型
	  * @throws
	 */
	public Integer findUnitOrgId(PageData pd);
	
	




}
