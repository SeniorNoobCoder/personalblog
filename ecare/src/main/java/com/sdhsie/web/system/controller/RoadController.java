package com.sdhsie.web.system.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import net.sf.jxls.transformer.XLSTransformer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sdhsie.base.controller.BaseController;
import com.sdhsie.base.util.Const;
import com.sdhsie.base.util.DateTimeUtil;
import com.sdhsie.base.util.FileUtil;
import com.sdhsie.base.util.Page;
import com.sdhsie.base.util.PageData;
import com.sdhsie.base.util.parameterUtil;
import com.sdhsie.web.system.service.DictionaryService;
import com.sdhsie.web.system.service.OrganizeService;
import com.sdhsie.web.system.service.RoadService;


@Controller
@RequestMapping(value="/system/road")
public class RoadController extends BaseController{
	
	@Autowired
	private RoadService roadService;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private OrganizeService organizeService;
	private PageData pd;
	
	/**
	 * 
	  * @Title: findListPage
	  * @Description: 查询信息列表
	  * @param @param request
	  * @param @param session
	  * @param @param response
	  * @param @param page
	  * @param @return    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/findList")
	public ModelAndView findListPage(HttpServletRequest request,HttpSession session,HttpServletResponse response,Page page){
		mv.clear();
		pd = new PageData(request);
		mv = getBaseMv(session, pd);
		pd.put("remove_logo", "N");
		page.setPd(pd);
		List<PageData> roadList = roadService.findListPage(page);
		//查询垃圾箱数量
		int num = roadService.findDelNum();
		
		//单位
		pd.put("parent_id", parameterUtil.org_branch);
		pd.put("remove_logo", "N");
		List<PageData> branchList = organizeService.findList(pd);

		List<PageData> ls = new ArrayList<PageData>();
		if(branchList!=null&&branchList.size()>0){
			for (PageData pageData : branchList) {
				pd.put("parent_id", pageData.get("id"));
				List<PageData> spotList = organizeService.findList(pd);
				if(spotList!=null&&spotList.size()>0){
					for (PageData spotPd : spotList) {
						pd.put("organize_id", spotPd.get("id"));
						List<PageData> roadist= roadService.findRoadList(pd);
						//处理道路
						spotPd.put("roadist", roadist);
					}
				}
				ls.addAll(spotList);
				mv.addObject("spotList", spotList);
			}
		}
		/*PageData p=new PageData();
		for (PageData pa : branchList) {
			p.put("organize_id", pa.get("id"));
			List<PageData> spotList = organizeService.findList(pd);
			ls.addAll(spotList);
		}
		mv.addObject("spotList", ls);
		List<PageData> roads= roadService.findRoadList(pd);
		*/
		
		pd.put("num", num);
		mv.addObject("pd",pd);
		mv.addObject("roadList",roadList);
		mv.addObject("power",getRoleButton(session));
		mv.addObject("branchList", branchList);
		
