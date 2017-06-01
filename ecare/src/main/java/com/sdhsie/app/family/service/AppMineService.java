package com.sdhsie.app.family.service;

import java.util.List;

import com.sdhsie.base.util.PageData;

public interface AppMineService {
	
	/**
	 * 
	  * @Title: findRelationshipList
	  * @Description: 查询关系
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findRelationshipList(PageData pd);
	
	/**
	 * 
	  * @Title: findDeviceNum
	  * @Description: 查询设备是否存在
	  * @param @param pd
	  * @param @return    设定文件
	  * @return int    返回类型
	  * @throws
	 */
	public int findDeviceNum(PageData pd);
	
	
	/**
	 * 
	  * @Title: findRepeatInfo
	  * @Description: 查找设备号是否已经绑定
	  * @param @param pd
	  * @param @return    设定文件
	  * @return int    返回类型
	  * @throws
	 */
	public int findRepeatInfo(PageData pd);
	
	/**
	 * 
	  * @Title: saveRelation
	  * @Description: 绑定设备
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void saveRelation(PageData pd);

	/**
	 * 
	  * @Title: updateDevice
	  * @Description: 修改设备信息
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void updateDevice(PageData pd);
	
	
	/**
	 * 
	  * @Title: findDeviceInfo
	  * @Description: 查询设备信息
	  * @param @param pd
	  * @param @return    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
	public PageData findDeviceInfo(PageData pd);
	
	/**
	 * 
	  * @Title: findFamilyList
	  * @Description: 查找家庭成员
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findFamilyList(PageData pd);
	
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
	public int findDateNum(PageData pd);
	
	/**
	 * 
	  * @Title: saveCollect
	  * @Description: 收藏
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void saveCollect(PageData pd);
	
	/**
	 * 
	  * @Title: findCollectList
	  * @Description: 收藏列表
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findCollectList(PageData pd);
	
	/**
	 * 
	  * @Title: findCollectNum
	  * @Description: 收藏数量
	  * @param @param pd
	  * @param @return    设定文件
	  * @return int    返回类型
	  * @throws
	 */
	public int findCollectNum(PageData pd);
	
	/**
	 * 
	  * @Title: delete
	  * @Description: 取消收藏
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void delete(PageData pd);
	
	/**
	 * 
	  * @Title: deleteDevice
	  * @Description: 解绑设备号
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void deleteDevice(PageData pd);
	
	
	/**
	 * 
	  * @Title: findStartNum
	  * @Description: 服务质量个数
	  * @param @param pd
	  * @param @return    设定文件
	  * @return int    返回类型
	  * @throws
	 */
	public int findStartNum(PageData pd);
	
	/**
	 * 
	  * @Title: findEvaluateList
	  * @Description: 查询评论信息
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findEvaluateList(PageData pd);
	
	/**
	 * 
	  * @Title: findEvaluateNum
	  * @Description: 查询评论数量
	  * @param @param pd
	  * @param @return    设定文件
	  * @return int    返回类型
	  * @throws
	 */
	public int findEvaluateNum(PageData pd);
	
	/**
	 * 
	  * @Title: findImageList
	  * @Description: 查看评论图片
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findImageList(PageData pd);
	
	/**
	 * 
	  * @Title: findHelpList
	  * @Description: TODO
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findHelpList(PageData pd);
	
	/**
	 * 
	  * @Title: updatefamily
	  * @Description: 修改家人设备信息
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void updatefamily(PageData pd);
	
	/**
	 * 
	  * @Title: updatefamilyUser
	  * @Description: 修改设备客户信息
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void updatefamilyUser(PageData pd);
	
	/**
	 * 
	  * @Title: updatefamilySn
	  * @Description: 修改安全岛名称
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void updatefamilySn(PageData pd);
	
	/**
	 * 
	  * @Title: findUserInfo
	  * @Description: 查询设备家人信息
	  * @param @param pd
	  * @param @return    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
	public PageData findUserInfo(PageData pd);	
	
	
}

