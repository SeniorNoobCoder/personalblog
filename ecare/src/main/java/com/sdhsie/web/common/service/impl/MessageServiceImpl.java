package com.sdhsie.web.common.service.impl;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdhsie.base.dao.impl.IDaoImpl;
import com.sdhsie.base.util.DateTimeUtil;
import com.sdhsie.base.util.Logger;
import com.sdhsie.base.util.Page;
import com.sdhsie.base.util.PageData;
import com.sdhsie.web.common.service.MessageService;
@Service
@Transactional
public class MessageServiceImpl implements MessageService {
		Logger logger = Logger.getLogger(ApkVersionServiceImpl.class);
		String mapperUrl = "com.ecare.web.common.message.";
		@Autowired
		private IDaoImpl dao;

	/**
	 * 分页查询
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

	public List<PageData> findListPageByUser(Page page) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findListPageByUser", page);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	public List<PageData> findListByUser(PageData pd) {
		List<PageData> list = null;
		try {
			list = (List<PageData>) dao.findForList(mapperUrl + "findListByUser", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
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

	public void saveUser(PageData pd) {
		try {
			dao.save(mapperUrl + "saveUser", pd);
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

	public void updateStatus(PageData pd) {
		try {
			dao.update(mapperUrl + "updateStatus", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * 删除
	 * @param id
     */
		public void delete(String[] id) {
			try {
				dao.updateBatch(mapperUrl + "delete", id);
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
		}

	public void delUser(String id) {
		try {
			dao.delete(mapperUrl + "delUser", id);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * 条数
	 * @param pd
	 * @return
     */
		public Integer findDelNum(PageData pd) {
			Integer num = 0;
			try {
				num = (Integer) dao.findForObject(mapperUrl + "findDelNum", pd);
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			return num;
		}

	/**
	 * 查询垃圾箱
	 * @param page
	 * @return
     */
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

	/**
	 *
	 * @param pd
     */
		@SuppressWarnings("unchecked")
		@Transactional
		public void clearMessage(PageData pd) {
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

	/**
	 *
	 * @param pd
     */
		@SuppressWarnings("unchecked")
		@Transactional
		public void revertMessage(PageData pd) {
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
}
