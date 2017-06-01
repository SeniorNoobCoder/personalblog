package com.sdhsie.app.common.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sdhsie.app.cache.CacheManager;
import com.sdhsie.app.common.service.AppMessageService;
import com.sdhsie.app.server.service.AppLoginService;
import com.sdhsie.app.server.service.AppOrderService;
import com.sdhsie.base.controller.AppBaseController;
import com.sdhsie.base.util.DateTimeUtil;
import com.sdhsie.base.util.GuidUtil;
import com.sdhsie.base.util.Json;
import com.sdhsie.base.util.MD5Util;
import com.sdhsie.base.util.PageData;
import com.sdhsie.base.util.Verify;
import com.sdhsie.base.util.ecareParameter;
import com.sdhsie.base.util.parameterUtil;
import com.sdhsie.base.util.upload.FileUpload;
import com.sdhsie.webService.toro.controller.ToroInfoUtil;

@Controller
@RequestMapping(value="/app/message")
public class AppMessageController extends AppBaseController{
	
	@Autowired
	private AppMessageService appMessageService;
	@Autowired
	private AppOrderService appOrderService;
	@Autowired
	private AppLoginService appLoginService;
	
	/**
	 * 
	  * @Title: findMessageInfo
	  * @Description: 查询最新消息
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findMessageInfo.html", method = RequestMethod.POST)
	public void findMessageInfo(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		try {
			if(Verify.verifyIsNotNull(session_id)){
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当前用户
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					
						PageData pd = new PageData();
						PageData pp=new PageData();
							pd.put("statusTwo", "2");
							pd.put("statusThree", "3");
							pd.put("statusFour", "4");
							
						pd.put("family_id", userPd.get("id"));
						PageData Order=appMessageService.findOrderOneInfo(pd);//订单消息
						int orderNum=appMessageService.findOrderNum(pd);//订单消息数量
						pp.put("user_id", userPd.get("id"));
						pp.put("user_type", userPd.get("user_type"));
						PageData system=appMessageService.findMessageOneInfo(pp);//系统消息
						int systemNum=appMessageService.findMessageNum(pp);//未读消息
						
						PageData rr=new PageData();
						rr.put("Order", Order);
						rr.put("system", system);
						rr.put("orderNum", orderNum);
						rr.put("systemNum", systemNum);
					json.setMyobject(rr);
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
	  * @Title: findmessageList
	  * @Description: 消息列表
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findmessageList.html", method = RequestMethod.POST)
	public void findmessageList(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String index=bodyMap.get("index").toString();
		try {
			if(Verify.verifyIsNotNull(session_id)){
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当前用户
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					List<PageData> system=new ArrayList<PageData>();
					int flag=0;
//					List<PageData> info=new ArrayList<PageData>();
						PageData pd=new PageData();
						pd.put("user_id", userPd.get("id"));
						pd.put("user_type", userPd.get("user_type"));
						pd.put("index", index);
						this.getPage(pd);
						system=appMessageService.findmessageList(pd);//系统消息
						int num=appMessageService.findMessageListNum(pd);//所有的数量
						if(num>0){
							if(num%parameterUtil.app_showCount>0){//取余 当余数大于0时，总页数+1 当余数 = 0时 不加
								flag = num/parameterUtil.app_showCount+1;
							}else {
								flag = num/parameterUtil.app_showCount;
							}
						}
						
					Map<String, Object> appMap = new HashMap<String, Object>();
					appMap.put("list", system);
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
	  * @Title: findOrderList
	  * @Description: 家人订单消息列表
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findOrderList.html", method = RequestMethod.POST)
	public void findOrderList(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String index=bodyMap.get("index").toString();
		try {
			if(Verify.verifyIsNotNull(session_id)){
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当前用户
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					List<PageData> order=new ArrayList<PageData>();
					int flag=0;
						PageData pd=new PageData();
							pd.put("statusFour", "3");
							pd.put("statusTwo", "2");
							pd.put("statusThree", "4");
						pd.put("family_id", userPd.get("id"));
						pd.put("index", index);
						this.getPage(pd);
						order=appMessageService.findOrderList(pd);//订单消息
						for (PageData ad : order) {
							if (Verify.verifyIsNotNull(ad.getString("head_address"))) {
								ad.put("head_address", FileUpload.findLocalFileUrl(ad.getString("head_address")));
							} else {
								ad.put("head_address", "");
							}
						}
						int num=appMessageService.findOrderNum(pd);
						if(num>0){
							if(num%parameterUtil.app_showCount>0){//取余 当余数大于0时，总页数+1 当余数 = 0时 不加
								flag = num/parameterUtil.app_showCount+1;
							}else {
								flag = num/parameterUtil.app_showCount;
							}
						}
						
					Map<String, Object> appMap = new HashMap<String, Object>();
					appMap.put("list", order);
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
	  * @Title: findDeviceList
	  * @Description: 进出安全岛未读消息列表
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findDeviceList.html", method = RequestMethod.POST)
	public void findDeviceList(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		try {
			if(Verify.verifyIsNotNull(session_id)){
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当前用户
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					List<PageData> island=new ArrayList<PageData>();
						PageData pd=new PageData();
						pd.put("family_id", userPd.get("id"));
						island=appMessageService.findDeviceList(pd);//近出入安全岛
						//图片处理
						for (PageData ad:island){
							if(Verify.verifyIsNotNull(ad.getString("head_address"))){
								ad.put("head_address", FileUpload.findLocalFileUrl(ad.getString("head_address")));
							}else{
								ad.put("head_address","");
							}
						}
					Map<String, Object> appMap = new HashMap<String, Object>();
					appMap.put("list", island);
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
	  * @Title: updateMessage
	  * @Description: 修改系统消息状态
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/updateMessage.html", method = RequestMethod.POST)
	public void updateMessage(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String userId=bodyMap.get("userId").toString();//common_message_user表中的id
		try {
			if(Verify.verifyIsNotNull(session_id)){
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当前用户
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					PageData pd = new PageData();
					pd.put("id", userId);
					pd.put("status", "Y");
					appMessageService.updateMessage(pd);
						
					json.setMsg("修改成功。");
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
	  * @Title: updateDevice
	  * @Description: 修改进出安全岛的状态
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/updateDevice.html", method = RequestMethod.POST)
	public void updateDevice(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String userId=bodyMap.get("id").toString();//common_message_user表中的id
		try {
			if(Verify.verifyIsNotNull(session_id)){
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当前用户
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					PageData pd = new PageData();
					pd.put("id", userId);
					pd.put("status", "Y");
					appMessageService.updateDevice(pd);
						
					json.setMsg("修改成功。");
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
	  * @Title: updateOrder
	  * @Description: 订单消息状态
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/updateOrder.html", method = RequestMethod.POST)
	public void updateOrder(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String userId=bodyMap.get("id").toString();//订单流程表中的id
		try {
			if(Verify.verifyIsNotNull(session_id)){
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当前用户
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					PageData pd = new PageData();
					pd.put("id", userId);
					pd.put("is_read", "Y");
					appMessageService.updateOrder(pd);
						
					json.setMsg("修改成功。");
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
	  * @Title: findUsList
	  * @Description: 注册信息（商户）
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findUsList.html", method = RequestMethod.POST)
	public void findUsList(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		try {
					PageData pd = new PageData();
					pd.put("user_type", ecareParameter.server_user);
					pd.put("info_type", "register");
					PageData register = appMessageService.findUsInfo(pd);
					json.setMyobject(register);
					json.setMsg("查询成功。");
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
	  * @Title: findUsJobs
	  * @Description: 加入我们（关于合作商）
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findUsJobs.html", method = RequestMethod.POST)
	public void findUsJobs(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		try {
			if(Verify.verifyIsNotNull(session_id)){
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当前用户
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					PageData pd = new PageData();
					pd.put("user_type", "server");
					pd.put("info_type", "joinUs");
					PageData register = appMessageService.findUsInfo(pd);
					json.setMyobject(register);
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
	  * @Title: findUser
	  * @Description: 注册协议（家人）
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findfamilyProtocol.html", method = RequestMethod.POST)
	public void findUser(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		try {
					PageData pd = new PageData();
					pd.put("user_type", ecareParameter.family_user);
					pd.put("info_type", "register");
					PageData register = appMessageService.findUsInfo(pd);
					json.setMyobject(register);
					json.setMsg("查询成功。");
					json.setCode("0");
					json.setVerifySessionId(true);
					json.setSuccess(true);
			this.writeJson(response, json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @throws ParseException 
	 * 
	  * @Title: findEmpreinteList
	  * @Description: 历史足迹
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findEmpreinteList.html", method = RequestMethod.POST)
	public void findEmpreinteList(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws ParseException{
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String create_time=null;
		try {
			if(Verify.verifyIsNotNull(session_id)){
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当前用户
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					List<PageData> island=new ArrayList<PageData>();
					List<PageData> info=new ArrayList<PageData>();
					List<PageData> coordinate=new ArrayList<PageData>();
					List<PageData> cc=new ArrayList<PageData>();//ceshi
						PageData pd=new PageData();
						pd.put("family_id", userPd.get("id"));
						Object create_time_obj= bodyMap.get("create_time");
					/*	if(create_time_obj!=null&&create_time_obj!=""){
							create_time = create_time_obj.toString();
							pd.put("create_time", create_time);
						}else{
							pd.put("create_time", DateTimeUtil.getDate());
						}*/
						island=appMessageService.findDeviceList(pd);//进出入安全岛的设备
						
