package com.sdhsie.web.system.service.impl;


import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdhsie.base.dao.impl.IDaoImpl;
import com.sdhsie.base.util.PageData;
import com.sdhsie.web.system.service.LogService;

@Service
@Transactional
public class LogServiceImpl implements LogService {
	Logger logger = Logger.getLogger(AreaServiceImpl.class);
	String mapperUrl = "com.ecare.web.system.log.";
	@Autowired
	private IDaoImpl dao;
	
	
	public void save(PageData pd) {
		try {
			dao.save(mapperUrl + "save", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}



	


}
