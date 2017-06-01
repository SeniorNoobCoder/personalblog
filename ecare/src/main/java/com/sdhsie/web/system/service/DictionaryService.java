/**
 * @Title: DictionaryService.java
 * @Package com.sdhsie.web.system.service
 * @Description: TODO
 * Copyright: Copyright (c) 2016 
 * Company:济南云融信息技术有限公司
 * 
 * @author Administrator
 * @date 2016-6-29 下午05:19:29
 * @version V1.0
 */

package com.sdhsie.web.system.service;

import java.util.List;

import com.sdhsie.base.util.Page;
import com.sdhsie.base.util.PageData;

/**
 * @ClassName: DictionaryService
 * @Description: TODO
 * @author Administrator
 * @date 2016-6-29 下午05:19:29
 *
 */

public interface DictionaryService {
	
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
	  * @Title: findList
	  * @Description: 查询信息列表
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findList(PageData pd);
	
	/**
	 * 
	  * @Title: findDicList
	  * @Description: TODO
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findDicList(PageData pd);
	
	/**
	 * 
	  * @Title: findInfo
	  * @Description: 查询字典信息
	  * @param @param pd
	  * @param @return    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
	public PageData findInfo(PageData pd);
	
	/**
	 * 
	  * @Title: save
	  * @Description: 添加字典信息
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void save(PageData pd);
	
	/**
	 * 
	  * @Title: update
	  * @Description: 修改字典信息
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void update(PageData pd);
	
	/**
	 * 
	  * @Title: delete
	  * @Description: 删除字典信息
	  * @param @param ids    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void delete(PageData pd);
	/**
	 * 
	  * @Title: findDelNum
	  * @Description: 查询删除的字典信息
	  * @param @return    设定文件
	  * @return Integer    返回类型
	  * @throws
	 */
	public Integer findDelNum();
	/**
	 * 
	  * @Title: findTrashPage
	  * @Description: 垃圾箱列表信息
	  * @param @param page
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findTrashPage(Page page);
	/**
	 * 
	  * @Title: revertDictionary
	  * @Description: 还原删除的信息
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void revertDictionary(PageData pd);
	
	/**
	 * 
	  * @Title: clearDictionary
	  * @Description: 清空删除数据
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void clearDictionary(PageData pd);
	
	

}
