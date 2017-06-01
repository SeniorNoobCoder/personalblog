package com.sdhsie.app.server.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdhsie.app.server.service.AppServerMineService;
import com.sdhsie.base.dao.impl.IDaoImpl;
import com.sdhsie.base.util.PageData;
import com.sdhsie.base.util.Verify;

@Service
@Transactional
public class AppServerMineServiceImpl implements AppServerMineService{

	Logger logger = Logger.getLogger(AppServerMineServiceImpl.class);
	String mapperUrl = "com.ecare.app.server.servermine.";
	@Autowired
	private IDaoImpl dao;
	
	//意见反馈
	public void saveFeedback(PageData pd) {
		try {
			dao.save(mapperUrl + "saveFeedback", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}

	//商家信息
	public PageData findUserInfo(PageData pd) {
		try {
			pd = (PageData) dao.findForObject(mapperUrl + "findUserInfo", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}

	//好评数量
	public int findPraiseNum(PageData pd) {
		Integer num = 0;
		try {
			num =  (Integer) dao.findForObject(mapperUrl + "findPraiseNum", pd);
			
			if(!(Verify.verifyIsNotNull(num))){
				num=0;
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return num;
	}

}
