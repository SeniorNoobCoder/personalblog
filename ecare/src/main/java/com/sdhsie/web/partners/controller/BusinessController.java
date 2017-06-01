package com.sdhsie.web.partners.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sdhsie.base.util.*;
import com.sdhsie.base.util.upload.FileUpload;
import com.sdhsie.web.partners.service.PartnersInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sdhsie.base.controller.BaseController;
import com.sdhsie.web.partners.service.BusinessService;

@Controller
@RequestMapping(value = "/partners/business")
public class BusinessController extends BaseController {
	@Autowired
	private BusinessService businessService;
	@Autowired
	private PartnersInfoService partnersInfoService;
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
		List<PageData> list = businessService.findListPage(page);
		
		mv.addObject("pd", pd);	
		mv.addObject("list", list);
		mv.addObject("power",getRoleButton(session));
		mv.setViewName("partners/business/business_list");
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
	
		PageData p = businessService.findInfo(pd);
		
		mv.addObject("p", p);	
		mv.addObject("pd", pd);	
		mv.setViewName("partners/business/business_edit");
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
		businessService.update(pd);
		
		pd.put("label", "b");
		mv.addObject("pd", pd);	
		mv.setViewName("partners/business/business_edit");
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
		businessService.delete(ids);
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
		PageData p = businessService.findInfo(pd);
		PageData comP = new PageData();
		comP.put("id",p.get("partner_id"));
		PageData companyInfo = partnersInfoService.findInfo(comP);
		if(Verify.verifyIsNotNull(p.getString("idCard_front"))){
			p.put("idCard_front", FileUpload.findLocalFileUrl(p.getString("idCard_front")));
		}else{
			p.put("idCard_front","");
		}
		if(Verify.verifyIsNotNull(p.getString("idCard_back"))){
			p.put("idCard_back", FileUpload.findLocalFileUrl(p.getString("idCard_back")));
		}else{
			p.put("idCard_back","");
		}
		mv.addObject("p", p);
		mv.addObject("pd", pd);
		mv.addObject("companyInfo", companyInfo);
		mv.setViewName("partners/business/business_info");
		return mv;
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
		businessService.update(pd);
		this.writeJson(response, true);
	}
	
	
	
	
	@RequestMapping(value = "/findAuthent")
	public ModelAndView findAuthent(HttpServletRequest request,HttpSession session) throws Exception {
		pd = new PageData(request);
	
		PageData p = businessService.findAuthent(pd);
		
		mv.addObject("p", p);	
		mv.addObject("pd", pd);	
		mv.setViewName("partners/business/business_authent");
		return mv;
	}
	/**
	 * 查询合作商分类下面选中的服务分类
	 */
	@RequestMapping(value = "/findServerCategory")
	public void findServerCategory(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws IOException {
		pd = new PageData(request);
		PageData user = (PageData) session.getAttribute(Const.SESSION_USER);
		PageData p = businessService.findInfo(pd);
		//查询组织
		pd.put("remove_logo", "N");
		pd.put("partner_userId", p.get("partner_id"));
		List<PageData> oraganizeList = businessService.findServerCategory(pd);
		this.writeJson(response, oraganizeList);
	}
}
