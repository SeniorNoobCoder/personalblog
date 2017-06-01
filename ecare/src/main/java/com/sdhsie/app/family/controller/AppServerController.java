package com.sdhsie.app.family.controller;

import java.io.IOException;
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

import com.sdhsie.app.common.controller.AppParameter;
import com.sdhsie.app.family.service.AppServerService;
import com.sdhsie.app.family.service.IndexService;
import com.sdhsie.app.server.service.AppOrderService;
import com.sdhsie.base.controller.AppBaseController;
import com.sdhsie.base.util.Config;
import com.sdhsie.base.util.GuidUtil;
import com.sdhsie.base.util.Json;
import com.sdhsie.base.util.PageData;
import com.sdhsie.base.util.Verify;
import com.sdhsie.base.util.parameterUtil;
import com.sdhsie.push.MessageBean;
import com.sdhsie.push.PushUtil;

@Controller
@RequestMapping(value="/app/server")
public class AppServerController extends AppBaseController{
	
	@Autowired
	private IndexService indexService;
	@Autowired
	private AppOrderService appOrderService;
	@Autowired
	private AppServerService appServerService;
	
	/**
	 * 
	  * @Title: findInfo
	  * @Description: 服务对象
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findServerList.html", method = RequestMethod.POST)
	public void findInfo(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
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
					//获取订单信息
					PageData pm=new PageData();
					pm.put("family_id", userPd.get("id"));
					List<PageData> list =appServerService.findServiceList(pm);//查询服务对象
					Map<String, Object> appMap = new HashMap<String, Object>();
					appMap.put("server",list);//服务对象
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
	  * @Title: saveorder
	  * @Description: 下订单
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/saveorder.html", method = RequestMethod.POST)
	public void saveorder(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String serverId=null;
		String server_user_id=null;
		try {
			if(Verify.verifyIsNotNull(session_id)){
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当用户信息
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					String deviceId = bodyMap.get("device_id").toString();//设备客户id
					String address = bodyMap.get("address").toString();//服务对象地址（设备客户地址）
					String name = bodyMap.get("name").toString();//服务对象名称（设备客户名称）
					String phone = bodyMap.get("phone").toString();//服务对象电话（设备客户电话）
					String remark = bodyMap.get("remark").toString();
					String appointment_time = bodyMap.get("appointment_time").toString();//预约时间
					String sn = bodyMap.get("sn").toString();
					String customer_id =bodyMap.get("customer_id").toString();//服务对象id
					//保存订单
					PageData pd = new PageData();
					//服务信息id 
					Object serverId_obj= bodyMap.get("id");
					if(serverId_obj!=null){
						String others_server=null;
						Object others_server_obj= bodyMap.get("others_server");
						if(others_server_obj!=null&&others_server_obj!=""){
							others_server = others_server_obj.toString();
							//如果没有server_user_id就不添加
							Object server_user_id_obj=bodyMap.get("server_user_id");
							if(server_user_id_obj!=null){
								server_user_id=server_user_id_obj.toString();
								 pd.put("server_user_id", server_user_id);
							}
							if(others_server.equals("Y")){
								 pd.put("receiving", "0");
								 pd.put("status", "11");
							}else{
								 pd.put("receiving", "0");
								 pd.put("status", "11");
							}
							pd.put("others_server", others_server);
						}else{
							 pd.put("status", "1");
							pd.put("others_server", "N");
						}
						//服务信息
						PageData pInfo=new PageData();
						serverId = serverId_obj.toString();
						pInfo.put("id", serverId);
						PageData info=indexService.findInfo(pInfo);
						
						pd.put("server_category_id", info.get("server_category_id"));//服务分类id
						pd.put("server_category_name", info.get("server_category_name"));//服务分类名称
						pd.put("server_info_id", info.get("id"));//合作商发布的服务信息id
						
						pd.put("device_id", deviceId);
						pd.put("customer_address", address);
						pd.put("customer_name", name);
						pd.put("customer_phone", phone);
						pd.put("remark", remark);
						pd.put("customer_id", customer_id);
						pd.put("customer_sn", sn);
						pd.put("appointment_time", appointment_time);
						pd.put("order_source", parameterUtil.order_source_family);//数据来源
						pd.put("order_no", "S-" + GuidUtil.geOrderNo());	//订单编号
					    pd.put("create_user", userPd.get("id"));
				        pd.put("remove_logo", "N");
				        appServerService.saveOder(pd);
				        if(server_user_id!=null&&!server_user_id.equals("0")){
							//business_id 根据这个指定单用户查找他的session
							PageData pop=new PageData();
							pop.put("id", server_user_id);
							PageData pp=appServerService.findUserInfo(pop);
							MessageBean ms = new MessageBean();
							ms.setContentType(Config.getValue("NOTICE"));
							ms.setContent("您有新指定的订单消息！");
							ms.setId("");
							ms.setTitle("指订单消息");
							PushUtil.sendPushBussinessAlias(ms, pp.getString("session_id"));
				      }else{
							PageData po=new PageData();
							po.put("id",info.get("server_category_id"));
							po.put("partner_id",info.get("partner_id"));
							List<PageData> list=appServerService.findTshList(po);
							MessageBean ms = new MessageBean();
							ms.setContentType(Config.getValue("ORDER"));
							ms.setContent("有新的订单进入订单池，请抢单！");
							ms.setId("");
							ms.setTitle("有新的订单");
							if(list.size()>0){
								for (PageData pag : list) {
//									PageData pop=new PageData();
//									pop.put("id", pag.get("id"));
//									PageData pp=appServerService.findUserInfo(pop);
//									if(pp!=null){
									PushUtil.sendPushBussinessAlias(ms, pag.getString("session_id"));
//									}
								}
							}
				        }
					}else{
						String server_category_name = bodyMap.get("server_category_name").toString();
						String server_category_id = bodyMap.get("server_category_id").toString();//
						pd.put("server_category_id", server_category_id);//服务分类id
						pd.put("server_category_name",server_category_name);//服务分类名称
						pd.put("others_server", "N");
						pd.put("device_id", deviceId);
						pd.put("customer_address", address);
						pd.put("customer_name", name);
						pd.put("customer_sn", sn);
						pd.put("customer_phone", phone);
						pd.put("customer_id", customer_id);
						pd.put("remark", remark);
						pd.put("appointment_time", appointment_time);
						pd.put("order_source", parameterUtil.order_source_family);//数据来源
						pd.put("order_no", "S-" + GuidUtil.geOrderNo());	//订单编号
					    pd.put("create_user", userPd.get("id"));
				        pd.put("remove_logo", "N");
				        pd.put("receiving", "");
				        pd.put("status", "1");
				        appServerService.saveOder(pd);
				        PageData oo=new PageData();
				        oo.put("category_id", server_category_id);
				        List<PageData> list=appServerService.findTsList(oo);
						MessageBean ms = new MessageBean();
						ms.setContentType(Config.getValue("ORDER"));
						ms.setContent("有新的订单进入订单池，请抢单！");
						ms.setId("");
						ms.setTitle("有新的订单");
						if(list.size()>0){
							for (PageData pag : list) {
//								PageData pop=new PageData();
//								pop.put("id", pag.get("business_id"));
//								PageData pp=appServerService.findUserInfo(pop);
//								if(pp!=null){
									PushUtil.sendPushBussinessAlias(ms, pag.getString("session_id"));
//								}
							}
						}
					}
					//===========保存订单流程====start=============
					PageData ps=new PageData();
					ps.put("order_no", pd.get("order_no"));
					ps.put("status",pd.get("status"));
					ps.put("operator_id", userPd.get("id"));
					ps.put("operator_name", userPd.get("nickname"));
					appOrderService.save(ps);//订单流程
					//===========保存订单流程====end=============
					json.setMsg("下单成功。");
					json.setMyobject("");
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
