package com.sdhsie.web.common.service;

import java.util.List;

import com.sdhsie.base.util.Page;
import com.sdhsie.base.util.PageData;
import com.sdhsie.web.system.model.Menu;
/**
 * 
  * @ClassName: RoleController
  * @Description: 角色管理
  * @author xiaol
  * @date 2016-6-29 下午05:38:15
  *
 */
public interface CommonRoleService {
	/**
	 * 
	 * @Title: findList
	 * @Description: 查询信息列表
	 * @param @param pd
	 * @param @return 设定文件
	 * @return List<PageData> 返回类型
	 * @throws
	 */
	public List<PageData> findListPage(Page page);

	/**
	 * 
	 * 
	 * @Title: save
	 * @Description: 添加
	 * @param @param pd 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void save(PageData pd);

	/**
	 * 
	 * 
	 * @Title: findInfo
	 * @Description: 查询单个角色
	 * @param @param pd
	 * @param @return 设定文件
	 * @return PageData 返回类型
	 * @throws
	 */
	public PageData findInfo(PageData pd);

	/**
	 * 
	 * 
	 * @Title: update
	 * @Description: 修改
	 * @param @param pd 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void update(PageData pd);

	/**
	 * 
	 * 
	 * @Title: delete
	 * @Description: 删除
	 * @param @param ids 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void delete(String[] id);

	/**
	 * 
	  * @Title: findList
	  * @Description: 根据删除标识得到列表
	  * @param @param pd
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	public List<PageData> findList(PageData pd);

	/**
	 * 
	  * @Title: delEmpty
	  * @Description: 清空
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void clear(PageData pd);

	/**
	 * 
	  * @Title: findListNum
	  * @Description: 查询数量
	  * @param @param pd
	  * @param @return    设定文件
	  * @return int    返回类型
	  * @throws
	 */
	public int findListNum(PageData pd);

	/**
	 * 
	  * @Title: listAllMenu
	  * @Description: 得到所有菜单
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return List<Menu>    返回类型
	  * @throws
	 */
	public List<Menu> listAllMenu(PageData pd) throws Exception;

	/**
	 * 
	  * @Title: getRoleById
	  * @Description: 根据菜单id角色id得到菜单角色信息
	  * @param @param pd
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
	public PageData getRoleById(PageData pd) throws Exception;

	/**
	 * 
	  * @Title: saveMemuRole
	  * @Description: 保存菜单角色配置
	  * @param @param menuIds
	  * @param @param pd    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void saveMemuRole(String menuIds, PageData pd);

	public List<PageData> findRoleList(PageData pd);

	public List<PageData> findUserRoleList(PageData pd);

	public void saveUserRole(List<PageData> list, PageData pd);

	

}
