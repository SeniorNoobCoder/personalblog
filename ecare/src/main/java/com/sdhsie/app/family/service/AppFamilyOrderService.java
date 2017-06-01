package com.sdhsie.app.family.service;

import java.util.List;

import com.sdhsie.base.util.PageData;

public interface AppFamilyOrderService {
	
	/**
	 * 
	  * @Title: findDeviceList
	  * @Description: 根据用户查询设备编号
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findDeviceList(PageData pd);
	
	/**
	 * 
	  * @Title: findFamilyOrderList
	  * @Description: 查询家人版的订单列表
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findFamilyOrderList(PageData pd);
	
	/**
	 * 
	  * @Title: findFamilyOrderNum
	  * @Description: 查询订单数量
	  * @param @param pd
	  * @param @return    设定文件
	  * @return int    返回类型
	  * @throws
	 */
	public int findFamilyOrderNum(PageData pd);
	
	/**
	 * 
	  * @Title: updateOrder
	  * @Description: 取消订单
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void updateOrder(PageData pd);
	
	/**
	 * 
	  * @Title: findUserList
	  * @Description: 查询商户信息
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findUserList(PageData pd);
	
	/**
	 * 
	  * @Title: findUserNum
	  * @Description: 商户数量
	  * @param @param pd
	  * @param @return    设定文件
	  * @return int    返回类型
	  * @throws
	 */
	public int findUserNum(PageData pd);
	
	
	/**
	 * 
	  * @Title: findStatus
	  * @Description: 查询订单状态
	  * @param @param pd
	  * @param @return    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
	public PageData findStatusInfo(PageData pd);
	
	/**
	 * 
	  * @Title: findAAssInfo
	  * @Description: 根据编号查询评论id
	  * @param @param pd
	  * @param @return    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
	public PageData findAAssInfo(PageData pd);
	
	/**
	 * 
	  * @Title: findOrderInfo
	  * @Description: 订单详情
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
	  * @Title: findMerchantInfo
	  * @Description: 商户信息
	  * @param @param pd
	  * @param @return    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
	public PageData findMerchantInfo(PageData pd);
	
	/**
	 * 
	  * @Title: saveAssessment
	  * @Description: 订单评价
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void saveAssessment(PageData pd);
	
	/**
	 * 
	  * @Title: saveAssessmentImage
	  * @Description: 评价图片
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void saveAssessmentImage(PageData pd);
	
	/**
	 * 
	  * @Title: findAssessmentList
	  * @Description: 根据订单编号查询评价
	  * @param @param pd
	  * @param @return    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
	public PageData findAssessmentInfo(PageData pd);
	
	/**
	 * 
	  * @Title: findStatInfo
	  * @Description: 星级及服务次数
	  * @param @param pd
	  * @param @return    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
	public List<PageData> findStatInfo(PageData pd);
	
	public List<PageData> findStat(PageData pd);
}
