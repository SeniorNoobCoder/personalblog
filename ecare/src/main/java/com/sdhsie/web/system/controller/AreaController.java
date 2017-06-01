package com.sdhsie.web.system.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sdhsie.base.controller.BaseController;
import com.sdhsie.base.util.Const;
import com.sdhsie.base.util.DateTimeUtil;
import com.sdhsie.base.util.GuidUtil;
import com.sdhsie.base.util.Page;
import com.sdhsie.base.util.PageData;
import com.sdhsie.web.system.service.AreaService;

@Controller
@RequestMapping(value = "/system/area")
public class AreaController extends BaseController {
	@Autowired
	private AreaService areaService;
	private PageData pd;


	/**
	 * 
	  * @Title: findList
	  * @Description: 查询信息列表
	  * @param @param request
	  * @param @param response
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/findList")
	public ModelAndView findList(HttpServletRequest request,HttpSession session,Page page) throws Exception {
		pd = new PageData(request);
		mv = getBaseMv(session, pd);
	
		pd.put("remove_logo", "N");
		page.setPd(pd);
		List<PageData> areaList = areaService.findListPage(page);
		
		//查询垃圾箱数量
		int num = areaService.findDelNum();
		
		pd.put("num", num);
		mv.addObject("pd", pd);	
		mv.addObject("areaList", areaList);
		mv.addObject("power",getRoleButton(session));
		mv.setViewName("system/area/area_list");
		return mv;
	}
	
	
	
	@RequestMapping(value = "/findListZtree")
	public void findListZtree(HttpServletRequest request,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
	
		List<PageData> areaList = areaService.findList(pd);
		
		this.writeJson(response, areaList);
	}
	
	
	/**
	 * 
	  * @Title: toadd
	  * @Description: 跳转到添加页面
	  * @param @param request
	  * @param @param session
	  * @param @param page
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/toadd")
	public ModelAndView toadd(HttpServletRequest request,HttpSession session) throws Exception {
		pd = new PageData(request);
	
		mv.addObject("pd", pd);	
		mv.setViewName("system/area/area_add");
		return mv;
	}
	
	
	/**
	 * 
	  * @Title: toedit
	  * @Description: 跳转到编辑页面
	  * @param @param request
	  * @param @param session
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/toedit")
	public ModelAndView toedit(HttpServletRequest request,HttpSession session) throws Exception {
		pd = new PageData(request);
	
		PageData p = areaService.findInfo(pd);
		
		mv.addObject("p", p);	
		mv.addObject("pd", pd);	
		mv.setViewName("system/area/area_edit");
		return mv;
	}
	
	
	/**
	 * 
	  * @Title: save
	  * @Description: 保存信息
	  * @param @param request
	  * @param @param session
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save(HttpServletRequest request,HttpSession session) throws Exception {
		pd = new PageData(request);
	
		PageData user = (PageData)session.getAttribute(Const.SESSION_USER);
		pd.put("create_user", user.get("id"));
		pd.put("type", pd.getString("parent_type")+"_"+GuidUtil.getSmailGuid());
		pd.put("remove_logo", "N");
		pd.put("create_time", DateTimeUtil.getDateTime());
		pd.put("update_time", DateTimeUtil.getDateTime());
		areaService.save(pd);
		
		pd.put("label", "b");
		mv.addObject("pd", pd);	
		mv.setViewName("system/area/area_add");
		return mv;
	}
	
	
	/**
	 * 
	  * @Title: update
	  * @Description: 更新信息
	  * @param @param request
	  * @param @param session
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/update")
	public ModelAndView update(HttpServletRequest request,HttpSession session) throws Exception {
		pd = new PageData(request);
	
		pd.put("update_time", DateTimeUtil.getDateTimeStr());
		areaService.update(pd);
		
		pd.put("label", "b");
		mv.addObject("pd", pd);	
		mv.setViewName("system/area/area_edit");
		return mv;
	}
	
	/**
	 * 
	  * @Title: del
	  * @Description: 删除信息
	  * @param @param request
	  * @param @param session
	  * @param @param response
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/del")
	public void del(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
	
		String[] ids = pd.getString("ids").split(",");
		for (String id : ids) {
			pd.put("id", id);
			PageData pp = areaService.findInfo(pd);
			
			PageData p = new PageData();
			p.put("type", pp.getString("type"));
			p.put("remove_logo", "Y");
			p.put("update_time", DateTimeUtil.getDateTime());
			areaService.updateType(p);
		}
		this.writeJson(response, true);
		
	}
	
	
	/**
	 * 
	  * @Title: findInfo
	  * @Description: 查询信息详情
	  * @param @param request
	  * @param @param session
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/findInfo")
	public ModelAndView findInfo(HttpServletRequest request,HttpSession session) throws Exception {
		pd = new PageData(request);
	
		PageData p = areaService.findInfo(pd);
		
		mv.addObject("p", p);	
		mv.addObject("pd", pd);	
		mv.setViewName("system/area/area_info");
		return mv;
	}
	
	
	/**
	 * 
	  * @Title: findTrash
	  * @Description: 垃圾箱处理
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
		List<PageData> list = areaService.findTrashPage(page);
		
		mv.addObject("list",list);	
		mv.addObject("pd", pd);	
		mv.setViewName("system/area/area_trash");
		return mv;
	}
	
	
	
	/**
	 * 
	  * @Title: revertTrash
	  * @Description: 清空垃圾箱
	  * @param @param request
	  * @param @param session
	  * @param @param response
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/clearTrash")
	public void clearTrash(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
	
		areaService.clearTrash(pd);
		
		this.writeJson(response, true);
		
	}
	
	
	/**
	 * 
	  * @Title: revertTrash
	  * @Description: 还原垃圾箱
	  * @param @param request
	  * @param @param session
	  * @param @param response
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/revertTrash")
	public void revertTrash(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
	
		areaService.revertTrash(pd);
		
		this.writeJson(response, true);
		
	}
	
	
	/**
	 * 
	  * @Title: updateStatus
	  * @Description: 更新状态
	  * @param @param request
	  * @param @param session
	  * @param @param response
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/updateStatus")
	public void updateStatus(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
	
		pd.put("update_time", DateTimeUtil.getDateTime());
		areaService.update(pd);
		
		this.writeJson(response, true);
		
	}
	
	
}
