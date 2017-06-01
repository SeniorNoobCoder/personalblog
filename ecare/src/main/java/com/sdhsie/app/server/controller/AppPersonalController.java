package com.sdhsie.app.server.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sdhsie.app.file.AppFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sdhsie.app.cache.Cache;
import com.sdhsie.app.cache.CacheManager;
import com.sdhsie.app.common.controller.AppParameter;
import com.sdhsie.app.family.service.AppMineService;
import com.sdhsie.app.server.service.AppOrderService;
import com.sdhsie.app.server.service.AppPersonalService;
import com.sdhsie.base.controller.AppBaseController;
import com.sdhsie.base.controller.BaseController;
import com.sdhsie.base.util.DateTimeUtil;
import com.sdhsie.base.util.Json;
import com.sdhsie.base.util.MD5Util;
import com.sdhsie.base.util.PageData;
import com.sdhsie.base.util.Verify;
import com.sdhsie.base.util.parameterUtil;
import com.sdhsie.base.util.upload.FileUpload;
import com.sdhsie.base.util.upload.ParaUtil;

@Controller
@RequestMapping(value = "/app/personal")
public class AppPersonalController extends AppBaseController{
	@Autowired
	private AppOrderService appOrderService;
	@Autowired
	private AppPersonalService appPersonalService;
	@Autowired
	private AppMineService appMineService;
	
