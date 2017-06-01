package com.sdhsie.web.partners.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sdhsie.base.qrCode.TwoDimensionCode;
import com.sdhsie.base.util.upload.FtpUtil;
import org.apache.xmlbeans.impl.xb.ltgfmt.Code;
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
import com.sdhsie.web.partners.service.PartnersInfoService;
import com.sdhsie.web.server.service.ServerCategoryService;
import com.sdhsie.web.system.service.DictionaryService;

/**
 * Created by zcx on 2016/11/28.
 */
@Controller
@RequestMapping(value = "/partners/partners")
public class PartnersInfoController extends BaseController {
    private PageData pd;
    @Autowired
    private DictionaryService dictionaryService;
    @Autowired
    private PartnersInfoService partnersInfoService;
    @Autowired
    private ServerCategoryService serverCategoryService;

    /**
     * @Title: findList
     * @Description: 查询信息列表
     * @param @param request
     * @param @param response
     * @param @throws Exception    设定文件
     * @return void    返回类型
     * @throws
     */
    @RequestMapping(value = "/findList")
    public ModelAndView findList(HttpServletRequest request, HttpSession session, Page page) throws Exception {
        pd = new PageData(request);
        mv = getBaseMv(session, pd);
        pd.put("remove_logo", "N");
        page.setPd(pd);
        List<PageData> userList = partnersInfoService.findListPage(page);
        //查询垃圾箱数量
        int num = partnersInfoService.findDelNum(pd);
        pd.put("parent_id","385");
        List<PageData> dictionaryList =dictionaryService.findList(pd);
        mv.addObject("dictionaryList", dictionaryList);
        pd.put("num", num);
        mv.addObject("pd", pd);
        mv.addObject("userList", userList);
        mv.addObject("power",getRoleButton(session));
        mv.setViewName("partners/partners/partners_list");
        return mv;
    }

