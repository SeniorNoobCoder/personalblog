/**
 * @Title: DictionaryServiceImpl.java
 * @Package com.sdhsie.web.system.service.impl
 * @Description: TODO
 * Copyright: Copyright (c) 2016 
 * Company:济南云融信息技术有限公司
 * 
 * @author Administrator
 * @date 2016-6-29 下午05:19:43
 * @version V1.0
 */

package com.sdhsie.web.system.service.impl;

import java.text.ParseException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdhsie.base.dao.impl.IDaoImpl;
import com.sdhsie.base.util.DateTimeUtil;
import com.sdhsie.base.util.Page;
import com.sdhsie.base.util.PageData;
import com.sdhsie.web.system.service.DictionaryService;


@Service
@Transactional
public  class DictionaryServiceImpl implements DictionaryService{
	
	Logger logger = Logger.getLogger(DictionaryServiceImpl.class);
	String mapperUrl = "com.ecare.web.system.dictionary.";
	@Autowired
	private IDaoImpl dao;

	/*
	  * <p>Title: findList</p>
	  * <p>Description: </p>
	  * @param page
	  * @return
	  * @see com.sdhsie.web.system.service.DictionaryService#findListPage(com.sdhsie.base.util.Page)
	  */
	
	
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

	/*
	  * <p>Title: save</p>
	  * <p>Description: </p>
	  * @param pd
	  * @see com.sdhsie.web.system.service.DictionaryService#save(com.sdhsie.base.util.PageData)
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
	  * <p>Title: update</p>
	  * <p>Description: </p>
	  * @param pd
	  * @see com.sdhsie.web.system.service.DictionaryService#update(com.sdhsie.base.util.PageData)
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
	  * <p>Title: findInfo</p>
	  * <p>Description: </p>
	  * @param pd
	  * @return
	  * @see com.sdhsie.web.system.service.DictionaryService#findInfo(com.sdhsie.base.util.PageData)
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
	  * <p>Title: delete</p>
	  * <p>Description: </p>
	  * @param ids
	  * @see com.sdhsie.web.system.service.DictionaryService#delete(java.lang.String[])
	  */
	
	
	public void delete(PageData pd) {
		try {
			dao.delete(mapperUrl + "delete", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	/*
	  * <p>Title: findListPage</p>
	  * <p>Description: </p>
	  * @param page
	  * @return
	  * @see com.sdhsie.web.system.service.DictionaryService#findListPage(com.sdhsie.base.util.Page)
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

	/*
	  * <p>Title: findDelNum</p>
	  * <p>Description: </p>
	  * @return
	  * @see com.sdhsie.web.system.service.DictionaryService#findDelNum()
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
	  * <p>Title: findTrashPage</p>
	  * <p>Description: </p>
	  * @param page
	  * @return
	  * @see com.sdhsie.web.system.service.DictionaryService#findTrashPage(com.sdhsie.base.util.Page)
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
	  * <p>Title: clearDictionary</p>
	  * <p>Description: </p>
	  * @param pd
	  * @see com.sdhsie.web.system.service.DictionaryService#clearDictionary(com.sdhsie.base.util.PageData)
	  */
	
	
	@SuppressWarnings("unchecked")
	public void clearDictionary(PageData pd) {
		try {
			String flag = pd.getString("flag");
			if(flag.equals("clear")){
				String[] ids = pd.getString("ids").split(",");
				
				dao.deleteBatch(mapperUrl + "delete", ids);
				
			}else if(flag.equals("all_clear")){
				pd.put("remove_logo", "Y");
				List<Integer> list = (List<Integer>) dao.findForList(mapperUrl + "findListIds", pd);
				
				dao.deleteBatch(mapperUrl + "delete", list);
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}

	/*
	  * <p>Title: revertDictionary</p>
	  * <p>Description: </p>
	  * @param pd
	  * @see com.sdhsie.web.system.service.DictionaryService#revertDictionary(com.sdhsie.base.util.PageData)
	  */
	
	
	@SuppressWarnings("unchecked")
	public void revertDictionary(PageData pd) {
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
					
					pd.put("parent_id", id);
					List<PageData> dictionaryList =  (List<PageData>) dao.findForList(mapperUrl + "findList", pd);
					for (PageData pageData:dictionaryList){
						PageData pp = new PageData();
						pp.put("id", pageData.get("id"));
						pp.put("remove_logo", "N");
						pp.put("update_time", DateTimeUtil.getDateTime());
						dao.update(mapperUrl + "update", pp);
					}
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

	@SuppressWarnings("unchecked")
	public List<PageData> findDicList(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findDicList", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	
	


}
