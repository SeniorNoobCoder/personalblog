package com.sdhsie.web.system.service;

import java.util.List;

import com.sdhsie.base.util.Page;
import com.sdhsie.base.util.PageData;

public interface OrganizeService {
	/**
	 * 
	 * @Title: findList
	 * @Description: 查询信息列表
	 * @param @param pd
	 * @param @return 设定文件
	 * @return List<PageData> 返回类型
	 * @throws
	 */
	public List<PageData> findListPage(Page page);

	/**
	 * 
	 * 
	 * @Title: save
	 * @Description: 添加
	 * @param @param pd 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void save(PageData pd);

	/**
	 * 
	 * 
	 * @Title: findInfo
	 * @Description: TODO
	 * @param @param pd
	 * @param @return 设定文件
	 * @return PageData 返回类型
	 * @throws
	 */
	public PageData findInfo(PageData pd);

	/**
	 * 
	 * 
	 * @Title: update
	 * @Description: 修改
	 * @param @param pd 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void update(PageData pd);

	/**
	 * 
	  * @Title: delete
	  * @Description: 删除
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
	  * @Title: clearUser
	  * @Description: 清空删除数据
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void clearOrganize(PageData pd);

	/**
	 * 
	  * @Title: revertUser
	  * @Description: 还原删除数据
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void revertOrganize(PageData pd);
	
	/**
	 * 
	  * @Title: findDelNum
	  * @Description: 查询删除数据的数量
	  * @param @return    设定文件
	  * @return Integer    返回类型
	  * @throws
	 */
	public Integer findDelNum();

	/**
	 * 
	  * @Title: findDelNum
	  * @Description: TODO
	  * @param @return    设定文件
	  * @return Integer    返回类型
	  * @throws
	 */
	public Integer findOrganizeId(PageData pd);


}
