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
import com.sdhsie.web.system.service.RoadService;

@Service
@Transactional
public class RoadServiceImpl implements RoadService{
	
	Logger logger = Logger.getLogger(RoadServiceImpl.class);
	String mapperUrl = "com.ecare.web.system.road.";
	@Autowired
	private IDaoImpl dao;

	/*
	  * <p>Title: findListPage</p>
	  * <p>Description: </p>
	  * @param page
	  * @return
	  * @see com.sdhsie.web.system.service.RoadService#findListPage(com.sdhsie.base.util.Page)
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
	  * @see com.sdhsie.web.system.service.RoadService#findDelNum()
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
	  * <p>Title: save</p>
	  * <p>Description: </p>
	  * @param pd
	  * @see com.sdhsie.web.system.service.RoadService#save(com.sdhsie.base.util.PageData)
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
	  * <p>Title: findInfo</p>
	  * <p>Description: </p>
	  * @param pd
	  * @return
	  * @see com.sdhsie.web.system.service.RoadService#findInfo(com.sdhsie.base.util.PageData)
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
	  * <p>Title: update</p>
	  * <p>Description: </p>
	  * @param pd
	  * @see com.sdhsie.web.system.service.RoadService#update(com.sdhsie.base.util.PageData)
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
	  * <p>Title: delete</p>
	  * <p>Description: </p>
	  * @param pd
	  * @see com.sdhsie.web.system.service.RoadService#delete(com.sdhsie.base.util.PageData)
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
	  * <p>Title: findList</p>
	  * <p>Description: </p>
	  * @param pd
	  * @return
	  * @see com.sdhsie.web.system.service.RoadService#findList(com.sdhsie.base.util.PageData)
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
	  * <p>Title: clearOrganize</p>
	  * <p>Description: </p>
	  * @param pd
	  * @see com.sdhsie.web.system.service.RoadService#clearOrganize(com.sdhsie.base.util.PageData)
	  */
	
	
	@SuppressWarnings("unchecked")
	public void clearRoad(PageData pd) {
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
	  * <p>Title: revertOrganize</p>
	  * <p>Description: </p>
	  * @param pd
	  * @see com.sdhsie.web.system.service.RoadService#revertOrganize(com.sdhsie.base.util.PageData)
	  */
	
	
	@SuppressWarnings("unchecked")
	public void revertRoad(PageData pd) {
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
					List<PageData> roadList =  (List<PageData>) dao.findForList(mapperUrl + "findListPage", pd);
					for (PageData pageData:roadList){
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
	  * <p>Title: findTree</p>
	  * <p>Description: </p>
	  * @param pd
	  * @return
	  * @see com.sdhsie.web.system.service.RoadService#findTree(com.sdhsie.base.util.PageData)
	  */
	
	
	@SuppressWarnings("unchecked")
	public List<PageData> findTree(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findTree", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	/*
	  * <p>Title: findSonTree</p>
	  * <p>Description: </p>
	  * @param pd
	  * @return
	  * @see com.sdhsie.web.system.service.RoleService#findSonTree(com.sdhsie.base.util.PageData)
	  */
	
	
	@SuppressWarnings("unchecked")
	public List<PageData> findSonTree(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findSonTree", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	/*
	  * <p>Title: find</p>
	  * <p>Description: </p>
	  * @param pd
	  * @return
	  * @see com.sdhsie.web.system.service.RoadService#find(com.sdhsie.base.util.PageData)
	  */
	
	

	/*
	  * <p>Title: findsumo</p>
	  * <p>Description: </p>
	  * @param pd
	  * @return
	  * @see com.sdhsie.web.system.service.RoadService#findsumo(com.sdhsie.base.util.PageData)
	  */
	
	
	@SuppressWarnings("unchecked")
	public List<PageData> findsumo(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findsumo", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	/*
	  * <p>Title: findsump</p>
	  * <p>Description: </p>
	  * @param pd
	  * @return
	  * @see com.sdhsie.web.system.service.RoadService#findsump(com.sdhsie.base.util.PageData)
	  */
	
	
	@SuppressWarnings("unchecked")
	public List<PageData> findsump(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findsump", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	/*
	 * 
	  * <p>Title: findRoadList</p>
	  * <p>Description: </p>
	  * @param pd
	  * @return
	  * @see com.sdhsie.web.system.service.RoadService#findRoadList(com.sdhsie.base.util.PageData)
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findRoadList(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findRoadList", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

}
