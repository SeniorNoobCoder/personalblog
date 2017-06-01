package com.sdhsie.web.appInfo.controller;
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
import com.sdhsie.base.util.upload.FileUpload;
import com.sdhsie.base.util.upload.ParaUtil;
import com.sdhsie.web.appInfo.service.MallPhotoService;

@Controller
@RequestMapping(value = "/appInfo/mallPhoto")
public class MallPhotoController extends BaseController {
	@Autowired	
	private MallPhotoService mallPhotoService;
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
		List<PageData> photoList = mallPhotoService.findListPage(page);
		List<PageData> newPhotoList = new ArrayList<PageData>();
		for (PageData newPd : photoList) {
			if(Verify.verifyIsNotNull(newPd.getString("image_href"))){
				newPd.put("image_href", FileUpload.findLocalFileUrl(newPd.getString("image_href")));
			}else{
				newPd.put("image_href","");
			}
			newPhotoList.add(newPd);
		}
		//查询垃圾箱数量
		PageData delPd = new PageData();
		delPd.put("remove_logo", "Y");
		int num = mallPhotoService.findNum(delPd);
		pd.put("num", num);
		mv.addObject("pd", pd);	
		mv.addObject("photoList", newPhotoList);
		mv.addObject("power",getRoleButton(session));
		mv.setViewName("appInfo/mallPhoto/mallPhoto_list");
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
		PageData p = mallPhotoService.findInfo(pd);
		if(Verify.verifyIsNotNull(p.getString("image_href"))){
			p.put("image_href", FileUpload.findLocalFileUrl(p.getString("image_href")));
		}else{
			p.put("image_href","");
		}
		pd.put("remove_logo", "N");
		mv.addObject("p", p);	
		mv.addObject("pd", pd);	
		mv.setViewName("appInfo/mallPhoto/mallPhoto_info");
		return mv;
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
		pd.putAll(FileUpload.saveLocalFile(request, ParaUtil.family+ParaUtil.topImages, pd));
		PageData user = (PageData)session.getAttribute(Const.SESSION_USER);
		pd.put("operator_id", user.get("id"));
		pd.put("operator_name", user.get("user_name"));
		pd.put("status", "0");
		pd.put("remove_logo", "N");
		mallPhotoService.save(pd);
		pd.put("label", "b");
		mv.addObject("pd", pd);	
		mv.setViewName("appInfo/mallPhoto/mallPhoto_add");
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
		mv.setViewName("appInfo/mallPhoto/mallPhoto_add");
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
		PageData p = mallPhotoService.findInfo(pd);
		if(Verify.verifyIsNotNull(p.getString("image_href"))){
			p.put("image_href", FileUpload.findLocalFileUrl(p.getString("image_href")));
		}else{
			p.put("image_href","");
		}
		mv.addObject("p", p);
		mv.setViewName("appInfo/mallPhoto/mallPhoto_edit");
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
		pd.putAll(FileUpload.uploadLocalFile(request, ParaUtil.family+ParaUtil.topImages, pd));
		pd.put("update_time", DateTimeUtil.getDateTimeStr());
		mallPhotoService.update(pd);
		pd.put("label", "b");
		mv.addObject("pd", pd);	
		mv.setViewName("appInfo/mallPhoto/mallPhoto_edit");
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
			p.put("status", "9");
			p.put("remove_logo", "Y");
			p.put("update_time", DateTimeUtil.getDateTime());
			mallPhotoService.update(p);
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
		List<PageData> list = mallPhotoService.findTrashPage(page);
		mv.addObject("list",list);	
		mv.addObject("pd", pd);	
		mv.setViewName("appInfo/mallPhoto/mallPhoto_trash");
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
	@RequestMapping(value = "/clearMallPhoto")
	public void clearUser(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
	
		mallPhotoService.clearMallPhoto(pd);
		
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
	@RequestMapping(value = "/revertMallPhoto")
	public void revertUser(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
		pd = new PageData(request);
		mallPhotoService.revertMallPhoto(pd);
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
		String status = pd.get("status").toString();
		boolean isTrue = false;
		if(status.equals("1")){//当启用广告位时，先判断一下启用数量是否超过了预定的5条
			int num = mallPhotoService.findNum(pd);
			if(num<5){
				mallPhotoService.update(pd);
				isTrue = true;
			}
		}else {
			mallPhotoService.update(pd);
			isTrue = true;
		}
		this.writeJson(response, isTrue);
	}
}
