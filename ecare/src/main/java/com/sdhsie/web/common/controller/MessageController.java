package com.sdhsie.web.common.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.sdhsie.base.util.*;
import com.sdhsie.push.MessageBean;
import com.sdhsie.push.PushUtil;
import com.sdhsie.web.customer.service.FamilyService;
import com.sdhsie.web.partners.service.BusinessService;
import com.sdhsie.web.partners.service.PartnersInfoService;
import com.sdhsie.web.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.sdhsie.base.controller.BaseController;
import com.sdhsie.web.common.service.MessageService;

@Controller
@RequestMapping(value= "/common/message")
public class MessageController extends BaseController {
	@Autowired
	private MessageService messageService;
	@Autowired
	private UserService userService;
	@Autowired
	private PartnersInfoService partnersInfoService;
	@Autowired
	private FamilyService familyService;
	@Autowired
	private BusinessService businessService;
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
		List<PageData> mesList = messageService.findListPage(page);
		
		//查询垃圾箱数量
		int num = messageService.findDelNum(pd);
		
		pd.put("num", num);
		mv.addObject("pd", pd);	
		mv.addObject("mesList", mesList);
		mv.addObject("power",getRoleButton(session));
		mv.setViewName("common/message/message_list");
		return mv;
	}

	@RequestMapping(value = "/findListByUser")
	public ModelAndView findListByUser(HttpServletRequest request,HttpSession session,Page page) throws Exception {
		pd = new PageData(request);
		mv = getBaseMv(session, pd);
		PageData user = (PageData)session.getAttribute(Const.SESSION_USER);
		pd.put("user_id", user.get("id"));
		pd.put("remove_logo", "N");
		page.setPd(pd);
		List<PageData> mesList = messageService.findListPageByUser(page);
		//查询垃圾箱数量
//		int num = messageService.findDelNum(pd);
//		pd.put("num", num);
		mv.addObject("pd", pd);
		mv.addObject("mesList", mesList);
		mv.addObject("power",getRoleButton(session));
		mv.setViewName("common/message/message_list_user");
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

		PageData p = messageService.findInfo(pd);
		pd.put("remove_logo", "N");
		mv.addObject("p", p);	
		mv.addObject("pd", pd);	
		mv.setViewName("common/message/message_info");
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
		String recipients_id = pd.get("recipients_id").toString();
		String recipients_name = this.getName(recipients_id);
		pd.put("operator_id", user.get("id"));
		pd.put("operator_name", user.get("user_name"));
		pd.put("status", "0");
		pd.put("source", "system");
		pd.put("recipients_name", recipients_name);
		pd.put("remove_logo", "N");
		messageService.save(pd);
		pd.put("label", "b");
		mv.addObject("pd", pd);	
		mv.setViewName("common/message/message_add");
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
		mv.setViewName("common/message/message_add");
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
		PageData p = messageService.findInfo(pd);
		pd.put("remove_logo", "N");
		mv.addObject("p", p);	
		mv.addObject("pd", pd);	
		mv.setViewName("common/message/message_edit");
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
		String recipients_id = pd.get("recipients_id").toString();
		String recipients_name = this.getName(recipients_id);
		pd.put("recipients_name", recipients_name);
		messageService.update(pd);
		pd.put("label", "b");
		mv.addObject("pd", pd);	
		mv.setViewName("common/message/message_edit");
		return mv;
	}
	
	/**
	 * 
	  * @Title: send
	  * @Description: 更新信息
	  * @param @param request
	  * @param @param session
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/send")
	public ModelAndView send(HttpServletRequest request,HttpSession session) throws Exception {
		pd = new PageData(request);
		PageData user = (PageData)session.getAttribute(Const.SESSION_USER);
		pd = messageService.findInfo(pd);
		String[] uIds = pd.getString("recipients_id").split(",");
		MessageBean ms = new MessageBean("id","系统消息",pd.getString("title"),Config.getValue("NOTICE"));//发送消息信息
		for(String uId:uIds){
			if(uId.equals(ecareParameter.telemarketer_user)){
				PageData telemarketer = new PageData();
				List<PageData>	telemarketerList = userService.findList(telemarketer);
				for (PageData bus:telemarketerList){
					bus.put("message_id",pd.get("id"));
					bus.put("user_id",bus.get("id"));
					bus.put("user_type", ecareParameter.telemarketer_user);
					bus.put("status",'N');
					messageService.saveUser(bus);
				}
			}else  if(uId.equals(ecareParameter.partners_user)){
				PageData partners = new PageData();
				List<PageData>	partnersList = partnersInfoService.findList(partners);
				for (PageData bus:partnersList){
					bus.put("message_id",pd.get("id"));
					bus.put("user_id",bus.get("id"));
					bus.put("user_type",ecareParameter.partners_user);
					bus.put("status",'N');
					messageService.saveUser(bus);
				}
			}else  if(uId.equals(ecareParameter.server_user)){
				PageData business = new PageData();
				List<PageData>	businessList = businessService.findList(business);
				for (PageData bus:businessList){
					bus.put("message_id",pd.get("id"));
					bus.put("user_id",bus.get("id"));
					bus.put("user_type",ecareParameter.server_user);
					bus.put("status",'N');
					messageService.saveUser(bus);
				}
				PushUtil.sendPushBusinessYIsWorkAlias(ms);
				PushUtil.sendPushBusinessNAlias(ms);
				PushUtil.sendPushBusinessYNotWorkAlias(ms);
			}else  if(uId.equals(ecareParameter.family_user)){
				PageData family = new PageData();
				List<PageData>	familyList = familyService.findList(family);
				for (PageData bus:familyList){
					bus.put("message_id",pd.get("id"));
					bus.put("user_id",bus.get("id"));
					bus.put("user_type",ecareParameter.family_user);
					bus.put("status",'N');
					messageService.saveUser(bus);
				}
				PushUtil.sendPushFamilyAlias(ms);
			}
		}
		pd.put("send_user", user.get("id"));
		pd.put("status", 1);
		messageService.update(pd);
		pd.put("label", "b");
		mv.addObject("pd", pd);	
		mv.setViewName("common/message/message_edit");
		return mv;
	}

	/**
	 * 撤销
	 * @param request
	 * @param session
	 * @return
     * @throws Exception
     */
	@RequestMapping(value = "/reply")
	public ModelAndView reply(HttpServletRequest request,HttpSession session) throws Exception {
		pd = new PageData(request);
		PageData user = (PageData)session.getAttribute(Const.SESSION_USER);
		messageService.delUser(pd.get("id").toString());//撤回消息的同时删除消息接收人
		pd.put("send_user", user.get("id"));
		pd.put("status", 2);
		messageService.update(pd);
		pd.put("label", "b");
		mv.addObject("pd", pd);
		mv.setViewName("common/message/message_edit");
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
			messageService.update(p);
		}
		this.writeJson(response, true);
	}

	/**
	 * 修改通知读取状态
	 * @param request
	 * @param session
	 * @param response
     * @throws Exception
     */
	@RequestMapping(value = "/updateStatus")
	public void updateStatus(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
		pd.put("status","Y");
		messageService.updateStatus(pd);
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
		List<PageData> list = messageService.findTrashPage(page);
		mv.addObject("list",list);	
		mv.addObject("pd", pd);	
		mv.setViewName("common/message/message_trash");
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
	@RequestMapping(value = "/clearMessage")
	public void clearUser(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
	
		messageService.clearMessage(pd);
		
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
	@RequestMapping(value = "/revertMessage")
	public void revertUser(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
		messageService.revertMessage(pd);
		this.writeJson(response, true);
	}

	public String getName(String recipients_id){
		String recipients_name = "";
		String[] recs = recipients_id.split(",");
		for(int i = 0;i<recs.length;i++){
			String code = recs[i];
			if(code.equals(ecareParameter.telemarketer_user)){
				recipients_name+="客服;";
			}else if(code.equals(ecareParameter.partners_user)){
				recipients_name+="商家;";
			}else if(code.equals(ecareParameter.server_user)){
				recipients_name+="商户;";
			}else if(code.equals(ecareParameter.family_user)){
				recipients_name+="家人;";
			}
		}
		return recipients_name;
	}
}
