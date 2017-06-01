package com.sdhsie.app.server.service;

import com.sdhsie.base.util.PageData;

public interface AppServerMineService {
	
	/**
	 * 
	  * @Title: saveFeedback
	  * @Description: 意见反馈
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void saveFeedback(PageData pd);
	
	
	/**
	 * 
	  * @Title: findUserInfo
	  * @Description: 商家信息
	  * @param @param pd
	  * @param @return    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
	public PageData findUserInfo(PageData pd);
	
	/**
	 * 
	  * @Title: findPraiseNum
	  * @Description: 好评数量
	  * @param @param pd
	  * @param @return    设定文件
	  * @return int    返回类型
	  * @throws
	 */
	public int findPraiseNum(PageData pd);
}
