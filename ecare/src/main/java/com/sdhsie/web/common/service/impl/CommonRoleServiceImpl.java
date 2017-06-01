package com.sdhsie.web.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdhsie.base.dao.impl.IDaoImpl;
import com.sdhsie.base.util.Page;
import com.sdhsie.base.util.PageData;
import com.sdhsie.base.util.parameterUtil;
import com.sdhsie.web.common.service.CommonRoleService;
import com.sdhsie.web.system.model.Menu;

/**
 * 
  * @ClassName: RoleController
  * @Description: 角色管理
  * @author xiaol
  * @date 2016-6-29 下午05:38:15
  *
 */
@Service
@Transactional
public class CommonRoleServiceImpl implements CommonRoleService {
	Logger logger = Logger.getLogger(CommonRoleServiceImpl.class);
	String mapperUrl = "com.ecare.web.common.commonRole.";
	@Autowired
	private IDaoImpl dao;
	
	
	/*
	 * 
	  * <p>Title: findList</p>
	  * <p>Description:查询列表 </p>
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

	/*
	 * 
	  * <p>Title: save</p>
	  * <p>Description:添加 </p>
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
	  * <p>Description:查询单个</p>
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
	  * <p>Description:修改 </p>
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
	  * <p>Description: 删除</p>
	  * @param id
	  * @see com.sdhsie.web.system.service.UserService#delete(java.lang.String[])
	 */
	public void delete(String[] id) {
		try {
			dao.deleteBatch(mapperUrl + "delete", id);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}
	/*
	 * 
	  * <p>Title: findList</p>
	  * <p>Description:查询垃圾箱列表 </p>
	  * @param pd
	  * @return
	  * @see com.sdhsie.web.system.service.UserService#findList(com.sdhsie.base.util.PageData)
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
	  * <p>Title: delEmpty</p>
	  * <p>Description:清空 </p>
	  * @param pd
	  * @see com.sdhsie.web.system.service.RoleService#delEmpty(com.sdhsie.base.util.PageData)
	 */
	@SuppressWarnings("unchecked")
	public void clear(PageData pd) {
		try {
			String flag = pd.getString("flag");
			if(flag.equals("clear")){
				String[] ids = pd.getString("id").split(",");
				//删除角色
				dao.deleteBatch(mapperUrl + "delete", ids);
				//删除角色菜单
				dao.deleteBatch(mapperUrl + "delRoleMenu", ids);
				//删除角色用户
				dao.deleteBatch(mapperUrl + "delRoleUser", ids);
			}else if(flag.equals("all_clear")){
				pd.put("remove_logo", "Y");
				List<Integer> list = (List<Integer>) dao.findForList(mapperUrl + "findList", pd);
				
				dao.deleteBatch(mapperUrl + "delete", list);
				dao.deleteBatch(mapperUrl + "delRoleMenu", list);
				dao.deleteBatch(mapperUrl + "delRoleUser", list);
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	/*
	 * 
	  * <p>Title: findListNum</p>
	  * <p>Description: 查询数量</p>
	  * @param pd
	  * @return
	  * @see com.sdhsie.web.system.service.RoleService#findListNum(com.sdhsie.base.util.PageData)
	 */
	public int findListNum(PageData pd) {
		Integer num = 0;
		try {
			num = (Integer) dao.findForObject(mapperUrl + "findListNum", pd);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return num;
	}


	@SuppressWarnings("unchecked")
	public List<Menu> listSubMenuByParentId(PageData pd) throws Exception {
		return (List<Menu>) dao.findForList(mapperUrl+"listSubMenuByParentId", pd);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Menu> listAllParentMenu() throws Exception {
		return (List<Menu>) dao.findForList(mapperUrl+"listAllParentMenu", null);
		
	}
	
	/*
	 * 
	  * <p>Title: listAllMenu</p>
	  * <p>Description: 得到所有的菜单</p>
	  * @return
	  * @throws Exception
	  * @see com.sdhsie.web.system.service.RoleService#listAllMenu()
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> listAllMenu(PageData pd)  throws Exception{
		List<Menu> rl = new ArrayList<Menu>();
		//如果是系统管理员直接操作
		rl = (List<Menu>) dao.findForList(mapperUrl+"listAllParentMenu", pd);
		for(Menu menu : rl){
			if(!"".equals(menu.getRole_id()) && null != menu.getRole_id()){
				menu.setHasMenu(true);
			}else{
				menu.setHasMenu(false);
			}
			PageData p = new PageData();
			p.put("parentId", menu.getMenu_id());
			p.put("type", "menu");
			p.put("user_roleid", pd.get("user_roleid"));
			p.put("classify", pd.get("classify"));
			List<Menu> subList = this.listSubMenuByParentId(p);
			for(Menu m : subList){
				if(!"".equals(m.getRole_id()) && null != m.getRole_id()){
					m.setHasMenu(true);
				}else{
					m.setHasMenu(false);
				}
				p.put("parentId", m.getMenu_id());
				p.put("type", "button");
				List<Menu> buttonList = this.listSubMenuByParentId(p);
				for(Menu mb : buttonList){
					if(!"".equals(mb.getRole_id()) && null != mb.getRole_id()){
						mb.setHasMenu(true);
					}else{
						mb.setHasMenu(false);
					}
				}
				m.setSubMenu(buttonList);
			}
			menu.setSubMenu(subList);
		}
		
		return rl;
	}

	/*
	 * 
	  * <p>Title: getRoleById</p>
	  * <p>Description: 根据菜单id角色id得到菜单角色信息</p>
	  * @param pd
	  * @return
	  * @throws Exception
	  * @see com.sdhsie.web.system.service.RoleService#getRoleById(com.sdhsie.base.util.PageData)
	 */
	public PageData getRoleById(PageData pd) throws Exception {
		return (PageData) dao.findForObject(mapperUrl+"findMenuRoleList",pd);
	}

	/*
	 * 
	  * <p>Title: saveMemuRole</p>
	  * <p>Description:保存菜单角色信息 </p>
	  * @param menuIds
	  * @param pd
	  * @see com.sdhsie.web.system.service.RoleService#saveMemuRole(java.lang.String, com.sdhsie.base.util.PageData)
	 */
	public void saveMemuRole(String menuIds, PageData pd) {
		try {
			String[] menuids = menuIds.split(",");
			//删除角色菜单
			dao.delete(mapperUrl + "delRoleMenu", pd.get("role_id"));
			for (int i = 0; i < menuids.length; i++) {
				pd.put("menu_id", menuids[i]);
				dao.save(mapperUrl + "saveMemuRole", pd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	@SuppressWarnings("unchecked")
	public List<PageData> findRoleList(PageData pd) {
		List<PageData> list=new ArrayList<PageData>();
		try {
			list = (List<PageData>) dao.findForList(mapperUrl+"findRoleList", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	
	@SuppressWarnings("unchecked")
	public List<PageData> findUserRoleList(PageData pd) {
		List<PageData> list=new ArrayList<PageData>();
		try {
			list = (List<PageData>) dao.findForList(mapperUrl+"findUserRoleList", pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public void saveUserRole(List<PageData> list, PageData pd) {
		try {
			//删除用户所有角色
			dao.delete(mapperUrl + "delUserRole", pd);
			//保存用户角色
			dao.saveBatch(mapperUrl + "saveUserRole", list);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}

}
