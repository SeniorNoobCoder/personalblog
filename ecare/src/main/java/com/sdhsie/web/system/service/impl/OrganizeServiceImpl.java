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
import com.sdhsie.web.system.service.OrganizeService;

@Service
@Transactional
public class OrganizeServiceImpl implements OrganizeService {
	Logger logger = Logger.getLogger(OrganizeServiceImpl.class);
	String mapperUrl = "com.ecare.web.system.organize.";
	@Autowired
	private IDaoImpl dao;
	
	
	/*
	 * 
	  * <p>Title: findList</p>
	  * <p>Description: </p>
	  * @param pd
	  * @return
	  * @see com.sdhsie.web.system.service.OrganizeService#findList(com.sdhsie.base.util.PageData)
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
	 * 
	  * <p>Title: save</p>
	  * <p>Description: </p>
	  * @param pd
	  * @see com.sdhsie.web.system.service.OrganizeService#save(com.sdhsie.base.util.PageData)
	 */
	public void save(PageData pd) {
		try {
			dao.save(mapperUrl + "save", pd);
			pd.put("org_cascade", pd.get("parent_org_cascade").toString()+pd.get("id").toString()+"_");
			dao.update(mapperUrl + "update", pd);
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
	  * @see com.sdhsie.web.system.service.OrganizeService#findInfo(com.sdhsie.base.util.PageData)
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
	  * @see com.sdhsie.web.system.service.OrganizeService#update(com.sdhsie.base.util.PageData)
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
	  * @see com.sdhsie.web.system.service.OrganizeService#delete(java.lang.String[])
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
	 * 
	  * <p>Title: findList</p>
	  * <p>Description: </p>
	  * @param pd
	  * @return
	  * @see com.sdhsie.web.system.service.OrganizeService#findList(com.sdhsie.base.util.PageData)
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
	 * 
	  * <p>Title: clearOrganize</p>
	  * <p>Description: </p>
	  * @param pd
	  * @see com.sdhsie.web.system.service.OrganizeService#clearOrganize(com.sdhsie.base.util.PageData)
	 */
	@SuppressWarnings("unchecked")
	public void clearOrganize(PageData pd) {
		try {
			String flag = pd.getString("flag");
			if(flag.equals("clear")){
				String[] ids = pd.getString("ids").split(",");
				
				dao.deleteBatch(mapperUrl + "delete", ids);
				dao.deleteBatch(mapperUrl + "deleteUserOrganize", ids);
				
			}else if(flag.equals("all_clear")){
				pd.put("remove_logo", "Y");
				List<Integer> list = (List<Integer>) dao.findForList(mapperUrl + "findListIds", pd);
				
				dao.deleteBatch(mapperUrl + "delete", list);
				dao.deleteBatch(mapperUrl + "deleteUserOrganize", list);
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	/*
	 * 
	  * <p>Title: revertOrganize</p>
	  * <p>Description: </p>
	  * @param pd
	  * @see com.sdhsie.web.system.service.OrganizeService#revertOrganize(com.sdhsie.base.util.PageData)
	 */
	@SuppressWarnings("unchecked")
	public void revertOrganize(PageData pd) {
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
					List<PageData> organizeList =  (List<PageData>) dao.findForList(mapperUrl + "findList", pd);
					for (PageData pageData:organizeList){
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
	
	/*
	 * 
	  * <p>Title: findDelNum</p>
	  * <p>Description: </p>
	  * @return
	  * @see com.sdhsie.web.system.service.OrganizeService#findDelNum()
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

	public Integer findOrganizeId(PageData pd) {
		Integer id = 0;
		try {
			id = (Integer) dao.findForObject(mapperUrl + "findOrganizeId", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return id;
	}


}