						if(island.size()!=0){
						for (PageData ad : island) {
							if(Verify.verifyIsNotNull(ad.getString("head_address"))){
								ad.put("head_address", FileUpload.findLocalFileUrl(ad.getString("head_address")));
							}else{
								ad.put("head_address",FileUpload.findLocalFileUrl("/IMAGES/mark_b1.png"));
							}
							/*
							 * //可以注掉部分 PageData pp=new PageData();
							 * pp.put("customer_id", pa.get("customer_id"));
							 * 
							 * if(create_time_obj!=null&&create_time_obj!=""){
							 * create_time = create_time_obj.toString();
							 * pp.put("end_time", create_time); }else{
							 * pp.put("end_time", DateTimeUtil.getDate()); }
							 * cc=appMessageService.findEmpreinteList(pp);//测试
							 * pa.put("coordinate", cc);//历史足迹测试 //注掉结束
							 */ 
							String date=null;
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							if (create_time_obj != null && create_time_obj != "") {
								create_time = create_time_obj.toString();
								 date = create_time.substring(0, 10);
							}else{
								date = DateTimeUtil.getDate().substring(0, 10);
							}
							 coordinate=ToroInfoUtil.getToroTrace(ad.getString("customer_sn"),date );//历史足迹
							 ad.put("coordinate", coordinate);//历史足迹测试
						}
					}
						
