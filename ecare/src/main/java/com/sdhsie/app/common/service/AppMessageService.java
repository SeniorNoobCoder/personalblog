package com.sdhsie.app.common.service;

import java.util.List;

import com.sdhsie.base.util.PageData;

public interface AppMessageService {
	
	/**
	 * 
	  * @Title: findMessageOneInfo
	  * @Description: 查询系统消息最新的消息
	  * @param @param pd
	  * @param @return    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
	public PageData findMessageOneInfo(PageData pd);
	
	/**
	 * 
	  * @Title: findDeviceOneInfo
	  * @Description: 家人进出安全岛
	  * @param @param pd
	  * @param @return    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
	public PageData findDeviceOneInfo(PageData pd);
	
	/**
	 * 
	  * @Title: findOrderOneInfo
	  * @Description: 订单消息
	  * @param @param pd
	  * @param @return    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
	public PageData findOrderOneInfo(PageData pd);
	
	/**
	 * 
	  * @Title: findMessageNum
	  * @Description: 消息数量
	  * @param @param pd
	  * @param @return    设定文件
	  * @return int    返回类型
	  * @throws
	 */
	public int findMessageNum(PageData pd);
	
	/**
	 * 
	  * @Title: findDeviceNum
	  * @Description: 进出安全岛的数量
	  * @param @param pd
	  * @param @return    设定文件
	  * @return int    返回类型
	  * @throws
	 */
	public int findDeviceNum(PageData pd);
	
	
	/**
	 * 
	  * @Title: findOrderNum
	  * @Description: 订单数量
	  * @param @param pd
	  * @param @return    设定文件
	  * @return int    返回类型
	  * @throws
	 */
	public int findOrderNum(PageData pd);
	
	
	/**
	 * 
	  * @Title: findmessageList
	  * @Description: 消息列表
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findmessageList(PageData pd);
	
	/**
	 * 
	  * @Title: findMessageListNum
	  * @Description: 消息数量
	  * @param @param pd
	  * @param @return    设定文件
	  * @return int    返回类型
	  * @throws
	 */
	public int findMessageListNum(PageData pd);
	
	/**
	 * 
	  * @Title: findMessageInfo
	  * @Description: 消息详情
	  * @param @param pd
	  * @param @return    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
	public PageData findMessageInfo(PageData pd);
	
	
	/**
	 * 
	  * @Title: findOrderList
	  * @Description: 订单列表
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findOrderList(PageData pd);
	
	
	/**
	 * 
	  * @Title: findDeviceList
	  * @Description: 进出安全岛未读消息列表
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findDeviceList(PageData pd);
	/**
	 * 
	  * @Title: findDeviceInfo
	  * @Description: 进出安全岛未读信息详情
	  * @param @param pd
	  * @param @return    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
	public PageData findDeviceInfo(PageData pd);
	
	/**
	 * 
	  * @Title: updateMessage
	  * @Description: 修改消息状态
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void updateMessage(PageData pd);
	
	/**
	 * 
	  * @Title: updateDevice
	  * @Description: 修改进出安全岛的状态
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void updateDevice(PageData pd);
	
	/**
	 * 
	  * @Title: updateOrder
	  * @Description: 修改订单的状态
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void updateOrder(PageData pd);
	
	/**
	 * 
	  * @Title: findUsList
	  * @Description: 关于我们、注册
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public PageData findUsInfo(PageData pd);
	
	/**
	 * 
	  * @Title: findEmpreinteList
	  * @Description: 历史足迹
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findEmpreinteList(PageData pd);
	
	/**
	 * 
	  * @Title: updateUser
	  * @Description: 修改手机号（也就是登录名）
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void updateUser(PageData pd);
	
	/**
	 * 
	  * @Title: findApkInfo
	  * @Description: apk版本
	  * @param @param pd
	  * @param @return    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
	public PageData findApkInfo(PageData pd);
	
	
	/**
	 * 
	  * @Title: findMessageShList
	  * @Description  商户版的订单消息
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findMessageShList(PageData pd);
	
	/**
	 * 
	  * @Title: findMessageShNum
	  * @Description: 商户版订单消息数量
	  * @param @param pd
	  * @param @return    设定文件
	  * @return int    返回类型
	  * @throws
	 */
	public int findMessageShNum(PageData pd);
	
	/**
	 * 
	  * @Title: findShOrderInfo
	  * @Description: 最新的订单消息（商户版）
	  * @param @param pd
	  * @param @return    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
	public PageData findShOrderInfo(PageData pd);
}
