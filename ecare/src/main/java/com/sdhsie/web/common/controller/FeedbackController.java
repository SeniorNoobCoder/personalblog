package com.sdhsie.web.common.controller;

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
import com.sdhsie.base.util.MD5Util;
import com.sdhsie.base.util.Page;
import com.sdhsie.base.util.PageData;
import com.sdhsie.base.util.Verify;
import com.sdhsie.base.util.parameterUtil;
import com.sdhsie.base.util.upload.FileUpload;
import com.sdhsie.base.util.upload.ParaUtil;
import com.sdhsie.web.common.service.FeedbackService;

@Controller
@RequestMapping(value = "/common/feedback")
public class FeedbackController extends BaseController {
	@Autowired	
	private FeedbackService feedbackService;
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
		List<PageData> feedbackList = feedbackService.findListPage(page);
		
		int num = feedbackService.findDelNum(pd);
		
		pd.put("num", num);
		mv.addObject("pd", pd);	
		mv.addObject("feedbackList", feedbackList);
		mv.addObject("power",getRoleButton(session));
		mv.setViewName("common/feedback/feedback_list");
		return mv;
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
		PageData p = feedbackService.findInfo(pd);
		mv.addObject("p", p);
		mv.addObject("pd", pd);	
		mv.setViewName("common/feedback/feedback_info");
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
		pd.put("status", "0");
		pd.put("remove_logo", "N");
		pd.put("create_time", DateTimeUtil.getDateTime());
		pd.put("dealwith_time", DateTimeUtil.getDateTime());
		feedbackService.save(pd);
		
		pd.put("label", "b");
		mv.addObject("pd", pd);	
		mv.setViewName("common/feedback/feedback_add");
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
		pd.put("remove_logo", "N");
		mv.setViewName("common/feedback/feedback_add");
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
	
		PageData p = feedbackService.findInfo(pd);
		pd.put("remove_logo", "N");
		mv.addObject("p", p);	
		mv.addObject("pd", pd);	
		mv.setViewName("common/feedback/feedback_edit");
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
		pd.put("dealwith_time", DateTimeUtil.getDateTimeStr());
		PageData user = (PageData)session.getAttribute(Const.SESSION_USER);
		pd.put("dealwith_user", user.get("id"));
		pd.put("status", "Y");
		feedbackService.update(pd);
		
		pd.put("label", "b");
		mv.addObject("pd", pd);	
		mv.setViewName("common/feedback/feedback_edit");
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
			p.put("dealwith_time", DateTimeUtil.getDateTime());
			feedbackService.update(p);
		}
		this.writeJson(response, true);
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
		List<PageData> list = feedbackService.findTrashPage(page);
		mv.addObject("list",list);	
		mv.addObject("pd", pd);	
		mv.setViewName("common/feedback/feedback_trash");
		return mv;
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
	@RequestMapping(value = "/clearFeedback")
	public void clearUser(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
	
		feedbackService.clearFeedback(pd);
		
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
	@RequestMapping(value = "/revertFeedback")
	public void revertUser(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
		feedbackService.revertFeedback(pd);
		this.writeJson(response, true);
	}
}