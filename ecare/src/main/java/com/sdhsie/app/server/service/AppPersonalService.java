package com.sdhsie.app.server.service;

import java.util.List;

import com.sdhsie.base.util.PageData;

public interface AppPersonalService {
	
	
	/**
	 * 
	  * @Title: update
	  * @Description: 忘记密码
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void update(PageData pd);
	
	
	/**
	 * 
	  * @Title: updateStatus
	  * @Description: 修改密码、个人信息、个人开工状态
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void updateStatus(PageData pd);
	
	/**
	 * 
	  * @Title: findPartner
	  * @Description: 根据合作商的code进行查找
	  * @param @param pd
	  * @param @return    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
	public PageData findPartnerInfo(PageData pd);
	
	/**
	 * 
	  * @Title: updateUser
	  * @Description: 修改商户认证信息
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void updateUser(PageData pd);
	
	/**
	 * 
	  * @Title: findUserInfo
	  * @Description: 查询商户认证信息
	  * @param @param pd
	  * @param @return    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
	public PageData findUserInfo(PageData pd);
	
	/**
	 * 
	  * @Title: findAllInfo
	  * @Description: 查询所有的认证
	  * @param @param pd
	  * @param @return    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
	public PageData findAllInfo(PageData pd);
	
	/**
	 * 获取商户认证信息
	  * @Title: authentInfo
	  * @Description: TODO
	  * @param @param pd
	  * @param @return    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
	
	public PageData authentInfo(PageData pd);
	
	/**
	 * 
	  * @Title: findAmountSum
	  * @Description: 累计收入
	  * @param @param pd
	  * @param @return    设定文件
	  * @return double    返回类型
	  * @throws
	 */
	public double findAmountSum(PageData pd);
	
	/**
	 * 
	  * @Title: findDateNum
	  * @Description: 当天的收入
	  * @param @param pd
	  * @param @return    设定文件
	  * @return double    返回类型
	  * @throws
	 */
	public double findDateNum(PageData pd);
	
	/**
	 * 
	  * @Title: findConsumeList
	  * @Description: 查询消费记录
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findConsumeList(PageData pd);
	
	
	/**
	 * 
	  * @Title: findTimeList
	  * @Description: 根据时间进行分组
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findTimeList(PageData pd);
	
	
	/**
	 * 
	  * @Title: findDateNum
	  * @Description: 根据日期分页
	  * @param @param pd
	  * @param @return    设定文件
	  * @return int    返回类型
	  * @throws
	 */
	public int findDateNums(PageData pd);
	
	
	/**
	 * 
	  * @Title: findNumList
	  * @Description: 根据时间分组
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findNumList(PageData pd);
	

}
