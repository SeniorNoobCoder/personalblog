package com.sdhsie.base.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sdhsie.web.common.service.MessageService;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.sdhsie.base.util.Const;
import com.sdhsie.base.util.Log;
import com.sdhsie.base.util.Page;
import com.sdhsie.base.util.PageData;
import com.sdhsie.base.util.Verify;
import com.sdhsie.base.util.parameterUtil;
import com.sdhsie.web.system.service.MainService;
import com.sdhsie.web.system.service.QuickMenuService;


public class BaseController {
	
	protected Logger logger = Log.getInstance(getClass());
	protected ModelAndView mv = this.getModelAndView();
	
	@Autowired
	protected MainService mainService;
	@Autowired
	protected QuickMenuService quickMenuService;
	@Autowired
	private MessageService messageService;
	
	/**
	 * 得到ModelAndView
	 */
	public ModelAndView getModelAndView(){
		
		return new ModelAndView();
	}
	
	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		
		return request;
	}
	
	/**
	 * 得到分页列表的信息 
	 */
	public Page getPage(){
		
		return new Page();
	}
	
	public static void logBefore(Logger logger, String interfaceName){
		logger.info("");
		logger.info("start");
		logger.info(interfaceName);
	}
	
	public static void logAfter(Logger logger){
		logger.info("end");
		logger.info("");
	}
	
	public void writeJson(HttpServletResponse response,Object object) throws IOException{
		
		response.setContentType("text/html;charset=utf-8");
		ObjectMapper objMapper = new ObjectMapper();
		JsonGenerator jsonGenerator = objMapper.getJsonFactory()
				.createJsonGenerator(response.getOutputStream(),
						JsonEncoding.UTF8);
		
		jsonGenerator.writeObject(object);
		jsonGenerator.flush();
		jsonGenerator.close();
	}
	
	/**
	 * 
	  * @Title: getBaseMv
	  * @Description: 查询用户角色菜单信息
	  * @param @param session
	  * @param @param pd
	  * @param @return    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	public ModelAndView getBaseMv(HttpSession session,PageData pd){
		//获取登录用户
		PageData uPd = (PageData) session.getAttribute(Const.SESSION_USER);
		if(Verify.verifyIsNotNull(uPd)){
			//获取用户角色
			pd.put("user_id", uPd.get("id"));
			pd.put("role_id", session.getAttribute(Const.SESSION_ROLE));
			List<Integer> userRoleList = mainService.findRoleList(pd);
			List<PageData> menuList = new ArrayList<PageData>();
			if(userRoleList.size()>0){
				//获取菜单信息
				pd.put("list", userRoleList);
				pd.put("level", 1);
				menuList = mainService.findMenuList(pd);
				for (PageData p : menuList) {
					PageData pp = new PageData();
					pp.put("level", 2);
					pp.put("parent_id", p.get("menu_id"));
					pp.put("list", userRoleList);
					List<PageData> menu2List = mainService.findMenuList(pp);
					p.put("menu2List", menu2List);
				}
				
			}
			//查询角色信息
			List <PageData> roleList = mainService.findRoleLs(pd);
			PageData mPd = pd;
			
			mPd.put("superAdministrator", parameterUtil.superAdministrator);
			pd.remove("role_id");
			List<Integer> urList = mainService.findRoleList(pd);
			
			
			
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
			PageData mesgPd = new PageData();
			mesgPd.put("remove_logo", "N");
			mesgPd.put("user_id", uPd.get("id"));
			mesgPd.put("status", "N");
			List<PageData> messageList = messageService.findListByUser(mesgPd);
			mv.addObject("noticeNum",messageList.size());
			mv.addObject("mPd", mPd);
			mv.addObject("menuList", menuList);
			mv.addObject("userRoleList", urList);
			mv.addObject("userAllRoleList", roleList);
			mv.addObject("quickmenuList", list);
		}
		return mv;
	}
	
	public String getRoleButton(HttpSession session){
		
		//获取登录用户
		PageData uPd = (PageData) session.getAttribute(Const.SESSION_USER);
		StringBuffer power = new StringBuffer();
		if(Verify.verifyIsNotNull(uPd)){
			PageData pd = new PageData();
			//获取用户角色
			pd.put("user_id", uPd.get("id"));
			pd.put("role_id", session.getAttribute(Const.SESSION_ROLE));
			List<Integer> roleList = mainService.findRoleList(pd);
			List<PageData> buttonList = new ArrayList<PageData>();
			//获取用户的菜单权限
			if(roleList.size()>0){
				pd.put("list", roleList);
				buttonList = mainService.findButtonList(pd);
			}
			for (PageData pageData : buttonList) {
				power.append(pageData.get("code")).append(",");
			}
		}
		return  power.toString();
	}
	
}
