package com.sdhsie.app.server.service;

import com.sdhsie.base.util.PageData;

public interface AppLoginService {
	
	/**
	 * 
	  * @Title: selectPhone
	  * @Description: 判断手机号是否注册
	  * @param @param pd
	  * @param @return    设定文件
	  * @return int    返回类型
	  * @throws
	 */
	public int findPhone(PageData pd);
	
	/**
	 * 
	  * @Title: save
	  * @Description: 注册
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void save(PageData pd);
	
	/**
	 * 
	  * @Title: saveBusiness
	  * @Description: 商户认证信息
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void saveBusiness(PageData pd);
	
	
	/**
	 * 
	  * @Title: findLogin
	  * @Description: 登录
	  * @param @param pd
	  * @param @return    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
	public PageData findLoginInfo(PageData pd);
	
	/**
	 * 
	  * @Title: update
	  * @Description: 修改信息
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void update(PageData pd);
	
	/**
	 * 
	  * @Title: saveyzm
	  * @Description: 保存验证码
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void saveyzm(PageData pd);
	
	/**
	 * 
	  * @Title: findYzmInfo
	  * @Description: 查询验证码
	  * @param @param pd
	  * @param @return    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
	public PageData findYzmInfo(PageData pd);
	
	/**
	 * 
	  * @Title: findNum
	  * @Description: 订单完成量
	  * @param @param pd
	  * @param @return    设定文件
	  * @return int    返回类型
	  * @throws
	 */
	public int findNum(PageData pd);
	
	/**
	 * 
	  * @Title: findMany
	  * @Description: 我的收入
	  * @param @param pd
	  * @param @return    设定文件
	  * @return double    返回类型
	  * @throws
	 */
	public double findMany(PageData pd);
}
