package com.sdhsie.web.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sdhsie.base.controller.BaseController;
import com.sdhsie.base.util.Const;
import com.sdhsie.base.util.DateTimeUtil;
import com.sdhsie.base.util.MD5Util;
import com.sdhsie.base.util.PageData;
import com.sdhsie.base.util.Verify;
import com.sdhsie.base.util.parameterUtil;
import com.sdhsie.base.util.upload.FileUpload;
import com.sdhsie.web.system.service.LogService;
import com.sdhsie.web.system.service.LoginService;

@Controller
@RequestMapping(value = "/system/login")
public class LoginController extends BaseController {
	@Autowired
	private LoginService loginService;
	@Autowired
	private LogService logService;
	private PageData pd;

	
	
	/**
	 * 
	  * @Title: toLogin
	  * @Description: 跳转到登录页面
	  * @param @param session
	  * @param @param request
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value="/toLogin")
	public ModelAndView toLogin(HttpSession session,HttpServletRequest request)throws Exception{
		pd = new PageData(request);
		mv.setViewName("system/admin/login");
		return mv;
	}
	

	/**
	 * 
	  * @Title: findList
	  * @Description: 登录方法
	  * @param @param request
	  * @param @param session
	  * @param @param page
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/login")
	public ModelAndView findList(HttpServletRequest request,HttpSession session) throws Exception {
		pd = new PageData(request);
		mv = getBaseMv(session, pd);
		
		String errMsg = "";
		boolean errBol = false;
		String ym = "system/admin/login";
		if(Verify.verifyIsNotNull(session.getAttribute(Const.SESSION_SECURITY_CODE))){
			if(!Verify.verifyIsNotNull(pd.getString("login_name"))){
				errMsg="登录名称不可为空。";
			}else if(!Verify.verifyIsNotNull(pd.getString("login_password"))){
				errMsg="登录密码不可为空。";
			}else if(!session.getAttribute(Const.SESSION_SECURITY_CODE).toString().toLowerCase().equals(pd.getString("code").toLowerCase())){
				errMsg="验证码不正确。";
			}else{
				PageData uPd = loginService.findUserInfo(pd);
				if(Verify.verifyIsNotNull(uPd)){
					if(uPd.getString("login_password").equals(MD5Util.MD5(pd.getString("login_password")))){
						String type =  uPd.get("type").toString();
						uPd.put("user_id", uPd.get("id"));
						if(type.equals(parameterUtil.system_user)){
							PageData orgPd = loginService.findUserOrganize(uPd);
							if(Verify.verifyIsNotNull(orgPd)){
								session.setAttribute(Const.SESSION_USER_ORG, orgPd.get("organize_id"));
								session.setAttribute(Const.SESSION_ORG_CASCADE, orgPd.get("org_cascade"));
							}else{
								session.setAttribute(Const.SESSION_USER_ORG, "");
								session.setAttribute(Const.SESSION_ORG_CASCADE, "");
							}
						}else if(type.equals(parameterUtil.telemarketer_user)){
							Integer orgId = loginService.findUnitOrgId(uPd);
							if(Verify.verifyIsNotNull(orgId)){
								session.setAttribute(Const.SESSION_USER_ORG, orgId);
							}else{
								session.setAttribute(Const.SESSION_USER_ORG, "");
							}
						}
						if(Verify.verifyIsNotNull(session.getAttribute(Const.SESSION_USER_ORG)) || type.equals(parameterUtil.system_user) ){
							if(uPd.getString("status").equals("Y")){
								uPd.put("head_address_url", FileUpload.findLocalFileUrl(uPd.getString("head_address")));
								//登录日志
								PageData logPd = new PageData();
								logPd.put("user_id", uPd.get("id"));
								logPd.put("username", uPd.get("login_name"));
								logPd.put("type", "登录");
								logPd.put("content", uPd.get("login_name")+"于"+DateTimeUtil.getDateTimeStr()+"登录了系统");
								logPd.put("path", request.getServletPath());
								logPd.put("createtime", DateTimeUtil.getDateTime());
								logService.save(logPd);
								//登录成功
								errBol=true;
								session.setAttribute(Const.SESSION_USER, uPd);
								ym = "redirect:/system/main/index.do";
							}else{
								errMsg="账号已被冻结，请联系管理员";
							}
						}else{
							errMsg="该账号暂时不能登录";
						}
					}else{
						errMsg="登录密码不正确。";
					}
				}else{
					errMsg="登录名称不存在。";
				}
				
			}
		}
		
		mv.addObject("errMsg", errMsg);
		mv.addObject("errBol", errBol);
		mv.addObject("pd", pd);	
		mv.setViewName(ym);
		return mv;
	}
	
	
	
	/**
	 * 
	  * @Title: outLogin
	  * @Description: 用户退出
	  * @param @param session
	  * @param @param request
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value="/outLogin")
	public ModelAndView outLogin(HttpSession session,HttpServletRequest request)throws Exception{
		pd = new PageData(request);
		
		session.removeAttribute(Const.SESSION_USER);//清除用户
		session.removeAttribute(Const.SESSION_ROLE);//清除权限
		session.removeAttribute(Const.SESSION_USER_ORG);//清除组织
		mv.setViewName("system/admin/login");
		return mv;
	}
	
	
	
	
}
