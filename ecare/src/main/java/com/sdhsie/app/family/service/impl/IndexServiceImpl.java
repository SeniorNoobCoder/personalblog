package com.sdhsie.app.family.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdhsie.app.family.service.IndexService;
import com.sdhsie.app.server.service.impl.AppOrderServiceImpl;
import com.sdhsie.base.dao.impl.IDaoImpl;
import com.sdhsie.base.util.PageData;

@Service
@Transactional
public class IndexServiceImpl implements IndexService{
	
	Logger logger = Logger.getLogger(IndexServiceImpl.class);
	String mapperUrl = "com.ecare.app.server.appindex.";
	@Autowired
	private IDaoImpl dao;

	//查询轮播图
	@SuppressWarnings("unchecked")
	public List<PageData> findCarouseList() {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findCarouseList", null);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	//查询根服务、查询优选服务 生活服务1级菜单
	@SuppressWarnings("unchecked")
	public List<PageData> findRootList(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findRootList", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	//热门服务
	@SuppressWarnings("unchecked")
	public List<PageData> findHotList(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findHotList", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	//查看热门服务详情
	@SuppressWarnings("unchecked")
	public List<PageData> findHotInfo(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findHotInfo", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	//查看详情
	public PageData findInfo(PageData pd) {
		try {
			pd = (PageData) dao.findForObject(mapperUrl + "findInfo", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}

	//获取服务分类信息（用于查找这个服务分类信息下的信息也就是server_info表中的信息）
	public PageData findRootInfo(PageData pd) {
		try {
			pd = (PageData) dao.findForObject(mapperUrl + "findRootInfo", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}

	//根据partner_id查找相应server_info中的信息
	@SuppressWarnings("unchecked")
	public List<PageData> findServerList(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findServerList", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 服务信息列表条数
	 * @param pd
	 * @return
     */
	public int findServerListCount(PageData pd) {
		Integer num = 0;
		try {
			num =  (Integer) dao.findForObject(mapperUrl + "findServerListCount", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return num;
	}
	//热门服务的数量
	public int findHotNum(PageData pd) {
		Integer num = 0;
		try {
			num =  (Integer) dao.findForObject(mapperUrl + "findHotNum", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return num;
	}

	/**
	 * 工作分类
	 */
	public List<PageData> findJobList(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findJobList", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 工作分类描述
	 */
	public List<PageData> findJobRemarkList(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findJobRemarkList", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	//查询收藏数量
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

	//服务详情中的评价信息
	public PageData findPjInfo(PageData pd) {
		try {
			pd = (PageData) dao.findForObject(mapperUrl + "findPjInfo", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}

	//评价列表
	@SuppressWarnings("unchecked")
	public List<PageData> findPjList(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findPjList", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	//评价列表数量
	public int findPjListNum(PageData pd) {
		Integer num = 0;
		try {
			num =  (Integer) dao.findForObject(mapperUrl + "findPjListNum", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return num;
	}

	//地区
	public List<PageData> findAreaList(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findAreaList", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	//地区信息
	public PageData findAreaInfo(PageData pd) {
		try {
			pd = (PageData) dao.findForObject(mapperUrl + "findAreaInfo", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}

}