    /**
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
        String nn = pd.getString("id");
        if(pd.get("id")!=null){
        PageData p = partnersInfoService.findInfo(pd);
        if(Verify.verifyIsNotNull(p)){
        	if(Verify.verifyIsNotNull(p.getString("head_address"))){
                p.put("head_url", FileUpload.findLocalFileUrl(p.getString("head_address")));
            }else{
                p.put("head_url","");
            }
        }
//        pd.put("remove_logo", "N");
//        pd.put("parent_id","385");
        p.put("step","1");
//        List<PageData> dictionaryList =dictionaryService.findList(pd);
//        mv.addObject("dictionaryList", dictionaryList);
        mv.addObject("partnersInfo", p);
        }
        mv.addObject("pd", pd);
        mv.setViewName("partners/partners/partners_add");
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
        PageData p = partnersInfoService.findInfo(pd);
        if(Verify.verifyIsNotNull(p.getString("head_address"))){
            p.put("head_url", FileUpload.findLocalFileUrl(p.getString("head_address")));
        }else{
            p.put("head_url","");
        }
        pd.put("remove_logo", "N");
        pd.put("parent_id","385");
        List<PageData> dictionaryList =dictionaryService.findList(pd);
        mv.addObject("dictionaryList", dictionaryList);
        mv.addObject("p", p);
        mv.addObject("pd", pd);
        mv.setViewName("partners/partners/partners_edit");
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
        if(Verify.verifyIsNotNull(pd.get("id"))){
        	pd.putAll(FileUpload.uploadLocalFile(request, ParaUtil.system+ParaUtil.user, pd));
            if(Verify.verifyIsNotNull(pd.getString("login_password"))){
                pd.put("login_password", MD5Util.MD5(pd.getString("login_password")));
            }
            pd.put("update_time", DateTimeUtil.getDateTimeStr());
            partnersInfoService.update(pd);
        }else{
            String code = GuidUtil.getStrGuid();
            String codeImage = getQRCode(code,request);
        	pd.putAll(FileUpload.saveLocalFile(request, ParaUtil.system+ParaUtil.user, pd));
            PageData user = (PageData)session.getAttribute(Const.SESSION_USER);
            pd.put("code", code);
            pd.put("write_type", "0");
            pd.put("create_user", user.get("id"));
            pd.put("login_password", MD5Util.MD5(pd.getString("login_password")));
            pd.put("status", "Y");
            pd.put("is_authent", "0");
            pd.put("remove_logo", "N");
            pd.put("create_time", DateTimeUtil.getDateTime());
            pd.put("update_time", DateTimeUtil.getDateTime());
            pd.put("code_image",codeImage);
            partnersInfoService.save(pd);
        }
        
        if(pd.getString("submit_type").equals("save")){
        	mv.setViewName("redirect:/partners/partners/toadd.do?id="+pd.get("id"));
        }else if(pd.getString("submit_type").equals("save_next")){
            mv.setViewName("redirect:/partners/partners/toaddAuthent.do?partner_id="+pd.get("id"));
        }
        pd.put("label", "b");
        mv.addObject("pd", pd);
        return mv;
    }

    public String getQRCode(String code,HttpServletRequest request){
        String filePath = ParaUtil.partners+"QRCode/";
        String key = code+".png";
        String realPath= request.getSession().getServletContext().getRealPath(ParaUtil.localName+filePath);
        File pageFile = new File(realPath);
        if(!pageFile.exists()){
            pageFile.mkdirs();
        }
        TwoDimensionCode handler = new TwoDimensionCode();
        handler.encoderQRCode(code, realPath+key, "png" );
        File f1 = new File(realPath+key);
        //					====图片上传至服务器 start =====
        InputStream input = null;
        try {
            input = new FileInputStream(f1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        FtpUtil.uploadFile(filePath,key,input);//上传图片到图片服务器
//					图片上传至服务器 end
        f1.delete();//删除临时图片
        return  "/"+ParaUtil.localName+"/"+filePath+key;
    }
    /**
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
        pd.putAll(FileUpload.uploadLocalFile(request, ParaUtil.system+ParaUtil.user, pd));
        if(Verify.verifyIsNotNull(pd.getString("login_password"))){
            pd.put("login_password", MD5Util.MD5(pd.getString("login_password")));
        }
        pd.put("update_time", DateTimeUtil.getDateTimeStr());
        partnersInfoService.update(pd);
        pd.put("label", "b");
        mv.addObject("pd", pd);
        mv.setViewName("partners/partners/partners_edit");
        return mv;
    }

    /**
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
            partnersInfoService.update(p);
        }
        this.writeJson(response, true);
    }

    /**
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
        PageData p = partnersInfoService.findInfo(pd);
        if(Verify.verifyIsNotNull(p.getString("head_address"))){
            p.put("head_url", FileUpload.findLocalFileUrl(p.getString("head_address")));
        }else{
            p.put("head_url","");
        }
        if(Verify.verifyIsNotNull(p.getString("authent_photo1"))){
            p.put("authent_photo1", FileUpload.findLocalFileUrl(p.getString("authent_photo1")));
        }else{
            p.put("authent_photo1","");
        }
        if(Verify.verifyIsNotNull(p.getString("code_image"))){
            p.put("code_image", FileUpload.findLocalFileUrl(p.getString("code_image")));
        }else{
            p.put("code_image","");
        }
        pd.put("remove_logo", "N");
//        List<PageData> dictionaryList =dictionaryService.findList(pd);
//        mv.addObject("dictionaryList", dictionaryList);
        mv.addObject("p", p);
        mv.addObject("pd", pd);
        mv.setViewName("partners/partners/partners_info");
        return mv;
    }

    /**
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
        pd.put("type", parameterUtil.partners_user);
        page.setPd(pd);
        List<PageData> list = partnersInfoService.findTrashPage(page);
        mv.addObject("list",list);
        mv.addObject("pd", pd);
        mv.setViewName("partners/partners/partners_trash");
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
    @RequestMapping(value = "/clearUser")
    public void clearUser(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
        pd = new PageData(request);
        pd.put("type", parameterUtil.partners_user);
        partnersInfoService.clearUser(pd);
        this.writeJson(response, true);
    }

    /**
     * @Title: revertUser
     * @Description: 还原删除信息
     * @param @param request
     * @param @param session
     * @param @param response
     * @param @throws Exception    设定文件
     * @return void    返回类型
     * @throws
     */
    @RequestMapping(value = "/revertUser")
    public void revertUser(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
        pd = new PageData(request);
        pd.put("type", parameterUtil.partners_user);
        partnersInfoService.revertUser(pd);
        this.writeJson(response, true);
    }
    /**
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
        partnersInfoService.update(pd);
        this.writeJson(response, true);
    }

    /**
     *
     * @Title: findServerTypes
     * @Description: 设置用户角色信息
     * @param @param request
     * @param @param session
     * @param @param page
     * @param @return
     * @param @throws Exception    设定文件
     * @return ModelAndView    返回类型
     * @throws
     */
    @RequestMapping(value = "/toServerCategory")
    public ModelAndView findServerTypesByPartners(HttpServletRequest request,HttpSession session) throws Exception {
        pd = new PageData(request);
        //查询服务分类
        mv.addObject("pd", pd);
        mv.setViewName("partners/partners/partners_serverCategory");
        return mv;
    }

