package com.sdhsie.web.system.service;

import java.util.List;

import com.sdhsie.base.util.Page;
import com.sdhsie.base.util.PageData;

public interface RoadService {
	/**
	 * 
	  * @Title: findListPage
	  * @Description: 查询信息列表
	  * @param @param page
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findListPage(Page page);
	/**
	 * 
	  * @Title: findDelNum
	  * @Description: 查询删除数量
	  * @param @return    设定文件
	  * @return Integer    返回类型
	  * @throws
	 */
	public Integer findDelNum();
	/**
	 * 
	  * @Title: save
	  * @Description: 添加信息
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void save(PageData pd);
	/**
	 * 
	  * @Title: findInfo
	  * @Description: 查看详情信息
	  * @param @param pd
	  * @param @return    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
	public PageData findInfo(PageData pd);
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
	  * @Title: delete
	  * @Description: 删除信息
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void delete(PageData pd);
	/**
	 * 
	  * @Title: findList
	  * @Description: 查询删除列表
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findList(PageData pd);
	/**
	 * 
	  * @Title: clearOrganize
	  * @Description: 清空删除的数据
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void clearRoad(PageData pd);
	/**
	 * 
	  * @Title: revertOrganize
	  * @Description: 还原删除的数据
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void revertRoad(PageData pd);
	/**
	 * 
	  * @Title: findTree
	  * @Description: 树列表信息
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findTree(PageData pd);
	
	public List<PageData> findSonTree(PageData pd);
	
	public List<PageData> findsump(PageData pd);
	public List<PageData> findsumo(PageData pd);
	public List<PageData> findRoadList(PageData pd);
	
}
