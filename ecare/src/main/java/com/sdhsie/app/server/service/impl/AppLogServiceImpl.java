package com.sdhsie.app.server.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdhsie.app.server.service.AppLogService;
import com.sdhsie.base.dao.impl.IDaoImpl;
import com.sdhsie.base.util.PageData;
import com.sdhsie.web.system.service.impl.AreaServiceImpl;

@Service
@Transactional
public class AppLogServiceImpl implements AppLogService{
	
	Logger logger = Logger.getLogger(AreaServiceImpl.class);
	String mapperUrl = "com.ecare.app.server.applog.";
	@Autowired
	private IDaoImpl dao;
	
	
	/**
	 * 保存日志
	 */
	public void save(PageData pd) {
		try {
			dao.save(mapperUrl + "save", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}

}
