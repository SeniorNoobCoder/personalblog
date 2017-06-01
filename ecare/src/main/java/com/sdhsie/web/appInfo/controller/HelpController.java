package com.sdhsie.web.appInfo.controller;

import com.sdhsie.base.controller.BaseController;
import com.sdhsie.base.util.*;
import com.sdhsie.base.util.upload.FileUpload;
import com.sdhsie.base.util.upload.ParaUtil;
import com.sdhsie.web.appInfo.service.HelpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by zcx on 2017/1/16.
 */
@Controller
@RequestMapping(value = "/appInfo/help")
public class HelpController extends BaseController {
    @Autowired
    private HelpService helpService;
    private PageData pd;
    /**
     * 查询帮助中心
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
        pd.put("remove_logo", "N");
        page.setPd(pd);
        List<PageData> helpList = helpService.findListPage(page);
        //查询垃圾箱数量
        mv.addObject("pd", pd);
        mv.addObject("helpList", helpList);
        mv.addObject("power",getRoleButton(session));
        mv.setViewName("appInfo/help/help_list");
        return mv;
    }

    /**
     * 添加
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/toadd")
    public ModelAndView toadd(HttpServletRequest request,HttpSession session) throws Exception {
        pd = new PageData(request);

        mv.addObject("pd", pd);
        mv.setViewName("appInfo/help/help_add");
        return mv;
    }

    /**
     * 保存
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView save(HttpServletRequest request,HttpSession session) throws Exception {
        pd = new PageData(request);
        pd.putAll(FileUpload.uploadLocalFile(request, ParaUtil.family+ParaUtil.customer, pd));
        pd.put("status", "N");
        PageData user = (PageData) session.getAttribute(Const.SESSION_USER);
        pd.put("create_user", user.get("id"));
        helpService.save(pd);
        pd.put("label", "b");
        mv.addObject("pd", pd);
        mv.setViewName("appInfo/help/help_add");
        return mv;
    }

    /**
     * 删除
     * @param request
     * @param session
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/del")
    public void del(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
        pd = new PageData(request);
        String[] ids = pd.getString("ids").split(",");
        for (String id : ids) {
            helpService.delete(id);
        }
        this.writeJson(response, true);
    }

    @RequestMapping(value = "/updateStatus")
    public void updateStatus(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws IOException {
        pd = new PageData(request);
        helpService.update(pd);
        this.writeJson(response, true);
    }

    /**
     * 查询详情
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findInfo")
    public ModelAndView findInfo(HttpServletRequest request,HttpSession session) throws Exception {
        pd = new PageData(request);
        PageData p = helpService.findInfo(pd);
        mv.addObject("p", p);
        mv.addObject("pd", pd);
        mv.setViewName("appInfo/help/help_info");
        return mv;
    }
    /**
     * 添加
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/toedit")
    public ModelAndView toedit(HttpServletRequest request,HttpSession session) throws Exception {
        pd = new PageData(request);
        PageData p = helpService.findInfo(pd);
        mv.addObject("pd", pd);
        mv.addObject("p", p);
        mv.setViewName("appInfo/help/help_edit");
        return mv;
    }

    /**
     * 保存
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/update")
    public ModelAndView update(HttpServletRequest request,HttpSession session) throws Exception {
        pd = new PageData(request);
        helpService.update(pd);
        pd.put("label", "b");
        mv.addObject("pd", pd);
        mv.setViewName("appInfo/help/help_edit");
        return mv;
    }
}
