package com.sdhsie.app.family.controller;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sdhsie.app.common.controller.AppParameter;
import com.sdhsie.base.util.*;
import com.sdhsie.base.util.upload.FileUpload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sdhsie.app.cache.Cache;
import com.sdhsie.app.cache.CacheManager;
import com.sdhsie.app.server.service.AppLogService;
import com.sdhsie.app.server.service.AppLoginService;
import com.sdhsie.base.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
@Controller
@RequestMapping(value="/app/family")
public class AppFamilyController extends BaseController{
	
	@Autowired
	private AppLoginService appLoginService;
	@Autowired
	private AppLogService appLogService;//日志
	private boolean success = false;
	private String msg = "";
	private String code = "";
	private Object myobject;
	private String sessionId = "";
	private boolean verifySessionId = false;
	private String flag="";
	/**
	 * 
	  * @Title: findPhone
	  * @Description: 判断手机号是否注册
	  * @param @param request
	  * @param @param session
	  * @param @param response
	  * @param @throws IOException    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public boolean findPhone(String phone) throws IOException{
		boolean isTrue = true;
		PageData pd = new PageData();
		pd.put("login_name",phone);
		int num = appLoginService.findPhone(pd);
		if(num>0){
			isTrue = false;
		}
		return isTrue;
	}
	
	
	/**
	 * @throws Exception 
	  * @Title: register
	  * @Description: 注册
	  * @param @param request
	  * @param @param session
	  * @param @param response
	  * @param @throws IOException    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/fmailyzc.html", method = RequestMethod.POST)
	public void register(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception  {
		Json json = new Json();
		PageData pd = new PageData();
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String phone = bodyMap.get("login_name").toString();//手机号
		String password=bodyMap.get("login_password").toString();//获取密码
		String yzm=bodyMap.get("yzm").toString();//获取验证码
		
		boolean isTrue = this.findPhone(phone);
		
		String yzmCache = "";//缓存验证码
		if(CacheManager.hasCache(phone)){
			yzmCache = (String) CacheManager.getCacheInfo(phone).getValue();
		}
		if(isTrue){
			if(Verify.verifyIsNotNull(yzmCache)){
				if(yzm.equals(yzmCache)){
					//========================================================
					String session_id =GuidUtil.getGuid();//生成sessionId
					pd.put("user_type", ecareParameter.family_user);
					pd.put("login_name", phone);
					pd.put("phone", phone);
					pd.put("login_password", MD5Util.MD5(password));
					pd.put("remove_logo", "N");
					pd.put("nickname", phone);
					pd.put("isAuthent", "N");
					pd.put("session_id",session_id);
					pd.put("status", "1");//1启用0禁用
					appLoginService.save(pd);
					
					//=========================================================
					json.setMsg("注册成功！");
					json.setSuccess(true);
					json.setMyobject(pd);
					json.setCode("0");
				}else{
					json.setMsg("验证码不正确！");
					json.setSuccess(false);
					json.setMyobject(pd);
					json.setCode("0");
				}
			}else{
				json.setMsg("验证码不能为空！");
				json.setSuccess(false);
				json.setMyobject(pd);
				json.setCode("0");
			}
		}else{
			json.setMsg("手机号码已注册，请直接登录!");
			json.setSuccess(false);
			json.setMyobject(pd);
			json.setCode("0");
		}
		this.writeJson(response, json);
		
	}
	
	
	/**
	 * 
	  * @Title: login
	  * @Description: 登录
	  * @param @param map
	  * @param @param request
	  * @param @param session
	  * @param @param response
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/familylogin.html", method = RequestMethod.POST)
	public void login(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception  {
		Json json = new Json();
		try {
			Map<String, Object> bodyMap = AppParameter.getBody(map);
			String name = bodyMap.get("login_name").toString();//登录名
			String password = bodyMap.get("login_password").toString();//密码
		
			String session_id = "";
				if(!Verify.verifyIsNotNull(name)){
					json.setMsg("登录名称不可为空。");
				}else if(!Verify.verifyIsNotNull(password)){
					json.setMsg("登录密码不可为空。");
				}else{
					PageData loginPd = new PageData();
					loginPd.put("login_name", name);
					loginPd.put("user_type", ecareParameter.family_user);//类型
					
					PageData uPd = appLoginService.findLoginInfo(loginPd);
					if(Verify.verifyIsNotNull(uPd)){
						if(uPd.getString("login_password").equals(MD5Util.MD5(password))){
								if(uPd.getString("status").equals("1")){
									//修改session_id
									session_id =GuidUtil.getGuid();
									uPd.put("session_id",session_id);
									appLoginService.update(uPd);
									//登录日志
									this.addLog(uPd,request);
									//登录成功
									if(Verify.verifyIsNotNull(uPd.getString("head_address"))){
										uPd.put("head_address", FileUpload.findLocalFileUrl(uPd.getString("head_address")));
									}else{
										uPd.put("head_address","");
									}
									json.setMyobject(uPd);
									code= "0";
									success = true;
									msg = "登录成功!";
								}else{
									code= "0";
									success = false;
									msg = "账号已被冻结，请联系管理员";
								}
							
						}else{
							code= "0";
							success = false;
							msg = "登录密码不正确";
						}
					}else{
						code= "0";
						success = false;
						msg = "登录名称不存在异常";
					}
				}
				
		} catch (Exception e) {
			e.printStackTrace();
			code= "0";
			success = false;
			msg = "数据异常";
		}
		json.setMsg(msg);
		json.setCode(code);
		json.setSuccess(success);
		this.writeJson(response, json);
	}
	
	
	/**
	 * 保存登录日志
	  * @Title: addLog
	  * @Description: TODO
	  * @param @param pd
	  * @param @param request
	  * @param @throws ParseException    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public void addLog(PageData pd,HttpServletRequest request) throws ParseException{
		PageData logPd = new PageData();
		logPd.put("user_id", pd.get("id"));
		logPd.put("username", pd.get("login_name"));
		logPd.put("type", "登录");
		logPd.put("content", pd.get("login_name")+"于"+DateTimeUtil.getDateTimeStr()+"登录了系统");
		logPd.put("path", request.getServletPath());
		logPd.put("createtime", DateTimeUtil.getDateTime());
		appLogService.save(logPd);
	}
	
	
	
	/**
	 * 
	  * @Title: familysession
	  * @Description: family自动登录
	  * @param @param map
	  * @param @param request
	  * @param @param session
	  * @param @param response
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/familysession.html", method = RequestMethod.POST)
	public void familysession(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception  {
		Json json = new Json();
		try {
			String session_id = AppParameter.getSessionId(map);//手机用户session_id
			Map<String, Object> bodyMap = AppParameter.getBody(map);
					PageData loginPd = new PageData();
					loginPd.put("user_type", ecareParameter.family_user);//类型
					loginPd.put("session_id", session_id);
					PageData uPd = appLoginService.findLoginInfo(loginPd);
					if(Verify.verifyIsNotNull(uPd)){
								if(uPd.getString("status").equals("1")){
									//登录日志
									this.addLog(uPd,request);
									
									//登录成功
									if(Verify.verifyIsNotNull(uPd.getString("head_address"))){
										uPd.put("head_address", FileUpload.findLocalFileUrl(uPd.getString("head_address")));
									}else{
										uPd.put("head_address","");
									}
									json.setMyobject(uPd);
									code= "0";
									success = true;
									msg = "登录成功!";
								}else{
									code= "0";
									success = false;
									msg = "账号已被冻结，请联系管理员";
								}
					}else{
						code= "-5";
						success = false;
						msg = "登录超时！";
					}
				
		} catch (Exception e) {
			e.printStackTrace();
			code= "0";
			success = false;
			msg = "数据异常";
		}
		json.setMsg(msg);
		json.setCode(code);
		json.setSuccess(success);
		this.writeJson(response, json);
	}
	
	
	
	
	
}
