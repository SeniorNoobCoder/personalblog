package com.sdhsie.app.server.service;

import java.util.List;

import com.sdhsie.base.util.PageData;

public interface AppOrderService {
	
	
	
	/**
	 * 
	  * @Title: findStatusList
	  * @Description: 根据状态查询订单
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findStatusList(PageData pd);
	
	/**
	 * 
	  * @Title: findPayList
	  * @Description: 查找已支付
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findPayList(PageData pd);
	
	/**
	 * 
	  * @Title: findListNum
	  * @Description: 订单的数量
	  * @param @param pd
	  * @param @return    设定文件
	  * @return int    返回类型
	  * @throws
	 */
	public int findListNum(PageData pd);
	
	/**
	 * 
	  * @Title: findPayListNum
	  * @Description: 查询订单已支付的数量
	  * @param @param pd
	  * @param @return    设定文件
	  * @return int    返回类型
	  * @throws
	 */
	public int findPayListNum(PageData pd);
	
	/**
	 * 
	  * @Title: findSessionInfo
	  * @Description: 获取sessionid
	  * @param @param pd
	  * @param @return    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
	public PageData findSessionInfo(PageData pd);
	
	/**
	 * 
	  * @Title: findUserInfo
	  * @Description: 查询用户的个人信息
	  * @param @param pd
	  * @param @return    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
	public PageData findUserInfo(PageData pd);
	/**
	 * 
	  * @Title: findOrderInfo
	  * @Description: 订单信息
	  * @param @param pd
	  * @param @return    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
	public PageData findOrderInfo(PageData pd);
	
	
	/**
	 * 
	  * @Title: findFlowList
	  * @Description: 订单流程
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findFlowList(PageData pd);
	
	/**
	 * 
	  * @Title: save
	  * @Description: 添加订单流程
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void save(PageData pd);
	
	/**
	 * 
	  * @Title: update
	  * @Description: 立即抢单
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void update(PageData pd);
	
	/**
	 * 
	  * @Title: updateStatus
	  * @Description: 确认开工、取消订单（普通订单取消）支付方式
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void updateStatus(PageData pd);
	
	/**
	 * 
	  * @Title: updateOrders
	  * @Description: 确认接单
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void updateOrders(PageData pd);
	
	/**
	 * 
	  * @Title: updateServer
	  * @Description: 取消订单（接受其他人服务的）
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void updateServer(PageData pd);
	
	/**
	 * 
	  * @Title: updatePay
	  * @Description: 支付方式
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void updatePay(PageData pd);
	
	/**
	 * 
	  * @Title: findfamilyList
	  * @Description: 查询家人的sessionid 
	  * @param @param pd
	  * @param @return    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
	public  List<PageData> findfamilyList(PageData pd);
	
}
