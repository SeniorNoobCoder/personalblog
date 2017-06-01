package com.sdhsie.app.family.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.sdhsie.app.family.service.AppMineService;
import com.sdhsie.app.family.service.IndexService;
import com.sdhsie.app.file.AppFileUtil;
import com.sdhsie.app.server.service.AppOrderService;
import com.sdhsie.app.server.service.AppServerMineService;
import com.sdhsie.base.controller.AppBaseController;
import com.sdhsie.base.util.Json;
import com.sdhsie.base.util.PageData;
import com.sdhsie.base.util.Verify;
import com.sdhsie.base.util.parameterUtil;
import com.sdhsie.base.util.upload.FileUpload;
import com.sdhsie.base.util.upload.ParaUtil;
import com.sdhsie.webService.toro.controller.ToroInfoUtil;

@Controller
@RequestMapping(value="/app/mine")
public class AppMineController extends AppBaseController{
	@Autowired
	private AppMineService appMineService;
	@Autowired
	private AppOrderService appOrderService;
	@Autowired
	private AppServerMineService appServerMineService;
	@Autowired
	private IndexService indexService;
	
	/**
	 * 
	  * @Title: findRelationshipList
	  * @Description:查找关系
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findRelationshipList.html", method = RequestMethod.POST)
	public void findRelationshipList(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
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
					//关系
					List<PageData> list = new ArrayList<PageData>();
					PageData pd=new PageData();
					list=appMineService.findRelationshipList(pd);
					
					Map<String, Object> appMap = new HashMap<String, Object>();
					appMap.put("list",list);
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
	  * @Title: saveRelation
	  * @Description: 绑定设备
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/saveRelation.html", method = RequestMethod.POST)
	public void saveRelation(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String sn=bodyMap.get("sn").toString();//设备编号
		String relationship=bodyMap.get("relationship").toString();//关系
		try {
			if(Verify.verifyIsNotNull(session_id)){
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当前用户
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					PageData pd = new PageData();
					pd.put("sn", sn);
					int num=appMineService.findDeviceNum(pd);
					PageData po=appMineService.findDeviceInfo(pd);
					
					if(num>0){
						pd.put("family_id", userPd.get("id"));
						int sum=appMineService.findRepeatInfo(pd);
						if(sum<=0){
							pd.put("relationship", relationship);
							pd.put("device_id",po.get("id") );
							appMineService.saveRelation(pd);
							//修改设备信息
							PageData pi=new PageData();
							pi.put("app_id", userPd.get("id"));
							pi.put("sn", sn);
							appMineService.updateDevice(pi);
							json.setMsg("绑定成功。");
							json.setCode("0");
							json.setVerifySessionId(true);
							json.setSuccess(true);
						}else{
							json.setMsg("设备已经绑定 不能重复绑定。");
							json.setCode("0");
							json.setVerifySessionId(false);
							json.setSuccess(false);
						}
					}else{
						json.setMsg("没有该设备，请重新输入设备号。");
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
	  * @Title: saveFeedback
	  * @Description: 意见反馈
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/saveFeedback.html", method = RequestMethod.POST)
	public void saveFeedback(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception{
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String content=bodyMap.get("content").toString();//反馈内容
		try {
			if(Verify.verifyIsNotNull(session_id)){
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当用户信息
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					PageData pd=new PageData();
					pd.put("create_user", userPd.get("id"));
					pd.put("status", "N");
					pd.put("remove_logo", "N");
					pd.put("content", content);
					pd.put("type", "family");//商户类型
					appServerMineService.saveFeedback(pd);
					
					json.setMsg("意见反馈成功。");
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
	  * @Title: findFamilyList
	  * @Description: 查找家庭承成员
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findFamilyList.html", method = RequestMethod.POST)
	public void findFamilyList(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
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
					List<PageData> list = new ArrayList<PageData>();
					PageData pd=new PageData();
					pd.put("family_id", userPd.get("id"));
					list=appMineService.findFamilyList(pd);
					for (PageData pl : list) {
						if(Verify.verifyIsNotNull(pl.getString("head_address"))){
							pl.put("head_address", FileUpload.findLocalFileUrl(pl.getString("head_address")));
						}else{
							pl.put("head_address","");
						}
						if(!Verify.verifyIsNotNull(pl.get("island_name"))){
							pl.put("island_name", "");
						}
					}
					Map<String, Object> appMap = new HashMap<String, Object>();
					appMap.put("list",list);
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
	  * @Title: findConsumeList
	  * @Description: 查询消费记录
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
					pd.put("family_id", userPd.get("id"));
					pd.put("index", index);
					Object update_time_obj= bodyMap.get("update_time");
					if(update_time_obj!=null){
						update_time = update_time_obj.toString();
						pd.put("update_time", update_time);
					}
					
					this.getPage(pd);
					int num=appMineService.findDateNum(pd);
					if(num>0){
						if(num%parameterUtil.app_showCount>0){//取余 当余数大于0时，总页数+1 当余数 = 0时 不加
							flag = num/parameterUtil.app_showCount+1;
						}else {
							flag = num/parameterUtil.app_showCount;
						}
					}
					
					list=appMineService.findTimeList(pd);
					listInfo=appMineService.findConsumeList(pd);
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
	  * @Title: saveCollect
	  * @Description: 收藏
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/saveCollect.html", method = RequestMethod.POST)
	public void saveCollect(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception{
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String server_info_id=bodyMap.get("id").toString();//服务信息id
		try {
			if(Verify.verifyIsNotNull(session_id)){
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当用户信息
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					PageData co=new PageData();
					co.put("server_info_id", server_info_id);
					co.put("user_id", userPd.get("id"));
					int num=indexService.findCollectNum(co);
					if(num>0){
						appMineService.delete(co);
					}else{
						appMineService.saveCollect(co);
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
	  * @Title: findCollectList
	  * @Description: 收藏列表
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findCollectList.html", method = RequestMethod.POST)
	public void findCollectList(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception{
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String index=bodyMap.get("index").toString();//服务信息id
		try {
			if(Verify.verifyIsNotNull(session_id)){
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当用户信息
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					int flag=0;
					PageData pd=new PageData();
					pd.put("user_id", userPd.get("id"));
					pd.put("index", index);
					this.getPage(pd);
					List<PageData> list=appMineService.findCollectList(pd);
					for (PageData rootPd : list) {
						if(Verify.verifyIsNotNull(rootPd.getString("head_address"))){
							rootPd.put("head_address", FileUpload.findLocalFileUrl(rootPd.getString("head_address")));
						}else{
							rootPd.put("head_address","");
						}
					}
					int num=appMineService.findCollectNum(pd);
					if(num>0){
						if(num%parameterUtil.app_showCount>0){//取余 当余数大于0时，总页数+1 当余数 = 0时 不加
							flag = num/parameterUtil.app_showCount+1;
						}else {
							flag = num/parameterUtil.app_showCount;
						}
					}
					
					Map<String, Object> appMap = new HashMap<String, Object>();
					appMap.put("list",list);
					appMap.put("flag", flag);
					json.setMyobject(appMap);
					json.setMsg("查询收藏列表成功。");
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
	  * @Title: deleteDevice
	  * @Description: 解绑设备号
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/deleteDevice.html", method = RequestMethod.POST)
	public void deleteDevice(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception{
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String id=bodyMap.get("id").toString();//家人设备信息id
		try {
			if(Verify.verifyIsNotNull(session_id)){
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当用户信息
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					PageData pd=new PageData();
					pd.put("id", id);
					appMineService.deleteDevice(pd);
					
					json.setMsg("解绑设备成功。");
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
	  * @Title: findEvaluateList
	  * @Description: 查询用户评价
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findEvaluateList.html", method = RequestMethod.POST)
	public void findEvaluateList(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception{
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String index=bodyMap.get("index").toString();
		try {
			if(Verify.verifyIsNotNull(session_id)){
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当用户信息
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					int flag=0;
					List<PageData> list =new ArrayList<PageData>();
					List<PageData> listImage =new ArrayList<PageData>();
					List<PageData> newList =new ArrayList<PageData>();
					PageData pd=new PageData();
					pd.put("server_user_id", userPd.get("id"));
					pd.put("index", index);
					this.getPage(pd);
					list=appMineService.findEvaluateList(pd);//查询用户评论
					for (PageData pj : list) {
						PageData d=new PageData();
						d.put("assessment_id", pj.get("id"));
						listImage=appMineService.findImageList(d);//查询图片
						for(PageData pl:listImage){
							if(Verify.verifyIsNotNull(pl.getString("image_url"))){
								pl.put("image_url", FileUpload.findLocalFileUrl(pl.getString("image_url")));
							}else{
								pl.put("image_url","");
							}
						}
						pj.put("listImage", listImage);
						newList.add(pj);
						
					}
					
					int num=appMineService.findEvaluateNum(pd);//分页
					if(num>0){
						if(num%parameterUtil.app_showCount>0){//取余 当余数大于0时，总页数+1 当余数 = 0时 不加
							flag = num/parameterUtil.app_showCount+1;
						}else {
							flag = num/parameterUtil.app_showCount;
						}
					}
					
					//查询星级为1的数量
					PageData st1=new PageData();
					st1.put("service_quality", "1");
					st1.put("server_user_id", userPd.get("id"));
					int statr1=appMineService.findStartNum(st1);
					//查询为2的
					PageData st2=new PageData();
					st2.put("service_quality", "2");
					st2.put("server_user_id", userPd.get("id"));
					int statr2=appMineService.findStartNum(st2);
					//查询为3的
					PageData st3=new PageData();
					st3.put("service_quality", "3");
					st3.put("server_user_id", userPd.get("id"));
					int statr3=appMineService.findStartNum(st3);
					//查询为4的
					PageData st4=new PageData();
					st4.put("service_quality", "4");
					st4.put("server_user_id", userPd.get("id"));
					int statr4=appMineService.findStartNum(st4);
					//查询为5的
					PageData st5=new PageData();
					st5.put("service_quality", "5");
					st5.put("server_user_id", userPd.get("id"));
					int statr5=appMineService.findStartNum(st5);
					
					double sum=statr1+statr2+statr3+statr4+statr5;//总评价数
					double sumQuality =statr1*1+statr2*2+statr3*3+statr4*4+statr5*5;//总服务质量
					double avg=0;
					if (list!=null && num != 0) {
						 avg=sumQuality/sum;//等级
					}
					else{
						 avg=0;
					}
					Map<String, Object> appMap = new HashMap<String, Object>();
					appMap.put("list",newList);
					appMap.put("flag", flag);
					appMap.put("statr1", statr1);
					appMap.put("statr2", statr2);
					appMap.put("statr3", statr3);
					appMap.put("statr4", statr4);
					appMap.put("statr5", statr5);
					appMap.put("sumQuality", sum);//总的服务质量
					appMap.put("grade", avg);
					json.setMsg("查询成功。");
					json.setMyobject(appMap);
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
					pd.put("type", "family");
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
	  * @Title: updatefamily
	  * @Description: 修改家庭成员信息
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/updatefamily.html", method = RequestMethod.POST)
	public void updatefamily(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception{
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String id=bodyMap.get("id").toString();//家人设备id
		String relationship=null;
		String name=null;
		String phone=null;
		String head_address = null;
		String island_name=null;
		try {
			if(Verify.verifyIsNotNull(session_id)){
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当用户信息
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					PageData  pp=new PageData();
					pp.put("id", id);
					PageData oo=appMineService.findUserInfo(pp);//查询家人设备信息
					PageData pd=new PageData();
					
					Object relationship_obj= bodyMap.get("relationship");
					if(relationship_obj!=null&&relationship_obj!=""){
						relationship = relationship_obj.toString();
						//修改家人设备信息 
						pd.put("id", id);
						pd.put("relationship", relationship);
						appMineService.updatefamily(pd);
					}
					
					PageData uu=new PageData();
				
					uu.put("device_id", oo.get("device_id"));
					Object head_address_obj = bodyMap.get("head_address");
					if(head_address_obj!=null && !"".equals(head_address_obj)){
						head_address = head_address_obj.toString();
						String  head_address_path = ParaUtil.business+ParaUtil.user+ParaUtil.authent;
						head_address = AppFileUtil.generateImage(head_address,head_address_path,request);//图片上传
						uu.put("head_address", head_address);
					}
					
					/*if (!"".equals(head_address_obj)) {
						uu.put("head_address", head_address);
					}*/
					Object phone_obj= bodyMap.get("phone");
					if(phone_obj!=null&&phone_obj!=""){
						phone = phone_obj.toString();
						uu.put("phone", phone);
					}
					Object name_obj= bodyMap.get("name");
					if(name_obj!=null&&name_obj!=""){
						name = name_obj.toString();
						uu.put("name", name);
					}
					Object island_name_obj= bodyMap.get("island_name");
					if(island_name_obj!=null&&island_name_obj!=""){
						island_name = island_name_obj.toString();
						uu.put("island_name", island_name);
					}
					appMineService.updatefamilyUser(uu);
					
