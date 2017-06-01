package com.sdhsie.web.common.controller;

import java.io.IOException;
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
import com.sdhsie.base.util.Verify;
import com.sdhsie.web.common.service.CommonMenuService;

/**
 * 
  * @ClassName: MenuController
  * @Description: 菜单管理
  * @author dyilo
  * @date 2016-6-30 下午01:24:46
  *
 */
@Controller
@RequestMapping(value = "/common/commonMenu")
public class CommonMenuController extends BaseController{

	@Autowired
	private CommonMenuService commonMenuService;
	private PageData pd;
	
	/**
	 * 
	  * @Title: findListPage
	  * @Description: 获取列表
	  * @param @param request
	  * @param @param session
	  * @param @param response
	  * @param @param page
	  * @param @return    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/findListPage")
	public ModelAndView findListPage(HttpServletRequest request,HttpSession session,HttpServletResponse response,Page page){
		mv.clear();
		pd = new PageData(request);
		mv = getBaseMv(session, pd);
		pd.put("remove_logo", "N");
		page.setPd(pd);
		List<PageData> list = commonMenuService.findListPage(page);
		mv.addObject("list",list);
		mv.addObject("pd",pd);
		mv.addObject("power",getRoleButton(session));
		mv.setViewName("common/commonMenu/commonMenu_list");
		return mv;
	}
	
	/**
	 * 
	  * @Title: findList
	  * @Description: 获取列表
	  * @param @param request
	  * @param @param session
	  * @param @param response    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/findList")
	public void findList(HttpServletRequest request,HttpSession session,HttpServletResponse response){
		mv.clear();
		pd = new PageData(request);
		pd.put("remove_logo", "N");
		List<PageData> list = commonMenuService.findList(pd);
		try {
			this.writeJson(response, list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	  * @Title: getListNum
	  * @Description: 获取菜单条数
	  * @param @param request
	  * @param @param session
	  * @param @param response    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/getListNum")
	public void getListNum(HttpServletRequest request,HttpSession session,HttpServletResponse response){
		mv.clear();
		pd = new PageData(request);
		pd.put("remove_logo", "N");
		List<PageData> list = commonMenuService.findList(pd);
		try {
			this.writeJson(response, list.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	  * @Title: toAdd
	  * @Description: 跳转到添加页面
	  * @param @param request
	  * @param @param session
	  * @param @param response
	  * @param @return    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/toadd")
	public ModelAndView toAdd(HttpServletRequest request,HttpSession session,HttpServletResponse response){
		mv.clear();
		pd = new PageData(request);
		pd.put("flag", "toadd");
		mv.addObject("pd",pd);
		mv.setViewName("common/commonMenu/commonMenu_add");
		return mv;
	}
	
	/**
	 * 
	  * @Title: save
	  * @Description: 保存
	  * @param @param request
	  * @param @param session
	  * @param @param response
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception{
		mv.clear();
		pd = new PageData(request);
		if(Verify.verifyIsNotNull(pd.get("level"))){
			String level = pd.get("level").toString();
			if("0".equals(level)){
				pd.put("level", "1");
			}else if("1".equals(level)){
				pd.put("level", "2");
			}else if("2".equals(level)){
				pd.put("level", "3");
			}
		}else{
			pd.put("level", "1");
		}
		pd.put("remove_logo", "N");
		PageData user = (PageData)session.getAttribute(Const.SESSION_USER);
		pd.put("create_user", user.get("id"));
		
		pd.put("create_time", DateTimeUtil.getDateTime());
		pd.put("update_time", DateTimeUtil.getDateTime());
		commonMenuService.save(pd);
		pd.put("flag", "success");
		mv.addObject("pd",pd);
		mv.setViewName("common/commonMenu/commonMenu_add");
		return mv;
	}
	
	
	/**
	 * 
	  * @Title: toEdit
	  * @Description: 跳转到编辑页面
	  * @param @param request
	  * @param @param session
	  * @param @param response
	  * @param @return    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/toedit")
	public ModelAndView toEdit(HttpServletRequest request,HttpSession session,HttpServletResponse response){
		mv.clear();
		pd = new PageData(request);
		PageData p = commonMenuService.findInfo(pd);
		p.put("flag", "toedit");
		mv.addObject("pd",p);
		mv.setViewName("common/commonMenu/commonMenu_edit");
		return mv;
	}
	
	/**
	 * 
	  * @Title: update
	  * @Description: 更新菜单信息
	  * @param @param request
	  * @param @param session
	  * @param @param response
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value="/update")
	public ModelAndView update(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception{
		mv.clear();
		pd = new PageData(request);
		pd.put("update_time", DateTimeUtil.getDateTime());
		this.commonMenuService.update(pd);
		pd.put("flag", "success");
		mv.addObject("pd",pd);
		mv.setViewName("common/commonMenu/commonMenu_edit");		
		return mv;
	}
	
	/**
	 * 
	  * @Title: toMenuEdit
	  * @Description: 跳转到菜单图标编辑页面
	  * @param @param request
	  * @param @param session
	  * @param @param response
	  * @param @return    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/toMenuList")
	public ModelAndView toMenuList(HttpServletRequest request,HttpSession session,HttpServletResponse response){
		mv.clear();
		pd = new PageData(request);
		pd.put("flag", "toedit");
		mv.addObject("pd",pd);
		mv.setViewName("common/commonMenu/commonMenu_icon");
		return mv;
	}
	
	/**
	 * 
	  * @Title: menuEdit
	  * @Description: 修改图标
	  * @param @param request
	  * @param @param session
	  * @param @param response
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value="/updateMenu")
	public void updateMenu(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception{
		mv.clear();
		pd = new PageData(request);
		pd.put("update_time", DateTimeUtil.getDateTime());
		this.commonMenuService.update(pd);
		this.writeJson(response, true);
	}
	
	
	/**
	 * 
	  * @Title: remove
	  * @Description: 删除菜单
	  * @param @param request
	  * @param @param session
	  * @param @param response
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/del")
	public void del(HttpServletRequest request,HttpServletResponse response) throws Exception{
		pd = new PageData(request);
		List<PageData> list = new ArrayList<PageData>();
		getCascadeInfo(pd,list);//递归查找所有的子节点
		for (PageData pageData : list) {
			pageData.put("remove_logo", "Y");
			pageData.put("update_time", DateTimeUtil.getDateTime());
			commonMenuService.delete(pageData);
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
		List<PageData> children=commonMenuService.findList(pd);
		if(null!=children&&children.size()>0){
			for(PageData child:children){
				getCascadeInfo(child, list);
			}
		}
	}
	
}
