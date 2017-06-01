package com.sdhsie.web.customer.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.sdhsie.base.controller.BaseController;
import com.sdhsie.base.util.Const;
import com.sdhsie.base.util.DateTimeUtil;
import com.sdhsie.base.util.Page;
import com.sdhsie.base.util.PageData;
import com.sdhsie.base.util.Verify;
import com.sdhsie.web.customer.service.CustomerService;
import com.sdhsie.web.customer.service.DeviceService;

@Controller
@RequestMapping(value = "/customer/device")
public class DeviceController extends BaseController {
	@Autowired
	private DeviceService deviceService;
	@Autowired
    private CustomerService customerService;
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
		List<PageData> list = deviceService.findListPage(page);
		
		//查询垃圾箱数量
		int num = deviceService.findDelNum();
		
		pd.put("num", num);
		mv.addObject("pd", pd);	
		mv.addObject("list", list);
		mv.addObject("power",getRoleButton(session));
		mv.setViewName("customer/device/device_list");
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
		mv.setViewName("customer/device/device_add");
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
	
		PageData p = deviceService.findInfo(pd);
		
		mv.addObject("p", p);	
		mv.addObject("pd", pd);	
		mv.setViewName("customer/device/device_edit");
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
		deviceService.save(pd);
		
		pd.put("label", "b");
		mv.addObject("pd", pd);	
		mv.setViewName("customer/device/device_add");
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
		deviceService.update(pd);
		
		pd.put("label", "b");
		mv.addObject("pd", pd);	
		mv.setViewName("customer/device/device_edit");
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
			deviceService.update(p);
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
	
		PageData p = deviceService.findInfo(pd);
		
		mv.addObject("p", p);	
		mv.addObject("pd", pd);	
		mv.setViewName("customer/device/device_info");
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
		List<PageData> list = deviceService.findTrashPage(page);
		
		mv.addObject("list",list);	
		mv.addObject("pd", pd);	
		mv.setViewName("customer/device/device_trash");
		return mv;
	}
	
	
	
	/**
	 * 
	  * @Title: cleardevice
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
	
		deviceService.clearTrash(pd);
		
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
	
		deviceService.revertTrash(pd);
		
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
		deviceService.update(pd);
		this.writeJson(response, true);
	}
	
	
	
	@RequestMapping(value = "/todeviceImport")
	public ModelAndView todeviceImport(HttpServletRequest request,HttpSession session) throws Exception {
		pd = new PageData(request);
	
		mv.addObject("pd", pd);	
		mv.setViewName("customer/device/device_import");
		return mv;
	}
	
	
	
	@RequestMapping(value="/savedeviceImport")
	public ModelAndView savedeviceImport(HttpServletRequest request,HttpSession session)throws Exception{
		mv.clear();
		pd = new PageData(request);
		
		//删除位置信息
//		List<PageData> list = plantdiseService.findList(pd);
//		for(PageData pageData:list){
//			PageData p = new PageData();
//			p.put("plantdise_id", pageData.get("id"));
//			plantdiseService.deletePositions(p);
//		}
		//删除病害库信息
		this.deviceService.delete(pd);
		PageData user = (PageData) session.getAttribute(Const.SESSION_USER);
		
		//病害导入
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
        MultipartFile multipartFile = multipartRequest.getFile("import_file");  
		Workbook book = new XSSFWorkbook(multipartFile.getInputStream());
		Sheet sheet = book.getSheetAt(0);
		int totalRows = sheet.getPhysicalNumberOfRows();
		//int totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
		for (int i = 0; i < totalRows; i++) {
			if(!sheet.getRow(i).getCell(0).toString().trim().contains("#")){
				String sn = sheet.getRow(i).getCell(0).toString().trim();
				String app_id = sheet.getRow(i).getCell(1).toString().trim();
				String secret = sheet.getRow(i).getCell(2).toString().trim();
				if(Verify.verifyIsNotNull(sn)&&Verify.verifyIsNotNull(app_id)&&Verify.verifyIsNotNull(secret)){
					PageData p = new PageData();
					p.put("sn", sn);
					p.put("app_id", app_id);
					p.put("secret", secret);
					p.put("status", "N");
					p.put("remove_logo", "N");
					p.put("create_user", user.get("id"));
					p.put("create_time", DateTimeUtil.getDateTime());
					p.put("update_time", DateTimeUtil.getDateTime());
					
					PageData pds = deviceService.findInfo(p);
					if(!Verify.verifyIsNotNull(pds)){
						deviceService.save(p);
					}
				}
			}
		}
		pd.put("flag", "success");
		mv.addObject("pd", pd);
		mv.setViewName("customer/device/device_import");
		return mv;
	}
	
	
	
	@RequestMapping(value = "/findSnNum")
	public void findSnNum(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
		PageData pds = deviceService.findInfo(pd);
		boolean bol = false;
		if(!Verify.verifyIsNotNull(pds)){
			bol = true;
		}
		this.writeJson(response, bol);
	}
	
	
	/**
	 * 
	  * @Title: findUserInfo
	  * @Description: 查询绑定的用户信息
	  * @param @param request
	  * @param @param session
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/findUserInfo")
	public ModelAndView findUserInfo(HttpServletRequest request,HttpSession session) throws Exception {
		pd = new PageData(request);
		PageData p = customerService.findInfo(pd);
		mv.addObject("p", p);	
		mv.addObject("pd", pd);	
		mv.setViewName("customer/device/device_userInfo");
		return mv;
	}
	
	
	/**
	 * 
	  * @Title: tobindUser
	  * @Description: 跳转到绑定用户信息
	  * @param @param request
	  * @param @param session
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/tobindUser")
	public ModelAndView tobindUser(HttpServletRequest request,HttpSession session) throws Exception {
		pd = new PageData(request);
	
		mv.addObject("pd", pd);	
		mv.setViewName("customer/device/device_bindUser");
		return mv;
	}
	
	
	@RequestMapping(value = "/savebindUser")
	public ModelAndView savebindUser(HttpServletRequest request,HttpSession session) throws Exception {
		pd = new PageData(request);
	
		pd.put("status", "Y");
		pd.put("remove_logo", "N");
		PageData user = (PageData) session.getAttribute(Const.SESSION_USER);
		pd.put("create_user", user.get("id"));
		pd.put("create_time", DateTimeUtil.getDateTime());
		pd.put("update_time", DateTimeUtil.getDateTime());
		customerService.save(pd);
		
		//更新绑定状态
		PageData p = new PageData();
		p.put("id", pd.get("device_id"));
		p.put("status", "Y");
		p.put("update_time", DateTimeUtil.getDateTime());
		deviceService.update(p);
		
		pd.put("label", "b");
		mv.addObject("pd", pd);	
		mv.setViewName("customer/device/device_bindUser");
		return mv;
	}
	
}
