package com.sdhsie.web.callCenter.service.impl;
import com.sdhsie.base.util.DateTimeUtil;
import com.sdhsie.base.util.Page;
import com.sdhsie.base.util.PageData;
import com.sdhsie.web.callCenter.service.TelemarketerInfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sdhsie.base.dao.impl.IDaoImpl;

import java.text.ParseException;
import java.util.List;

/**
 * Created by zcx on 2016/11/25.
 */
@Service
@Transactional
public class TelemarketerInfoServiceImpl implements TelemarketerInfoService {
    Logger logger = Logger.getLogger(TelemarketerInfoServiceImpl.class);
    String mapperUrl = "com.ecare.web.callCenter.telemarketer.";
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


	public void clearUser(PageData pd) {
		try {
			String flag = pd.getString("flag");
			if(flag.equals("clear")){
				String[] ids = pd.getString("ids").split(",");
				
				dao.deleteBatch(mapperUrl + "delete", ids);
				dao.deleteBatch(mapperUrl + "deleteUserOrganize", ids);
				dao.deleteBatch(mapperUrl + "deleteUserRole", ids);
				
			}else if(flag.equals("all_clear")){
				pd.put("remove_logo", "Y");
				List<Integer> list = (List<Integer>) dao.findForList(mapperUrl + "findListIds", pd);
				
				dao.deleteBatch(mapperUrl + "delete", list);
				dao.deleteBatch(mapperUrl + "deleteUserOrganize", list);
				dao.deleteBatch(mapperUrl + "deleteUserRole", list);
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

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

	public PageData findInfo(PageData pd) {
		try {
			pd = (PageData) dao.findForObject(mapperUrl + "findInfo", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}

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

	public void save(PageData pd) {
		try {
			dao.save(mapperUrl + "save", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	public void update(PageData pd) {
		try {
			dao.update(mapperUrl + "update", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}


}
