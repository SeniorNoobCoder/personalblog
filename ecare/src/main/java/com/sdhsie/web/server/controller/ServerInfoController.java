package com.sdhsie.web.server.controller;

import com.sdhsie.base.controller.BaseController;
import com.sdhsie.base.util.*;
import com.sdhsie.web.partners.service.PartnersInfoService;
import com.sdhsie.web.server.service.ServerCategoryDetailService;
import com.sdhsie.web.server.service.ServerCategoryService;
import com.sdhsie.web.server.service.ServerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 服务信息管理
 * Created by zcx on 2016/12/6.
 */
@Controller
@RequestMapping(value = "/server/info")
public class ServerInfoController extends BaseController {
    @Autowired
    private ServerCategoryDetailService categoryDetailService;
    @Autowired
    private ServerCategoryService categoryService;
    @Autowired
    private ServerInfoService serverInfoService;
    @Autowired
    private PartnersInfoService partnersInfoService;
    private PageData pd;

    /**
     * 分页查询
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
        page.setPd(pd);
        List<PageData> infoList = serverInfoService.findListPage(page);//查询工作分类
        mv.addObject("pd", pd);
        mv.addObject("infoList", infoList);
        mv.addObject("power",getRoleButton(session));
        mv.setViewName("server/info/info_list");
        return mv;
    }

    /**
     * 查看详情
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findInfo")
    public ModelAndView findInfo(HttpServletRequest request,HttpSession session) throws Exception {
        pd = new PageData(request);
        String partnerId = pd.getString("partner_id");
        mv = getBaseMv(session, pd);
        pd =  serverInfoService.findInfo(pd);
        PageData partnerPd = new PageData();
        partnerPd.put("id",partnerId);
        PageData partnerInfo  =partnersInfoService.findInfo(pd);
        mv.addObject("pd", pd);
        mv.addObject("partnerInfo", partnerInfo);
        mv.setViewName("server/info/info_detail");
        return mv;
    }

    /**
     * 审核
     */
    @RequestMapping(value = "/updateStatus")
    public void updateStatus(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
        pd = new PageData(request);
        String[] ids = pd.getString("ids").split(",");
        PageData user = (PageData)session.getAttribute(Const.SESSION_USER);
        pd.put("operator_id",user.get("id"));
        pd.put("operator_name",user.get("user_name"));
        for (String id : ids) {
            pd.put("id", id);
            serverInfoService.updateStatus(pd);
        }
        this.writeJson(response, true);
    }

    /**
     * 跳转添加界面
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/toadd")
    public ModelAndView toadd(HttpServletRequest request)throws Exception{
        mv.clear();
        pd = new PageData(request);
        mv.addObject("pd", pd);
        mv.setViewName("server/info/info_add");
        return mv;
    }

    /**
     *保存服务信息
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView save(HttpServletRequest request,HttpSession session) throws Exception {
        pd = new PageData(request);
        PageData user = (PageData)session.getAttribute(Const.SESSION_USER);
        pd.put("partner_id", user.get("id"));
        pd.put("partner_name", user.get("user_name"));
        pd.put("remove_logo", "N");
        pd.put("status","0");
        serverInfoService.save(pd);
        pd.put("label", "add");
        mv.addObject("pd", pd);
        mv.setViewName("server/info/info_add");
        return mv;
    }
}
