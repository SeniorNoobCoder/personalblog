package com.sdhsie.web.partners.service.impl;

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
import com.sdhsie.web.partners.service.PartnersInfoService;

/**
 * Created by zcx on 2016/11/28.
 */
@Service
@Transactional
public class PartnersInfoServiceImpl implements PartnersInfoService{
    Logger logger = Logger.getLogger(PartnersInfoServiceImpl.class);
    String mapperUrl = "com.ecare.web.partners.partners.";
    @Autowired
    private IDaoImpl dao;

    /**
     * 查询
     * @param page
     * @return
     */
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

    @Override
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

    /**
     * 添加
     * @param pd
     */
    public void save(PageData pd) {
        try {
            dao.save(mapperUrl + "save", pd);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    /**
     * 修改
     * @param pd
     */
    public void update(PageData pd) {
        try {
            dao.update(mapperUrl + "update", pd);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    /**
     * 查询详情
     * @param pd
     * @return
     */
    public PageData findInfo(PageData pd) {
        try {
            pd = (PageData) dao.findForObject(mapperUrl + "findInfo", pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pd;
    }

    /**
     * 查询详情
     * @param pd
     * @return
     */
    public PageData findInfoByLoginName(PageData pd) {
        try {
            pd = (PageData) dao.findForObject(mapperUrl + "findInfo", pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pd;
    }

    /**
     *添加服务分类
     * @param pd
     */
    public void saveServerCategory(PageData pd) {
        try {
            dao.save(mapperUrl + "saveServerCategory", pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除服务分类
     */
    public void delServerCategory(String user_id) {
        try {
            dao.delete(mapperUrl + "delServerCategory", user_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询服务分类
     * @param pd
     * @return
     */
    public List<PageData> findServerCategory(PageData pd) {
        List<PageData> list = null;
        try {
            list = (List<PageData>) dao.findForList(mapperUrl + "findServerCategory", pd);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
        return list;
    }

	@SuppressWarnings("unchecked")
	public void clearUser(PageData pd) {
		try {
			String flag = pd.getString("flag");
			if(flag.equals("clear")){
				String[] ids = pd.getString("ids").split(",");
				
				dao.deleteBatch(mapperUrl + "delete", ids);
				dao.deleteBatch(mapperUrl + "deleteAuthent", ids);
				
			}else if(flag.equals("all_clear")){
				pd.put("remove_logo", "Y");
				List<Integer> list = (List<Integer>) dao.findForList(mapperUrl + "findListIds", pd);
				
				dao.deleteBatch(mapperUrl + "delete", list);
				dao.deleteBatch(mapperUrl + "deleteAuthent", list);
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	/*
	 * 
	  * <p>Title: findDelNum</p>
	  * <p>Description: </p>
	  * @param pd
	  * @return
	  * @see com.sdhsie.web.partners.service.PartnersInfoService#findDelNum(com.sdhsie.base.util.PageData)
	 */
	public int findDelNum(PageData pd) {
		Integer num = 0;
		try {
			num = (Integer) dao.findForObject(mapperUrl + "findDelNum", pd);
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
	  * @see com.sdhsie.web.partners.service.PartnersInfoService#findTrashPage(com.sdhsie.base.util.Page)
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

	@SuppressWarnings("unchecked")
	public void revertUser(PageData pd) {
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

	public PageData findAuthentInfo(PageData pd) {
		try {
            pd = (PageData) dao.findForObject(mapperUrl + "findAuthentInfo", pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pd;
	}

	@Transactional
	public void saveAuthent(PageData pd) {
		try {
            dao.save(mapperUrl + "saveAuthent", pd);
            //更新认证信息
            pd.put("id", pd.get("partner_id"));
            pd.put("is_authent", 1);
            dao.update(mapperUrl + "update", pd);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
	}

	public void updateAuthent(PageData pd) {
		try {
            dao.update(mapperUrl + "updateAuthent", pd);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
	}
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
