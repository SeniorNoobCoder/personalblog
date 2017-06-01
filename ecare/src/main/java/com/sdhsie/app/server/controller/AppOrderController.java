package com.sdhsie.app.server.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sdhsie.app.common.controller.AppParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.sdhsie.app.server.service.AppOrderService;
import com.sdhsie.base.controller.AppBaseController;
import com.sdhsie.base.util.Config;
import com.sdhsie.base.util.Json;
import com.sdhsie.base.util.PageData;
import com.sdhsie.base.util.Verify;
import com.sdhsie.base.util.parameterUtil;
import com.sdhsie.push.MessageBean;
import com.sdhsie.push.PushUtil;
import com.sdhsie.webService.toro.controller.ToroInfoUtil;

@Controller
@RequestMapping(value = "/app/order")
public class AppOrderController extends AppBaseController{
	
	@Autowired
	private AppOrderService appOrderService;

	/**
	 * 
	  * @Title: findList
	  * @Description: 查看订单列表
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findList.html", method = RequestMethod.POST)
	public void findList(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
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
					List<PageData> list = new ArrayList<PageData>();
					int flag = 0;
					if(userPd.getString("isAuthent").equals("Y")){
						PageData pd = new PageData();
						if(userPd.get("is_work").equals("0")&&status.equals("1")){
						}else{
							pd.put("index",index);
							pd.put("business_id",userPd.get("id"));
							if(!status.equals("1")){//如果查看的不是抢单的话需要把个人id穿进去
								pd.put("server_user_id",userPd.get("id"));
							}
							if(status.equals("-1")){//如果手机端传过来 的值为 -1 则查询全部的个人订单
								pd.put("status", "");
							}
							if(status.equals("2")){
								pd.put("status", "");
								pd.put("statusTwo", "2");
								pd.put("statusThree", "11");
								
							}else{
								pd.put("status", status);
							}
							
							this.getPage(pd);
							list = appOrderService.findStatusList(pd);
							int num = appOrderService.findListNum(pd);

							if(num>0){
								if(num%parameterUtil.app_showCount>0){//取余 当余数大于0时，总页数+1 当余数 = 0时 不加
									flag = num/parameterUtil.app_showCount+1;
								}else {
									flag = num/parameterUtil.app_showCount;
								}
							}
						}
						}
						
						
//					json.setFlag(String.valueOf(flag));
					
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
	  * @Title: findPayList
	  * @Description: 查询已支付的数据
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findPayList.html", method = RequestMethod.POST)
	public void findPayList(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String index = bodyMap.get("index").toString();
		try {
			if(Verify.verifyIsNotNull(session_id)){
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当前用户
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					List<PageData> list = new ArrayList<PageData>();
					int flag = 0;
					if(userPd.getString("isAuthent").equals("Y")){
						PageData pd = new PageData();
						pd.put("index",index);
						pd.put("server_user_id", userPd.get("id"));
						pd.put("business_id", userPd.get("id"));
						this.getPage(pd);
						list = appOrderService.findPayList(pd);
						int num = appOrderService.findPayListNum(pd);
						
						if(num>0){
							if(num%parameterUtil.app_showCount>0){//取余 当余数大于0时，总页数+1 当余数 = 0时 不加
								flag = num/parameterUtil.app_showCount+1;
							}else {
								flag = num/parameterUtil.app_showCount;
							}
						}
					}
//					json.setFlag(String.valueOf(flag));
					
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
		String orderId = bodyMap.get("id").toString();
		
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
					PageData pOrder=appOrderService.findOrderInfo(pm);
					PageData ps=new PageData();
					ps.put("order_no", pOrder.get("order_no"));
					List<PageData> list = appOrderService.findFlowList(ps);//订单流程
					
					Map<String, Object> appMap = new HashMap<String, Object>();
					appMap.put("info",pOrder);//订单信息
					appMap.put("flow", list);
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
	 * @throws Exception 
	 * 
	  * @Title: grab
	  * @Description: 立即抢单
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/grab.html", method = RequestMethod.POST)
	public void grab(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception{
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String orderId = bodyMap.get("id").toString();
		
		try {
			if(Verify.verifyIsNotNull(session_id)){
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当用户信息
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					if(userPd.get("is_work").equals("1")){
//						PushUtil send= new PushUtil();
						//更新订单信息
						PageData pm=new PageData();
						pm.put("id",orderId);
						pm.put("status", "2");
						pm.put("server_user_id", userPd.get("id"));
						appOrderService.update(pm);
						//查询订单信息
						PageData psa=new PageData();
						psa.put("id", orderId);
						PageData pp=appOrderService.findOrderInfo(psa);
						//保存订单流程
						PageData ps=new PageData();
						ps.put("order_no", pp.get("order_no"));
						ps.put("status",pp.get("status"));
						ps.put("operator_id", userPd.get("id"));
						ps.put("operator_name", userPd.get("authent_name"));
						appOrderService.save(ps);//订单流程
						
						//消息推送
						MessageBean ms = new MessageBean();
				        ms.setContentType(Config.getValue("NOTICE"));
				        ms.setContent("您的订单已经受理！");
				        ms.setId("");
				        ms.setTitle("订单消息！");
				        PageData ii=new PageData();
				        ii.put("sn", pp.get("customer_sn"));
				       List<PageData> pi=appOrderService.findfamilyList(ii);
						if (pi != null) {
							for (PageData pa : pi) {
								PushUtil.sendPushFamilyAlias(ms, pa.getString("session_id"));
							}
						}
						json.setMsg("抢单成功。");
						json.setCode("0");
						json.setVerifySessionId(true);
						json.setSuccess(true);
					}else{
						json.setMsg("还没有开工，不能抢单。");
						json.setCode("0");
						json.setVerifySessionId(false);
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
	  * @Title: start
	  * @Description: 确认开工
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/starts.html", method = RequestMethod.POST)
	public void start(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception{
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String orderId = bodyMap.get("id").toString();
		
		try {
			if(Verify.verifyIsNotNull(session_id)){
//				PushUtil send= new PushUtil();
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当用户信息
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					//更新订单信息
					PageData pm=new PageData();
					pm.put("id",orderId);
					pm.put("status", "3");
					appOrderService.updateStatus(pm);
					//查询订单信息
					PageData psa=new PageData();
					psa.put("id", orderId);
					PageData pp=appOrderService.findOrderInfo(psa);
					//保存订单流程
					PageData ps=new PageData();
					ps.put("order_no", pp.get("order_no"));
					ps.put("status",pp.get("status"));
					ps.put("operator_id", userPd.get("id"));
					ps.put("operator_name", userPd.get("authent_name"));
					appOrderService.save(ps);//订单流程
					
					//消息推送
					MessageBean ms = new MessageBean();
			        ms.setContentType(Config.getValue("NOTICE"));
			        ms.setContent("商家已开工！");
			        ms.setId("");
			        ms.setTitle("已开工！");
			        PageData ii=new PageData();
			        ii.put("sn", pp.get("customer_sn"));
			       List<PageData> pi=appOrderService.findfamilyList(ii);
					if (pi != null) {
						for (PageData pa : pi) {
							PushUtil.sendPushFamilyAlias(ms, pa.getString("session_id"));
						}
					}
					json.setMsg("已开工。");
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
	  * @Title: orders
	  * @Description: 确认接单
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/orders.html", method = RequestMethod.POST)
	public void orders(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception{
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String orderId = bodyMap.get("id").toString();
		
		try {
			if(Verify.verifyIsNotNull(session_id)){
//				PushUtil send= new PushUtil();
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当用户信息
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					//更新订单信息
					PageData pm=new PageData();
					pm.put("id",orderId);
					pm.put("receiving", "1");
					pm.put("status", "2");
					appOrderService.updateOrders(pm);
					//查询订单信息
					PageData psa=new PageData();
					psa.put("id", orderId);
					PageData pp=appOrderService.findOrderInfo(psa);
					//保存订单流程
					PageData ps=new PageData();
					ps.put("order_no", pp.get("order_no"));
					ps.put("status",pp.get("status"));
					ps.put("operator_id", userPd.get("id"));
					ps.put("operator_name", userPd.get("authent_name"));
					appOrderService.save(ps);//订单流程
					
					//消息推送
					MessageBean ms = new MessageBean();
			        ms.setContentType(Config.getValue("NOTICE"));
			        ms.setContent("商家已经接单！");
			        ms.setId("");
			        ms.setTitle("已接单！");
			        PageData ii=new PageData();
			        ii.put("sn", pp.get("customer_sn"));
			       List<PageData> pi=appOrderService.findfamilyList(ii);
					if (pi != null) {
						for (PageData pa : pi) {
							PushUtil.sendPushFamilyAlias(ms, pa.getString("session_id"));
						}
					}
					json.setMsg("已接单。");
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
	  * @Title: cancel
	  * @Description: 取消订单
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/cancel.html", method = RequestMethod.POST)
	public void cancel(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception{
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String orderId = bodyMap.get("id").toString();
		
		try {
			if(Verify.verifyIsNotNull(session_id)){
//				PushUtil send= new PushUtil();
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当用户信息
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					PageData pdq = new PageData();
					pdq.put("id", orderId);
					PageData pa=appOrderService.findOrderInfo(pdq);//查询server_other
					PageData pm=new PageData();
					if(pa.get("others_server").toString().equals("Y")&&pa.get("status").toString().equals("11")){
						pm.put("id",orderId);
						pm.put("others_server", "B");
						pm.put("server_user_id", null);
						pm.put("status", "1");
						pm.put("receiving", "-1");
						appOrderService.updateServer(pm);
					}
					if(pa.get("others_server").toString().equals("N")&&pa.get("status").toString().equals("11")){
						pm.put("id",orderId);
						pm.put("server_user_id", null);
						pm.put("status", "0");
						pm.put("receiving", "-1");
						appOrderService.updateServer(pm);
					}if(pa.get("others_server").toString().equals("N")&&pa.get("status").toString().equals("2")){
						pm.put("id",orderId);
						pm.put("status", "1");
						appOrderService.updateServer(pm);
					}
					
					//查询订单信息
					PageData psa=new PageData();
					psa.put("id", orderId);
					PageData pp=appOrderService.findOrderInfo(psa);
					//保存订单流程
					PageData ps=new PageData();
					ps.put("order_no", pp.get("order_no"));
					if(pp.get("others_server").toString().equals("B")){
						ps.put("status","8");
					}else{
						ps.put("status",pp.get("status"));
					}
					ps.put("operator_id", userPd.get("id"));
					ps.put("operator_name", userPd.get("authent_name"));
					appOrderService.save(ps);//订单流程
					
					MessageBean ms = new MessageBean();
					if (pp.get("others_server").toString().equals("B")) {
						ms.setContentType(Config.getValue("NOTICE"));
						ms.setContent("商家已经取消订单，订单扔进订单池，等待重新等待抢单！");
						ms.setId("");
						ms.setTitle("已经取消订单！");
						PageData ii = new PageData();
						ii.put("sn", pp.get("customer_sn"));
						List<PageData> pi = appOrderService.findfamilyList(ii);
						if (pi != null) {
							for (PageData pas : pi) {
								PushUtil.sendPushFamilyAlias(ms, pas.getString("session_id"));
							}
						}
					}	if (pp.get("status").toString().equals("0")) {
						ms.setContentType(Config.getValue("NOTICE"));
						ms.setContent("商家已经取消订单，订单已经摧毁，请重新下单！");
						ms.setId("");
						ms.setTitle("已经取消订单！");
						PageData ii = new PageData();
						ii.put("sn", pp.get("customer_sn"));
						List<PageData> pi = appOrderService.findfamilyList(ii);
						if (pi != null) {
							for (PageData pas : pi) {
								PushUtil.sendPushFamilyAlias(ms, pas.getString("session_id"));
							}
						}
					}
					
					json.setMsg("已取消订单。");
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
	  * @Title: pay
	  * @Description: 待完成中的支付方式
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/pay.html", method = RequestMethod.POST)
	public void pay (@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception{
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String orderId = bodyMap.get("id").toString();
		String pay = bodyMap.get("pay_type").toString();
		String amount = bodyMap.get("amount").toString();
		try {
			if(Verify.verifyIsNotNull(session_id)){
//				PushUtil send= new PushUtil();
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当用户信息
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					if(pay.equals("cash")){
						PageData pm=new PageData();
						pm.put("id",orderId);
						pm.put("amount", amount);
						pm.put("status", "5");
						appOrderService.updatePay(pm);
						
						PageData po=new PageData();
						po.put("id", orderId);
						PageData  pv=appOrderService.findOrderInfo(po);
						ToroInfoUtil.getToroEvaluate(pv.getString("customer_sn"),pv.getString("order_no"));
					}else{
						PageData pm=new PageData();
						pm.put("id",orderId);
						pm.put("amount", amount);
						pm.put("status", "4");
						appOrderService.updatePay(pm);
						
						MessageBean ms = new MessageBean();
						ms.setContentType( Config.getValue("NOTICE"));
						ms.setContent("订单已经支付完成！");
						ms.setId("");
						ms.setTitle("订单消息！");
						PageData ii=new PageData();
						
						//查询订单信息
						PageData psa=new PageData();
						psa.put("id", orderId);
						PageData pp=appOrderService.findOrderInfo(psa);
						ii.put("sn", pp.get("customer_sn"));
						List<PageData> pi=appOrderService.findfamilyList(ii);
						if (pi != null) {
							for (PageData pa : pi) {
								PushUtil.sendPushFamilyAlias(ms, pa.getString("session_id"));
							}
						}
					}
	
					//查询订单信息
					PageData psa=new PageData();
					psa.put("id", orderId);
					PageData pp=appOrderService.findOrderInfo(psa);
					//保存订单流程
					PageData ps=new PageData();
					ps.put("order_no", pp.get("order_no"));
					ps.put("status",pp.get("status"));
					ps.put("operator_id", userPd.get("id"));
					ps.put("operator_name", userPd.get("authent_name"));
					appOrderService.save(ps);//订单流程
					
						
				
					
					json.setMsg("待完成。");
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
