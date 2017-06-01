package com.sdhsie.web.customer.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sdhsie.base.util.upload.FileUpload;
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
import com.sdhsie.web.customer.service.FamilyService;

@Controller
@RequestMapping(value = "/customer/family")
public class FamilyController extends BaseController {
	@Autowired
	private FamilyService familyService;
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
		pd.put("create_organize", session.getAttribute(Const.SESSION_USER_ORG));
		pd.put("remove_logo", "N");
		page.setPd(pd);
		List<PageData> list = familyService.findListPage(page);
		
		//查询垃圾箱数量
		int num = familyService.findDelNum();
		
		pd.put("num", num);
		mv.addObject("pd", pd);	
		mv.addObject("list", list);
		mv.addObject("power",getRoleButton(session));
		mv.setViewName("customer/family/family_list");
		return mv;
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
		mv.setViewName("customer/family/family_add");
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
	
		PageData p = familyService.findInfo(pd);
		
		mv.addObject("p", p);	
		mv.addObject("pd", pd);	
		mv.setViewName("customer/family/family_edit");
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
	
		
		pd.put("status", "N");
		pd.put("remove_logo", "N");
		PageData user = (PageData) session.getAttribute(Const.SESSION_USER);
		pd.put("create_user", user.get("id"));
		pd.put("create_time", DateTimeUtil.getDateTime());
		pd.put("update_time", DateTimeUtil.getDateTime());
		familyService.save(pd);
		
		pd.put("label", "b");
		mv.addObject("pd", pd);	
		mv.setViewName("customer/family/family_add");
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
		familyService.update(pd);
		
		pd.put("label", "b");
		mv.addObject("pd", pd);	
		mv.setViewName("customer/family/family_edit");
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
			PageData p = new PageData();
			p.put("id", id);
			p.put("remove_logo", "Y");
			p.put("update_time", DateTimeUtil.getDateTime());
			familyService.update(p);
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
		PageData p = familyService.findInfo(pd);
		if(Verify.verifyIsNotNull(p.getString("head_address"))){
			p.put("head_address", FileUpload.findLocalFileUrl(p.getString("head_address")));
		}else{
			p.put("head_address","");
		}
		List<PageData> familyList = familyService.findListByFamilyId(pd);
		mv.addObject("p", p);
		mv.addObject("familyList", familyList);
		mv.addObject("pd", pd);	
		mv.setViewName("customer/family/family_info");
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
		List<PageData> list = familyService.findTrashPage(page);
		
		mv.addObject("list",list);	
		mv.addObject("pd", pd);	
		mv.setViewName("customer/family/family_trash");
		return mv;
	}
	
	
	
	/**
	 * 
	  * @Title: clearfamily
	  * @Description: 清空彻底删除
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
	
		familyService.clearTrash(pd);
		
		this.writeJson(response, true);
		
	}
	
	
	/**
	 * 
	  * @Title: revertTrash
	  * @Description: 还原删除信息
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
	
		familyService.revertTrash(pd);
		
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
		familyService.update(pd);
		this.writeJson(response, true);
	}
	
	
	
	 @RequestMapping(value="/checkLoginName")
	    public void checkLoginName(HttpSession session,HttpServletResponse response) throws Exception{
	        pd = new PageData(this.getRequest());
	        boolean bol = true;
	        PageData p = familyService.findInfo(pd);
	        if(Verify.verifyIsNotNull(p)){
	            bol = false;
	            if(Verify.verifyIsNotNull(pd.get("flag"))){
	                if(pd.get("flag").toString().trim().equals(p.get("login_name").toString().trim())){
	                    bol = true;
	                }
	            }
	        }
	        this.writeJson(response, bol);
	    }
	 
	 
	
	 /**
	  * 
	   * @Title: findDevice
	   * @Description: 查询用户关联设备信息
	   * @param @param request
	   * @param @param session
	   * @param @param page
	   * @param @return
	   * @param @throws Exception    设定文件
	   * @return ModelAndView    返回类型
	   * @throws
	  */
	 @RequestMapping(value = "/findDevice")
		public ModelAndView findDevice(HttpServletRequest request,HttpSession session,Page page) throws Exception {
			pd = new PageData(request);
		
			List<PageData> list = familyService.findDeviceList(pd);
			
			mv.addObject("list",list);	
			mv.addObject("pd", pd);	
			mv.setViewName("customer/family/family_device");
			return mv;
		}
	 
	 
}
