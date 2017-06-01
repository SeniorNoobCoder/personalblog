package com.sdhsie.web.common.controller;

import com.sdhsie.base.controller.BaseController;
import com.sdhsie.base.util.Const;
import com.sdhsie.base.util.Page;
import com.sdhsie.base.util.PageData;
import com.sdhsie.web.common.service.CommonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by zcx on 2017/1/24.
 */
@Controller
@RequestMapping(value = "/common/info")
public class CommonInfoController extends BaseController{
    @Autowired
    private CommonInfoService commonInfoService;
    private PageData pd;

    /**
     * 查询列表
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
//        pd.put("remove_logo", "N");
//        pd.put("info_type","register");
        page.setPd(pd);
        List<PageData> mesList = commonInfoService.findListPage(page);
        mv.addObject("pd", pd);
        mv.addObject("mesList", mesList);
        mv.addObject("power",getRoleButton(session));
        mv.setViewName("common/info/agreement_list");
        return mv;
    }

    /**
     * 查询详情
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findAgreementInfo")
    public ModelAndView findInfo(HttpServletRequest request,HttpSession session) throws Exception {
        pd = new PageData(request);
        PageData p = commonInfoService.findInfo(pd);
        pd.put("remove_logo", "N");
        mv.addObject("p", p);
        mv.addObject("pd", pd);
        mv.setViewName("common/info/agreement_info");
        return mv;
    }

    /**
     *保存信息
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView save(HttpServletRequest request,HttpSession session) throws Exception {
        pd = new PageData(request);
        PageData user = (PageData)session.getAttribute(Const.SESSION_USER);
        pd.put("operator_id", user.get("id"));
        pd.put("status", "0");
        commonInfoService.save(pd);
        pd.put("label", "b");
        mv.addObject("pd", pd);
        mv.setViewName("common/info/agreement_add");
        return mv;
    }

    /**
     * 跳转到注册协议页面
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/toAgreementAdd")
    public ModelAndView toadd(HttpServletRequest request,HttpSession session) throws Exception {
        pd = new PageData(request);
        mv.addObject("pd", pd);
        mv.setViewName("common/info/agreement_add");
        return mv;
    }

    /**
     *跳转到修改页面
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/toAgreementEdit")
    public ModelAndView toedit(HttpServletRequest request,HttpSession session) throws Exception {
        pd = new PageData(request);
        PageData p = commonInfoService.findInfo(pd);
        mv.addObject("p", p);
        mv.addObject("pd", pd);
        mv.setViewName("common/info/agreement_edit");
        return mv;
    }

    /**
     * 修改
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateAgreement")
    public ModelAndView updateAgreement(HttpServletRequest request,HttpSession session) throws Exception {
        pd = new PageData(request);
        if(pd.getString("isPublish").equals("yes")){
            pd.put("publish_content",pd.get("content"));
        }
        commonInfoService.update(pd);
        pd.put("label", "b");
        mv.addObject("pd", pd);
        mv.setViewName("common/info/agreement_edit");
        return mv;
    }
}
