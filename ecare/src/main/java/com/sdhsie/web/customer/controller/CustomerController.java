package com.sdhsie.web.customer.controller;

import com.sdhsie.base.controller.BaseController;
import com.sdhsie.base.util.*;
import com.sdhsie.base.util.upload.FileUpload;
import com.sdhsie.base.util.upload.ParaUtil;
import com.sdhsie.web.customer.service.CustomerService;
import com.sdhsie.web.customer.service.DeviceService;

import com.sdhsie.web.customer.service.FamilyService;
import com.sdhsie.webService.toro.controller.ToroInfoUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zcx on 2016/11/29.
 */
@Controller
@RequestMapping(value = "/customer/customer")
public class CustomerController extends BaseController {
    private PageData pd;
    @Autowired
    private CustomerService customerService;
    @Autowired
	private DeviceService deviceService;
	@Autowired
	private FamilyService familyService;
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
		List<PageData> list = customerService.findListPage(page);
		
		//查询垃圾箱数量
		int num = customerService.findDelNum();
		
		pd.put("num", num);
		mv.addObject("pd", pd);	
		mv.addObject("list", list);
		mv.addObject("power",getRoleButton(session));
		mv.setViewName("customer/customer/customer_list");
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
		mv.setViewName("customer/customer/customer_add");
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
		PageData p = customerService.findInfo(pd);
		if(Verify.verifyIsNotNull(p.getString("head_address"))){
			p.put("head_address", FileUpload.findLocalFileUrl(p.getString("head_address")));
		}else{
			p.put("head_address","");
		}
		mv.addObject("p", p);	
		mv.addObject("pd", pd);	
		mv.setViewName("customer/customer/customer_edit");
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
		pd.putAll(FileUpload.uploadLocalFile(request, ParaUtil.family+ParaUtil.customer, pd));
		pd.put("status", "N");
		pd.put("remove_logo", "N");
		PageData user = (PageData) session.getAttribute(Const.SESSION_USER);
		pd.put("create_user", user.get("id"));
		pd.put("create_time", DateTimeUtil.getDateTime());
		pd.put("update_time", DateTimeUtil.getDateTime());
		customerService.save(pd);
		
		pd.put("label", "b");
		mv.addObject("pd", pd);	
		mv.setViewName("customer/customer/customer_add");
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
		pd.putAll(FileUpload.uploadLocalFile(request, ParaUtil.family+ParaUtil.customer, pd));
		pd.put("update_time", DateTimeUtil.getDateTimeStr());
		customerService.update(pd);
		pd.put("label", "b");
		mv.addObject("pd", pd);	
		mv.setViewName("customer/customer/customer_edit");
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
			customerService.update(p);
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
		PageData p = customerService.findInfo(pd);
		if(Verify.verifyIsNotNull(p.getString("head_address"))){
			p.put("head_address", FileUpload.findLocalFileUrl(p.getString("head_address")));
		}else{
			p.put("head_address","");
		}
		List<PageData> famaryList = new ArrayList<PageData>();
		if(Verify.verifyIsNotNull(p.getString("sn"))){
			famaryList = familyService.findListBySn(p);
		}
		List<PageData> contactList = ToroInfoUtil.getContacts(p.getString("sn"));
		mv.addObject("p", p);
		mv.addObject("pd", pd);
		mv.addObject("famaryList", famaryList);
		mv.addObject("contactList", contactList);
		mv.setViewName("customer/customer/customer_info");
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
		List<PageData> list = customerService.findTrashPage(page);
		
