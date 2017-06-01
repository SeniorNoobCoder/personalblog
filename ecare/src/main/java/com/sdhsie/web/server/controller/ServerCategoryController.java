package com.sdhsie.web.server.controller;

import com.sdhsie.base.controller.BaseController;
import com.sdhsie.base.util.*;
import com.sdhsie.base.util.upload.FileUpload;
import com.sdhsie.base.util.upload.ParaUtil;
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
 * Created by zcx on 2016/11/23.
 */
@Controller
@RequestMapping(value = "/server/category")
public class ServerCategoryController extends BaseController {
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
    public void treeList(HttpServletRequest request,HttpServletResponse response) throws Exception {
        pd = new PageData(request);
        pd.put("remove_logo", "N");//删除标识---未删除
        pd.put("type",parameterUtil.server_category_category);
        List<PageData> categoryList = categoryService.findList(pd);
        for (PageData p:categoryList){
            if(Verify.verifyIsNotNull(p.getString("image"))){
                p.put("image", FileUpload.findLocalFileUrl(p.getString("image")));
            }else{
                p.put("image","");
            }
        }
        this.writeJson(response, categoryList);
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
        pd.put("type",parameterUtil.server_category_category);
        page.setPd(pd);
        List<PageData> categoryList = categoryService.findListPage(page);
        //查询垃圾箱数量
        int num = categoryService.findListNum(pd);
        pd.put("num", num);
        mv.addObject("pd", pd);
        mv.addObject("categoryList", categoryList);
        mv.addObject("power",getRoleButton(session));
        mv.setViewName("server/category/category_list");
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
        pd.put("type",parameterUtil.server_category_category);
        page.setPd(pd);
        List<PageData> categoryList = categoryService.findListPage(page);
        //查询垃圾箱数量
        int num = categoryService.findListNum(pd);
        pd.put("num", num);
        mv.addObject("pd", pd);
        mv.addObject("categoryList", categoryList);
        mv.addObject("power",getRoleButton(session));
        mv.setViewName("server/category/category_trash");
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
        mv.setViewName("server/category/category_add");
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
        pd.putAll(FileUpload.saveLocalFile(request, ParaUtil.system+ParaUtil.serverCategory, pd));
        PageData user = (PageData)session.getAttribute(Const.SESSION_USER);
        pd.put("create_user", user.get("id"));
//        if(Verify.verifyIsNotNull(pd.getString("image"))){
//            pd.put("image", FileUpload.findLocalFileUrl(pd.getString("image")));
//        }else{
//            pd.put("image","");
//        }
        pd.put("type",parameterUtil.server_category_category);
        pd.put("remove_logo", "N");
        pd.put("create_time", DateTimeUtil.getDateTime());
        pd.put("update_time", DateTimeUtil.getDateTime());
        categoryService.save(pd);
        pd.put("label", "add");
        mv.addObject("pd", pd);
        mv.setViewName("server/category/category_add");
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
        String parentName = pd.getString("parentName");
        mv = getBaseMv(session, pd);
        pd =  categoryService.findInfo(pd);
        if(Verify.verifyIsNotNull(pd.getString("image"))){
            pd.put("image", FileUpload.findLocalFileUrl(pd.getString("image")));
        }else{
            pd.put("image","");
        }
        pd.put("parentName",parentName);
        mv.addObject("pd", pd);
        mv.setViewName("server/category/category_edit");
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
    public ModelAndView update(HttpServletRequest request) throws Exception {
        pd = new PageData(request);
        pd.putAll(FileUpload.uploadLocalFile(request, ParaUtil.system+ParaUtil.serverCategory, pd));
//        if(Verify.verifyIsNotNull(pd.getString("image"))){
//            pd.put("image", FileUpload.findLocalFileUrl(pd.getString("image")));
//        }else{
//            pd.put("image","");
//        }
        categoryService.update(pd);
        pd.put("label", "update");
        mv.addObject("pd", pd);
        mv.setViewName("server/category/category_edit");
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
    public void del(HttpServletRequest request,HttpServletResponse response) throws Exception {
        pd = new PageData(request);
        String remove_logo = pd.getString("remove_logo");
        String[] ids = pd.getString("ids").split(",");
        for (String id : ids) {
            PageData p = new PageData();
            p.put("id", id);
            p.put("remove_logo", remove_logo);
            categoryService.update(p);
        }
        pd.put("code",true);
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
        String parentName = pd.getString("parentName");
        mv = getBaseMv(session, pd);
        pd =  categoryService.findInfo(pd);
        pd.put("parentName",parentName);
        mv.addObject("pd", pd);
        mv.setViewName("server/category/category_info");
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
        categoryService.delete(ids);
        this.writeJson(response, true);
    }
}