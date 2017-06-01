package com.sdhsie.web.server.controller;

import com.sdhsie.base.controller.BaseController;
import com.sdhsie.base.util.*;
import com.sdhsie.base.util.upload.FileUpload;
import com.sdhsie.base.util.upload.ParaUtil;
import com.sdhsie.web.server.service.ServerCategoryDetailService;
import com.sdhsie.web.server.service.ServerCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by zcx on 2016/12/1.
 */
@Controller
@RequestMapping(value = "/server/categoryDetail")
public class ServerCategoryDetailController extends BaseController {
    @Autowired
    private ServerCategoryDetailService categoryDetailService;
    @Autowired
    private ServerCategoryService categoryService;
    private PageData pd;

    /**
     * 查询未删除的list列表
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/treeList")
    public void treeList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        pd = new PageData(request);
        pd.put("remove_logo", "N");//删除标识---未删除
        pd.put("type",parameterUtil.server_category_detail);
        List<PageData> categoryDetailList = categoryService.findList(pd);
        this.writeJson(response, categoryDetailList);
    }
    /**
     * 查询未删除的list列表
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/detailList")
    public void detailList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        pd = new PageData(request);
        pd.put("remove_logo", "N");//删除标识---未删除
        List<PageData> categoryDetailList = categoryDetailService.findList(pd);
        this.writeJson(response, categoryDetailList);
    }
    /**
     * 查询
     * @param request
     * @param session
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findList")
    public ModelAndView findList(HttpServletRequest request, HttpSession session, Page page) throws Exception {
        pd = new PageData(request);
        mv = getBaseMv(session, pd);
        pd.put("remove_logo", "N");//删除标识---未删除
        pd.put("type",parameterUtil.server_category_detail);
        page.setPd(pd);
        List<PageData> categoryDetailList = categoryDetailService.findList(pd);//查询工作分类
        List<PageData> jobList = categoryService.findList(pd);//查询工作详情

        //查询垃圾箱数量
        int num = categoryDetailService.findListNum(pd);
        pd.put("num", num);
        mv.addObject("pd", pd);
//        mv.addObject("jobList", jobList);
//        mv.addObject("categoryDetailList", categoryDetailList);
        mv.addObject("power",getRoleButton(session));
        mv.setViewName("server/categoryDetail/categoryDetail_list");
        return mv;
    }
    /**
     * 查询垃圾箱
     * @param request
     * @param session
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/trashList")
    public ModelAndView trashList(HttpServletRequest request, HttpSession session, Page page) throws Exception {
        pd = new PageData(request);
        mv = getBaseMv(session, pd);
        pd.put("remove_logo", "Y");//删除标识---未删除
        page.setPd(pd);
        List<PageData> categoryList = categoryDetailService.findListPage(page);
        //查询垃圾箱数量
        int num = categoryDetailService.findListNum(pd);
        pd.put("num", num);
        mv.addObject("pd", pd);
        mv.addObject("categoryList", categoryList);
        mv.addObject("power",getRoleButton(session));
        mv.setViewName("server/categoryDetail/categoryDetail_trash");
        return mv;
    }
    /**
     *
     * @Title: toadd
     * @Description: 去新增页面
     * @param @param request
     * @param @return
     * @param @throws Exception    设定文件
     * @return ModelAndView    返回类型
     * @throws
     */
    @RequestMapping(value="/toadd")
    public ModelAndView toadd(HttpServletRequest request)throws Exception{
        mv.clear();
        pd = new PageData(request);
        mv.addObject("pd", pd);
        mv.setViewName("server/categoryDetail/categoryDetail_add");
        return mv;
    }

