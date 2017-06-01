package com.sdhsie.app.family.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdhsie.app.family.service.AppServerService;
import com.sdhsie.app.server.service.impl.AppOrderServiceImpl;
import com.sdhsie.base.dao.impl.IDaoImpl;
import com.sdhsie.base.util.PageData;

@Service
@Transactional
public class AppServerServiceImpl implements AppServerService{
	Logger logger = Logger.getLogger(AppOrderServiceImpl.class);
	String mapperUrl = "com.ecare.app.server.appserver.";
	@Autowired
	private IDaoImpl dao;
	
	
	//下订单
	public void saveOder(PageData pd) {
		try {
			dao.save(mapperUrl + "saveOder", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}
	
	
	//查询服务对象
	@SuppressWarnings("unchecked")
	public List<PageData> findServiceList(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findServiceList", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}


	//根据id查询用户的session
	public PageData findUserInfo(PageData pd) {
		try {
			pd = (PageData) dao.findForObject(mapperUrl + "findUserInfo", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}


	//查询要推送的商户
	public List<PageData> findTsList(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findTsList", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}


	//查询要推送的商户(服务信息 合作商下的商户进行推送）
	public List<PageData> findTshList(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findTshList", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

}
