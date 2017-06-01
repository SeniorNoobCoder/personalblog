package com.sdhsie.app.server.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdhsie.app.server.service.AppOrderService;
import com.sdhsie.base.dao.impl.IDaoImpl;
import com.sdhsie.base.util.PageData;

@Service
@Transactional
public class AppOrderServiceImpl implements AppOrderService{
	
	Logger logger = Logger.getLogger(AppOrderServiceImpl.class);
	String mapperUrl = "com.ecare.app.server.apporder.";
	@Autowired
	private IDaoImpl dao;

	//根据订单状态查询订单信息
	@SuppressWarnings("unchecked")
	public List<PageData> findStatusList(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findStatusList", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	//订单的数量
	public int findListNum(PageData pd) {
		Integer num = 0;
		try {
			num =  (Integer) dao.findForObject(mapperUrl + "findListNum", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return num;
	}

	//获取sessionid
	public PageData findSessionInfo(PageData pd) {
		try {
			pd = (PageData) dao.findForObject(mapperUrl + "findSessionInfo", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}

	//订单信息
	public PageData findOrderInfo(PageData pd) {
		try {
			pd = (PageData) dao.findForObject(mapperUrl + "findOrderInfo", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}

	//订单流程信息
	@SuppressWarnings("unchecked")
	public List<PageData> findFlowList(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findFlowList", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	//添加订单流程
	public void save(PageData pd) {
		try {
			dao.save(mapperUrl + "save", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}

	//立即抢单
	public void update(PageData pd) {
		try {
			dao.update(mapperUrl + "update", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}

	//确认开工 取消订单（普通订单取消）支付方式
	public void updateStatus(PageData pd) {
		try {
			dao.update(mapperUrl + "updateStatus", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	//确认接单
	public void updateOrders(PageData pd) {
		try {
			dao.update(mapperUrl + "updateOrders", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}

	//取消订单（接受其他人服务的）
	public void updateServer(PageData pd) {
		try {
			dao.update(mapperUrl + "updateServer", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}

	//支付方式
	public void updatePay(PageData pd) {
		try {
			dao.update(mapperUrl + "updatePay", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}

	//查询已支付
	@SuppressWarnings("unchecked")
	public List<PageData> findPayList(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findPayList", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	//查询已支付的数量
	public int findPayListNum(PageData pd) {
		Integer num = 0;
		try {
			num =  (Integer) dao.findForObject(mapperUrl + "findPayListNum", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return num;
	}

	//查询个人信息
	public PageData findUserInfo(PageData pd) {
		try {
			pd = (PageData) dao.findForObject(mapperUrl + "findUserInfo", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}

	//查询家人的sessionid 
	public List<PageData> findfamilyList(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findfamilyList", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}


}