		//mv.addObject("roads", roads);
		mv.setViewName("system/road/road_list");
		return mv;
	}
	/**
	 * 
	  * @Title: treefindList
	  * @Description: 树列表信息
	  * @param @param request
	  * @param @param response
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/treefindList")
	public void findtreeList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
		
		pd.put("remove_logo", "N");//删除标识---未删除
		List<PageData> roadList = new ArrayList<PageData>();
		List<PageData> list = new ArrayList<PageData>();
		list = getRoadInfo(pd,list);
		for (PageData pageData : list) {
			List<PageData> road = roadService.findSonTree(pageData);
			roadList.addAll(road);
		}
		this.writeJson(response, roadList);
	}
	
	
	/**
	 * 
	  * @Title: getRoadInfo
	  * @Description: 递归
	  * @param @param pd
	  * @param @param list
	  * @param @return    设定文件
	  * @return List<PageData>    返回类型
	  * @throws
	 */
	private List<PageData> getRoadInfo(PageData pd ,List<PageData> list){
		List<PageData> orgs = roadService.findTree(pd);
		if(orgs!=null&&orgs.size()>0){
			for (PageData pdg : orgs) {
				pdg.put("remove_logo", "N");
				getRoadInfo(pdg,list);
			}
		}else{
			list.add(pd);
		}
		return list ;
	}
	/**
	 * 
	  * @Title: toadd
	  * @Description: 去新增页面
	  * @param @param request
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value="/toadd")
	public ModelAndView toadd(HttpServletRequest request,Page page)throws Exception{
		mv.clear();
		pd = new PageData(request);
		
		List<PageData> dictionaryList = dictionaryService.findList(pd);
		mv.addObject("dictionaryList", dictionaryList);
		
		//获取资产
		pd.put("parent_id",parameterUtil.assets_type);
		List<PageData>  assetsList = dictionaryService.findList(pd);
		mv.addObject("assetsList",assetsList);
		//道路类型
		pd.put("parent_id",parameterUtil.road_type);
		List<PageData> roadList = dictionaryService.findList(pd);
		mv.addObject("roadList",roadList);
		
		pd.put("label", "toadd");
		mv.addObject("pd", pd);
		mv.setViewName("system/road/road_add");
		
		return mv;
	}
	/**
	 * 
	  * @Title: save
	  * @Description: 保存
	  * @param @param request
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value="/save")
	public ModelAndView save(HttpServletRequest request,HttpSession session) throws Exception{
		mv.clear();
		pd = new PageData(request);
		
		pd.put("remove_logo", "N");//未删除
		pd.put("create_time", DateTimeUtil.getDateTime());
		pd.put("update_time", DateTimeUtil.getDateTime());
		PageData user = (PageData)session.getAttribute(Const.SESSION_USER);
		pd.put("create_user", user.get("id"));
		this.roadService.save(pd);
		pd.put("label", "add");
		mv.addObject("pd", pd);
		
		mv.setViewName("system/road/road_add");
		
		return mv;
	}
	/**
	 * 
	  * @Title: findInfo
	  * @Description: 详情信息
	  * @param @param request
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findInfo")
	public ModelAndView findInfo(HttpServletRequest request)throws Exception{
		mv.clear();
		pd = new PageData(request);
		PageData p=this.roadService.findInfo(pd);
		//获取资产
		pd.put("parent_id",parameterUtil.assets_type);
		List<PageData>  assetsList = dictionaryService.findList(pd);
		mv.addObject("assetsList",assetsList);
		//道路类型
		pd.put("parent_id",parameterUtil.road_type);
		List<PageData> roadList = dictionaryService.findList(pd);
		mv.addObject("roadList",roadList);
		
		mv.addObject("p", p);
		mv.addObject("pd", pd);
		
		mv.setViewName("system/road/road_info");
		
		return mv;
	}
	/**
	 * 
	  * @Title: toedit
	  * @Description: 去修改页面
	  * @param @param request
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value="/toedit")
	public ModelAndView toedit(HttpServletRequest request)throws Exception{
		mv.clear();
		pd = new PageData(request);
		PageData p=this.roadService.findInfo(pd);
		//获取资产
		pd.put("parent_id",parameterUtil.assets_type);
		List<PageData>  assetsList = dictionaryService.findList(pd);
		mv.addObject("assetsList",assetsList);
		
		//道路类型
		pd.put("parent_id",parameterUtil.road_type);
		List<PageData> roadList = dictionaryService.findList(pd);
		mv.addObject("roadList",roadList);
		
		mv.addObject("p", p);
		mv.addObject("pd", pd);
		
		mv.setViewName("system/road/road_edit");
		
		return mv;
	}
	/**
	 * 
	  * @Title: update
	  * @Description: 修改
	  * @param @param request
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value="/update")
	public ModelAndView update(HttpServletRequest request) throws Exception{
		mv.clear();
		pd = new PageData(request);
		
		pd.put("update_time", DateTimeUtil.getDateTime());
		this.roadService.update(pd);
		pd.put("label", "edit");
		mv.addObject("pd", pd);
		
		mv.setViewName("system/road/road_edit");
		
		return mv;
	}
	/**
	 * 
	  * @Title: delete
	  * @Description: 删除-删除到垃圾箱
	  * @param @param request
	  * @param @param response
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/delete")
	public void delete(HttpServletRequest request,HttpServletResponse response)throws Exception{
		pd = new PageData(request);
		String[] ids = pd.getString("ids").split(",");
		for (String id : ids) {
			PageData p = new PageData();
			p.put("id", id);
			p.put("remove_logo","Y");//删除
			p.put("update_time", DateTimeUtil.getDateTime());
			roadService.update(p);
			pd.put("organize_id", id);
			List<PageData> roadList =  roadService.findList(pd);
			if(roadList.size()>0){
				for(PageData pageData:roadList){
					PageData pp = new PageData();
					pp.put("id", pageData.get("id"));
					pp.put("remove_logo", "Y");
					pp.put("update_time", DateTimeUtil.getDateTime());
					roadService.update(pp);
				}
			}
		}
		
		this.writeJson(response, true);
		
		
	}
	/**
	 * 
	  * @Title: dustbinfindList
	  * @Description: 垃圾箱树列表
	  * @param @param request
	  * @param @param response
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/dustbinfindList")
	public void dustbinfindList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
		
		pd.put("remove_logo", "Y");//删除标识---未删除
		List<PageData> roadList = new ArrayList<PageData>();
		List<PageData> list = new ArrayList<PageData>();
		list = getRoadInfo(pd,list);
		for (PageData pageData : list) {
			pageData.put("remove_logo", "Y");
			List<PageData> road = roadService.findSonTree(pageData);
			roadList.addAll(road);
		}
		this.writeJson(response, roadList);
	}
	

	
	/**
	 * 
	  * @Title: findTrash
	  * @Description: 查询垃圾箱列表
	  * @param @param request
	  * @param @param session
	  * @param @param page
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/findTrash")
	public ModelAndView findTrash(HttpServletRequest request,HttpSession session,Page page) throws Exception {
		pd = new PageData(request);
		
		pd.put("remove_logo", "Y");
		page.setPd(pd);
		List<PageData> roadList = roadService.findListPage(page);
		mv.addObject("pd", pd);	
		mv.addObject("roadList", roadList);
		mv.setViewName("system/road/road_trash");
		return mv;
	}
	/**
	 * 
	  * @Title: clearRoad
	  * @Description: 彻底删除
	  * @param @param request
	  * @param @param session
	  * @param @param response
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/clearRoad")
	public void clearRoad(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
	
		roadService.clearRoad(pd);
		
		this.writeJson(response, true);
		
	}
	/**
	 * 
	  * @Title: revertRoad
	  * @Description: 还原删除
	  * @param @param request
	  * @param @param session
	  * @param @param response
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/revertRoad")
	public void revertRoad(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
	
		roadService.revertRoad(pd);
		
		this.writeJson(response, true);
		
	}
	
	
	@RequestMapping(value="/exportRoad")
	public void exportRoad(HttpSession session,HttpServletResponse response,HttpServletRequest request)throws Exception{
		pd = new PageData(request);

		Map<String, Object> beans = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		PageData user = (PageData)session.getAttribute(Const.SESSION_USER);
		map.put("create_user", user.get("id"));
		map.put("create_time", DateTimeUtil.getDateTime());
		beans.put("record_jbxx", map);
		
		//单位
		pd.put("parent_id", parameterUtil.org_branch);
		pd.put("remove_logo", "N");
		List<PageData> branchList = organizeService.findList(pd);
		
		List<PageData> ls = new ArrayList<PageData>();
		
		if(branchList!=null && branchList.size()>0){
			for (PageData pageData : branchList) {
				pd.put("parent_id", pageData.get("id"));
				List<PageData> spotList = organizeService.findList(pd);
				if(spotList!=null&&spotList.size()>0){
					for (PageData spotPd : spotList) {
						pd.put("organize_id", spotPd.get("id"));
						List<PageData> roadList= roadService.findRoadList(pd);
						//处理道路
						spotPd.put("roadList", roadList);
					}
				}
				//处理所
				ls.addAll(spotList);
				pageData.put("spotList", spotList);
			}
		}
		//处理分公司
		beans.put("branchList", branchList);
		
		String tempPath = session.getServletContext().getRealPath("/")+"/excelExport/system/road.xls";
		String toFile = session.getServletContext().getRealPath("/")+"/excelTemp/upload/road.xls";
		
		XLSTransformer transformer = new XLSTransformer();
		transformer.transformXLS(tempPath, beans, toFile);
		FileUtil.downFile(response,toFile,"各单位里程统计表.xls");
		File file = new File(toFile);
		file.delete();
		file.deleteOnExit();
	}
	/*	}
	}*/
}