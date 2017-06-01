package com.sdhsie.app.family.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sdhsie.base.util.upload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sdhsie.app.common.controller.AppParameter;
import com.sdhsie.app.family.service.AppMineService;
import com.sdhsie.app.family.service.IndexService;
import com.sdhsie.app.server.service.AppOrderService;
import com.sdhsie.base.controller.AppBaseController;
import com.sdhsie.base.controller.BaseController;
import com.sdhsie.base.util.Json;
import com.sdhsie.base.util.PageData;
import com.sdhsie.base.util.Verify;
import com.sdhsie.base.util.parameterUtil;

@Controller
@RequestMapping(value="/app/home")
public class IndexController extends AppBaseController{
	
	@Autowired
	private IndexService indexService;
	@Autowired
	private AppOrderService appOrderService;
	@Autowired
	private AppMineService appMineService;
	
	/**
	 * 
	  * @Title: findHomeList
	  * @Description: 家人版首页
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findHomeList.html", method = RequestMethod.POST)
	public void findHomeList(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		Json json = new Json();
		try {
					//服务优选（级别为1）
					List<PageData> listServer = new ArrayList<PageData>();
					PageData po=new PageData();
					po.put("level", "1");
					po.put("show_app", "Y");
					listServer=indexService.findRootList(po);
					for(PageData serverPd:listServer){
						if(Verify.verifyIsNotNull(serverPd.getString("image"))){
							serverPd.put("image", FileUpload.findLocalFileUrl(serverPd.getString("image")));
						}else{
							serverPd.put("image","");
						}
					}
					//轮播图列表
					List<PageData> list = new ArrayList<PageData>();
					list=indexService.findCarouseList();
					for(PageData pl:list){
						if(Verify.verifyIsNotNull(pl.getString("image_href"))){
							pl.put("image_href", FileUpload.findLocalFileUrl(pl.getString("image_href")));
						}else{
							pl.put("image_href","");
						}
					}
					//根服务（级别为0）
					PageData pdq=new PageData();
					List<PageData> listRoot = new ArrayList<PageData>();
					pdq.put("level", "0");
					pdq.put("parent_id", "0");
					pdq.put("show_app","Y");
					listRoot=indexService.findRootList(pdq);
					for(PageData rootPd:listRoot){
						if(Verify.verifyIsNotNull(rootPd.getString("image"))){
							rootPd.put("image", FileUpload.findLocalFileUrl(rootPd.getString("image")));
						}else{
							rootPd.put("image","");
						}
					}
					Map<String, Object> appMap = new HashMap<String, Object>();
					appMap.put("Carouse",list);
					appMap.put("root",listRoot);
					appMap.put("server",listServer);
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
	  * @Title: findHotList
	  * @Description: 热门服务列表
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findHotList.html", method = RequestMethod.POST)
	public void findHotList(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String index=bodyMap.get("index").toString();
		String name=bodyMap.get("name").toString();//地区名称
		try {
					//根据城市查询热门服务
					PageData pp = new PageData();
					PageData areaName = null;
//					if (!name.equals("-1")) {
						pp.put("name", name);
						areaName = indexService.findAreaInfo(pp);
//					}
					int flag = 0;
//					//热门服务
					PageData pd=new PageData();
					pd.put("index", index);
					pd.put("city", areaName.get("id"));
					this.getPage(pd);
					List<PageData> listHot = new ArrayList<PageData>();
					listHot=indexService.findServerList(pd);
					for (PageData ad:listHot){
						if(Verify.verifyIsNotNull(ad.getString("head_address"))){
							ad.put("head_address", FileUpload.findLocalFileUrl(ad.getString("head_address")));
						}else{
							ad.put("head_address","");
						}
					}
					int num=indexService.findServerListCount(pd);
					if(num>0){
						if(num%parameterUtil.app_showCount>0){//取余 当余数大于0时，总页数+1 当余数 = 0时 不加
							flag = num/parameterUtil.app_showCount+1;
						}else {
							flag = num/parameterUtil.app_showCount;
						}
					}
					
					Map<String, Object> appMap = new HashMap<String, Object>();
					appMap.put("hot",listHot);
					appMap.put("flag",flag);//分页
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
	  * @Title: findLiftList
	  * @Description: 生活服务2级（点击生活服务出现的信息）
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findLiftList.html", method = RequestMethod.POST)
	public void findLiftList(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String liftId=bodyMap.get("id").toString();
		try {
					//根服务（级别为1）
					List<PageData> listRoot = new ArrayList<PageData>();
					PageData pp=new PageData();
					pp.put("parent_id", liftId);
					pp.put("level", "1");
					listRoot=indexService.findRootList(pp);
					for(PageData pl:listRoot){
						if(Verify.verifyIsNotNull(pl.getString("image"))){
							pl.put("image", FileUpload.findLocalFileUrl(pl.getString("image")));
						}else{
							pl.put("image","");
						}
					}
					
					Map<String, Object> appMap = new HashMap<String, Object>();
					appMap.put("root",listRoot);
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
	  * @Title: findLiftInfo
	  * @Description: 生活服务下边的3级（点击2级查询的信息 例如：点击家政服务查询服务信息）
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findLiftInfo.html", method = RequestMethod.POST)
	public void findLiftInfo(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String towLevelId=bodyMap.get("towLevelId").toString();//二级分类id
		String oneLevelId=bodyMap.get("oneLevelId").toString();//一级分类id
		String areaId=bodyMap.get("areaId").toString();//地区id 默认为null
		String type=bodyMap.get("type").toString();//类型默认-1、city、county
		String name=bodyMap.get("name").toString();//地区名称 
		String companyName=null;//合作商名称
		String index = bodyMap.get("index").toString();
		if("-1".equals(oneLevelId)){//如果手机端传过来-1 则查询全部
			oneLevelId = "";
			towLevelId = "";
		}
		if("-1".equals(towLevelId)){
			towLevelId = "";
		}
		try {
				int flag = 0;
					PageData pd=new PageData();
					this.getPage(pd);
					pd.put("towLevelId", towLevelId); 
					pd.put("oneLevelId", oneLevelId);
					pd.put("index",index);
					PageData areaName=null;
					if(areaId==""){
						pd.put("name", name);
						 areaName=indexService.findAreaInfo(pd);
						 pd.put("city", areaName.get("id"));
					}
					if ("city".equals(type)) {
						pd.put("city", areaId);
					} else if ("county".equals(type)) {
						pd.put("county", areaId);
					}
					
					//合作商名称
					Object companyName_obj= bodyMap.get("companyName");
					if(companyName_obj!=null){
						companyName = companyName_obj.toString();
						pd.put("companyName", companyName);
					}
					List<PageData> listRoot =  indexService.findServerList(pd);//根据partner_id查找相应server_info中的信息
					for (PageData ad:listRoot){
						if(Verify.verifyIsNotNull(ad.getString("head_address"))){
							ad.put("head_address", FileUpload.findLocalFileUrl(ad.getString("head_address")));
						}else{
							ad.put("head_address","");
						}
					}
					int num=indexService.findServerListCount(pd);
					if(num>0){
						if(num%parameterUtil.app_showCount>0){//取余 当余数大于0时，总页数+1 当余数 = 0时 不加
							flag = num/parameterUtil.app_showCount+1;
						}else {
							flag = num/parameterUtil.app_showCount;
						}
					}
					
					Map<String, Object> appMap = new HashMap<String, Object>();
					appMap.put("root",listRoot);
					appMap.put("flag",flag);//分页
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
	  * @Title: findHotInfo
	  * @Description: 热门服务详情
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findHotInfo.html", method = RequestMethod.POST)
	public void findHotInfo(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String liftId=bodyMap.get("id").toString();
		try {
					PageData pp=new PageData();
					pp.put("id", liftId);
					PageData info=indexService.findInfo(pp);//商家详情
					if(Verify.verifyIsNotNull(info.getString("head_address"))){
						info.put("head_address", FileUpload.findLocalFileUrl(info.getString("head_address")));
					}else{
						info.put("head_address","");
					}
					PageData sercer = new PageData();//工作分类条件 
					sercer.put("parent_id", info.get("server_category_id"));
					List<PageData> serverList = indexService.findJobList(sercer);
					//评论
					List<PageData> listImage=new ArrayList<PageData>();//图片评论
					
					List<PageData> newList=new ArrayList<PageData>();//
					PageData po=new PageData();
					po.put("id", liftId);
					
					PageData listpj = indexService.findPjInfo(po);
					if(listpj!=null&&listpj.size()!=0){
						PageData d = new PageData();
						d.put("assessment_id", listpj.get("pId"));
						listImage = appMineService.findImageList(d);// 查询图片
						for (PageData pl : listImage) {
							if (Verify.verifyIsNotNull(pl.getString("image_url"))) {
								pl.put("image_url", FileUpload.findLocalFileUrl(pl.getString("image_url")));
							} else {
								pl.put("image_url", "");
							}
						}
						listpj.put("listImage", listImage);
					}
					
					for(PageData nn:serverList){
						PageData ii = new PageData();
						ii.put("server_category_id", nn.get("id"));
						List<PageData> remarkList = indexService.findJobRemarkList(ii);
						for(PageData uu:remarkList){
							String remark = "";
							if(uu.getString("pay_type").equals("1")){
							 remark = uu.getString("remark")+"      收费标准："+uu.get("starting_price")+"元起  超出起始价 每"+uu.get("charge_mode_time")+uu.getString("charge_mode_unit")+"收费"+uu.get("price")+"元";
							}else {
								remark = uu.getString("remark")+"      收费标准："+uu.get("starting_price")+"元起";
							}
								uu.put("remark",remark);
						}
						nn.put("remarkList", remarkList);
					}
					int coll=0;
					if (Verify.verifyIsNotNull(session_id)) {
						PageData p = new PageData();
						p.put("session_id", session_id);
						//获取当前用户
						PageData userPd = appOrderService.findSessionInfo(p);
						if(Verify.verifyIsNotNull(userPd)){
							
							PageData pd=new PageData();
							pd.put("server_info_id", liftId);
							pd.put("user_id", userPd.get("id"));
							int num=indexService.findCollectNum(pd);
							if(num>0){
								coll=1;
							}else{
								coll=0;
							}
						}else{
							json.setMsg("session_id失效，请重新登录");
							json.setCode("-6");
							json.setSuccess(false);
						}
					} 
					
					Map<String, Object> appMap = new HashMap<String, Object>();
					appMap.put("info",info);
					appMap.put("assessment", listpj);//第一条评价
					appMap.put("serverList",serverList);//工作分类
					appMap.put("coll",coll);//是否收藏
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
	  * @Title: findLiftList
	  * @Description: 点击家政服务查询的服务分类信息
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
		try {
					List<PageData>  allCategory = new ArrayList<PageData>();
					//根服务（级别为0）
					List<PageData> listRoot = new ArrayList<PageData>();//1级
					List<PageData> lists=new ArrayList<PageData>();//2级
					List<PageData> list=new ArrayList<PageData>();//3级
					PageData pp=new PageData();//0级
					PageData pas=new PageData();//2级
					pp.put("level", 0);
					pp.put("parent_id", "0");
					//将全部加如一级菜单中
					PageData allpd = new PageData();
					allpd.put("id","-1");
					allpd.put("name","全部");
					allpd.put("parent_id","-1");
					allpd.put("level","-1");
					allpd.put("url","");
					allpd.put("remark","全部");
					allpd.put("type","type");
					listRoot.add(allpd);
					List<PageData> listRootTemp=indexService.findRootList(pp);
					for (PageData dd :listRootTemp){
						
						if(Verify.verifyIsNotNull(dd.getString("image"))){
							dd.put("image", FileUpload.findLocalFileUrl(dd.getString("image")));
						}else{
							dd.put("image","");
						}
						
						listRoot.add(dd);
					}
					for (PageData rpd:listRoot) {
						PageData pa=new PageData();//1级
						pa.put("parent_id", rpd.get("id"));
//						pa.put("level", "1");
						lists=indexService.findRootList(pa);
						
						for(PageData pl:lists){
							if(Verify.verifyIsNotNull(pl.getString("image"))){
								pl.put("image", FileUpload.findLocalFileUrl(pl.getString("image")));
							}else{
								pl.put("image","");
							}
						}
						rpd.put("categoryList", lists);
						allCategory.add(rpd);
					}
					Map<String, Object> appMap = new HashMap<String, Object>();
					appMap.put("root",allCategory);
					
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
	  * @Title: findPjList
	  * @Description: 评价详情列表
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findPjList.html", method = RequestMethod.POST)
	public void findPjList(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String liftId=bodyMap.get("id").toString();
		String index=bodyMap.get("index").toString();
		try {
					//评论
					List<PageData> listImage=new ArrayList<PageData>();//图片评论
					List<PageData> listpj=new ArrayList<PageData>();
					List<PageData> newList=new ArrayList<PageData>();//
					PageData pd=new PageData();
					pd.put("id", liftId);
					pd.put("index", index);
					this.getPage(pd);
					listpj = indexService.findPjList(pd);
					for (PageData pj : listpj) {
						PageData d=new PageData();
						d.put("assessment_id", pj.get("pId"));
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
					int flag=0;
					int num=indexService.findPjListNum(pd);
					if(num>0){
						if(num%parameterUtil.app_showCount>0){//取余 当余数大于0时，总页数+1 当余数 = 0时 不加
							flag = num/parameterUtil.app_showCount+1;
						}else {
							flag = num/parameterUtil.app_showCount;
						}
					}
					Map<String, Object> appMap = new HashMap<String, Object>();
					appMap.put("list", newList);//评价
					appMap.put("flag", flag);
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
	  * @Title: findAreaList
	  * @Description: 地区
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findAreaList.html", method = RequestMethod.POST)
	public void findAreaList(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		Json json = new Json();
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
//		String oneAreaId=bodyMap.get("oneAreaId").toString();//城市code
		String name=bodyMap.get("name").toString();//地区名称
		try {
				List<PageData> listRootTemp = new ArrayList<PageData>();
				PageData pd = new PageData();
				pd.put("name", name);
				PageData areaName = indexService.findAreaInfo(pd);
				PageData pp = new PageData();
				pp.put("parent_id", areaName.get("id"));
				listRootTemp = indexService.findAreaList(pp);
				
				// 将全部加如一级菜单中
				PageData allpd = new PageData();
				allpd.put("type", "city");
				allpd.put("name", "全部");
				allpd.put("areaId", areaName.get("id"));
				listRootTemp.add(allpd);
				Map<String, Object> appMap = new HashMap<String, Object>();
				appMap.put("root", listRootTemp);
	
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
	
	
	
	
	
	

}
