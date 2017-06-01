
package com.sdhsie.web.system.service;

import java.util.List;

import com.sdhsie.base.util.Page;
import com.sdhsie.base.util.PageData;

/**
 * @ClassName: MenuService
 * @Description: TODO
 * @author Administrator
 * @date 2016-7-23 上午11:22:54
 *
 */

public interface QuickMenuService {
	
	/**
	 * 
	  * @Title: findList
	  * @Description: TODO
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findList(PageData pd);
	
	/**
	 * 
	  * @Title: update
	  * @Description: TODO
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void update(PageData pd);
	
	/**
	 * 
	  * @Title: findInfo
	  * @Description: TODO
	  * @param @param pd
	  * @param @return    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
	public PageData findInfo(PageData pd);
	
	/**
	 * 
	  * @Title: save
	  * @Description: TODO
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void save(PageData pd);
	
	/**
	 * 
	  * @Title: delete
	  * @Description: TODO
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void delete(String[] ids);

	public PageData findPd(PageData p);

}
