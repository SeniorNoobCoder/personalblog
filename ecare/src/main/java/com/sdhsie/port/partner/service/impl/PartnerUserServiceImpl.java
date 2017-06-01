package com.sdhsie.port.partner.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdhsie.base.dao.impl.IDaoImpl;
import com.sdhsie.base.util.PageData;
import com.sdhsie.port.partner.service.PartnerUserService;


@Service
@Transactional
public class PartnerUserServiceImpl implements PartnerUserService {
	Logger logger = Logger.getLogger(PartnerUserServiceImpl.class);
	String mapperUrl = "com.ecare.web.port.partner.user.";
	@Autowired
	private IDaoImpl dao;
	

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


	public PageData findLoginInfo(PageData pd) {
		PageData p = null;
		try {
			p = (PageData) dao.findForObject(mapperUrl + "findLoginInfo", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return p;
	}


	public void updateUser(PageData pd) {
		try {
			dao.update(mapperUrl + "updateUser", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}



}
