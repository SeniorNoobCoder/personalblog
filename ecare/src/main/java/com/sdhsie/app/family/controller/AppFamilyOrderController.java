package com.sdhsie.app.family.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.sdhsie.app.common.controller.AppParameter;
import com.sdhsie.app.family.service.AppFamilyOrderService;
import com.sdhsie.app.family.service.IndexService;
import com.sdhsie.app.file.AppFileUtil;
import com.sdhsie.app.server.service.AppOrderService;
import com.sdhsie.base.controller.AppBaseController;
import com.sdhsie.base.util.Config;
import com.sdhsie.base.util.DateTimeUtil;
import com.sdhsie.base.util.Json;
import com.sdhsie.base.util.PageData;
import com.sdhsie.base.util.Verify;
import com.sdhsie.base.util.parameterUtil;
import com.sdhsie.base.util.upload.FileUpload;
import com.sdhsie.base.util.upload.ParaUtil;
import com.sdhsie.push.MessageBean;

@Controller
@RequestMapping(value="/app/familyOrder")
public class AppFamilyOrderController  extends AppBaseController{
	@Autowired
	private IndexService indexService;
	@Autowired
	private AppFamilyOrderService appFamilyOrderService;
	@Autowired
	private AppOrderService appOrderService;	
	/**
	 * 
	  * @Title: findfamilyOrderList
	  * @Description: 查询订单列表
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findfamilyOrderList.html", method = RequestMethod.POST)
	public void findfamilyOrderList(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String status = bodyMap.get("status").toString();
		String index = bodyMap.get("index").toString();
		try {
			if(Verify.verifyIsNotNull(session_id)){
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当前用户
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					int flag = 0;
					PageData pd = new PageData();
					
					
					if(status.equals("-1")){//如果手机端传过来 的值为 -1 则查询全部的个人订单
						pd.put("status","");
					}else if(status.equals("1")){
						pd.put("status","");
						pd.put("statusTwo", "1");
						pd.put("statusThree", "11");
						pd.put("statusFour", "-1");
					}
					else if(status.equals("2")){
						pd.put("statusTwo", "2");
						pd.put("statusThree", "3");
					}
					else{
						pd.put("status",status);
					}
					
					pd.put("index",index);
					pd.put("family_id", userPd.get("id"));
					this.getPage(pd);
					List<PageData>	list = appFamilyOrderService.findFamilyOrderList(pd);
					
					int num = appFamilyOrderService.findFamilyOrderNum(pd);
					
					if(num>0){
						if(num%parameterUtil.app_showCount>0){//取余 当余数大于0时，总页数+1 当余数 = 0时 不加
							flag = num/parameterUtil.app_showCount+1;
						}else {
							flag = num/parameterUtil.app_showCount;
						}
					}
					Map<String, Object> appMap = new HashMap<String, Object>();
					appMap.put("list",list);
					appMap.put("flag",flag);
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
	  * @Title: updateOrder
	  * @Description: 取消订单 确认完成
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
		String id = bodyMap.get("id").toString();//订单id
		try {
			if(Verify.verifyIsNotNull(session_id)){
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当前用户
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					PageData pd=new PageData();
					PageData po=new PageData();
					po.put("id", id);
					PageData pp=appFamilyOrderService.findStatusInfo(po);
					
					if(pp.get("status").equals(1)){
						pd.put("remove_logo", "Y");
					}else if(pp.get("status").equals(4)){
						pd.put("status", "5");
					}else if(pp.get("status").equals(5)){
						pd.put("status", "9");
					}else if(pp.get("status").equals(11)){
						pd.put("remove_logo", "Y");
					}else if(pp.get("status").equals(-1)){
						pd.put("remove_logo", "Y");
					}else if(pp.get("status").equals(6)){
						pd.put("status", "9");
					}else if(pp.get("status").equals(7)){
						pd.put("status", "9");
					}else if(pp.get("status").equals(0)){
						pd.put("remove_logo", "Y");
					}
					pd.put("id", id);
					appFamilyOrderService.updateOrder(pd);
					//=========订单流程=======
					PageData psa=new PageData();
					psa.put("id", id);
					PageData pps=appOrderService.findOrderInfo(psa);
					//查询订单信息
					if(pp.get("status").equals(4)){
						//保存订单流程
						PageData ps=new PageData();
						ps.put("order_no", pps.get("order_no"));
						ps.put("status",pps.get("status"));
						ps.put("operator_id", userPd.get("id"));
						ps.put("operator_name", userPd.get("nickname"));
						appOrderService.save(ps);//订单流程
					}	
					
					json.setMsg("操作成功。");
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
	  * @Title: findUsers
	  * @Description: 查询商户信息
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findUsers.html", method = RequestMethod.POST)
	public void findUsers(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String index=bodyMap.get("index").toString();
		String partner_id=bodyMap.get("id").toString();//合作商id
		try {
					int flag = 0;
					PageData pd = new PageData();
					pd.put("index", index);
					pd.put("partner_id", partner_id);
					this.getPage(pd);
					List<PageData> list = new ArrayList<PageData>();
					list = appFamilyOrderService.findUserList(pd);
					PageData pp=new PageData();
					for (PageData ad : list) {
						if (Verify.verifyIsNotNull(ad.getString("head_address"))) {
							ad.put("head_address", FileUpload.findLocalFileUrl(ad.getString("head_address")));
						} else {
							ad.put("head_address", "");
						}
						
						pp.put("partner_id", partner_id);
						List<PageData> po=appFamilyOrderService.findStatInfo(pp);
						for (PageData pag : po) {
							if(ad.get("id").equals(pag.get("serverId"))){
								PageData pi=new PageData();
								pi.put("id", pag.get("id"));
								int count = 0;
								if(pag.get("count") != null){
									count = Integer.parseInt(pag.get("count").toString());
								}
								ad.put("count", count);//服务次数
								float ss= 0;
								int quality = 0;
								if(pag.get("quality")!=null){
									quality = Integer.parseInt(pag.get("quality").toString());
								}
								if(count>0){
									ss = quality/count;
								}
								ad.put("quality", ss);//等级
								break;
							}
					}
						if(ad.get("quality") == null){
							ad.put("quality", "0");
						}
						if(ad.get("count") == null){
							ad.put("count", "0");
						}
				}
					int num = appFamilyOrderService.findUserNum(pd);
					if (num > 0) {
						if (num % parameterUtil.app_showCount > 0) {// 取余 当余数大于0时，总页数+1
																	// 当余数 = 0时 不加
							flag = num / parameterUtil.app_showCount + 1;
						} else {
							flag = num / parameterUtil.app_showCount;
						}
					}
		
					Map<String, Object> appMap = new HashMap<String, Object>();
					appMap.put("list", list);
					appMap.put("flag", flag);// 分页
					json.setMyobject(appMap);
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
	  * @Title: findInfo
	  * @Description: 查看订单详情
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findInfo.html", method = RequestMethod.POST)
	public void findInfo(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String orderId = bodyMap.get("id").toString();//订单id
		
		try {
			if(Verify.verifyIsNotNull(session_id)){
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当用户信息
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					//获取订单信息
					PageData pm=new PageData();
					pm.put("id", orderId);
					PageData pOrder=appFamilyOrderService.findOrderInfo(pm);
					PageData ps=new PageData();
					ps.put("order_no", pOrder.get("order_no"));
					List<PageData> list = appFamilyOrderService.findFlowList(ps);//订单流程
					
					PageData ui=new PageData();
					ui.put("id", pOrder.get("server_user_id"));
					PageData shanghu=appFamilyOrderService.findMerchantInfo(ui);
					if(shanghu!=null&&shanghu.size()>0){//如果商户信息不存在
						if(Verify.verifyIsNotNull(shanghu.getString("head_address"))){
							shanghu.put("head_address", FileUpload.findLocalFileUrl(shanghu.getString("head_address")));
						}else{
							shanghu.put("head_address","");
						}
					}
					
					Map<String, Object> appMap = new HashMap<String, Object>();
					appMap.put("info",pOrder);//订单信息
					appMap.put("flow", list);
					appMap.put("shanghu", shanghu);
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
	  * @Title: saveAssessment
	  * @Description: 订单评价
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/saveAssessment.html", method = RequestMethod.POST)
	public void saveAssessment(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String orderId = bodyMap.get("id").toString();//订单id
		String count = null;//评论内容
		List<Object> photos =(List<Object>) bodyMap.get("image_url");
		String is_punctuality = bodyMap.get("is_punctuality").toString();
		String service_quality = bodyMap.get("service_quality").toString();
		String service_attitude = bodyMap.get("service_attitude").toString();
		try {
			if(Verify.verifyIsNotNull(session_id)){
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当用户信息
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					//获取订单信息(订单编号与服务信息）
					PageData pm=new PageData();
					pm.put("id", orderId);
					PageData pOrder=appFamilyOrderService.findOrderInfo(pm);
					PageData ps=new PageData();
					//评论内容
					Object count_obj= bodyMap.get("count");
					if(count_obj!=null){
						count = count_obj.toString();
						ps.put("count", count);
					}
					ps.put("is_punctuality", is_punctuality);
					ps.put("service_quality", service_quality);
					ps.put("service_attitude", service_attitude);
					ps.put("order_no", pOrder.get("order_no"));
					ps.put("server_info_id", pOrder.get("server_info_id"));
					appFamilyOrderService.saveAssessment(ps);
					PageData pi=new PageData();
					pi.put("order_no", pOrder.get("order_no"));
					PageData list =appFamilyOrderService.findAAssInfo(pi);//根据编号查询评论id
					//保存评论图片
						
					if(photos!=null && !"".equals(photos)){
						for (Object ph: photos) {
							String  head_address_path = ParaUtil.business+ParaUtil.user+ParaUtil.authent;
							ph = AppFileUtil.generateImage(ph.toString(),head_address_path,request);//图片上传
							PageData ui=new PageData();
							ui.put("assessment_id", list.get("id"));
							ui.put("image_url",  ph);
							appFamilyOrderService.saveAssessmentImage(ui);
						}
					}
					PageData uu=new PageData();
					uu.put("id", orderId);
					uu.put("status", "6");
					appFamilyOrderService.updateOrder(uu);
					
					//保存订单流程
					PageData pss=new PageData();
					pss.put("order_no", pOrder.get("order_no"));
					pss.put("status","6");
					pss.put("operator_id", userPd.get("id"));
					pss.put("operator_name", userPd.get("nickname"));
					appOrderService.save(pss);//订单流程
					
					json.setMsg("评论成功。");
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
	  * @Title: cancelOrder
	  * @Description: 定时取消
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/cancelOrder.html", method = RequestMethod.POST)
	public void cancelOrder(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String id = bodyMap.get("id").toString();//订单id
			try {
				
				PageData pd = new PageData();
				pd.put("id", id);
				PageData order=appFamilyOrderService.findOrderInfo(pd);
				if(order.get("status").equals("11")&&order.get("status").equals("1")){
					PageData pp=new PageData();
					pp.put("id", id);
					pp.put("status", "-1");
					appFamilyOrderService.updateOrder(pp);
					
					// ====保存订单流程====
					PageData ps = new PageData();
					ps.put("id", id);
					ps.put("order_no", order.get("order_no"));
					ps.put("status", "-1");
					ps.put("operator_id", 0);
					ps.put("operator_name", "系统");
					appOrderService.save(ps);// 订单流程
				}
				
	
				json.setMsg("操作成功。");
				json.setCode("0");
				json.setVerifySessionId(true);
				json.setSuccess(true);
				this.writeJson(response, json);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	

}
