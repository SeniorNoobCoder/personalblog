package com.sdhsie.app.common.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdhsie.app.common.service.AppMessageService;
import com.sdhsie.base.dao.impl.IDaoImpl;
import com.sdhsie.base.util.PageData;

@Service
@Transactional
public class AppMessageServiceImpl implements AppMessageService{
	
	Logger logger = Logger.getLogger(AppMessageServiceImpl.class);
	String mapperUrl = "com.ecare.app.server.appmessage.";
	@Autowired
	private IDaoImpl dao;
	
	
	//查询系统消息最新的消息
	public PageData findMessageOneInfo(PageData pd) {
		try {
			pd = (PageData) dao.findForObject(mapperUrl + "findMessageOneInfo", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}
	
	
	//家人进出安全岛
	public PageData findDeviceOneInfo(PageData pd) {
		try {
			pd = (PageData) dao.findForObject(mapperUrl + "findDeviceOneInfo", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}
	
	
	//订单消息
	public PageData findOrderOneInfo(PageData pd) {
		try {
			pd = (PageData) dao.findForObject(mapperUrl + "findOrderOneInfo", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}


	//消息数量
	public int findMessageNum(PageData pd) {
		Integer num = 0;
		try {
			num =  (Integer) dao.findForObject(mapperUrl + "findMessageNum", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return num;
	}


	//进出安全岛数量
	public int findDeviceNum(PageData pd) {
		Integer num = 0;
		try {
			num =  (Integer) dao.findForObject(mapperUrl + "findDeviceNum", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return num;
	}


	//订单数量
	public int findOrderNum(PageData pd) {
		Integer num = 0;
		try {
			num =  (Integer) dao.findForObject(mapperUrl + "findOrderNum", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return num;
	}


	//消息列表
	@SuppressWarnings("unchecked")
	public List<PageData> findmessageList(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findmessageList", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}


	//消息数量
	public int findMessageListNum(PageData pd) {
		Integer num = 0;
		try {
			num =  (Integer) dao.findForObject(mapperUrl + "findMessageListNum", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return num;
	}


	//消息详情
	public PageData findMessageInfo(PageData pd) {
		try {
			pd = (PageData) dao.findForObject(mapperUrl + "findMessageInfo", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}


	//订单列表
	public List<PageData> findOrderList(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findOrderList", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}


	//进出安全岛未读消息列表
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


	//进出安全岛未读消息详情
	public PageData findDeviceInfo(PageData pd) {
		try {
			pd = (PageData) dao.findForObject(mapperUrl + "findDeviceInfo", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}


	//修改消息的状态
	public void updateMessage(PageData pd) {
		try {
			dao.update(mapperUrl + "updateMessage", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}


	//修改进出安全岛的状态
	public void updateDevice(PageData pd) {
		try {
			dao.update(mapperUrl + "updateDevice", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}


	//修改订单状态
	public void updateOrder(PageData pd) {
		try {
			dao.update(mapperUrl + "updateOrder", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}


	//关于我们 、注册
	public PageData findUsInfo(PageData pd) {
		try {
			pd = (PageData) dao.findForObject(mapperUrl + "findUsInfo", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}


	//历史足迹
	public List<PageData> findEmpreinteList(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findEmpreinteList", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}


	//修改手机号（登录名）
	public void updateUser(PageData pd) {
		try {
			dao.update(mapperUrl + "updateUser", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}


	//apk版本
	public PageData findApkInfo(PageData pd) {
		try {
			pd = (PageData) dao.findForObject(mapperUrl + "findApkInfo", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}


	//商户版的订单消息
	public List<PageData> findMessageShList(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findMessageShList", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}


	//商户订单数量
	public int findMessageShNum(PageData pd) {
		Integer num = 0;
		try {
			num =  (Integer) dao.findForObject(mapperUrl + "findMessageShNum", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return num;
	}


	//商户版的订单信息（最新的）
	public PageData findShOrderInfo(PageData pd) {
		try {
			pd = (PageData) dao.findForObject(mapperUrl + "findShOrderInfo", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}


	

}