    @RequestMapping(value = "/toUpdateServerCategory")
    public ModelAndView toUpdateServerCategory(HttpServletRequest request,HttpSession session) throws Exception {
        pd = new PageData(request);
        //查询服务分类
        mv.addObject("pd", pd);
        mv.setViewName("partners/partners/partners_serverCategory_update");
        return mv;
    }
    /**
     * 查询服务分类
     * @param request
     * @param session
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findServerCategory")
    public void findServerCategory(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
        pd = new PageData(request);
        //查询组织
        pd.put("remove_logo", "N");
        List<PageData> oraganizeList = partnersInfoService.findServerCategory(pd);
        this.writeJson(response, oraganizeList);
    }

    /**
     * 保存服务分类
     * @param session
     * @param response
     * @throws Exception
     */
    @RequestMapping(value="/saveServerCategory")
    public void saveServerCategory(HttpSession session,HttpServletResponse response) throws Exception{
        pd = new PageData(this.getRequest());
        String user_id=pd.getString("user_id");
        mv = getBaseMv(session,pd);
        String ids[] = pd.getString("category").split(",");
        String categoryName[] = pd.getString("categoryName").split(",");
        pd.put("user_id", user_id);
        partnersInfoService.delServerCategory(user_id);
        if(ids.length>0){
            for (int i= 0;i<ids.length;i++) {
                PageData p = new PageData();
                p.put("category_id", ids[i]);
                p.put("category_name", categoryName[i]);
                p.put("partner_userId", user_id);
                p.put("create_user", ((PageData)session.getAttribute(Const.SESSION_USER)).get("id"));
                partnersInfoService.saveServerCategory(p);
            }
        }
//        pd.put("remark",categoryName);
        pd.put("id", user_id);
        partnersInfoService.update(pd);
        this.writeJson(response, true);
    }
    /**
     *
     * @Title: checkLoginName
     * @Description: 验证登录名是否存在
     * @param @param session
     * @param @param response
     * @param @throws Exception    设定文件
     * @return void    返回类型
     * @throws
     */
    @RequestMapping(value="/checkLoginName")
    public void checkLoginName(HttpSession session,HttpServletResponse response) throws Exception{
        pd = new PageData(this.getRequest());
        boolean bol = true;
        if(pd.get("step") == null || "".equals(pd.get("step"))) {
            PageData p = partnersInfoService.findInfoByLoginName(pd);
            if(Verify.verifyIsNotNull(p)){
                bol = false;
            }
        }
        this.writeJson(response, bol);
    }
    @RequestMapping(value = "/findAuthentInfo")
    public ModelAndView findAuthentInfo(HttpServletRequest request,HttpSession session) throws Exception {
        pd = new PageData(request);
        PageData p = partnersInfoService.findAuthentInfo(pd);
        if(Verify.verifyIsNotNull(p)){
        	if(Verify.verifyIsNotNull(p.getString("authent_photo1"))){
                p.put("authent_photo1_url", FileUpload.findLocalFileUrl(p.getString("authent_photo1")));
            }else{
                p.put("authent_photo1_url","");
            }
            if(Verify.verifyIsNotNull(p.getString("authent_photo2"))){
                p.put("authent_photo2_url", FileUpload.findLocalFileUrl(p.getString("authent_photo2")));
            }else{
                p.put("authent_photo2_url","");
            }
            if(Verify.verifyIsNotNull(p.getString("authent_photo3"))){
                p.put("authent_photo3_url", FileUpload.findLocalFileUrl(p.getString("authent_photo3")));
            }else{
                p.put("authent_photo3_url","");
            }
            if(Verify.verifyIsNotNull(p.getString("admin_photo"))){
                p.put("admin_photo_url", FileUpload.findLocalFileUrl(p.getString("admin_photo")));
            }else{
                p.put("admin_photo_url","");
            }
        }
        
        mv.addObject("p", p);
        mv.addObject("pd", pd);
        mv.setViewName("partners/partners/partnersAuthent_info");
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
   @RequestMapping(value = "/toaddAuthent")
   public ModelAndView toaddAuthent(HttpServletRequest request,HttpSession session) throws Exception {
       pd = new PageData(request);
       PageData p = partnersInfoService.findAuthentInfo(pd);
       if(Verify.verifyIsNotNull(p)){
    	   if(Verify.verifyIsNotNull(p.getString("authent_photo1"))){
               p.put("authent_photo1_url", FileUpload.findLocalFileUrl(p.getString("authent_photo1")));
           }else{
               p.put("authent_photo1_url","");
           }
           if(Verify.verifyIsNotNull(p.getString("authent_photo2"))){
               p.put("authent_photo2_url", FileUpload.findLocalFileUrl(p.getString("authent_photo2")));
           }else{
               p.put("authent_photo2_url","");
           }
           if(Verify.verifyIsNotNull(p.getString("authent_photo3"))){
               p.put("authent_photo3_url", FileUpload.findLocalFileUrl(p.getString("authent_photo3")));
           }else{
               p.put("authent_photo3_url","");
           }
           if(Verify.verifyIsNotNull(p.getString("admin_photo"))){
               p.put("admin_photo_url", FileUpload.findLocalFileUrl(p.getString("admin_photo")));
           }else{
               p.put("admin_photo_url","");
           }
       }
       mv.addObject("p", p);
       mv.addObject("pd", pd);
       mv.setViewName("partners/partners/partnersAuthent_add");
       return mv;
   }
   
   
   
   
   @RequestMapping(value = "/saveAuthent")
   public ModelAndView saveAuthent(HttpServletRequest request,HttpSession session) throws Exception {
       pd = new PageData(request);
       
       if(Verify.verifyIsNotNull(pd.get("id"))){
       	   pd.putAll(FileUpload.uploadLocalFile(request, ParaUtil.system+ParaUtil.user, pd));
           pd.put("update_time", DateTimeUtil.getDateTimeStr());
           partnersInfoService.updateAuthent(pd);
       }else{
       	pd.putAll(FileUpload.saveLocalFile(request, ParaUtil.system+ParaUtil.user, pd));
           PageData user = (PageData)session.getAttribute(Const.SESSION_USER);
           pd.put("create_user", user.get("id"));
           pd.put("create_time", DateTimeUtil.getDateTime());
           pd.put("update_time", DateTimeUtil.getDateTime());
           partnersInfoService.saveAuthent(pd);
       }
       
       if(pd.getString("submit_type").equals("save_before")){
    	   mv.setViewName("redirect:/partners/partners/toadd.do?id="+pd.get("partner_id"));
       }else if(pd.getString("submit_type").equals("save")){
           mv.setViewName("redirect:/partners/partners/toaddAuthent.do?partner_id="+pd.get("partner_id"));
       }else if(pd.getString("submit_type").equals("save_next")){
           mv.setViewName("redirect:/partners/partners/toServerCategory.do?user_id="+pd.get("partner_id"));
       }
       pd.put("label", "b");
       mv.addObject("pd", pd);
       return mv;
   }
   
   
    
}
