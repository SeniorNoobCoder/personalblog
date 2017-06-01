package com.sdhsie.app.family.service;

import java.util.List;

import com.sdhsie.base.util.PageData;

public interface AppServerService {
	
	/**
	 * 
	  * @Title: saveOder
	  * @Description: 下订单
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void saveOder(PageData pd);
	
	
	/**
	 * 
	  * @Title: findServiceList
	  * @Description: 查询服务对象
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findServiceList(PageData pd);

	/**
	 * 
	  * @Title: findUserInfo
	  * @Description: 查询用户
	  * @param @param pd
	  * @param @return    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
	public PageData findUserInfo(PageData pd);
	
	/**
	 * 
	  * @Title: findTsList
	  * @Description: 查询要推送的商户
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findTsList(PageData pd);
	
	/**
	 * 
	  * @Title: findTshList
	  * @Description: 查询要推送的商户(服务信息 合作商下的商户进行推送）
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findTshList(PageData pd);
}
