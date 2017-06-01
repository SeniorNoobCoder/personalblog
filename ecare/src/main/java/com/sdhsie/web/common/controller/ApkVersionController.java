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
import com.sdhsie.base.util.Page;
import com.sdhsie.base.util.PageData;
import com.sdhsie.base.util.Verify;
import com.sdhsie.base.util.upload.FileUpload;
import com.sdhsie.base.util.upload.ParaUtil;
import com.sdhsie.web.common.service.ApkVersionService;

@Controller
@RequestMapping(value = "/common/apkVersion")
public class ApkVersionController extends BaseController {
	@Autowired	
	private ApkVersionService apkVersionService;
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
		List<PageData> apkList = apkVersionService.findListPage(page);
		
		//查询垃圾箱数量
		int num = apkVersionService.findDelNum(pd);
		
		pd.put("num", num);
		mv.addObject("pd", pd);	
		mv.addObject("apkList", apkList);
		mv.addObject("power",getRoleButton(session));
		mv.setViewName("common/apkversion/apkversion_list");
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
	
		PageData p = apkVersionService.findInfo(pd);
		if(Verify.verifyIsNotNull(p.getString("file_address"))){
			p.put("file_address", FileUpload.findLocalFileUrl(p.getString("file_address")));
		}else{
			p.put("file_address","");
		}
		pd.put("remove_logo", "N");
		mv.addObject("p", p);	
		mv.addObject("pd", pd);	
		mv.setViewName("common/apkversion/apkversion_info");
		return mv;
	}
	
	@RequestMapping(value="/Download")
	public void Download(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
		mv.clear();
		pd = new PageData(request);
		FileUpload.downloadLocalFile(response,pd.getString("file_address"), "ekanhu.apk");
	}
	/**
	 * 
	  * @Title: save
	  * @Description: 保存信息
	  * 
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
		pd.putAll(FileUpload.saveLocalFile(request, ParaUtil.system+"apk/", pd));
		PageData user = (PageData)session.getAttribute(Const.SESSION_USER);
		pd.put("create_user", user.get("id"));
		pd.put("status", "N");
		pd.put("remove_logo", "N");
		pd.put("create_time", DateTimeUtil.getDateTime());
		pd.put("update_time", DateTimeUtil.getDateTime());
		apkVersionService.save(pd);
		pd.put("label", "b");
		mv.addObject("pd", pd);	
		mv.setViewName("common/apkversion/apkversion_add");
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
		mv.setViewName("common/apkversion/apkversion_add");
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
	
		PageData p = apkVersionService.findInfo(pd);
		if(Verify.verifyIsNotNull(p.getString("file_address"))){
			p.put("file_address", FileUpload.findLocalFileUrl(p.getString("file_address")));
		}else{
			p.put("file_address","");
		}
		pd.put("remove_logo", "N");
		mv.addObject("p", p);	
		mv.addObject("pd", pd);	
		mv.setViewName("common/apkversion/apkversion_edit");
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
		pd.putAll(FileUpload.uploadLocalFile(request, ParaUtil.system+"apk/", pd));
		pd.put("update_time", DateTimeUtil.getDateTimeStr());
		apkVersionService.update(pd);
		pd.put("label", "b");
		mv.addObject("pd", pd);	
		mv.setViewName("common/apkversion/apkversion_edit");
		return mv;
	}

	/**
	 * 修改状态
	 * @param request
	 * @param session
	 * @param response
     * @throws Exception
     */
	@RequestMapping(value = "/updateStatus")
	public void updateStatus(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
		pd.put("update_time", DateTimeUtil.getDateTimeStr());
		apkVersionService.update(pd);
		this.writeJson(response, true);
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
			apkVersionService.update(p);
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
		List<PageData> list = apkVersionService.findTrashPage(page);
		mv.addObject("list",list);	
		mv.addObject("pd", pd);	
		mv.setViewName("common/apkversion/apkversion_trash");
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
	@RequestMapping(value = "/clearApkVersion")
	public void clearUser(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
	
		apkVersionService.clearApkVersion(pd);
		
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
	@RequestMapping(value = "/revertApkVersion")
	public void revertUser(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
		apkVersionService.revertApkVersion(pd);
		this.writeJson(response, true);
	}
}
