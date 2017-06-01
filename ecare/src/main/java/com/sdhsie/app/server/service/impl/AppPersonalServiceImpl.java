package com.sdhsie.app.server.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdhsie.app.server.service.AppPersonalService;
import com.sdhsie.base.dao.impl.IDaoImpl;
import com.sdhsie.base.util.PageData;
import com.sdhsie.base.util.Verify;

@Service
@Transactional
public class AppPersonalServiceImpl implements AppPersonalService{
	
	Logger logger = Logger.getLogger(AppPersonalServiceImpl.class);
	String mapperUrl = "com.ecare.app.server.personal.";
	@Autowired
	private IDaoImpl dao;

	//忘记密码
	public void update(PageData pd) {
		try {
			dao.update(mapperUrl + "update", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}

	//修改密码、个人信息、个人开工状态
	public void updateStatus(PageData pd) {
		try {
			dao.update(mapperUrl + "updateStatus", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}

	//根据合作商code进行查找
	public PageData findPartnerInfo(PageData pd) {
		try {
			pd = (PageData) dao.findForObject(mapperUrl + "findPartnerInfo", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}

	//修改商户认证信息
	public void updateUser(PageData pd) {
		try {
			dao.update(mapperUrl + "updateUser", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}

	//查询商户认证信息
	public PageData findUserInfo(PageData pd) {
		try {
			pd = (PageData) dao.findForObject(mapperUrl + "findUserInfo", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}

	//查询所有的认证信息
	public PageData findAllInfo(PageData pd) {
		try {
			pd = (PageData) dao.findForObject(mapperUrl + "findAllInfo", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}

	/**
	 * 获取认证信息
	 */
	public PageData authentInfo(PageData pd) {
		try {
			pd = (PageData) dao.findForObject(mapperUrl + "authentInfo", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}

	//总的收入
	public double findAmountSum(PageData pd) {
		Double num = 0.0;
		try {
			num =  (Double) dao.findForObject(mapperUrl + "findAmountSum", pd);
			if(!(Verify.verifyIsNotNull(num))){
				num=0.0;
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return num;
	}

	//当天收入
	public double findDateNum(PageData pd) {
		Double num = 0.0;
		try {
			num =  (Double) dao.findForObject(mapperUrl + "findDateNum", pd);
			if(!(Verify.verifyIsNotNull(num))){
				num=0.0;
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return num;
	}

	//查询消费记录
		@SuppressWarnings("unchecked")
		public List<PageData> findConsumeList(PageData pd) {
			List<PageData> list = null;
			try {
				list = (List<PageData>) dao.findForList(mapperUrl + "findConsumeList", pd);
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			return list;
		}

		//根据时间进行分组
		@SuppressWarnings("unchecked")
		public List<PageData> findTimeList(PageData pd) {
			List<PageData> list = null;
			try {
				list = (List<PageData>) dao.findForList(mapperUrl + "findTimeList", pd);
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			return list;
		}

		//根据日期进行分页
		public int findDateNums(PageData pd) {
			Integer num = 0;
			try {
				num =  (Integer) dao.findForObject(mapperUrl + "findDateNums", pd);
				if(num==null){
					num=0;
				}
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
		
			return num;
		}

		//根据时间分组
		@SuppressWarnings("unchecked")
		public List<PageData> findNumList(PageData pd) {
			List<PageData> list = null;
			try {
				list = (List<PageData>) dao.findForList(mapperUrl + "findNumList", pd);
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			return list;
		}


}
