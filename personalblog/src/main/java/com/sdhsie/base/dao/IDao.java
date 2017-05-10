/**    
 * 文件名：IDao.java    
 *    
 * 版本信息：    
 * 日期：2014-2-10    
 * Copyright 足下 Corporation 2014     
 * 版权所有    
 *    
 */
package com.sdhsie.base.dao;

import com.sdhsie.base.util.PageData;
import com.sdhsie.base.util.PagerList;

/**    
 *     
 * 项目名称：web-core    
 * 类名称：IDao    
 * 类描述： baseDao的接口   
 * 创建人：anxingtao    
 * 创建时间：2014-2-10 上午09:27:15    
 * 修改人：anxingtao    
 * 修改时间：2014-2-10 上午09:27:15    
 * 修改备注：    
 * @version     
 *     
 */
public abstract interface IDao {
	  public abstract Object save(String paramString, Object paramObject)
	    throws Exception;
	  
	  public abstract Object update(String paramString, Object paramObject)
	    throws Exception;

	  public abstract Object delete(String paramString, Object paramObject)
	    throws Exception;

	  public abstract Object findForObject(String paramString, Object paramObject)
	    throws Exception;

	  public abstract Object findForList(String paramString, Object paramObject)
	    throws Exception;

	  public abstract Object findForMap(String paramString1, Object paramObject, String paramString2, String paramString3)
	    throws Exception;

	  public abstract PagerList<PageData> findForPageList(PagerList<PageData> PagerList, PageData paramPageData)
	    throws Exception;
}
































