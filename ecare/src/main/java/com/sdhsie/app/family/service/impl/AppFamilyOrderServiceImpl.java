package com.sdhsie.app.family.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdhsie.app.family.service.AppFamilyOrderService;
import com.sdhsie.base.dao.impl.IDaoImpl;
import com.sdhsie.base.util.PageData;

@Service
@Transactional
public class AppFamilyOrderServiceImpl implements AppFamilyOrderService{
	Logger logger = Logger.getLogger(AppFamilyOrderServiceImpl.class);
	String mapperUrl = "com.ecare.app.server.appfamily.";
	@Autowired
	private IDaoImpl dao;

	
	
	//根据用户id查询设备编号
	@SuppressWarnings("unchecked")
	public List<PageData> findDeviceList(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findDeviceList", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	//查询家人版订单列表
	@SuppressWarnings("unchecked")
	public List<PageData> findFamilyOrderList(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findFamilyOrderList", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	//查询订单数量
	public int findFamilyOrderNum(PageData pd) {
		Integer num = 0;
		try {
			num =  (Integer) dao.findForObject(mapperUrl + "findFamilyOrderNum", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return num;
	}

	//取消订单
	public void updateOrder(PageData pd) {
		try {
			dao.update(mapperUrl + "updateOrder", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}

	//查询商户信息
	@SuppressWarnings("unchecked")
	public List<PageData> findUserList(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findUserList", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	//查询商户数量
	public int findUserNum(PageData pd) {
		Integer num = 0;
		try {
			num =  (Integer) dao.findForObject(mapperUrl + "findUserNum", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return num;
	}

	//查询订单状态
	public PageData findStatusInfo(PageData pd) {
		try {
			pd = (PageData) dao.findForObject(mapperUrl + "findStatusInfo", pd);
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

	//订单流程
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

	//商户信息
	public PageData findMerchantInfo(PageData pd) {
		try {
			pd = (PageData) dao.findForObject(mapperUrl + "findMerchantInfo", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}

	//订单评价
	public void saveAssessment(PageData pd) {
		try {
			dao.save(mapperUrl + "saveAssessment", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}

	//评价图片
	public void saveAssessmentImage(PageData pd) {
		try {
			dao.save(mapperUrl + "saveAssessmentImage", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}

	//查询评价信息
	public PageData findAssessmentInfo(PageData pd) {
		try {
			pd = (PageData) dao.findForObject(mapperUrl + "findAssessmentInfo", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}

	//根据订单编号查询评论id
	public PageData findAAssInfo(PageData pd) {
		try {
			pd = (PageData) dao.findForObject(mapperUrl + "findAAssInfo", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}
	//星级 服务次数
	public List<PageData> findStatInfo(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findStatInfo", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	
	public List<PageData> findStat(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findStat", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	
	

}
