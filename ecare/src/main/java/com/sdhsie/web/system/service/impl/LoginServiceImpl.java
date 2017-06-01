package com.sdhsie.web.system.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdhsie.base.dao.impl.IDaoImpl;
import com.sdhsie.base.util.PageData;
import com.sdhsie.web.system.service.LoginService;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {
	Logger logger = Logger.getLogger(LoginServiceImpl.class);
	String mapperUrl = "com.ecare.web.system.login.";
	@Autowired
	private IDaoImpl dao;
	
	
	
	/*
	 * 
	  * <p>Title: findUserInfo</p>
	  * <p>Description: </p>
	  * @param pd
	  * @return
	  * @see com.sdhsie.web.system.service.LoginService#findUserInfo(com.sdhsie.base.util.PageData)
	 */
	public PageData findUserInfo(PageData pd) {
		try {
			pd = (PageData) dao.findForObject(mapperUrl + "findUserInfo", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}



	
	/*
	 * 
	  * <p>Title: findUserPath</p>
	  * <p>Description: </p>
	  * @param p
	  * @return
	  * @see com.sdhsie.web.system.service.LoginService#findUserPath(com.sdhsie.base.util.PageData)
	 */
	public String findUserPath(PageData p) {
		String user_ids = null;
		try {
			user_ids = (String) dao.findForObject(mapperUrl + "findUserPath", p);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return user_ids;
	}


	/*
	 * 
	  * <p>Title: findUserOrganize</p>
	  * <p>Description: </p>
	  * @param pd
	  * @return
	  * @see com.sdhsie.web.system.service.LoginService#findUserOrganize(com.sdhsie.base.util.PageData)
	 */
	public PageData findUserOrganize(PageData pd) {
		try {
			pd = (PageData) dao.findForObject(mapperUrl + "findUserorganizeinfo", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}


	/*
	 * 
	  * <p>Title: findUnitOrgId</p>
	  * <p>Description: </p>
	  * @param pd
	  * @return
	  * @see com.sdhsie.web.system.service.LoginService#findUnitOrgId(com.sdhsie.base.util.PageData)
	 */
	public Integer findUnitOrgId(PageData pd) {
		Integer id = 0;
		try {
			id =  (Integer) dao.findForObject(mapperUrl + "findUnitOrgId", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}


}
