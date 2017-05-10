package com.sdhsie.web.system.controller;

import java.util.ArrayList;
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
import com.sdhsie.base.util.Page;
import com.sdhsie.base.util.PageData;
import com.sdhsie.base.util.parameterUtil;
import com.sdhsie.web.system.service.DictionaryService;
import com.sdhsie.web.system.service.OrganizeService;

@Controller
@RequestMapping(value = "/system/organize")
public class OrganizeController extends BaseController {
	@Autowired
	private OrganizeService organizeService;
	private PageData pd;

	/**
	 * 
	  * @Title: findList
	  * @Description: 列表查询
	  * @param @param request
	  * @param @param session
	  * @param @param page
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/findList")
	public ModelAndView findList(HttpServletRequest request,HttpSession session,Page page) throws Exception {
		pd = new PageData(request);
		mv = getBaseMv(session, pd);
		pd.put("remove_logo", "N");//删除标识---未删除
		page.setPd(pd);
		List<PageData> organizeList = organizeService.findListPage(page);
		
		//查询垃圾箱数量
		int num = organizeService.findDelNum();
		
		pd.put("num", num);
		mv.addObject("pd", pd);	
		mv.addObject("organizeList", organizeList);
		mv.addObject("power",getRoleButton(session));
		mv.setViewName("system/organize/organize_list");
		return mv;
	}
	
	/**
	 * 
	  * @Title: treefindList
	  * @Description: 查询树形列表
	  * @param @param request
	  * @param @param response
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/treefindList")
	public void treefindList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
		
		pd.put("remove_logo", "N");//删除标识---未删除
		List<PageData> organizeList = organizeService.findList(pd);
		
		this.writeJson(response, organizeList);
	}
	
	/**
	 * 
	  * @Title: findInfo
	  * @Description: 查看详情
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
		PageData p = organizeService.findInfo(pd);
		mv.addObject("p", p);
		mv.addObject("pd", pd);
		mv.setViewName("system/organize/organize_info");
		return mv;
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
	public ModelAndView toadd(HttpServletRequest request)throws Exception{
		mv.clear();
		pd = new PageData(request);
		mv.addObject("pd", pd);
		mv.setViewName("system/organize/organize_add");
		return mv;
	}
	
	/**
	 * 
	  * @Title: save
	  * @Description: 保存
	  * @param @param request
	  * @param @param session
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value="/save")
	public ModelAndView save(HttpServletRequest request,HttpSession session) throws Exception{
		mv.clear();
		pd = new PageData(request);
		
		String level = pd.get("level").toString();
		if(level.equals("0")){
			pd.put("type", parameterUtil.ministry);
		}else if(level.equals("1")){
			pd.put("type", parameterUtil.branchoffice);
		}else if(level.equals("2")){
			pd.put("type", parameterUtil.place);
		}
		pd.put("remove_logo", "N");//未删除
		pd.put("create_time", DateTimeUtil.getDateTime());
		pd.put("update_time", DateTimeUtil.getDateTime());
		PageData user = (PageData)session.getAttribute(Const.SESSION_USER);
		pd.put("create_user", user.get("id"));
		this.organizeService.save(pd);
		pd.put("label", "add");
		mv.addObject("pd", pd);
		
		mv.setViewName("system/organize/organize_add");
		
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
		PageData p = organizeService.findInfo(pd);
		mv.addObject("p", p);
		mv.addObject("pd", pd);
		mv.setViewName("system/organize/organize_edit");
		
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
		this.organizeService.update(pd);
		pd.put("label", "edit");
		mv.addObject("pd", pd);
		
		mv.setViewName("system/organize/organize_edit");
		
		return mv;
	}
	
	/**
	 * 
	  * @Title: del
	  * @Description: 批量删除
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
			PageData p = new PageData();
			p.put("id", id);
			List<PageData> list = new ArrayList<PageData>();
			getCascadeInfo(p,list);//递归查找所有的子节点
			for (PageData pageData : list) {
				pageData.put("remove_logo", "Y");
				pageData.put("update_time", DateTimeUtil.getDateTime());
				organizeService.update(pageData);
			}
		}
		
		this.writeJson(response, true);
	
	}
	
	/**
	 * 
	  * @Title: getCascadeInfo
	  * @Description: 递归算法-查找一个节点的子节点及其孙子节点
	  * @param @param data	    参照
	  * @param @param list    家族所有成员存在的位置
	  * @return void    返回类型
	  * @throws
	 */
	private void getCascadeInfo(PageData data,List<PageData> list){
		list.add(data);
		PageData pd=new PageData();
		pd.put("parent_id", data.get("id"));
		List<PageData> children=organizeService.findList(pd);
		if(null!=children&&children.size()>0){
			for(PageData child:children){
				getCascadeInfo(child, list);
			}
		}
	}
	
	/**
	 * 
	  * @Title: findDustbin
	  * @Description: 得到角色垃圾箱列表
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
		mv = getBaseMv(session, pd);
		pd.put("remove_logo", "Y");//删除标识---已删除
		page.setPd(pd);
		List<PageData> organizeList = organizeService.findListPage(page);
		mv.addObject("pd", pd);	
		mv.addObject("organizeList", organizeList);
		mv.setViewName("system/organize/organize_trash");
		return mv;
	}
	
	/**
	 * 
	  * @Title: dustbintreefindList
	  * @Description: 得到角色垃圾箱树形列表
	  * @param @param request
	  * @param @param response
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/dustbintreefindList")
	public void dustbintreefindList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
		
		pd.put("remove_logo", "Y");//删除标识---未删除
		List<PageData> organizeList = organizeService.findList(pd);
		
		this.writeJson(response, organizeList);
	}
	
	/**
	 * 
	  * @Title: clearUser
	  * @Description: 清空彻底删除
	  * @param @param request
	  * @param @param session
	  * @param @param response
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/clearOrganize")
	public void clearOrganize(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
	
		organizeService.clearOrganize(pd);
		
		this.writeJson(response, true);
		
	}
	
	
	/**
	 * 
	  * @Title: revertUser
	  * @Description: 还原删除信息
	  * @param @param request
	  * @param @param session
	  * @param @param response
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/revertOrganize")
	public void revertOrganize(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
	
		organizeService.revertOrganize(pd);
		
		this.writeJson(response, true);
		
	}
	
	
	
	
	@RequestMapping(value = "/findOrganizeJson")
	public void findOrganizeJson(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
		pd.put("remove_logo", "N");//删除标识---未删除
		List<PageData> organizeList = organizeService.findList(pd);
		this.writeJson(response, organizeList);
	}
	
	
}
