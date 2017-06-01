package com.sdhsie.app.family.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdhsie.app.family.service.AppMineService;
import com.sdhsie.app.server.service.impl.AppOrderServiceImpl;
import com.sdhsie.base.dao.impl.IDaoImpl;
import com.sdhsie.base.util.PageData;

@Service
@Transactional
public class AppMineServiceImpl implements AppMineService{
	
	Logger logger = Logger.getLogger(AppMineServiceImpl.class);
	String mapperUrl = "com.ecare.app.server.appmine.";
	@Autowired
	private IDaoImpl dao;
	
	
	
	//查询关系
	@SuppressWarnings("unchecked")
	public List<PageData> findRelationshipList(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findRelationshipList", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}
	
	//查询设备是否存在
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

	//绑定设备
	public void saveRelation(PageData pd) {
		try {
			dao.save(mapperUrl + "saveRelation", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}

	//设备信息修改
	public void updateDevice(PageData pd) {
		try {
			dao.update(mapperUrl + "updateDevice", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}

	//查询设备信息
	public PageData findDeviceInfo(PageData pd) {
		try {
			pd = (PageData) dao.findForObject(mapperUrl + "findDeviceInfo", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}

	//查找设备号是否已经绑定
	public int findRepeatInfo(PageData pd) {
		Integer num = 0;
		try {
			num =  (Integer) dao.findForObject(mapperUrl + "findRepeatInfo", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return num;
	}

	//查找家庭成员信息
	@SuppressWarnings("unchecked")
	public List<PageData> findFamilyList(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findFamilyList", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
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
	public int findDateNum(PageData pd) {
		Integer num = 0;
		try {
			num =  (Integer) dao.findForObject(mapperUrl + "findDateNum", pd);
			if(num==null){
				num=0;
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	
		return num;
	}

	//收藏
	public void saveCollect(PageData pd) {
		try {
			dao.save(mapperUrl + "saveCollect", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}

	//收藏列表
	@SuppressWarnings("unchecked")
	public List<PageData> findCollectList(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findCollectList", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	//收藏分页
	public int findCollectNum(PageData pd) {
		Integer num = 0;
		try {
			num =  (Integer) dao.findForObject(mapperUrl + "findCollectNum", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return num;
	}

	//取消收藏
	public void delete(PageData pd) {
		try {
			dao.delete(mapperUrl + "delete", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}
	
	//解绑设备号
	public void deleteDevice(PageData pd) {
		try {
			dao.delete(mapperUrl + "deleteDevice", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}

	//质量个数
	public int findStartNum(PageData pd) {
		Integer num = 0;
		try {
			num =  (Integer) dao.findForObject(mapperUrl + "findStartNum", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return num;
	}

	//查询用户评论
	@SuppressWarnings("unchecked")
	public List<PageData> findEvaluateList(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findEvaluateList", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	//查询用户评论数量
	public int findEvaluateNum(PageData pd) {
		Integer num = 0;
		try {
			num =  (Integer) dao.findForObject(mapperUrl + "findEvaluateNum", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return num;
	}
	
	//查看评论图片
	@SuppressWarnings("unchecked")
	public List<PageData> findImageList(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findImageList", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	//帮助中心
	@SuppressWarnings("unchecked")
	public List<PageData> findHelpList(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findHelpList", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	//修改家人设备信息
	public void updatefamily(PageData pd) {
		try {
			dao.update(mapperUrl + "updatefamily", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}

	//修改设备客户信息
	public void updatefamilyUser(PageData pd) {
		try {
			dao.update(mapperUrl + "updatefamilyUser", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}

	//查询设备家人信息
	public PageData findUserInfo(PageData pd) {
		try {
			pd = (PageData) dao.findForObject(mapperUrl + "findUserInfo", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}

	//修改安全岛
	public void updatefamilySn(PageData pd) {
		try {
			dao.update(mapperUrl + "updatefamilySn", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}


	

}