    /**
     *
     * @Title: toadd
     * @Description: 去新增页面
     * @param @param request
     * @param @return
     * @param @throws Exception    设定文件
     * @return ModelAndView    返回类型
     * @throws
     */
    @RequestMapping(value="/toAddDetail")
    public ModelAndView toAddDetail(HttpServletRequest request)throws Exception{
        mv.clear();
        pd = new PageData(request);
        mv.addObject("pd", pd);
        mv.setViewName("server/categoryDetail/jobDetail_add");
        return mv;
    }
    /**
     *
     * @Title: saveCategory
     * @Description: 保存工作分类信息
     * @param @param request
     * @param @param session
     * @param @return
     * @param @throws Exception    设定文件
     * @return ModelAndView    返回类型
     * @throws
     */
    @RequestMapping(value = "/saveCategoryDetail")
    public ModelAndView saveCategoryDetail(HttpServletRequest request,HttpSession session) throws Exception {
        pd = new PageData(request);
        PageData user = (PageData)session.getAttribute(Const.SESSION_USER);
        pd.put("create_user", user.get("id"));
        pd.put("remove_logo", "N");
        pd.put("type",parameterUtil.server_category_detail);
        categoryDetailService.saveCategoryDetail(pd);
        pd.put("label", "add");
        mv.addObject("pd", pd);
        mv.setViewName("server/categoryDetail/categoryDetail_add");
        return mv;
    }
    /**
     *
     * @Title: save
     * @Description: 保存工作描述信息
     * @param @param request
     * @param @param session
     * @param @return
     * @param @throws Exception    设定文件
     * @return ModelAndView    返回类型
     * @throws
     */
    @RequestMapping(value = "/saveJobDetail")
    public ModelAndView saveJobDetail(HttpServletRequest request,HttpSession session) throws Exception {
        pd = new PageData(request);
        PageData user = (PageData)session.getAttribute(Const.SESSION_USER);
        pd.put("create_user", user.get("id"));
        pd.put("remove_logo", "N");
        pd.put("is_approve",parameterUtil.price_approve);
        String price = pd.get("price").toString();
        if(!"".equals(price)&& price!=null){
            pd.put("price",price);
        }else {
            pd.put("price","0");
        }
        String charge_mode_time = pd.getString("charge_mode_time");
        if(!"".equals(charge_mode_time)&& charge_mode_time!=null){
            pd.put("charge_mode_time",charge_mode_time);
        }else {
            pd.put("charge_mode_time","0");
        }
        categoryDetailService.save(pd);
        pd.put("label", "add");
        mv.addObject("pd", pd);
        mv.setViewName("server/categoryDetail/jobDetail_add");
        return mv;
    }
    /**
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
        String parentName = pd.getString("parentName");
        mv = getBaseMv(session, pd);
        pd =  categoryService.findInfo(pd);
        pd.put("parentName",parentName);
        mv.addObject("pd", pd);
        mv.setViewName("server/categoryDetail/categoryDetail_edit");
        return mv;
    }
    /**
     * @Title: toedit
     * @Description: 跳转到编辑页面
     * @param @param request
     * @param @param session
     * @param @return
     * @param @throws Exception    设定文件
     * @return ModelAndView    返回类型
     * @throws
     */
    @RequestMapping(value = "/toEditJobDetail")
    public ModelAndView toEditJobDetail(HttpServletRequest request,HttpSession session) throws Exception {
        pd = new PageData(request);
        String parentName = pd.getString("parentName");
        mv = getBaseMv(session, pd);
        pd =  categoryDetailService.findInfo(pd);
        pd.put("server_category_name",parentName);
        mv.addObject("pd", pd);
        mv.setViewName("server/categoryDetail/jobDetail_edit");
        return mv;
    }

    /**
     * 修改工作详情
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateJobDetail")
    public ModelAndView updateJobDetail(HttpServletRequest request) throws Exception {
        pd = new PageData(request);
        categoryDetailService.update(pd);
        pd.put("label", "update");
        mv.addObject("pd", pd);
        mv.setViewName("server/categoryDetail/jobDetail_edit");
        return mv;
    }

    /**
     * 修改工作分类信息
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateCategory")
    public ModelAndView updateCategory(HttpServletRequest request) throws Exception {
        pd = new PageData(request);
        categoryService.update(pd);
        pd.put("label", "update");
        mv.addObject("pd", pd);
        mv.setViewName("server/categoryDetail/categoryDetail_edit");
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
    @RequestMapping(value = "/delJobDetail")
    public void delJobDetail(HttpServletRequest request,HttpServletResponse response) throws Exception {
        pd = new PageData(request);
        String remove_logo = pd.getString("remove_logo");
        String server_category_id = pd.getString("server_category_id");
        String[] ids = pd.getString("ids").split(",");
        for (String id : ids) {
            PageData p = new PageData();
            p.put("id", id);
            p.put("remove_logo", remove_logo);
            categoryDetailService.update(p);
        }
        pd.put("server_category_id",server_category_id);
        pd.put("code",true);
        this.writeJson(response, pd);
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
    @RequestMapping(value = "/delCategory")
    public void delCategory(HttpServletRequest request,HttpServletResponse response) throws Exception {
        pd = new PageData(request);
        boolean result = true;
        String remove_logo = pd.getString("remove_logo");
        String parent_id = pd.getString("parent_id");
        String[] ids = pd.getString("ids").split(",");
        for (String id : ids) {
            PageData jobPage = new PageData();
            jobPage.put("server_category_id",id);
            jobPage.put("remove_logo","N");
            List<PageData> jobList = categoryDetailService.findList(jobPage);
            //判断工作分类下面是否还有工作描述。如果有先删除工作描述后再删除工作分类
            if (jobList.size()>0) {
                result = false;
            }else {
                PageData p = new PageData();
                p.put("id", id);
                p.put("remove_logo", remove_logo);
                categoryService.update(p);
            }
        }
        pd.put("parent_id",parent_id);
        pd.put("code",result);
        this.writeJson(response, pd);
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
        mv = getBaseMv(session, pd);
        pd =  categoryDetailService.findInfo(pd);
        mv.addObject("pd", pd);
        mv.setViewName("server/categoryDetail/category_info");
        return mv;
    }

    /**
     * @Title: del
     * @Description: 彻底删除信息
     * @param @param request
     * @param @param session
     * @param @param response
     * @param @throws Exception    设定文件
     * @return void    返回类型
     * @throws
     */
    @RequestMapping(value = "/clear")
    public void clear(HttpServletRequest request,HttpServletResponse response) throws Exception {
        pd = new PageData(request);
        String[] ids = pd.getString("ids").split(",");
        categoryDetailService.delete(ids);
        this.writeJson(response, true);
    }
}