		mv.addObject("list",list);	
		mv.addObject("pd", pd);	
		mv.setViewName("customer/customer/customer_trash");
		return mv;
	}
	
	
	
	/**
	 * 
	  * @Title: clearcustomer
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
	
		customerService.clearTrash(pd);
		
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
	
		customerService.revertTrash(pd);
		
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
		customerService.update(pd);
		this.writeJson(response, true);
	}
	
	
	/**
	 * 
	  * @Title: tobindDevice
	  * @Description: 跳转到绑定设备页面
	  * @param @param request
	  * @param @param session
	  * @param @param page
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/tobindDevice")
	public ModelAndView tobindDevice(HttpServletRequest request,HttpSession session,Page page) throws Exception {
		pd = new PageData(request);
		pd.put("remove_logo", "N");
		pd.put("status", "N");
		page.setPd(pd);
		List<PageData> list = deviceService.findListPage(page);
		mv.addObject("pd", pd);	
		mv.addObject("list", list);	
		mv.setViewName("customer/customer/customer_bindDevice");
		return mv;
	}
	
	/**
	 * 
	  * @Title: savebindDevice
	  * @Description: 绑定设备
	  * @param @param request
	  * @param @param session
	  * @param @param page
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/savebindDevice")
	public ModelAndView savebindDevice(HttpServletRequest request,HttpSession session,Page page) throws Exception {
		pd = new PageData(request);
		
		pd.put("update_time", DateTimeUtil.getDateTimeStr());
		customerService.update(pd);
		pd.put("status", "Y");
		pd.put("id", pd.get("device_id"));
		deviceService.update(pd);
		pd.put("label", "b");
		mv.addObject("pd", pd);	
		mv.setViewName("customer/customer/customer_bindDevice");
		return mv;
	}
	
	
	/**
	 * 
	  * @Title: findDeviceInfo
	  * @Description: 跳转到设备详情页面
	  * @param @param request
	  * @param @param session
	  * @param @param page
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/findDeviceInfo")
	public ModelAndView findDeviceInfo(HttpServletRequest request,HttpSession session,Page page) throws Exception {
		pd = new PageData(request);
		
		pd.put("id", pd.get("device_id"));
		PageData p = deviceService.findInfo(pd);
		
		mv.addObject("p", p);
		mv.addObject("pd", pd);	
		mv.setViewName("customer/device/device_info");
		return mv;
	}

	@RequestMapping(value = "/removeDevice")
	public ModelAndView removeDevice(HttpServletRequest request,HttpSession session,Page page) throws Exception {
		pd = new PageData(request);
		
		pd.put("update_time", DateTimeUtil.getDateTimeStr());
		customerService.removeDevice(pd);
		pd.put("id", pd.get("device_id"));
		pd.put("status", "N");
		deviceService.update(pd);
		
		mv.addObject("pd", pd);	
		mv.setViewName("customer/device/device_info");
		return mv;
	}
	
	
	@RequestMapping(value = "/findFootprint")
	public void findFootprint(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
		if(!Verify.verifyIsNotNull(pd.get("find_time"))){
			pd.put("find_time", DateTimeUtil.getDate());
		}
		List<PageData> list = new ArrayList<PageData>();
		if(pd.getString("type").equals("location")){
			pd = ToroInfoUtil.getToroLoc(pd.getString("sn"));
			list.add(pd);
		}else {
			//查询地图坐标
			list = ToroInfoUtil.getToroTrace(pd.getString("sn"),pd.getString("find_time"));//customerService.findFootprint(pd);
		}
		super.writeJson(response,list);
	}

	/**
	 * 设置电子围栏
	 * @param request
	 * @param session
	 * @param response
	 * @throws Exception
     */
	@RequestMapping(value = "/setFence")
	public void setFence(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
		if(pd.get("fence_id").equals("")){
			pd.put("sign",1);
		}else {
			pd.put("sign",2);
		}
		Boolean isTrue = ToroInfoUtil.setFence(pd);
		super.writeJson(response,isTrue);
	}

	/**
	 * 获取电子围栏信息
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
     */
	@RequestMapping(value = "/getFence")
	public void getFence(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
		List<PageData> list = ToroInfoUtil.getFence(pd.get("sn").toString());
		if(list==null||list.size()<1){
			pd = ToroInfoUtil.getToroLoc(pd.getString("sn"));
			pd.put("radius",1000);
			list.add(pd);
		}else {
			pd= list.get(0);
		}
		super.writeJson(response,pd);
	}

	/**
	 * 获取电话联系人
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
     */
	@RequestMapping(value = "/getContacts")
	public ModelAndView getContacts(HttpServletRequest request,HttpSession session) throws Exception {
		pd = new PageData(request);
		List<PageData> list = ToroInfoUtil.getContacts(pd.get("sn").toString());
		mv.addObject("list",list);
		mv.addObject("pd", pd);
		mv.setViewName("customer/customer/customer_contacts");
		return mv;
	}

	@RequestMapping(value = "/toContactsAdd")
	public ModelAndView toContactsAdd(HttpServletRequest request,HttpSession session) throws Exception {
		pd = new PageData(request);
		mv.addObject("pd", pd);
		mv.setViewName("customer/customer/customer_contactsAdd");
		return mv;
	}
	/**
	 * 设置电话联系人
	 * @param request
	 * @param request
	 * @throws Exception
     */
	@RequestMapping(value = "/saveContacts")
	public ModelAndView saveContacts(HttpServletRequest request) throws Exception {
		pd = new PageData(request);
		pd.put("sign",1);
		pd.put("cid","");
		Boolean isTrue = ToroInfoUtil.setContacts(pd);
		mv.addObject("isTrue", isTrue);
		pd.put("label", "b");
		mv.addObject("pd", pd);
		mv.setViewName("customer/customer/customer_contactsAdd");
		return mv;
	}

	/**
	 * 删除联系人
	 * @param request
	 * @param session
	 * @param response
	 * @throws Exception
     */
	@RequestMapping(value = "/delContacts")
	public void delContacts(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
		pd.put("sign",3);
		Boolean isTrue = ToroInfoUtil.setContacts(pd);
		super.writeJson(response,isTrue);
	}

	@RequestMapping(value = "/toContactsUpdate")
	public ModelAndView toContactsUpdate(HttpServletRequest request,HttpSession session) throws Exception {
		pd = new PageData(request);
		mv.addObject("pd", pd);
		mv.setViewName("customer/customer/customer_contactsUpdate");
		return mv;
	}
	/**
	 * 修改联系人
	 * @param request
	 * @return
	 * @throws Exception
     */
	@RequestMapping(value = "/updateContacts")
	public ModelAndView updateContacts(HttpServletRequest request) throws Exception {
		pd = new PageData(request);
		pd.put("sign",2);
		Boolean isTrue = ToroInfoUtil.setContacts(pd);
		mv.addObject("isTrue", isTrue);
		pd.put("label", "b");
		mv.addObject("pd", pd);
		mv.setViewName("customer/customer/customer_contactsUpdate");
		return mv;
	}
}
