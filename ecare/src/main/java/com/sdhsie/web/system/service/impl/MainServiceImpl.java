package com.sdhsie.web.system.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdhsie.base.dao.impl.IDaoImpl;
import com.sdhsie.base.util.PageData;
import com.sdhsie.web.system.service.MainService;

@Service
@Transactional
public class MainServiceImpl implements MainService {
	Logger logger = Logger.getLogger(MainServiceImpl.class);
	String mapperUrl = "com.ecare.web.system.main.";
	@Autowired
	private IDaoImpl dao;
	
	
	/*
	 * 
	  * <p>Title: findRoleList</p>
	  * <p>Description: </p>
	  * @param pd
	  * @return
	  * @see com.sdhsie.web.system.service.MainService#findRoleList(com.sdhsie.base.util.PageData)
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> findRoleList(PageData pd) {
		List<Integer> list = null;
		try {
			list = (List<Integer>) dao.findForList(mapperUrl + "findRoleList", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}
	

	
	@SuppressWarnings("unchecked")
	public List<PageData> findMenuList(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findMenuList", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}


	@SuppressWarnings("unchecked")
	public List<PageData> findButtonList(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findButtonList", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}



	@SuppressWarnings("unchecked")
	public List<PageData> findRoleLs(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findRoleLs", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}



}
