package com.sdhsie.web.order.controller;

import com.sdhsie.base.controller.BaseController;
import com.sdhsie.base.util.*;
import com.sdhsie.base.util.upload.FileUpload;
import com.sdhsie.base.util.upload.ParaUtil;
import com.sdhsie.web.customer.service.CustomerService;
import com.sdhsie.web.order.service.CallRecordService;
import com.sdhsie.web.order.service.OrderService;
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
 * Created by zcx on 2016/11/29.
 */
@Controller
@RequestMapping(value = "/order/callRecord")
public class CallRecordController extends BaseController {
    private PageData pd;
    @Autowired
    private CallRecordService callRecordService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderService orderService;


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
//        pd.put("remove_logo", "N");
        page.setPd(pd);
        List<PageData> callRecordList = callRecordService.findListPage(page);
        mv.addObject("pd", pd);
        mv.addObject("callRecordList", callRecordList);
        mv.addObject("power",getRoleButton(session));
        mv.setViewName("order/callRecord/callRecord_list");
        return mv;
    }
    /**
     * 受理
     */
    @RequestMapping(value = "/acceptCall")
    public void  acceptCall(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
        pd = new PageData(request);
        PageData user = (PageData)session.getAttribute(Const.SESSION_USER);
        pd.put("operator_id", user.get("id"));
        pd.put("operator_name", user.get("user_name"));
        pd.put("status", "1");
        pd.put("remove_logo", "N");
        callRecordService.updateStatus(pd);
        this.writeJson(response, true);
    }

    /**
     * 撤回受理电话
     */
    @RequestMapping(value = "/replyCall")
    public void  replyCall(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
        pd = new PageData(request);
        PageData user = (PageData)session.getAttribute(Const.SESSION_USER);
        pd.put("operator_id","");
        pd.put("operator_name", "");
        pd.put("status", "0");
        pd.put("remove_logo", "N");
        callRecordService.updateStatus(pd);
        this.writeJson(response, true);
    }

    /**
     * 跳转到订单添加页面
     */
    @RequestMapping(value = "/toAddOrderByCall")
    public ModelAndView toAddOrderByCall(HttpServletRequest request, HttpSession session, Page page) throws Exception{
        pd = new PageData(request);
        //获取设备基本信息
        PageData customerPd = new PageData();
        customerPd.put("sn",pd.get("customer_sn"));
        PageData customer = customerService.findInfo(customerPd);
        mv = getBaseMv(session, pd);
        mv.addObject("pd", pd);
        mv.addObject("customer", customer);
        mv.addObject("power",getRoleButton(session));
        mv.setViewName("order/callRecord/order_addByCall");
        return mv;
    }

    /**
     * 保存订单
     */
    @RequestMapping(value = "/saveOrderByCall")
    public ModelAndView saveOrderByCall(HttpServletRequest request,HttpSession session) throws Exception {
        pd = new PageData(request);
        //判断一下电话是否被受理完成了，受理过的电话记录不能重复受理
        PageData call = callRecordService.findInfo(pd.get("callRecordId").toString());
        if(call!=null && !"Y".equals(call.get("remove_logo").toString())) {
            PageData user = (PageData) session.getAttribute(Const.SESSION_USER);
            pd.put("create_user", user.get("id"));
            pd.put("remove_logo", "N");
            pd.put("status", "1");
            pd.put("others_server", "Y");//是否接收其他人服务
            pd.put("order_source", parameterUtil.order_source_telemarketer);//客服
            pd.put("order_no", "S-" + GuidUtil.geOrderNo());
            orderService.saveOrderByTelemarketer(pd);
//            callRecordService.acceptedOrder(pd.get("callRecordId").toString());
            pd.put("label", "b");
        }else {
            pd.put("label", "false");
        }
        mv.addObject("pd", pd);
        mv.setViewName("order/callRecord/order_addByCall");
        return mv;
    }
}