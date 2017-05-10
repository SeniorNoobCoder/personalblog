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
import com.sdhsie.base.util.GuidUtil;
import com.sdhsie.base.util.MD5Util;
import com.sdhsie.base.util.Page;
import com.sdhsie.base.util.PageData;
import com.sdhsie.base.util.Verify;
import com.sdhsie.base.util.parameterUtil;
import com.sdhsie.base.util.upload.FileUpload;
import com.sdhsie.base.util.upload.ParaUtil;
import com.sdhsie.web.system.service.DictionaryService;
import com.sdhsie.web.system.service.UserService;

@Controller
@RequestMapping(value = "/system/user")
public class UserController extends BaseController {
	@Autowired
	private UserService userService;
	private PageData pd;
	@Autowired
	private DictionaryService dictionaryService;


	/**
	 * 
	  * @Title: findList
	  * @Description: 查询信息列表
	  * @param @param request
	  * @param @param response
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/findList")
	public ModelAndView findList(HttpServletRequest request,HttpSession session,Page page) throws Exception {
		pd = new PageData(request);
		mv = getBaseMv(session, pd);
	
		pd.put("remove_logo", "N");
		pd.put("type", parameterUtil.system_user);
		page.setPd(pd);
		List<PageData> userList = userService.findListPage(page);
		
		//查询垃圾箱数量
		int num = userService.findDelNum(pd);
		
		pd.put("num", num);
		mv.addObject("pd", pd);	
		mv.addObject("userList", userList);
		mv.addObject("power",getRoleButton(session));
		mv.setViewName("system/user/user_list");
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
	public ModelAndView toadd(HttpServletRequest request,HttpSession session) throws Exception {
		pd = new PageData(request);
		mv.addObject("pd", pd);	
		pd.put("remove_logo", "N");
		pd.put("parent_id", "117");
		List<PageData> dictionaryList =dictionaryService.findList(pd);
		mv.addObject("dictionaryList", dictionaryList);
		mv.setViewName("system/user/user_add");
		return mv;
	}
	
	
	/**
	 * 
	  * @Title: toedit
	  * @Description: 跳转到编辑页面
	  * @param @param request
	  * @param @param session
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/toedit")
	public ModelAndView toedit(HttpServletRequest request,HttpSession session) throws Exception {
		pd = new PageData(request);
	
		PageData p = userService.findInfo(pd);
		if(Verify.verifyIsNotNull(p.getString("head_address"))){
			p.put("head_url", FileUpload.findLocalFileUrl(p.getString("head_address")));
		}else{
			p.put("head_url","");
		}
		
		pd.put("remove_logo", "N");
		pd.put("parent_id", "117");
		List<PageData> dictionaryList =dictionaryService.findList(pd);
		mv.addObject("dictionaryList", dictionaryList);
		mv.addObject("p", p);	
		mv.addObject("pd", pd);	
		mv.setViewName("system/user/user_edit");
		return mv;
	}
	
	
	/**
	 * 
	  * @Title: save
	  * @Description: 保存信息
	  * @param @param request
	  * @param @param session
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save(HttpServletRequest request,HttpSession session) throws Exception {
		pd = new PageData(request);
	
		pd.putAll(FileUpload.saveLocalFile(request, ParaUtil.system+ParaUtil.user, pd));
		
		PageData user = (PageData)session.getAttribute(Const.SESSION_USER);
		String wearth_city_id = pd.getString("wearth_city_id");
		if(wearth_city_id!=null && "".equals(wearth_city_id)){
			pd.put("wearth_city_id", 1);
		}
		pd.put("create_user", user.get("id"));
		pd.put("login_password", MD5Util.MD5(pd.getString("login_password")));
		pd.put("digital_account", GuidUtil.getCodeNum());
		pd.put("status", "Y");
		pd.put("remove_logo", "N");
		pd.put("type", parameterUtil.system_user);
		pd.put("create_time", DateTimeUtil.getDateTime());
		pd.put("update_time", DateTimeUtil.getDateTime());
		userService.save(pd);
		
		pd.put("label", "b");
		mv.addObject("pd", pd);	
		mv.setViewName("system/user/user_add");
		return mv;
	}
	
	
	/**
	 * 
	  * @Title: update
	  * @Description: 更新信息
	  * @param @param request
	  * @param @param session
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/update")
	public ModelAndView update(HttpServletRequest request,HttpSession session) throws Exception {
		pd = new PageData(request);
	
		pd.putAll(FileUpload.uploadLocalFile(request, ParaUtil.system+ParaUtil.user, pd));
		
		if(Verify.verifyIsNotNull(pd.getString("login_password"))){
			pd.put("login_password", MD5Util.MD5(pd.getString("login_password")));
		}
		pd.put("update_time", DateTimeUtil.getDateTimeStr());
		userService.update(pd);
		
		pd.put("label", "b");
		mv.addObject("pd", pd);	
		mv.setViewName("system/user/user_edit");
		return mv;
	}
	
	/**
	 * 
	  * @Title: del
	  * @Description: 删除信息
	  * @param @param request
	  * @param @param session
	  * @param @param response
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/del")
	public void del(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
	
		String[] ids = pd.getString("ids").split(",");
		for (String id : ids) {
			PageData p = new PageData();
			p.put("id", id);
			p.put("remove_logo", "Y");
			p.put("update_time", DateTimeUtil.getDateTime());
			userService.update(p);
		}
		this.writeJson(response, true);
	}
	
	
	/**
	 * 
	  * @Title: findInfo
	  * @Description: 查询信息详情
	  * @param @param request
	  * @param @param session
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/findInfo")
	public ModelAndView findInfo(HttpServletRequest request,HttpSession session) throws Exception {
		pd = new PageData(request);
	
		PageData p = userService.findInfo(pd);
		if(Verify.verifyIsNotNull(p.getString("head_address"))){
			p.put("head_url", FileUpload.findLocalFileUrl(p.getString("head_address")));
		}else{
			p.put("head_url","");
		}
		pd.put("remove_logo", "N");
		pd.put("parent_id", "117");
		List<PageData> dictionaryList =dictionaryService.findList(pd);
		mv.addObject("dictionaryList", dictionaryList);
		mv.addObject("p", p);	
		mv.addObject("pd", pd);	
		mv.setViewName("system/user/user_info");
		return mv;
	}
	
	
	/**
	 * 
	  * @Title: findTrash
	  * @Description: 垃圾箱处理
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
	
		pd.put("remove_logo", "Y");
		pd.put("type", parameterUtil.system_user);
		page.setPd(pd);
		List<PageData> list = userService.findTrashPage(page);
		
		mv.addObject("list",list);	
		mv.addObject("pd", pd);	
		mv.setViewName("system/user/user_trash");
		return mv;
	}
	
	
	
	/**
	 * 
	  * @Title: clearUser
	  * @Description: 清空彻底删除
	  * @param @param request
	  * @param @param session
	  * @param @param response
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/clearUser")
	public void clearUser(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
	
		pd.put("type", parameterUtil.system_user);
		userService.clearUser(pd);
		
		this.writeJson(response, true);
		
	}
	
	
	/**
	 * 
	  * @Title: revertUser
	  * @Description: 还原删除信息
	  * @param @param request
	  * @param @param session
	  * @param @param response
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/revertUser")
	public void revertUser(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
		pd.put("type", parameterUtil.system_user);
		userService.revertUser(pd);
		this.writeJson(response, true);
	}
	
	
	/**
	 * 
	  * @Title: updateStatus
	  * @Description: 更新状态
	  * @param @param request
	  * @param @param session
	  * @param @param response
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/updateStatus")
	public void updateStatus(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
	
		pd.put("update_time", DateTimeUtil.getDateTime());
		userService.update(pd);
		
		this.writeJson(response, true);
		
	}
	
	
	
	/**
	 * 
	  * @Title: findUserRole
	  * @Description: 设置用户角色信息
	  * @param @param request
	  * @param @param session
	  * @param @param page
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/findUserRole")
	public ModelAndView findUserRole(HttpServletRequest request,HttpSession session) throws Exception {
		pd = new PageData(request);
	
		//查询所有角色
		List<PageData> allRoleList = userService.findRoleList(pd);
		
		//查询用户角色
		List<PageData> userRoleList = userService.findUserRoleList(pd);
		
		pd.put("superAdministrator", parameterUtil.superAdministrator);
		mv.addObject("allRoleList",allRoleList);	
		mv.addObject("userRoleList",userRoleList);	
		mv.addObject("pd", pd);	
		mv.setViewName("system/user/user_role");
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
			p.put("create_user", pd.get("user_id"));
			p.put("create_time", DateTimeUtil.getDateTime());
			list.add(p);
		}
		userService.saveUserRole(list,pd);
		this.writeJson(response, true);
	}
	
	
	
	@RequestMapping(value = "/findUserOrganize")
	public ModelAndView findUserOrganize(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
	
		//查询组织
		mv.addObject("pd", pd);	
		mv.setViewName("system/user/user_organize");
		return mv;
	}
	
	
	@RequestMapping(value = "/findOraganize")
	public void findOraganize(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
		//查询组织
		pd.put("remove_logo", "N");
		List<PageData> oraganizeList = userService.findOraganizeList(pd);
		
		this.writeJson(response, oraganizeList);
	}
	
	
	
	@RequestMapping(value="/saveOrganize")
	public void saveOrganize(HttpSession session,HttpServletResponse response) throws Exception{
		pd = new PageData(this.getRequest());
		String user_id=pd.getString("user_id");
		
		mv = getBaseMv(session,pd);
		String ids[] = pd.getString("organizes").split(",");
		pd.put("user_id", user_id);
		userService.delOrganize(pd);
		if(ids.length>0){
			for (String id : ids) {
				PageData p = new PageData();
				p.put("user_id", user_id);
				p.put("organize_id", id);
				p.put("create_user", ((PageData)session.getAttribute(Const.SESSION_USER)).get("id"));
				p.put("create_time", DateTimeUtil.getDateTime());
				userService.saveOrganize(p);
			}
		}
		this.writeJson(response, true);
	}
	
	
	
	
	/**
	 * 
	  * @Title: checkLoginName
	  * @Description: 验证登录名是否存在
	  * @param @param session
	  * @param @param response
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/checkLoginName")
	public void checkLoginName(HttpSession session,HttpServletResponse response) throws Exception{
		pd = new PageData(this.getRequest());
		
		boolean bol = true;
		PageData p = userService.findInfo(pd);
		if(Verify.verifyIsNotNull(p)){
			bol = false;
			if(Verify.verifyIsNotNull(pd.get("flag"))){
				if(pd.get("flag").toString().trim().equals(p.get("login_name").toString().trim())){
					bol = true;
				}
			}
		}
		
		this.writeJson(response, bol);
	}
	
}
