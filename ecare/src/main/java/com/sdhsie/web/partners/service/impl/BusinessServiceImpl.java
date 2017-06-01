package com.sdhsie.web.partners.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdhsie.base.dao.impl.IDaoImpl;
import com.sdhsie.base.util.Page;
import com.sdhsie.base.util.PageData;
import com.sdhsie.web.partners.service.BusinessService;

@Service
@Transactional
public class BusinessServiceImpl implements BusinessService {
	Logger logger = Logger.getLogger(BusinessServiceImpl.class);
	String mapperUrl = "com.ecare.web.partners.business.";
	@Autowired
	private IDaoImpl dao;
	
	
	/*
	 * 
	  * <p>Title: findList</p>
	  * <p>Description: </p>
	  * @param pd
	  * @return
	  * @see com.sdhsie.web.system.service.UserService#findList(com.sdhsie.base.util.PageData)
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findListPage(Page page) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findListPage", page);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}


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

	public List<PageData> findBusinessIsWorkList(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findBusinessIsWorkList", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}


	/*
	 * 
	  * <p>Title: findInfo</p>
	  * <p>Description: </p>
	  * @param pd
	  * @return
	  * @see com.sdhsie.web.system.service.UserService#findInfo(com.sdhsie.base.util.PageData)
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
	 * 
	  * <p>Title: update</p>
	  * <p>Description: </p>
	  * @param pd
	  * @see com.sdhsie.web.system.service.UserService#update(com.sdhsie.base.util.PageData)
	 */
	public void update(PageData pd) {
		try {
			dao.update(mapperUrl + "update", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	/*
	 * 
	  * <p>Title: delete</p>
	  * <p>Description: </p>
	  * @param id
	  * @see com.sdhsie.web.system.service.UserService#delete(java.lang.String[])
	 */
	public void delete(String[] id) {
		try {
			dao.updateBatch(mapperUrl + "delete", id);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}


	public PageData findAuthent(PageData pd) {
		try {
			pd = (PageData) dao.findForObject(mapperUrl + "findAuthent", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}

	public List<PageData> findServerCategory(PageData pd){
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findServerCategory", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	public int findNum(PageData pd) {
		Integer num = 0;
		try {
			num = (Integer) dao.findForObject(mapperUrl + "findNum", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return num;
	}
}
