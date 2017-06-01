package com.sdhsie.app.server.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdhsie.app.server.service.AppLoginService;
import com.sdhsie.base.dao.impl.IDaoImpl;
import com.sdhsie.base.util.PageData;
import com.sdhsie.base.util.Verify;

@Service
@Transactional
public class AppLoginServiceImpl implements AppLoginService{
	
	Logger logger = Logger.getLogger(AppLoginServiceImpl.class);
	String mapperUrl = "com.ecare.app.server.login.";
	@Autowired
	private IDaoImpl dao;

	//判断手机号是否注册
	@Override
	public int findPhone(PageData pd) {
		Integer num =0;
		try {
			num =  (Integer) dao.findForObject(mapperUrl + "findPhone", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return num;
	}
	//注册
	@Override
	public void save(PageData pd) {
		try {
			dao.save(mapperUrl + "save", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}
	//登录
	@Override
	public PageData findLoginInfo(PageData pd) {
		try {
			pd = (PageData) dao.findForObject(mapperUrl + "findLoginInfo", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}
	
	//修改信息
	public void update(PageData pd) {
		try {
			dao.update(mapperUrl + "update", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}

	//商户认证信息添加
	public void saveBusiness(PageData pd) {
		try {
			dao.save(mapperUrl + "saveBusiness", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}
	//保存验证码
	public void saveyzm(PageData pd) {
		try {
			dao.save(mapperUrl + "saveyzm", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}

	//查询验证码信息
	public PageData findYzmInfo(PageData pd) {
		try {
			pd = (PageData) dao.findForObject(mapperUrl + "findYzmInfo", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}
	//我的完成量
	public int findNum(PageData pd) {
		Integer num = 0;
		try {
			num =  (Integer) dao.findForObject(mapperUrl + "findNum", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return num;
	}

	//我的收入
	public double findMany(PageData pd) {
		Double num = 0.0;
		try {
			num =  (Double) dao.findForObject(mapperUrl + "findMany", pd);
			if(!(Verify.verifyIsNotNull(num))){
				num=0.0;
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return num;
	}

}