	/**
	 * 
	  * @Title: retransmission
	  * @Description: 忘记密码时发短信
	  * @param @param map
	  * @param @param session
	  * @param @param response
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/retransmission.html" , method = RequestMethod.POST)
	public void retransmission(@RequestBody Map<String, Object> map, HttpSession session, HttpServletResponse response) throws Exception{
		Json json = new Json();
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String phone = bodyMap.get("login_name").toString();//手机号
		PageData pd = new PageData();
		String yzm ="";
			yzm = "123456";//GuidUtil.getRandNum(6);//验证码
			String[] str = {yzm, "1"};//短信内容
			CacheManager.putCache(phone, new Cache(null, yzm, 1, true));
//			SMSUtil.sendSMS(phone, SMSUtil.templateId, str);//发送短信
		
		
		pd.put("yzm", yzm);
		json.setMsg("发送成功！");
		json.setSuccess(true);
		json.setMyobject(pd);
		json.setCode("0");
		this.writeJson(response, json);
	}
	
	
	
	/**
	 * 
	  * @Title: forget
	  * @Description: 忘记密码
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/forget.html", method = RequestMethod.POST)
	public void forget(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		Json json = new Json();
//		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String yzm=bodyMap.get("yzm").toString();//获取验证码
		String phone=bodyMap.get("login_name").toString();
		String password=bodyMap.get("login_password").toString();
		try {
			
					PageData pp=new PageData();
					String yzmCache = "";//缓存验证码
					if(CacheManager.hasCache(phone)){
						yzmCache = (String) CacheManager.getCacheInfo(phone).getValue();
					}
					if (Verify.verifyIsNotNull(yzmCache)) {
						if (yzm.equals(yzmCache)) {
							pp.put("login_name", phone);
							pp.put("login_password",  MD5Util.MD5(password));
							appPersonalService.update(pp);
							
							json.setMsg("成功找回密码。");
							json.setCode("0");
							json.setVerifySessionId(true);
							json.setSuccess(true);
						}else{
							json.setMsg("验证码不正确！");
							json.setSuccess(false);
							json.setCode("0");
						}
					}else{
						json.setMsg("验证码不能为空！");
						json.setSuccess(false);
						json.setCode("0");
					}

			this.writeJson(response, json);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * @throws Exception 
	 * 
	  * @Title: update
	  * @Description: 修改密码 与个人信息修 个人开工状态改用的是相同的sql
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
		@RequestMapping(value="/update.html", method = RequestMethod.POST)
		public void update(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception{
			Json json = new Json();
			String session_id = AppParameter.getSessionId(map);//手机用户session_id
			Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
			String newPassword = bodyMap.get("login_password").toString();//新密码
			String oldPassword = bodyMap.get("password").toString();//旧密码
			
			try {
				if(Verify.verifyIsNotNull(session_id)){
					PageData p = new PageData();
					p.put("session_id", session_id);
					//获取当用户信息
					PageData userPd = appOrderService.findSessionInfo(p);
					if(Verify.verifyIsNotNull(userPd)){
						PageData pd = new PageData();
						String password=userPd.get("login_password").toString();//旧密码（数据库中的）
						String id=userPd.get("id").toString();
					if (!newPassword.equals(oldPassword)) {
						if (MD5Util.MD5(oldPassword).equals(password)) {
							pd.put("id", id);
							pd.put("login_password", MD5Util.MD5(newPassword));
							appPersonalService.updateStatus(pd);
							json.setMsg("修改密码成功。");
							json.setCode("0");
							json.setVerifySessionId(true);
							json.setSuccess(true);
						} else {
							json.setMsg("输入的旧密码不正确。");
							json.setCode("0");
							json.setVerifySessionId(true);
							json.setSuccess(false);
						}
					} else {
						json.setMsg("输入的旧密码不能与新密码相同。");
						json.setCode("0");
						json.setVerifySessionId(true);
						json.setSuccess(false);
					}
						
					}else{
						json.setMsg("session_id失效，请重新登录");
						json.setCode("-6");
						json.setSuccess(false);
					}
				}else{
					json.setMsg("session_id为空");
					json.setCode("-6");
					json.setSuccess(false);
				}
				this.writeJson(response, json);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
		
		
		
		/**
		 * 
		  * @Title: updatepeson
		  * @Description: 修改个人信息与修改密码 个人开工状态用的是相同的sql  
		  * @param @param map
		  * @param @param request
		  * @param @param response
		  * @param @param session
		  * @param @throws Exception    设定文件
		  * @return void    返回类型
		  * @throws
		 */
		@RequestMapping(value="/updatepeson.html", method = RequestMethod.POST)
		public void updatepeson(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception{
			Json json = new Json();
			String head_address = null;
			String sex=null;
			String live_address=null;
			String nickname=null;
			String session_id = AppParameter.getSessionId(map);//手机用户session_id
			Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
			try {
				if(Verify.verifyIsNotNull(session_id)){
					PageData p = new PageData();
					p.put("session_id", session_id);
					//获取当用户信息
					PageData userPd = appOrderService.findSessionInfo(p);
					if(Verify.verifyIsNotNull(userPd)){
						
						Object sex_obj= bodyMap.get("sex");
						if(sex_obj!=null){
							sex = sex_obj.toString();
						}
						Object nickname_obj= bodyMap.get("nickname");
						if(nickname_obj!=null){
							nickname = nickname_obj.toString();
						}
						//头像
						Object head_address_obj = bodyMap.get("head_address");
						if(head_address_obj!=null && !"".equals(head_address_obj)){
							head_address = head_address_obj.toString();
							String  head_address_path = ParaUtil.business+ParaUtil.user+ParaUtil.authent;
							head_address = AppFileUtil.generateImage(head_address,head_address_path,request);//图片上传
						}
						Object live_address_obj = bodyMap.get("live_address");
						if(live_address_obj!=null){
							 live_address = live_address_obj.toString();
						}
						PageData pd = new PageData();
						String id=userPd.get("id").toString();
							pd.put("id", id);
							pd.put("sex",  sex);
							pd.put("nickname", nickname);
							if(!"".equals(head_address_obj)){
							pd.put("head_address",  head_address);
							}
							pd.put("live_address",  live_address);
							appPersonalService.updateStatus(pd);
							
							PageData user = appOrderService.findUserInfo(p);//重新查找用户信息
							
							if(Verify.verifyIsNotNull(user.getString("head_address"))){
								user.put("head_address", FileUpload.findLocalFileUrl(user.getString("head_address")));
								}else{
									user.put("head_address","");
								}
							json.setMsg("修改个人信息成功。");
							json.setCode("0");
							json.setMyobject(user);
							json.setVerifySessionId(true);
							json.setSuccess(true);
					
					}else{
						json.setMsg("session_id失效，请重新登录");
						json.setCode("-6");
						json.setSuccess(false);
					}
				}else{
					json.setMsg("session_id为空");
					json.setCode("-6");
					json.setSuccess(false);
				}
				this.writeJson(response, json);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
		
		/**
		 * 
		  * @Title: onoff
		  * @Description: 个人开工状态
		  * @param @param map
		  * @param @param request
		  * @param @param response
		  * @param @param session
		  * @param @throws Exception    设定文件
		  * @return void    返回类型
		  * @throws
		 */
		@RequestMapping(value="/onoff.html", method = RequestMethod.POST)
		public void onoff(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception{
			Json json = new Json();
			String session_id = AppParameter.getSessionId(map);//手机用户session_id
			Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
			try {
				if(Verify.verifyIsNotNull(session_id)){
					PageData p = new PageData();
					p.put("session_id", session_id);
					//获取当用户信息
					PageData userPd = appOrderService.findSessionInfo(p);
					if(Verify.verifyIsNotNull(userPd)){
						PageData pd = new PageData();
						
						String id=userPd.get("id").toString();
						String iswork=userPd.get("is_work").toString();
						if(iswork.equals("0")){
							pd.put("is_work",  "1");
						}
						else{
							pd.put("is_work",  "0");
						}
							pd.put("id", id);
							appPersonalService.updateStatus(pd);
							json.setMsg("修改开工工成功。");
							json.setCode("0");
							json.setVerifySessionId(true);
							json.setSuccess(true);
					
					}else{
						json.setMsg("session_id失效，请重新登录");
						json.setCode("-6");
						json.setSuccess(false);
					}
				}else{
					json.setMsg("session_id为空");
					json.setCode("-6");
					json.setSuccess(false);
				}
				this.writeJson(response, json);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
		
		/**
		 * 
		  * @Title: findpartner
		  * @Description: 查找合作商
		  * @param @param map
		  * @param @param request
		  * @param @param response
		  * @param @param session
		  * @param @throws Exception    设定文件
		  * @return void    返回类型
		  * @throws
		 */
		@RequestMapping(value="/findpartner.html", method = RequestMethod.POST)
		public void findpartner(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception{
			Json json = new Json();
			String session_id = AppParameter.getSessionId(map);//手机用户session_id
			Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
			String code = bodyMap.get("code").toString();
			try {
				if(Verify.verifyIsNotNull(session_id)){
					PageData p = new PageData();
					p.put("session_id", session_id);
					//获取当用户信息
					PageData userPd = appOrderService.findSessionInfo(p);
					if(Verify.verifyIsNotNull(userPd)){
						PageData pd = new PageData();
							pd.put("code", code);
							PageData Partner=appPersonalService.findPartnerInfo(pd);
							
							json.setMsg("查询成功。");
							json.setMyobject(Partner);
							json.setCode("0");
							json.setVerifySessionId(true);
							json.setSuccess(true);
					
					}else{
						json.setMsg("session_id失效，请重新登录");
						json.setCode("-6");
						json.setSuccess(false);
					}
				}else{
					json.setMsg("session_id为空");
					json.setCode("-6");
					json.setSuccess(false);
				}
				this.writeJson(response, json);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
		
		
		/**
		 * 
		  * @Title: partnerUpdate
		  * @Description: 认证（下一步）
		  * @param @param map
		  * @param @param request
		  * @param @param response
		  * @param @param session
		  * @param @throws Exception    设定文件
		  * @return void    返回类型
		  * @throws
		 */
		@RequestMapping(value="/partnerUpdate.html", method = RequestMethod.POST)
		public void partnerUpdate(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception{
			Json json = new Json();
			String session_id = AppParameter.getSessionId(map);//手机用户session_id
			Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
			String code = bodyMap.get("code").toString();
			try {
				if(Verify.verifyIsNotNull(session_id)){
					PageData p = new PageData();
					p.put("session_id", session_id);
					//获取当用户信息
					PageData userPd = appOrderService.findSessionInfo(p);
					if(Verify.verifyIsNotNull(userPd)){
						
						PageData pd = new PageData();
						PageData Partner=new PageData();
						if(userPd.get("isAuthent").equals("N")){
							PageData ui=new PageData();
							//查询步数
							ui.put("business_id", userPd.get("id"));
							PageData user=appPersonalService.findUserInfo(ui);
							
							pd.put("code", code);
							Partner=appPersonalService.findPartnerInfo(pd);
							Partner.put("phone", userPd.get("phone"));
							Partner.put("isAuthent", userPd.get("isAuthent"));
							PageData pp=new PageData();
							pp.put("business_id", userPd.get("id"));
							pp.put("partner_code", code);
							pp.put("partner_id", Partner.get("id"));
							if(!"2".equals(user.get("step").toString())){
								pp.put("step", "1");
								appPersonalService.updateUser(pp);
							}
							json.setMsg("访问成功。");
							json.setMyobject(Partner);
							json.setCode("0");
							json.setVerifySessionId(true);
							json.setSuccess(true);
							
						}
						else{
//							isAuthent为Y的情况
							if(Verify.verifyIsNotNull(Partner.getString("idCard_front"))){
								Partner.put("idCard_front", FileUpload.findLocalFileUrl(Partner.getString("idCard_front")));
								}else{
									Partner.put("idCard_front","");
								}
							 if(Verify.verifyIsNotNull(Partner.getString("idCard_back"))){
								 Partner.put("idCard_back", FileUpload.findLocalFileUrl(Partner.getString("idCard_back")));
								}else{
									Partner.put("idCard_back","");
								}
							pd.put("business_id",  userPd.get("id"));
							Partner=appPersonalService.findUserInfo(pd);
							Partner.put("phone", userPd.get("phone"));
							Partner.put("isAuthent", userPd.get("isAuthent"));
							json.setMsg("访问成功。");
							json.setMyobject(Partner);
							json.setCode("0");
							json.setVerifySessionId(true);
							json.setSuccess(true);
						}
						
							
					}else{
						json.setMsg("session_id失效，请重新登录");
						json.setCode("-6");
						json.setSuccess(false);
					}
				}else{
					json.setMsg("session_id为空");
					json.setCode("-6");
					json.setSuccess(false);
				}
				this.writeJson(response, json);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
		
		/**
		 * 
		  * @Title: cardUpdate
		  * @Description: 认证（身份认证）
		  * @param @param map
		  * @param @param request
		  * @param @param response
		  * @param @param session
		  * @param @throws Exception    设定文件
		  * @return void    返回类型
		  * @throws
		 */
		@RequestMapping(value="/cardUpdate.html", method = RequestMethod.POST)
		public void cardUpdate(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception{
			Json json = new Json();
			String session_id = AppParameter.getSessionId(map);//手机用户session_id
			Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
			String authent_name = bodyMap.get("authent_name").toString();
			String PID = bodyMap.get("PID").toString();
			String idCard_back = bodyMap.get("idCard_back").toString();
			String idCard_front = bodyMap.get("idCard_front").toString();
			String idCard_back_path = ParaUtil.business+ParaUtil.user+ParaUtil.authent;
			String  idCard_front_path = ParaUtil.business+ParaUtil.user+ParaUtil.authent;
			if(!"".equals(idCard_back)) {
				idCard_back = AppFileUtil.generateImage(idCard_back, idCard_back_path, request);//图片上传
			}
			if(!"".equals(idCard_front)) {
				idCard_front = AppFileUtil.generateImage(idCard_front, idCard_front_path, request);//图片上传
			}
			try {
				if(Verify.verifyIsNotNull(session_id)){
					PageData p = new PageData();
					p.put("session_id", session_id);
					//获取当用户信息
					PageData userPd = appOrderService.findSessionInfo(p);
					if(Verify.verifyIsNotNull(userPd)){
						PageData pp=new PageData();
						if(userPd.get("isAuthent").equals("N")){
							PageData Partner=new PageData();
							pp.put("business_id", userPd.get("id"));
							pp.put("authent_name", authent_name);
							pp.put("PID", PID);
							if(!"".equals(idCard_front)){
							pp.put("idCard_front", idCard_front);
							}
							if(!"".equals(idCard_back)) {
								pp.put("idCard_back", idCard_back);
							}
//							pp.putAll(FileUpload.saveLocalFile(request, ParaUtil.maint+ParaUtil.maint, pp)); //图片上传
							pp.put("step", "2");
							Partner.put("isAuthent", userPd.get("isAuthent"));
							appPersonalService.updateUser(pp);
							
							json.setMsg("访问成功，待审核。");
							json.setCode("0");
							json.setMyobject(Partner);
							json.setVerifySessionId(true);
							json.setSuccess(true);
						}else{
							PageData pd=new PageData();
							PageData Partner=new PageData();
							if(Verify.verifyIsNotNull(Partner.getString("idCard_front"))){
								Partner.put("idCard_front", FileUpload.findLocalFileUrl(Partner.getString("idCard_front")));
								}else{
									Partner.put("idCard_front","");
								}
							 if(Verify.verifyIsNotNull(Partner.getString("idCard_back"))){
								 Partner.put("idCard_back", FileUpload.findLocalFileUrl(Partner.getString("idCard_back")));
								}else{
									Partner.put("idCard_back","");
								}
							pd.put("business_id",  userPd.get("id"));
							Partner=appPersonalService.findUserInfo(pd);
							Partner.put("isAuthent", userPd.get("isAuthent"));
							Partner.put("phone", userPd.get("phone"));
							json.setMsg("访问成功。");
							json.setMyobject(Partner);
							json.setCode("0");
							json.setVerifySessionId(true);
							json.setSuccess(true);
							
						}
							
							
							
					
					}else{
						json.setMsg("session_id失效，请重新登录");
						json.setCode("-6");
						json.setSuccess(false);
					}
				}else{
					json.setMsg("session_id为空");
					json.setCode("-6");
					json.setSuccess(false);
				}
				this.writeJson(response, json);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}	
		
		
		/**
		 * 
		  * @Title: findUser
		  * @Description: 查询商户认证信息（认证）
		  * @param @param map
		  * @param @param request
		  * @param @param response
		  * @param @param session
		  * @param @throws Exception    设定文件
		  * @return void    返回类型
		  * @throws
		 */
		@RequestMapping(value="/findUser.html", method = RequestMethod.POST)
		public void findUser(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception{
			Json json = new Json();
			String session_id = AppParameter.getSessionId(map);//手机用户session_id
			Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
			try {
				if(Verify.verifyIsNotNull(session_id)){
					PageData p = new PageData();
					p.put("session_id", session_id);
					//获取当用户信息
					PageData userPd = appPersonalService.authentInfo(p);
					if(Verify.verifyIsNotNull(userPd)){
						if(Verify.verifyIsNotNull(userPd.getString("idCard_front"))){
							userPd.put("idCard_front", FileUpload.findLocalFileUrl(userPd.getString("idCard_front")));
							}else{
								userPd.put("idCard_front","");
							}
						 if(Verify.verifyIsNotNull(userPd.getString("idCard_back"))){
							 userPd.put("idCard_back", FileUpload.findLocalFileUrl(userPd.getString("idCard_back")));
							}else{
								userPd.put("idCard_back","");
							}
							
							json.setMsg("访问成功。");
							json.setCode("0");
							json.setMyobject(userPd);
							json.setVerifySessionId(true);
							json.setSuccess(true);
					
					}else{
						json.setMsg("session_id失效，请重新登录");
						json.setCode("-6");
						json.setSuccess(false);
					}
				}else{
					json.setMsg("session_id为空");
					json.setCode("-6");
					json.setSuccess(false);
				}
				this.writeJson(response, json);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}	
		
		/**
		 * 
		  * @Title: findHelpList
		  * @Description: 帮助中心
		  * @param @param map
		  * @param @param request
		  * @param @param response
		  * @param @param session
		  * @param @throws Exception    设定文件
		  * @return void    返回类型
		  * @throws
		 */
		@RequestMapping(value="/findHelpList.html", method = RequestMethod.POST)
		public void findHelpList(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception{
			Json json = new Json();
			String session_id = AppParameter.getSessionId(map);//手机用户session_id
			Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
			try {
						List<PageData> list =new ArrayList<PageData>();
						PageData pd=new PageData();
						pd.put("type", "business");
						list=appMineService.findHelpList(pd);
						
						Map<String, Object> appMap = new HashMap<String, Object>();
						appMap.put("list",list);
						
						json.setMsg("查询成功。");
						json.setMyobject(appMap);
						json.setCode("0");
						json.setVerifySessionId(true);
						json.setSuccess(true);
						
				this.writeJson(response, json);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
		
		/**
		 * 
		  * @Title: findAmountSum
		  * @Description: 查询收入
		  * @param @param map
		  * @param @param request
		  * @param @param response
		  * @param @param session
		  * @param @throws Exception    设定文件
		  * @return void    返回类型
		  * @throws
		 */
		@RequestMapping(value="/findAmountSum.html", method = RequestMethod.POST)
		public void findAmountSum(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception{
			Json json = new Json();
			String session_id = AppParameter.getSessionId(map);//手机用户session_id
			Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
			try {
				if(Verify.verifyIsNotNull(session_id)){
					PageData p = new PageData();
					p.put("session_id", session_id);
					//获取当用户信息
					PageData userPd = appOrderService.findSessionInfo(p);
					if(Verify.verifyIsNotNull(userPd)){
							PageData pd=new PageData();
							pd.put("server_user_id", userPd.get("id"));
							double sum=appPersonalService.findAmountSum(pd);//累计金额
							pd.put("statusTwo", "5");
							pd.put("statusThree", "6");
							double num=appPersonalService.findDateNum(pd);//已付款
							PageData pp=new PageData();
							pp.put("server_user_id", userPd.get("id"));
							pp.put("status", "4");
							double nonum=appPersonalService.findDateNum(pp);//待付款
							PageData yy=new PageData();
							yy.put("sum", sum);
							yy.put("num", num);
							yy.put("nonum", nonum);
							yy.put("tixian", "0");// 提现
							
							json.setMsg("查询成功。");
							json.setCode("0");
							json.setMyobject(yy);
							json.setVerifySessionId(true);
							json.setSuccess(true);
					
					}else{
						json.setMsg("session_id失效，请重新登录");
						json.setCode("-6");
						json.setSuccess(false);
					}
				}else{
					json.setMsg("session_id为空");
					json.setCode("-6");
					json.setSuccess(false);
				}
				this.writeJson(response, json);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}	
		/**
		 * 
		  * @Title: findConsumeList
		  * @Description: 收入明细
		  * @param @param map
		  * @param @param request
		  * @param @param response
		  * @param @param session    设定文件
		  * @return void    返回类型
		  * @throws
		 */
		@RequestMapping(value="/findConsumeList.html", method = RequestMethod.POST)
		public void findConsumeList(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
			Json json = new Json();
			String session_id = AppParameter.getSessionId(map);//手机用户session_id
			Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
			String index=bodyMap.get("index").toString();
			String update_time=null;
			try {
				if(Verify.verifyIsNotNull(session_id)){
					PageData p = new PageData();
					p.put("session_id", session_id);
					//获取当前用户
					PageData userPd = appOrderService.findSessionInfo(p);
					if(Verify.verifyIsNotNull(userPd)){
						List<PageData> list = new ArrayList<PageData>();//根据时间分组
						List<PageData> listInfo = new ArrayList<PageData>();//遍历信息
						int flag=0;
						PageData pd=new PageData();
						pd.put("server_user_id", userPd.get("id"));
						pd.put("index", index);
						Object update_time_obj= bodyMap.get("update_time");
						if(update_time_obj!=null){
							update_time = update_time_obj.toString();
							pd.put("update_time", update_time);
						}
						
						this.getPage(pd);
						int num=appPersonalService.findDateNums(pd);
						if(num>0){
							if(num%parameterUtil.app_showCount>0){//取余 当余数大于0时，总页数+1 当余数 = 0时 不加
								flag = num/parameterUtil.app_showCount+1;
							}else {
								flag = num/parameterUtil.app_showCount;
							}
						}
						
						list=appPersonalService.findTimeList(pd);
						listInfo=appPersonalService.findConsumeList(pd);
						for (PageData pa : list) {
							List<PageData> listshuju = new ArrayList<PageData>();//遍历信息
							for (PageData pp : listInfo) {
								if(pa.get("monthName").equals(pp.get("monthName"))){
									listshuju.add(pp);
								}
							}
							pa.put("list", listshuju);
						}
						
						Map<String, Object> appMap = new HashMap<String, Object>();
						appMap.put("list",list);
						appMap.put("flag", flag);
						json.setMyobject(appMap);
						json.setMsg("查询成功。");
						json.setCode("0");
						json.setVerifySessionId(true);
						json.setSuccess(true);
					}else{
						json.setMsg("session_id失效，请重新登录");
						json.setCode("-6");
						json.setSuccess(false);
					}
				}else{
					json.setMsg("session_id为空");
					json.setCode("-6");
					json.setSuccess(false);
				}
				this.writeJson(response, json);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * 
		  * @Title: findNumList
		  * @Description: 查询每天的订单数
		  * @param @param map
		  * @param @param request
		  * @param @param response
		  * @param @param session    设定文件
		  * @return void    返回类型
		  * @throws
		 */
		@RequestMapping(value="/findOrderNumList.html", method = RequestMethod.POST)
		public void findNumList(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
			Json json = new Json();
			String session_id = AppParameter.getSessionId(map);//手机用户session_id
			Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
			String update_time=bodyMap.get("update_time").toString();//传的时间
//			String update_time=null;
			try {
				if(Verify.verifyIsNotNull(session_id)){
					PageData p = new PageData();
					p.put("session_id", session_id);
					//获取当前用户
					PageData userPd = appOrderService.findSessionInfo(p);
					if(Verify.verifyIsNotNull(userPd)){
						List<PageData> list =new ArrayList<PageData>();
						List<PageData> listWeek =new ArrayList<PageData>();
						int days= 30; //默认近30天
						Date nowDate = new Date();
						String nowMonth = DateTimeUtil.getDateStr(nowDate,"yyyy-MM");
						String searchDay = DateTimeUtil.getDateStr(nowDate,"yyyy-MM-dd")+" 23:59:59";
						if(update_time!=null && !"".equals(update_time)) {
							if(nowMonth.equals(update_time)){
								days =DateTimeUtil.getday();
							}else {
								days = DateTimeUtil.getDayNumber(update_time);
							}
							searchDay = update_time+"-"+days+" 23:59:59";
						}
						String startDay = DateTimeUtil.getDateStr(DateTimeUtil.getdate(searchDay,-days+1),"yyyy-MM-dd")+" 00:00:00";
						PageData pd=new PageData();
						pd.put("startDay", startDay);//开始时间
						pd.put("searchDay", searchDay);//结束时间
						pd.put("server_user_id", userPd.get("id"));
						list=appPersonalService.findNumList(pd);//
						for (int i=days-1;i>=0;i--){
							PageData week = new PageData();
							Date d=DateTimeUtil.getdate(searchDay,-i);
							week.put("day", DateTimeUtil.getDateStr(d,"MM-dd"));//几号
							week.put("week", "周"+DateTimeUtil.getWeekOfDate(d));//周几
							String day = DateTimeUtil.getDateStr(d,"yyyy-MM-dd");
							if(list.size()!=0){
								for (PageData pa : list) {
									if(day.equals(pa.getString("group_time"))){
										week.put("num", pa.get("num"));//订单数
										break;
									}else{
										week.put("num", "0");//订单数
									}
								}
							}else{
								week.put("num", "0");//订单数
							}
							listWeek.add(week);
						}
						
						Map<String, Object> appMap = new HashMap<String, Object>();
						appMap.put("list",listWeek);
//						appMap.put("flag", flag);
						json.setMyobject(appMap);
						json.setMsg("查询成功。");
						json.setCode("0");
						json.setVerifySessionId(true);
						json.setSuccess(true);
					}else{
						json.setMsg("session_id失效，请重新登录");
						json.setCode("-6");
						json.setSuccess(false);
					}
				}else{
					json.setMsg("session_id为空");
					json.setCode("-6");
					json.setSuccess(false);
				}
				this.writeJson(response, json);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		

}
