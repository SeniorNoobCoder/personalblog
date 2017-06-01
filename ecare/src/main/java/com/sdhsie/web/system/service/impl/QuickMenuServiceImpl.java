
package com.sdhsie.web.system.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdhsie.base.dao.impl.IDaoImpl;
import com.sdhsie.base.util.PageData;
import com.sdhsie.web.system.service.QuickMenuService;

/**
 * @ClassName: MenuServiceImpl
 * @Description: TODO
 * @author Administrator
 * @date 2016-7-23 上午11:23:27
 *
 */
@Service
@Transactional
public class QuickMenuServiceImpl implements QuickMenuService{
	Logger logger = Logger.getLogger(QuickMenuServiceImpl.class);
	String mapperUrl = "com.ecare.web.system.quickMenu.";
	@Autowired
	private IDaoImpl dao;

	/*
	  * <p>Title: findListPage</p>
	  * <p>Description: </p>
	  * @param page
	  * @return
	  * @see com.sdhsie.web.common.service.MenuService#findListPage(com.sdhsie.base.util.Page)
	  */
	
	
	@SuppressWarnings("unchecked")
	public List<PageData> findList(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findList", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	/*
	  * <p>Title: update</p>
	  * <p>Description: </p>
	  * @param pd
	  * @see com.sdhsie.web.common.service.QuickMenuService#update(com.sdhsie.base.util.PageData)
	  */
	
	
	public void update(PageData pd) {
		try {
			dao.save(mapperUrl + "update", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}

	/*
	  * <p>Title: findInfo</p>
	  * <p>Description: </p>
	  * @param pd
	  * @return
	  * @see com.sdhsie.web.common.service.QuickMenuService#findInfo(com.sdhsie.base.util.PageData)
	  */
	
	
	public PageData findInfo(PageData pd) {
		try {
			pd = (PageData) dao.findForObject(mapperUrl + "findInfo", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}

	/*
	  * <p>Title: delete</p>
	  * <p>Description: </p>
	  * @param pd
	  * @see com.sdhsie.web.common.service.QuickMenuService#delete(com.sdhsie.base.util.PageData)
	  */
	
	
	public void delete(String[] ids) {
		try {
			dao.deleteBatch(mapperUrl + "delete", ids);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}

	/*
	  * <p>Title: save</p>
	  * <p>Description: </p>
	  * @param pd
	  * @see com.sdhsie.web.common.service.QuickMenuService#save(com.sdhsie.base.util.PageData)
	  */
	
	
	public void save(PageData pd) {
		try {
			dao.save(mapperUrl + "save", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}

	public PageData findPd(PageData pd) {
		try {
			pd = (PageData) dao.findForObject(mapperUrl + "findPd", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}

}
