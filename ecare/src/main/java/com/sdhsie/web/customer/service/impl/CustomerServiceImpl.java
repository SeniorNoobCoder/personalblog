package com.sdhsie.web.customer.service.impl;

import com.sdhsie.base.dao.impl.IDaoImpl;
import com.sdhsie.base.util.DateTimeUtil;
import com.sdhsie.base.util.Page;
import com.sdhsie.base.util.PageData;
import com.sdhsie.web.customer.service.CustomerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;

/**
 * Created by zcx on 2016/11/29.
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    Logger logger = Logger.getLogger(CustomerServiceImpl.class);
    String mapperUrl = "com.ecare.web.customer.customer.";
    @Autowired
    private IDaoImpl dao;

    /*
	 * 
	  * <p>Title: findList</p>
	  * <p>Description: </p>
	  * @param pd
	  * @return
	  * @see com.sdhsie.web.system.service.UserService#findList(com.sdhsie.base.util.PageData)
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findListPage(Page page) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findListPage", page);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 无分页查询
	 * @param pd
	 * @return
     */
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

	/*
	 * 
	  * <p>Title: save</p>
	  * <p>Description: </p>
	  * @param pd
	  * @see com.sdhsie.web.system.service.UserService#save(com.sdhsie.base.util.PageData)
	 */
	public void save(PageData pd) {
		try {
			dao.save(mapperUrl + "save", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}

	}

	/*
	 * 
	  * <p>Title: findInfo</p>
	  * <p>Description: </p>
	  * @param pd
	  * @return
	  * @see com.sdhsie.web.system.service.UserService#findInfo(com.sdhsie.base.util.PageData)
	 */
	public PageData findInfo(PageData pd) {
		try {
			pd = (PageData) dao.findForObject(mapperUrl + "findInfo", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}

	/*
	 * 
	  * <p>Title: update</p>
	  * <p>Description: </p>
	  * @param pd
	  * @see com.sdhsie.web.system.service.UserService#update(com.sdhsie.base.util.PageData)
	 */
	public void update(PageData pd) {
		try {
			dao.update(mapperUrl + "update", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	/*
	 * 
	  * <p>Title: delete</p>
	  * <p>Description: </p>
	  * @param id
	  * @see com.sdhsie.web.system.service.UserService#delete(java.lang.String[])
	 */
	public void delete(String[] id) {
		try {
			dao.updateBatch(mapperUrl + "delete", id);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	/*
	 * 
	  * <p>Title: findDelNum</p>
	  * <p>Description: </p>
	  * @return
	  * @see com.sdhsie.web.system.service.UserService#findDelNum()
	 */
	public Integer findDelNum() {
		Integer num = 0;
		try {
			num = (Integer) dao.findForObject(mapperUrl + "findDelNum", null);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return num;
	}

	/*
	 * 
	  * <p>Title: findTrashPage</p>
	  * <p>Description: </p>
	  * @param page
	  * @return
	  * @see com.sdhsie.web.system.service.UserService#findTrashPage(com.sdhsie.base.util.Page)
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findTrashPage(Page page) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findListPage", page);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	/*
	 * 
	  * <p>Title: clearUser</p>
	  * <p>Description: </p>
	  * @param pd
	  * @see com.sdhsie.web.system.service.UserService#clearUser(com.sdhsie.base.util.PageData)
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public void clearTrash(PageData pd) {
		try {
			String flag = pd.getString("flag");
			if(flag.equals("clear")){
				String[] ids = pd.getString("ids").split(",");
				for (String string : ids) {
					pd.put("id", string);
					PageData p = (PageData) dao.findForObject(mapperUrl + "findInfo", pd);
					p.put("id", p.get("device_id"));
					p.put("status", "N");
					p.put("update_time", DateTimeUtil.getDateTimeStr());
					dao.update(mapperUrl + "updateDeviceStatus", p);
				}
				dao.deleteBatch(mapperUrl + "delete", ids);
				
			}else if(flag.equals("all_clear")){
				pd.put("remove_logo", "Y");
				List<Integer> list = (List<Integer>) dao.findForList(mapperUrl + "findListIds", pd);
				for (Integer string : list) {
					pd.put("id", string);
					PageData p = (PageData) dao.findForObject(mapperUrl + "findInfo", pd);
					p.put("id", p.get("device_id"));
					p.put("status", "N");
					p.put("update_time", DateTimeUtil.getDateTimeStr());
					dao.update(mapperUrl + "updateDeviceStatus", p);
				}
				dao.deleteBatch(mapperUrl + "delete", list);
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	/*
	 * 
	  * <p>Title: revertUser</p>
	  * <p>Description: </p>
	  * @param pd
	  * @see com.sdhsie.web.system.service.UserService#revertUser(com.sdhsie.base.util.PageData)
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public void revertTrash(PageData pd) {
		try {
			String flag = pd.getString("flag");
			if(flag.equals("revert")){
				String[] ids = pd.getString("ids").split(",");
				for (String id : ids) {
					PageData p = new PageData();
					p.put("id", id);
					p.put("remove_logo", "N");
					p.put("update_time", DateTimeUtil.getDateTime());
					dao.update(mapperUrl + "update", p);
				}
			}else if(flag.equals("all_revert")){
				pd.put("remove_logo", "Y");
				List<Integer> list = (List<Integer>) dao.findForList(mapperUrl + "findListIds", pd);
				for (Integer id : list) {
					PageData p = new PageData();
					p.put("id", id);
					p.put("remove_logo", "N");
					p.put("update_time", DateTimeUtil.getDateTime());
					dao.update(mapperUrl + "update", p);
				}
			}
		} catch (ParseException e) {
			logger.error(e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}

	public void removeDevice(PageData pd) {
		try {
			dao.update(mapperUrl + "removeDevice", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	
	@SuppressWarnings("unchecked")
	public List<PageData> findFootprint(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findFootprint", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<PageData> findOrderFootprint(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findOrderFootprint", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 查询数量
	 * @param pd
	 * @return
     */
	public int findNum(PageData pd) {
		Integer num = 0;
		try {
			num = (Integer) dao.findForObject(mapperUrl + "findNum", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return num;
	}
}
