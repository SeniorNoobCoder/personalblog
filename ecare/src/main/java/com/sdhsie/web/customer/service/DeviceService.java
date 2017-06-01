package com.sdhsie.web.customer.service;

import java.util.List;

import com.sdhsie.base.util.Page;
import com.sdhsie.base.util.PageData;

public interface DeviceService {
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
	 * 
	 * @Title: delete
	 * @Description: 删除
	 * @param @param ids 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void delete(String[] ids);

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
	  * @Title: findTrashPage
	  * @Description: 查询删除的数据
	  * @param @param page
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findTrashPage(Page page);

	/**
	 * 
	  * @Title: clearUser
	  * @Description: 清空删除数据
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void clearTrash(PageData pd);

	/**
	 * 
	  * @Title: revertUser
	  * @Description: 还原删除数据
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void revertTrash(PageData pd);

	public void delete(PageData pd);


}
