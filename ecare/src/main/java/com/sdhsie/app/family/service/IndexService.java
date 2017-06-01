package com.sdhsie.app.family.service;

import java.util.List;

import com.sdhsie.base.util.PageData;

public interface IndexService {
	
	/**
	 * 
	  * @Title: findCarouseList
	  * @Description: 查询轮播图
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findCarouseList();
	
	
	/**
	 * 
	  * @Title: findRootList
	  * @Description: 查询根服务、查询优选服务 生活服务1级菜单
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findRootList(PageData pd);
	
	
	/**
	 * 
	  * @Title: findHotList
	  * @Description: 热门服务
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findHotList(PageData pd);
	
	/**
	 * 
	  * @Title: findHotInfo
	  * @Description: 查看热门服务详情
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findHotInfo(PageData pd);
	
	/**
	 * 
	  * @Title: findInfo
	  * @Description: 查看详情
	  * @param @param pd
	  * @param @return    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
	public PageData findInfo(PageData pd);
	
	/**
	 * 
	  * @Title: findRootInfo
	  * @Description: 获取服务分类信息（用于查找这个服务分类信息下的信息也就是server_info表中的信息）
	  * @param @param pd
	  * @param @return    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
	public PageData findRootInfo(PageData pd);
	
	/**
	 * 
	  * @Title: findServerList
	  * @Description: 根据partner_id查找相应server_info中的信息
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findServerList(PageData pd);
	
	
	/**
	 * 
	  * @Title: findHotNum
	  * @Description: 热门服务数量
	  * @param @param pd
	  * @param @return    设定文件
	  * @return int    返回类型
	  * @throws
	 */
	public int findHotNum(PageData pd);
	
	/**
	 * 
	  * @Title: findCollectNum
	  * @Description: 查询收藏数量 根据服务信息
	  * @param @param pd
	  * @param @return    设定文件
	  * @return int    返回类型
	  * @throws
	 */
	public int findCollectNum(PageData pd);
	
	/**
	 * 服务信息列表条数
	 * @param pd
     * @return
     */
	public int findServerListCount(PageData pd);
	
	/**
	 * 工作分类
	  * @Title: findJobList
	  * @Description: TODO
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findJobList(PageData pd);
	/**
	 * 工作分类描述
	  * @Title: findJobRemarkList
	  * @Description: TODO
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findJobRemarkList(PageData pd);
	
	/**
	 * 
	  * @Title: findPjList
	  * @Description: 在服务详情中评价信息
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public PageData findPjInfo(PageData pd);
	
	
	/**
	 * 
	  * @Title: findPjList
	  * @Description: 评价列表
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findPjList(PageData pd);
	
	/**
	 * 
	  * @Title: findPjListNum
	  * @Description: 评价列表数量
	  * @param @param pd
	  * @param @return    设定文件
	  * @return int    返回类型
	  * @throws
	 */
	public int findPjListNum(PageData pd);
	
	/**
	 * 
	  * @Title: findAreaList
	  * @Description: 地区
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findAreaList(PageData pd);
	
	
	/**
	 * 
	  * @Title: findAreaInfo
	  * @Description: 地区信息
	  * @param @param pd
	  * @param @return    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
	public PageData findAreaInfo(PageData pd);
}
