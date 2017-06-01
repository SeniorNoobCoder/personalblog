package com.sdhsie.web.common.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sdhsie.base.controller.BaseController;
import com.sdhsie.base.util.Const;
import com.sdhsie.base.util.DateTimeUtil;
import com.sdhsie.base.util.Json;
import com.sdhsie.base.util.Page;
import com.sdhsie.base.util.PageData;
import com.sdhsie.base.util.Verify;
import com.sdhsie.base.util.parameterUtil;
import com.sdhsie.web.common.service.CommonRoleService;
import com.sdhsie.web.system.model.Menu;
import com.sdhsie.web.system.service.OrganizeService;

/**
 * 
  * @ClassName: RoleController
  * @Description: 角色管理
  * @author xiaol
  * @date 2016-6-29 下午05:38:15
  *
 */
@Controller
@RequestMapping(value = "/common/commonRole")
public class CommonRoleController extends BaseController {
	@Autowired
	private CommonRoleService commonRoleService;
	@Autowired
	private OrganizeService organizeService;
	private PageData pd;


	/**
	 * 
	  * @Title: findList
	  * @Description: 角色列表
	  * @param @param request
	  * @param @param session
	  * @param @param page
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/findList")
	public ModelAndView findList(HttpServletRequest request,HttpSession session,Page page) throws Exception {
		pd = new PageData(request);
		mv = getBaseMv(session, pd);
		pd.put("remove_logo", "N");//删除标识---未删除
//		PageData userPd = (PageData) session.getAttribute(Const.SESSION_USER);
//		pd.put("user_id", userPd.get("id"));
//		Integer organize_id = organizeService.findOrganizeId(pd);
//		pd.put("organize_id", organize_id);
//		pd.put("admin_type", parameterUtil.admin_type);
		page.setPd(pd);
		List<PageData> roleList = commonRoleService.findListPage(page);
		pd.put("remove_logo", "Y");//删除标识---删除
		//查询垃圾箱数量
		int num = commonRoleService.findListNum(pd);
		pd.put("num", num);
		
		//处理超级管理员
		pd.put("superAdministrator", parameterUtil.superAdministrator);
		pd.put("admin_type", parameterUtil.admin_type);
		pd.put("ordinary_type", parameterUtil.ordinary_type);
		mv.addObject("pd", pd);	
		mv.addObject("roleList", roleList);
		mv.addObject("power",getRoleButton(session));
		mv.setViewName("common/commonRole/commonRole_list");
		return mv;
	}
	
	/**
	 * 
	  * @Title: toadd
	  * @Description: 跳转到添加页面
	  * @param @param request
	  * @param @param session
	  * @param @param page
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/toadd")
	public ModelAndView toadd(HttpServletRequest request,HttpSession session,Page page) throws Exception {
		pd = new PageData(request);
		mv = getBaseMv(session, pd);
	
		
		mv.addObject("pd", pd);	
		mv.setViewName("common/commonRole/commonRole_add");
		return mv;
	}
	
	
	/**
	 * 
	  * @Title: save
	  * @Description: 添加角色
	  * @param @param session
	  * @param @param page
	  * @param @param request
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value="/save")
	public ModelAndView save(HttpSession session,Page page,HttpServletRequest request) throws Exception{
		mv.clear();
		pd = new PageData(request); 
		
		String role_id = (String) session.getAttribute(Const.SESSION_ROLE);
		PageData userPd = (PageData) session.getAttribute(Const.SESSION_USER);
		pd.put("user_id", userPd.get("id"));
		Integer organize_id = organizeService.findOrganizeId(pd);
		pd.put("organize_id", organize_id);
		
		if(Verify.verifyIsNotNull(role_id)){
			if(parameterUtil.superAdministrator.equals(role_id)){
				pd.put("type", parameterUtil.admin_type);
			}else{
				pd.put("type", parameterUtil.ordinary_type);
			}
		}else{
			pd.put("type", parameterUtil.ordinary_type);
		}
		
		pd.put("remove_logo", "N");//未删除
		pd.put("create_time", DateTimeUtil.getDateTime());
		pd.put("update_time", DateTimeUtil.getDateTime());
		pd.put("create_user", userPd.get("id"));
		commonRoleService.save(pd);
		pd.put("flag", "add");
		mv.addObject("pd", pd);
		mv.setViewName("common/commonRole/commonRole_add");
		return mv;
	}
	
	/**
	 * 
	  * @Title: toedit
	  * @Description: 跳转到修改页面
	  * @param @param request
	  * @param @param session
	  * @param @param page
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/toedit")
	public ModelAndView toedit(HttpServletRequest request,HttpSession session,Page page) throws Exception {
		pd = new PageData(request);
		mv = getBaseMv(session, pd);
		pd =  commonRoleService.findInfo(pd);
		
		mv.addObject("pd", pd);	
		mv.setViewName("common/commonRole/commonRole_edit");
		return mv;
	}
	
	/**
	 * 
	  * @Title: update
	  * @Description: 修改角色
	  * @param @param session
	  * @param @param page
	  * @param @param request
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value="/update")
	public ModelAndView update(HttpSession session,Page page,HttpServletRequest request) throws Exception{
		mv.clear();
		pd = new PageData(request); 
		pd.put("update_time", DateTimeUtil.getDateTime());
		commonRoleService.update(pd);
		pd.put("flag", "update");
		mv.addObject("pd", pd);
		mv.setViewName("common/commonRole/commonRole_edit");
		return mv;
	}
	
	/**
	 * 
	  * @Title: findInfo
	  * @Description: 查询单个角色
	  * @param @param request
	  * @param @param session
	  * @param @param page
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/findInfo")
	public ModelAndView findInfo(HttpServletRequest request,HttpSession session,Page page) throws Exception {
		pd = new PageData(request);
		mv = getBaseMv(session, pd);
		pd =  commonRoleService.findInfo(pd);
		
		mv.addObject("pd", pd);	
		mv.setViewName("common/commonRole/commonRole_info");
		return mv;
	}
	
	/**
	 * 
	  * @Title: delete
	  * @Description: 删除/还原-将数据删除、还原（修改删除标识）
	  * @param @param request
	  * @param @param response
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/delete")
	public void delete(HttpServletRequest request,HttpServletResponse response)throws Exception{
		pd = new PageData(request);
		Json json = new Json();
		String[] ids = pd.getString("id").split(",");
		for (String id : ids) {
			PageData p = new PageData();
			p.put("id", id);
			p.put("update_time", DateTimeUtil.getDateTime());
			p.put("remove_logo", pd.getString("remove_logo"));//删除
			commonRoleService.update(p);
		}
		json.setMsg("操作成功。");
		json.setSuccess(true);
		this.writeJson(response, json);
	}

	/**
	 * 
	  * @Title: findDustbin
	  * @Description: 得到角色垃圾箱列表
	  * @param @param request
	  * @param @param session
	  * @param @param page
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/findTrash")
	public ModelAndView findTrash(HttpServletRequest request,HttpSession session,Page page) throws Exception {
		pd = new PageData(request);
		mv = getBaseMv(session, pd);
		pd.put("remove_logo", "Y");//删除标识---已删除
		page.setPd(pd);
		List<PageData> roleList = commonRoleService.findListPage(page);
		mv.addObject("pd", pd);	
		mv.addObject("roleList", roleList);
		mv.setViewName("common/commonRole/commonRole_trash");
		return mv;
	}
	/**
	 * 
	  * @Title: restoreAll
	  * @Description: 一键还原
	  * @param @param request
	  * @param @param response
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/revertRoleAll")
	public void revertRoleAll(HttpServletRequest request,HttpServletResponse response)throws Exception{
		pd = new PageData(request);
		Json json = new Json();
		
		List<PageData> roleList =  commonRoleService.findList(pd);
		for (PageData pageData : roleList) {
			pageData.put("remove_logo", pd.getString("remove_logo_N"));
			commonRoleService.update(pageData);
		}
		json.setMsg("操作成功。");
		json.setSuccess(true);
		this.writeJson(response, json);
	}
	
	/**
	 * 
	  * @Title: delEmpty
	  * @Description: 清空
	  * @param @param request
	  * @param @param response
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/clearRole")
	public void clearRole(HttpServletRequest request,HttpServletResponse response)throws Exception{
		pd = new PageData(request);
		Json json = new Json();
		commonRoleService.clear(pd);
		json.setMsg("操作成功。");
		json.setSuccess(true);
		this.writeJson(response, json);
	}
	
	/**
	 * 
	  * @Title: toaddMenuRole
	  * @Description: 跳转到菜单权限页面
	  * @param @param role_id
	  * @param @param model
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@RequestMapping(value="/toaddMenuRole")
	public String toaddMenuRole(@RequestParam String role_id,Model model,HttpSession session)throws Exception{
		try{
			//处理拥有角色的权限
			pd.put("role_id", session.getAttribute(Const.SESSION_ROLE));
			pd.put("user_roleid", role_id);
			List<Menu> menuList = commonRoleService.listAllMenu(pd);

			JSONArray arr = JSONArray.fromObject(menuList);
			String json = arr.toString();
			json = json.replaceAll("menu_id", "id").replaceAll("menu_name", "name").replaceAll("subMenu", "nodes").replaceAll("hasMenu", "checked");
			model.addAttribute("zTreeNodes", json);
			model.addAttribute("roleId", role_id);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return "common/commonRole/commonRole_role";
	}
	
	/**
	 * 
	  * @Title: saveMenuRole
	  * @Description: 保存角色菜单
	  * @param @param ROLE_ID
	  * @param @param menuIds
	  * @param @param out
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/saveMenuRole")
	public void saveMenuRole(@RequestParam String ROLE_ID,@RequestParam String menuIds,PrintWriter out,HttpSession session)throws Exception{
		try{
			pd.put("role_id", ROLE_ID);
			pd.put("create_time", DateTimeUtil.getDateTime());
			PageData userPd = (PageData) session.getAttribute(Const.SESSION_USER);
			pd.put("create_user", userPd.get("id"));
			pd.put("type", '1');
			commonRoleService.saveMemuRole(menuIds,pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
	}
	
	
	
	@RequestMapping(value = "/findUserRole")
	public ModelAndView findUserRole(HttpServletRequest request,HttpSession session) throws Exception {
		pd = new PageData(request);
	
		//查询所有角色
		List<PageData> allRoleList = commonRoleService.findRoleList(pd);
		
		//查询用户角色
		List<PageData> userRoleList = commonRoleService.findUserRoleList(pd);
		
		mv.addObject("allRoleList",allRoleList);	
		mv.addObject("userRoleList",userRoleList);	
		mv.addObject("pd", pd);	
		mv.setViewName("common/commonRole/user_commonRole");
		return mv;
	}
	
	
	
	@RequestMapping(value = "/saveUserRole")
	public void saveUserRole(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
	
		List<PageData> list = new ArrayList<PageData>();
		String[] role_ids = pd.getString("role_ids").split(",");
		for (String role_id : role_ids) {
			PageData p = new PageData();
			p.put("role_id", role_id);
			p.put("user_id", pd.get("user_id"));
			p.put("classify", pd.get("classify"));
			p.put("create_user", pd.get("user_id"));
			p.put("create_time", DateTimeUtil.getDateTime());
			list.add(p);
		}
		commonRoleService.saveUserRole(list,pd);
		this.writeJson(response, true);
	}
	
	
	
}
