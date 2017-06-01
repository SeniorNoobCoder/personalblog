
package com.sdhsie.web.system.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sdhsie.base.controller.BaseController;
import com.sdhsie.base.util.Const;
import com.sdhsie.base.util.DateTimeUtil;
import com.sdhsie.base.util.PageData;
import com.sdhsie.base.util.Verify;
import com.sdhsie.base.util.parameterUtil;
import com.sdhsie.web.system.service.MenuService;
import com.sdhsie.web.system.service.QuickMenuService;

/**
 * @ClassName: MenuController
 * @Description: TODO
 * @author Administrator
 * @date 2016-7-23 上午11:24:58
 *
 */
@Controller
@RequestMapping(value = "/system/quickmenu")
public class QuickMenuController extends BaseController{
	@Autowired
	private QuickMenuService quickMenuService;
	@Autowired
	private MenuService menuService;
	private PageData pd;
	
	/**
	 * 
	  * @Title: findListPage
	  * @Description: 获取列表信息
	  * @param @param request
	  * @param @param session
	  * @param @param response
	  * @param @param page
	  * @param @return    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/findList")
	public ModelAndView findListPage(HttpServletRequest request,HttpSession session,HttpServletResponse response){
		mv.clear();
		pd = new PageData(request);
		mv = getBaseMv(session, pd);
		PageData uPd = (PageData)session.getAttribute(Const.SESSION_USER);
		List<PageData> list = new ArrayList<PageData>();
			
		PageData p = new PageData();
		//判断是否是系统管理员
		String role_id = (String) session.getAttribute(Const.SESSION_ROLE);
		if(Verify.verifyIsNotNull(role_id)){
			if(parameterUtil.superAdministrator.equals(role_id)){
				p.put("type", parameterUtil.admin_type);
				list = quickMenuService.findList(p);
			}else{
				for (int i = 1; i <= 4; i++) {
					p.put("create_user", uPd.get("id"));
					p.put("type", parameterUtil.ordinary_type);
					p.put("level", i);
					PageData pp = quickMenuService.findPd(p);
					if(Verify.verifyIsNotNull(pp)){
						list.add(pp);
					}else{
						p.put("type", parameterUtil.admin_type);
						p.put("level", i);
						p.remove("create_user");
						PageData ap = quickMenuService.findPd(p);
						list.add(ap);
					}
				}
			}
		}else{
			for (int i = 1; i <= 4; i++) {
				p.put("create_user", uPd.get("id"));
				p.put("type", parameterUtil.ordinary_type);
				p.put("level", i);
				PageData pp = quickMenuService.findPd(p);
				if(Verify.verifyIsNotNull(pp)){
					list.add(pp);
				}else{
					p.put("type", parameterUtil.admin_type);
					p.put("level", i);
					p.remove("create_user");
					PageData ap = quickMenuService.findPd(p);
					list.add(ap);
				}
			}
		}
		
		mv.addObject("power",getRoleButton(session));
		mv.addObject("list",list);
		mv.setViewName("system/quickmenu/quickmenu_list");
		return mv;
	}
	/**
	 * 
	  * @Title: findInfo
	  * @Description: TODO
	  * @param @param request
	  * @param @param session
	  * @param @param response
	  * @param @return    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/findInfo")
	public ModelAndView findInfo(HttpServletRequest request,HttpSession session,HttpServletResponse response){
		mv.clear();
		pd = new PageData(request);
		PageData p = quickMenuService.findInfo(pd);
		mv.addObject("p", p);
		mv.addObject("pd", pd);
		mv.setViewName("system/quickmenu/quickmenu_info");
		return mv;
	}
	
	/**
	 * 
	  * @Title: toadd
	  * @Description: TODO
	  * @param @param request
	  * @param @param session
	  * @param @param response
	  * @param @return    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/toadd")
	public ModelAndView toadd(HttpServletRequest request,HttpSession session,HttpServletResponse response){
		mv.clear();
		pd = new PageData(request);
		
		PageData uPd = (PageData)session.getAttribute(Const.SESSION_USER);
		pd.put("user_id", uPd.get("id"));
		pd.put("role_id", session.getAttribute(Const.SESSION_ROLE));
		List<Integer> userRoleList = mainService.findRoleList(pd);
		List<PageData> menuList = new ArrayList<PageData>();
		if(userRoleList.size()>0){
			pd.put("list", userRoleList);
			pd.put("level", 1);
			menuList = mainService.findMenuList(pd);
		}
		
		mv.addObject("pd",pd);
		mv.addObject("list",menuList);
		mv.setViewName("system/quickmenu/quickmenu_add");
		return mv;
	}
	
	@RequestMapping(value="/save")
	public ModelAndView save(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception{
		mv.clear();
		pd = new PageData(request);
		
		PageData user = (PageData)session.getAttribute(Const.SESSION_USER);
		
		//判断是否是系统管理员
		String role_id = (String) session.getAttribute(Const.SESSION_ROLE);
		if(Verify.verifyIsNotNull(role_id)){
			if(parameterUtil.superAdministrator.equals(role_id)){
				pd.put("type", parameterUtil.admin_type);
			}else{
				pd.put("type", parameterUtil.ordinary_type);
			}
		}else{
			pd.put("type", parameterUtil.ordinary_type);
		}
		
		pd.put("create_user", user.get("id"));
		pd.put("update_time", DateTimeUtil.getDateTime());
		pd.put("create_time", DateTimeUtil.getDateTime());
		this.quickMenuService.save(pd);
		pd.put("flag", "b");
		mv.addObject("pd",pd);
		mv.setViewName("system/quickmenu/quickmenu_add");		
		return mv;
	}
	
	/**
	 * 
	  * @Title: toEdit
	  * @Description: TODO
	  * @param @param request
	  * @param @param session
	  * @param @param response
	  * @param @return    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/toedit")
	public ModelAndView toEdit(HttpServletRequest request,HttpSession session,HttpServletResponse response){
		mv.clear();
		pd = new PageData(request);
		
		PageData p = quickMenuService.findInfo(pd);
		
		//查询一级菜单
		pd.put("remove_logo", "N");
		pd.put("parent_id", parameterUtil.menu_one);
		List<PageData> list = menuService.findList(pd);
		
		mv.addObject("p", p);
		mv.addObject("pd", pd);
		mv.addObject("list", list);
		mv.setViewName("system/quickmenu/quickmenu_edit");
		return mv;
	}
	
	/**
	 * 
	  * @Title: update
	  * @Description: TODO
	  * @param @param request
	  * @param @param session
	  * @param @param response
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value="/update")
	public ModelAndView update(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception{
		mv.clear();
		pd = new PageData(request);
		PageData user = (PageData)session.getAttribute(Const.SESSION_USER);
		//判断是否是系统管理员
		String type = pd.getString("type");
		
		if(parameterUtil.superAdministrator.equals(session.getAttribute(Const.SESSION_ROLE))){
			//编辑
			pd.put("update_time", DateTimeUtil.getDateTime());
			this.quickMenuService.update(pd);
		}else{
			if(type.equals(parameterUtil.admin_type)){
				//添加
				pd.put("type", parameterUtil.ordinary_type);
				pd.put("create_user", user.get("id"));
				pd.put("update_time", DateTimeUtil.getDateTime());
				pd.put("create_time", DateTimeUtil.getDateTime());
				this.quickMenuService.save(pd);
			}else if(type.equals(parameterUtil.ordinary_type)){
				//编辑
				pd.put("update_time", DateTimeUtil.getDateTime());
				this.quickMenuService.update(pd);
			}
		}
		
		
		pd.put("flag", "b");
		mv.addObject("pd",pd);
		mv.setViewName("system/quickmenu/quickmenu_edit");		
		return mv;
	}
	
	
	/**
	 * 
	  * @Title: del
	  * @Description: 删除方法
	  * @param @param request
	  * @param @param session
	  * @param @param response
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/delete")
	public void del(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
		String[] ids = pd.getString("ids").split(",");
		quickMenuService.delete(ids);
		this.writeJson(response, true);
	}
	
	
	
	/**
	 * 
	  * @Title: findMenuJson
	  * @Description: 查询二级菜单
	  * @param @param request
	  * @param @param session
	  * @param @param response
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findMenuJson")
	public void findMenuJson(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception{
		mv.clear();
		pd = new PageData(request);
		//查询二级菜单
		PageData uPd = (PageData)session.getAttribute(Const.SESSION_USER);
		pd.put("user_id", uPd.get("id"));
		List<Integer> userRoleList = mainService.findRoleList(pd);
		List<PageData> menuList = new ArrayList<PageData>();
		if(userRoleList.size()>0){
			pd.put("list", userRoleList);
			pd.put("level", 2);
			menuList = mainService.findMenuList(pd);
		}
		this.writeJson(response, menuList);
	}

}