					json.setMsg("修改信息成功。");
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
	  * @Title: GetContact
	  * @Description: 获取联系人
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/GetContact.html", method = RequestMethod.POST)
	public void GetContact(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception{
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String sn=bodyMap.get("sn").toString();//设备编号
		try {
			if(Verify.verifyIsNotNull(session_id)){
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当用户信息
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					
					List<PageData> list=ToroInfoUtil.getContacts(sn);
					Map<String, Object> appMap = new HashMap<String, Object>();
					appMap.put("list",list);
					json.setMyobject(appMap);
					json.setMsg("查询设备信息成功。");
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
	  * @Title: SetContact
	  * @Description: 新增联系人
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/SetContact.html", method = RequestMethod.POST)
	public void SetContact(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception{
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String sn=bodyMap.get("sn").toString();//设备编号
		String mobile=bodyMap.get("mobile").toString();//手机号
		String name=bodyMap.get("name").toString();//名称
		String key=bodyMap.get("key").toString();//快捷键
		String cid=bodyMap.get("cid").toString();//联系人id
		try {
			if(Verify.verifyIsNotNull(session_id)){
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当用户信息
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					PageData pd=new PageData();
					if(cid.equals("")){
						pd.put("cid", "");
						pd.put("sign", 1);
					}else{
						pd.put("cid", cid);
						pd.put("sign", 2);
					}
					pd.put("sn", sn);
					pd.put("mobile", mobile);
					pd.put("name",name);
					pd.put("key", key);
					boolean setContacts=ToroInfoUtil.setContacts(pd);
					if(setContacts==true){
						json.setMsg("操作成功。");
						json.setCode("0");
						json.setVerifySessionId(true);
						json.setSuccess(true);
					}else{
						json.setMsg("操作失败。");
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
	  * @Title: deleteContact
	  * @Description: 删除联系人
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/deleteContact.html", method = RequestMethod.POST)
	public void deleteContact(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception{
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String sn=bodyMap.get("sn").toString();//设备编号
		String cid=bodyMap.get("cid").toString();//联系人id
		try {
			if(Verify.verifyIsNotNull(session_id)){
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当用户信息
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					PageData pd=new PageData();
					pd.put("sign", 3);
					pd.put("sn", sn);
					pd.put("cid", cid);
					boolean setContacts=ToroInfoUtil.setContacts(pd);
					if(setContacts==true){
						json.setMsg("删除联系人成功。");
						json.setCode("0");
						json.setVerifySessionId(true);
						json.setSuccess(true);
					}else{
						json.setMsg("删除联系人失败。");
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
	  * @Title: GetEnclosure
	  * @Description: 获取电子围栏
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/GetEnclosure.html", method = RequestMethod.POST)
	public void GetEnclosure(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception{
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String sn=bodyMap.get("sn").toString();//设备编号
		
		try {
			if(Verify.verifyIsNotNull(session_id)){
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当用户信息
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					
					List<PageData> GetEnclosure = ToroInfoUtil.getFence(sn);
					PageData pp=null;
					if(GetEnclosure.size()>0){
						pp=GetEnclosure.get(0);
					}else{
						PageData loc= ToroInfoUtil.getToroLoc(sn);
						pp.put("lon", loc.get("lon"));
						pp.put("lat", loc.get("lat"));
					}
					json.setMyobject(pp);
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
	  * @Title: SetEnclosure
	  * @Description: 设置电子围栏
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/SetEnclosure.html", method = RequestMethod.POST)
	public void SetEnclosure(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception{
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String sn=bodyMap.get("sn").toString();//设备编号
		String name=bodyMap.get("name").toString();
		String radius=bodyMap.get("radius").toString();
		String lon=bodyMap.get("lon").toString();
		String lat=bodyMap.get("lat").toString();
		String fence_id=bodyMap.get("fence_id").toString();
		try {
			if(Verify.verifyIsNotNull(session_id)){
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当用户信息
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					PageData pd = new PageData();
					pd.put("sn", sn);
					if(fence_id.equals("")){
						pd.put("fence_id", "");
						pd.put("sign", 1);
					}else{
						pd.put("fence_id", fence_id);
						pd.put("sign", 2);
					}
					pd.put("name", name);
					pd.put("radius", radius);
					pd.put("lon", lon);
					pd.put("lat", lat);
					boolean SetEnclosure = ToroInfoUtil.setFence(pd);
					pd.put("island_name", name);
					appMineService.updatefamilySn(pd);
					if(SetEnclosure==true){
						json.setMsg("设置电子围栏成功。");
						json.setCode("0");
//						json.setMyobject(myobject);
						json.setVerifySessionId(true);
						json.setSuccess(true);
					}else{
						json.setMsg("设置电子围栏失败。");
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
	  * @Title: deleteEnclosure
	  * @Description: 删除电子围栏
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
/*	@RequestMapping(value="/deleteEnclosure.html", method = RequestMethod.POST)
	public void deleteEnclosure(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception{
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String fence_id=bodyMap.get("fence_id").toString();//围栏id
		String sn=bodyMap.get("sn").toString();//设备编号
		try {
			if(Verify.verifyIsNotNull(session_id)){
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当用户信息
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					PageData pd = new PageData();
					pd.put("fence_id",fence_id);
					pd.put("sign", 3);
					pd.put("sn", sn);
					boolean SetEnclosure = ToroInfoUtil.setFence(pd);
					if(SetEnclosure==true){
						json.setMsg("删除电子围栏成功。");
						json.setCode("0");
						json.setVerifySessionId(true);
						json.setSuccess(true);
					}else{
						json.setMsg("删除电子围栏失败。");
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
	}*/
	
	
	
	
	
	
	
	
	
	
	

}