					Map<String, Object> appMap = new HashMap<String, Object>();
					appMap.put("list", island);
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
	  * @Title: findfamilyGuarantee
	  * @Description: 用户保障（家庭版）
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findfamilyGuarantee.html", method = RequestMethod.POST)
	public void findfamilyGuarantee(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		try {
					PageData pd = new PageData();
					pd.put("user_type", ecareParameter.family_user);
					pd.put("info_type", "guarantee");
					PageData register = appMessageService.findUsInfo(pd);
					json.setMyobject(register);
					json.setMsg("查询成功。");
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
	  * @Title: findRealtimeList
	  * @Description: 实时位置
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session
	  * @param @throws ParseException    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findRealtimeList.html", method = RequestMethod.POST)
	public void findRealtimeList(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws ParseException{
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String create_time=null;
		try {
			if(Verify.verifyIsNotNull(session_id)){
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当前用户
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					List<PageData> island=new ArrayList<PageData>();
					
					PageData coordinate=null;
					List<PageData> info=new ArrayList<PageData>();
						PageData pd=new PageData();
						pd.put("family_id", userPd.get("id"));
						island=appMessageService.findDeviceList(pd);//进出入安全岛的设备
						
						if(island.size()!=0){
							for (PageData ad : island) {
								 coordinate= ToroInfoUtil.getToroLoc(ad.getString("customer_sn"));//实时位置
								 if(Verify.verifyIsNotNull(ad.getString("head_address"))){
										ad.put("head_address", FileUpload.findLocalFileUrl(ad.getString("head_address")));
									}else{
										ad.put("head_address","");
									}
								 ad.put("lat", coordinate.get("lat"));
								 ad.put("lon", coordinate.get("lon"));
							}
					}
						
					Map<String, Object> appMap = new HashMap<String, Object>();
					appMap.put("list", island);
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
	  * @Title: updateUser
	  * @Description: 修改手机号（也就是登录名）
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/updateUser.html", method = RequestMethod.POST)
	public void updateUser(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String loginName=bodyMap.get("login_name").toString();//获取手机号
		String yzm=bodyMap.get("yzm").toString();//获取验证码
		try {
			PageData pd = new PageData();
			if(Verify.verifyIsNotNull(session_id)){
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当前用户
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					
					String yzmCache = "";//缓存验证码
					if(CacheManager.hasCache(loginName)){
						yzmCache = (String) CacheManager.getCacheInfo(loginName).getValue();
					}
						if(Verify.verifyIsNotNull(yzmCache)){
							if(yzm.equals(yzmCache)){
								pd.put("login_name", loginName);
								pd.put("phone", loginName);
								pd.put("id", userPd.get("id"));
								appMessageService.updateUser(pd);
								
								json.setMsg("修改成功。");
								json.setCode("0");
								json.setVerifySessionId(true);
								json.setSuccess(true);
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
	  * @Title: findOldPhone
	  * @Description: 通知旧手机
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findOldPhone.html", method = RequestMethod.POST)
	public void findOldPhone(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String loginName=bodyMap.get("login_name").toString();//获取手机号
		String yzm=bodyMap.get("yzm").toString();//获取验证码
		try {
			PageData pd = new PageData();
			if(Verify.verifyIsNotNull(session_id)){
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当前用户
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					
					String yzmCache = "";//缓存验证码
					if(CacheManager.hasCache(loginName)){
						yzmCache = (String) CacheManager.getCacheInfo(loginName).getValue();
					}
						if(Verify.verifyIsNotNull(yzmCache)){
							if(yzm.equals(yzmCache)){
								json.setMsg("验证码通过。");
								json.setCode("0");
								json.setVerifySessionId(true);
								json.setSuccess(true);
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
	  * @Title: findApkInfo
	  * @Description: apk版本
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findApkInfo.html", method = RequestMethod.POST)
	public void findApkInfo(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		Json json = new Json();
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String type=bodyMap.get("type").toString();//版本类型
		String device_type=bodyMap.get("device_type").toString();//设备类型
		try {
			PageData pd = new PageData();
			pd.put("device_type", device_type);
			pd.put("type", type);
			PageData register = appMessageService.findApkInfo(pd);
			if(register!=null){
				if(Verify.verifyIsNotNull(register.getString("file_address"))){
					register.put("file_address", FileUpload.findLocalFileUrl(register.getString("file_address")));
				}
				json.setSuccess(true);
			}else{
				json.setSuccess(false);
			}
			json.setMsg("");
			json.setMyobject(register);
			json.setCode("0");
			json.setVerifySessionId(true);
			this.writeJson(response, json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	  * @Title: findMessageShList
	  * @Description: 商户版的订单消息
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findMessageShList.html", method = RequestMethod.POST)
	public void findMessageShList(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String index=bodyMap.get("index").toString();
		try {
			if(Verify.verifyIsNotNull(session_id)){
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当前用户
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					List<PageData> order=new ArrayList<PageData>();
					int flag=0;
						PageData pd=new PageData();
						pd.put("server_user_id", userPd.get("id"));
						pd.put("index", index);
						pd.put("statusTwo", "5");
						pd.put("statusThree", "6");
						this.getPage(pd);
						order=appMessageService.findMessageShList(pd);
						for (PageData ad : order) {
							if (Verify.verifyIsNotNull(ad.getString("head_address"))) {
								ad.put("head_address", FileUpload.findLocalFileUrl(ad.getString("head_address")));
							} else {
								ad.put("head_address", "");
							}
						}
						int num=appMessageService.findMessageShNum(pd);
						if(num>0){
							if(num%parameterUtil.app_showCount>0){//取余 当余数大于0时，总页数+1 当余数 = 0时 不加
								flag = num/parameterUtil.app_showCount+1;
							}else {
								flag = num/parameterUtil.app_showCount;
							}
						}
						
					Map<String, Object> appMap = new HashMap<String, Object>();
					appMap.put("list", order);
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
	  * @Title: findMessageShInfo
	  * @Description: 商户版的最近消息
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findMessageShInfo.html", method = RequestMethod.POST)
	public void findMessageShInfo(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		try {
			if(Verify.verifyIsNotNull(session_id)){
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当前用户
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
						PageData pd = new PageData();
							pd.put("statusTwo", "5");
							pd.put("statusThree", "6");
							pd.put("statusFour", "7");
						pd.put("server_user_id", userPd.get("id"));
						PageData Order=appMessageService.findShOrderInfo(pd);//订单消息
						int orderNum=appMessageService.findMessageShNum(pd);//订单消息数量
						PageData pp=new PageData();
						pp.put("user_id", userPd.get("id"));
						pp.put("user_type", userPd.get("user_type"));
						PageData system=appMessageService.findMessageOneInfo(pp);//系统消息
						int systemNum=appMessageService.findMessageNum(pp);//未读消息
						
						PageData rr=new PageData();
						rr.put("Order", Order);
						rr.put("system", system);
						rr.put("orderNum", orderNum);
						rr.put("systemNum", systemNum);
					json.setMyobject(rr);
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
