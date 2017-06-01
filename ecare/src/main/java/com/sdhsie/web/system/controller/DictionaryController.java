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
import com.sdhsie.web.system.service.DictionaryService;

@Controller
@RequestMapping(value="/system/dictionary")
public class DictionaryController extends BaseController{
	@Autowired
	private DictionaryService dictionaryService;
	private PageData pd;
	
	
	/**
	 * 
	  * @Title: findList
	  * @Description: 查询信息列表
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
		mv.clear();
		pd = new PageData(request);
		mv = getBaseMv(session, pd);
		
		pd.put("remove_logo", "N");
		page.setPd(pd);
		List<PageData> dictionarylist = dictionaryService.findListPage(page);
		//查询垃圾箱数量
		int num = dictionaryService.findDelNum();
		
		pd.put("num", num);
		mv.addObject("dictionarylist",dictionarylist);
		mv.addObject("pd",pd);
		mv.addObject("power",getRoleButton(session));
		mv.setViewName("system/dictionary/dictionary_list");
		return mv;
	}
	

	/**
	 * 
	  * @Title: findList
	  * @Description: TODO
	  * @param @param request
	  * @param @param response
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/JsonfindList")
	public void findList(HttpServletRequest request,HttpSession session,HttpServletResponse response)throws Exception{
		pd = new PageData(request);
		mv = getBaseMv(session, pd);
		pd.put("remove_logo", "N");//删除标识---未删除
		List<PageData> dictionarylist = dictionaryService.findDicList(pd);
		this.writeJson(response, dictionarylist);
	}
	
	/**
	 * 
	  * @Title: toadd
	  * @Description: 跳转到添加页面
	  * @param @param session
	  * @param @param request
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value="/toadd")
	public ModelAndView toadd(HttpSession session,HttpServletRequest request)throws Exception{
		mv.clear();
		pd = new PageData(request);
		mv = getBaseMv(session,pd);
		
		pd.put("flag", "toadd");
		mv.addObject("pd", pd);
		mv.setViewName("system/dictionary/dictionary_add");
		return mv;
	}
	/**
	 * 
	  * @Title: save
	  * @Description: 添加信息
	  * @param @param request
	  * @param @param response
	  * @param @param session
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/save")
	public ModelAndView save(HttpServletRequest request,HttpServletResponse response,HttpSession session)throws Exception{
		pd = new PageData(request);
		PageData user = (PageData)session.getAttribute(Const.SESSION_USER);
		pd.put("create_user", user.get("id"));
		pd.put("remove_logo", "N");//未删除标识
		pd.put("create_time",DateTimeUtil.getDateTime());
		pd.put("update_time", DateTimeUtil.getDateTime());
		dictionaryService.save(pd);
		pd.put("flag", "add");
		mv.addObject("pd", pd);
		mv.setViewName("system/dictionary/dictionary_add");
		return mv;
	
	}
	
	/**
	 * 
	  * @Title: toedit
	  * @Description: 去编辑界面
	  * @param @param session
	  * @param @param request
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value="/toedit")
	public ModelAndView toedit(HttpSession session,HttpServletRequest request)throws Exception{
		mv.clear();
		mv.clear();
		pd = new PageData(request);
		PageData p = dictionaryService.findInfo(pd);
		mv.addObject("p", p);
		mv.addObject("pd", pd);
		mv.setViewName("system/dictionary/dictionary_edit");
		return mv;
	}
	
	/**
	 * 
	  * @Title: update
	  * @Description: 修改信息
	  * @param @param request
	  * @param @param response
	  * @param @param session
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/update")
	public ModelAndView update(HttpServletRequest request,HttpServletResponse response,HttpSession session)throws Exception{
		pd = new PageData(request);
		pd.put("update_time", DateTimeUtil.getDateTime());
		this.dictionaryService.update(pd);
		pd.put("flag", "edit");
		mv.addObject("pd", pd);
		mv.setViewName("system/dictionary/dictionary_edit");
		return mv;
	
	}
	
	/**
	 * 
	  * @Title: findinfo
	  * @Description: 查看详情
	  * @param @param request
	  * @param @param response
	  * @param @param session
	  * @param @param page
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findinfo")
	public ModelAndView findinfo(HttpServletRequest request,HttpServletResponse response,HttpSession session,Page page) throws Exception{
		mv.clear();
		pd = new PageData(request);
		PageData p = dictionaryService.findInfo(pd);
		mv.addObject("p", p);
		mv.addObject("pd", pd);
		mv.setViewName("system/dictionary/dictionary_info");
		return mv;
	}
	/**
	 * 
	  * @Title: delete
	  * @Description: 删除--删除到垃圾箱
	  * @param @param request
	  * @param @param out    设定文件
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
			List<PageData> list = new ArrayList<PageData>();
			getCascadeInfo(p,list);//递归查找所有的子节点
			for (PageData pageData : list) {
				pageData.put("remove_logo", "Y");
				pageData.put("update_time", DateTimeUtil.getDateTime());
				dictionaryService.update(pageData);
			}
		}
		
		this.writeJson(response, true);
		
	}
	/**
	 * 
	  * @Title: getCascadeInfo
	  * @Description: 递归算法-查找一个节点的子节点及其孙子节点
	  * @param @param data   参照
	  * @param @param list     家族所有成员存在的位置
	  * @return void    返回类型
	  * @throws
	 */
	private void getCascadeInfo(PageData data,List<PageData> list){
		list.add(data);
		PageData pd=new PageData();
		pd.put("parent_id", data.get("id"));
		List<PageData> children=dictionaryService.findDicList(pd);
		if(null!=children&&children.size()>0){
			for(PageData child:children){
				getCascadeInfo(child, list);
			}
		}
	}
	
	/**
	 * 
	  * @Title: findTrash
	  * @Description: 垃圾箱列表
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
		List<PageData> dictionaryList = dictionaryService.findListPage(page);
		mv.addObject("pd", pd);	
		mv.addObject("dictionaryList", dictionaryList);
		mv.setViewName("system/dictionary/dictionary_trash");
		return mv;
	}
	/**
	 * 
	  * @Title: dustbinfindList
	  * @Description: 垃圾箱的树列表
	  * @param @param request
	  * @param @param session
	  * @param @param page
	  * @param @param response
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/dustbinfindList")
	public void dustbintreefindList(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
		mv = getBaseMv(session, pd);
		pd.put("remove_logo", "Y");//删除标识---未删除
		List<PageData> dictionaryList = dictionaryService.findDicList(pd);
		
		this.writeJson(response, dictionaryList);
	}
	
	
	
	/**
	 * 
	  * @Title: clearDictionary
	  * @Description: 彻底删除
	  * @param @param request
	  * @param @param response
	  * @param @param out
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/clearDictionary")
	public void clearDictionary(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
	
		dictionaryService.clearDictionary(pd);
		
		this.writeJson(response, true);
		
	}
	/**
	 * 
	  * @Title: revertDictionary
	  * @Description: 还原删除
	  * @param @param request
	  * @param @param response
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/revertDictionary")
	public void revertDictionary(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
	
		dictionaryService.revertDictionary(pd);
		
		this.writeJson(response, true);
		
	}
	
	
	
	
	@RequestMapping(value = "/findDictionaryJson")
	public void findDictionaryJson(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
		pd.put("remove_logo", "N");
		List<PageData> list = dictionaryService.findDicList(pd);
		this.writeJson(response, list);
	}
	
	
	
	

}
