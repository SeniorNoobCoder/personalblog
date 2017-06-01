package com.sdhsie.web.order.controller;
import com.sdhsie.base.controller.BaseController;
import com.sdhsie.base.util.*;
import com.sdhsie.base.util.upload.FileUpload;
import com.sdhsie.push.MessageBean;
import com.sdhsie.push.PushUtil;
import com.sdhsie.web.customer.service.CustomerService;
import com.sdhsie.web.order.service.CallRecordService;
import com.sdhsie.web.order.service.OrderFlowService;
import com.sdhsie.web.order.service.OrderJobService;
import com.sdhsie.web.order.service.OrderService;
import com.sdhsie.web.partners.service.BusinessService;
import com.sdhsie.web.visiting.service.VisitingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zcx on 2016/11/29.
 */
@Controller
@RequestMapping(value = "/order/order")
public class OrderController extends BaseController {
    private PageData pd;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CallRecordService callRecordService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderFlowService orderFlowService;
    @Autowired
    private OrderJobService orderJobService;
    @Autowired
    private VisitingService visitingService;
    @Autowired
    private BusinessService businessService;
    /**
     * 订单查询
     * @param request
     * @param session
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findList")
    public ModelAndView findList(HttpServletRequest request, HttpSession session, Page page) throws Exception {
        mv.clear();
        pd = new PageData(request);
        mv = getBaseMv(session, pd);
        pd.put("remove_logo", "N");
        page.setPd(pd);
        List<PageData> userList = orderService.findListPage(page);
        mv.addObject("pd", pd);
        mv.addObject("userList", userList);
        mv.addObject("power",getRoleButton(session));
        mv.setViewName("order/order/order_list");
        return mv;
    }

    @RequestMapping(value = "/findWarningList")
    public ModelAndView findWarningList(HttpServletRequest request, HttpSession session, Page page) throws Exception {
        mv.clear();
        pd = new PageData(request);
        mv = getBaseMv(session, pd);
        pd.put("remove_logo", "N");
        pd.put("warning", "Y");
//        pd.put("status", "1");
        page.setPd(pd);
        List<PageData> userList = orderService.findListPage(page);
        mv.addObject("pd", pd);
        mv.addObject("userList", userList);
        mv.addObject("power",getRoleButton(session));
        mv.setViewName("order/order/order_warningList");
        return mv;
    }

    /**
     * 客服查询我处理过的的订单
     * @param request
     * @param session
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findListByUserId")
    public ModelAndView findListByUserId(HttpServletRequest request, HttpSession session, Page page) throws Exception {
        mv.clear();
        PageData user = (PageData) session.getAttribute(Const.SESSION_USER);
        pd = new PageData(request);
        mv = getBaseMv(session, pd);
//        pd.put("remove_logo", "N");
        pd.put("create_user", user.get("id"));
        pd.put("order_source", "telemarketer");
        page.setPd(pd);
        List<PageData> userList = orderService.findListPage(page);
        mv.addObject("pd", pd);
        mv.addObject("userList", userList);
        mv.addObject("power",getRoleButton(session));
        mv.setViewName("order/order/order_list_my");
        return mv;
    }
    /**
     * 跳转到订单添加页面
     */
    @RequestMapping(value = "/toAdd")
    public ModelAndView toAdd(HttpServletRequest request, HttpSession session, Page page) throws Exception {
        mv.clear();
        pd = new PageData(request);
        mv = getBaseMv(session, pd);
        pd.put("remove_logo", "N");
//        page.setPd(pd);
//        List<PageData> customerList = customerService.findListPage(page);
        List<PageData> callRecordList = callRecordService.findList(pd);
        mv.addObject("pd", pd);
//        mv.addObject("customerList", customerList);
        mv.addObject("callRecordList", callRecordList);
        mv.addObject("power",getRoleButton(session));
        mv.setViewName("order/order/order_add");
        return mv;
    }
    /**
     * 跳转到订单添加页面
     */
    @RequestMapping(value = "/callRecordList")
    public void callRecordList(HttpServletRequest request,HttpServletResponse response, Page page) throws Exception {
        mv.clear();
        pd = new PageData(request);
        pd.put("remove_logo", "N");
        List<PageData> callRecordList = callRecordService.findList(pd);
        Map<String,Object> m = new HashMap<String,Object>();
        m.put("isTrue", true);
        m.put("callRecordPd", pd);
        m.put("callRecordList", callRecordList);
        m.put("page", page);
        this.writeJson(response, m);
    }
    /**
     * 跳转到订单添加页面
     */
    @RequestMapping(value = "/customerList")
    public void customerList(HttpServletRequest request,HttpServletResponse response, Page page) throws Exception {
        mv.clear();
        pd = new PageData(request);
        pd.put("remove_logo", "N");
//        page.setPd(pd);
        List<PageData> customerList = customerService.findList(pd);
        Map<String,Object> m = new HashMap<String,Object>();
        m.put("isTrue", true);
        m.put("callRecordPd", pd);
        m.put("customerList", customerList);
        m.put("page", page);
        this.writeJson(response, m);
    }
    /**
     * 跳转到订单详情页面
     */
    @RequestMapping(value = "/findInfo")
    public ModelAndView findInfo(HttpServletRequest request,HttpSession session) throws Exception {
        mv.clear();
        pd = new PageData(request);
        PageData p = orderService.findInfo(pd);
        List<PageData> orderFlows = orderFlowService.findListByOrderNo(p.get("order_no").toString());
        List<PageData> orderJobs = orderJobService.findListByOrderNo(p.get("order_no").toString());

        pd.put("remove_logo", "N");
        mv.addObject("p", p);
        mv.addObject("pd", pd);
        mv.addObject("orderFlows",orderFlows);
        mv.addObject("orderJobs",orderJobs);
        mv.setViewName("order/order/order_info");
        return mv;
    }
    /**
     * 客服取消订单
     */
    @RequestMapping(value="/cancelOrder")
    public void cancelOrder(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception{
        pd = new PageData(request);
        orderService.updateStatus(pd);
        //保存订单流程
        PageData user = (PageData) session.getAttribute(Const.SESSION_USER);
        this.saveOrderFlow(pd,user,"0");
        this.writeJson(response, true);
    }
    /**
     * 客户取消已受理的订单
     */
    @RequestMapping(value="/cancelOrderByCustomer")
    public void cancelOrderByCustomer(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception{
        pd = new PageData(request);
        orderService.updateStatus(pd);
        //保存订单流程
        PageData user = (PageData) session.getAttribute(Const.SESSION_USER);
        pd.put("remark","客户毁单");
        this.saveOrderFlow(pd,user,"7");
        this.writeJson(response, true);
    }

    /**
     * 删除订单
     */
    @RequestMapping(value="/del")
    public void del(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception{
        pd = new PageData(request);
        pd.put("remove_logo","Y");
        pd.put("status","9");
        orderService.updateStatus(pd);
        //保存订单流程
        PageData user = (PageData) session.getAttribute(Const.SESSION_USER);
        this.saveOrderFlow(pd,user,"9");
        this.writeJson(response, true);
    }

    /**
     * 跳转到电话提交订单页面
     */
    @RequestMapping(value = "/toAddOrderByCall")
    public ModelAndView toAddOrderByCall(HttpServletRequest request, HttpSession session, Page page) throws Exception{
        pd = new PageData(request);
        //获取设备基本信息
        PageData customerPd = new PageData();
        customerPd.put("sn",pd.get("sn"));
        PageData customer = customerService.findInfo(customerPd);
        if(Verify.verifyIsNotNull(customer.getString("head_address"))){
            customer.put("head_address", FileUpload.findLocalFileUrl(customer.getString("head_address")));
        }else{
            customer.put("head_address","");
        }
        mv = getBaseMv(session, pd);
        mv.addObject("pd", pd);
        mv.addObject("customer", customer);
        mv.addObject("power",getRoleButton(session));
        mv.setViewName("order/order/order_addByCall");
        return mv;
    }
    /**
     * 根据通话记录添加订单
     */
    @RequestMapping(value = "/saveOrderByCall")
    public void saveOrderByCall(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
        pd = new PageData(request);
        //判断一下电话是否被受理完成了，受理过的电话记录不能重复受理
        PageData call = callRecordService.findInfo(pd.get("callRecordId").toString());
        Boolean isTrue = false;
        if(call!=null && !"Y".equals(call.get("remove_logo").toString())) {
            PageData user = (PageData) session.getAttribute(Const.SESSION_USER);
            this.addOrder(pd,user);//添加订单
            this.callRecordAccepted(pd.get("callRecordId").toString(),"order","已经为用户下订单");//电话记录处理
            //保存订单流程
            this.saveOrderFlow(pd,user,"1");
            isTrue = true;
        }
        this.writeJson(response, isTrue);
    }

    /**
     *电话记录备注
     */
    public void callRecordAccepted(String id,String type,String remark){
        PageData callPd = new PageData();
        callPd.put("id",id);
        callPd.put("remark",remark);
        callPd.put("type",type);
        callRecordService.acceptedOrder(callPd);//更改电话记录状态
    }

    /**
     * 根据用户信息添加订单
     */
    @RequestMapping(value = "/saveOrderByCustomer")
    public void saveOrderByCustomer(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
        pd = new PageData(request);
        PageData user = (PageData) session.getAttribute(Const.SESSION_USER);
        this.addOrder(pd,user);
        //保存订单流程
        this.saveOrderFlow(pd,user,"1");
        this.writeJson(response, true);
    }

    /**
     * 保存订单
     * @param pd
     * @param user
     */
    public  void saveOrderFlow(PageData pd,PageData user,String status){
        //保存订单流程
        PageData flowPd = new PageData();
        flowPd.put("order_no",pd.get("order_no"));
        flowPd.put("status",status);
        flowPd.put("operator_id",user.get("id"));
        flowPd.put("operator_name","系统");
        orderFlowService.save(flowPd);
    }

    /**
     * 添加订单
     */
    public void addOrder(PageData pd,PageData user){
        pd.put("create_user", user.get("id"));
        pd.put("remove_logo", "N");
        pd.put("status", "1");
        pd.put("others_server", "Y");//是否接收其他人服务
        pd.put("order_source", parameterUtil.order_source_telemarketer);//客服
        pd.put("order_no", "S-" + GuidUtil.geOrderNo());
        MessageBean ms = new MessageBean("","订单消息","有用户下订单啦！", Config.getValue("ORDER"));
        orderService.saveOrderByTelemarketer(pd);//添加订单

        //===========   查询符合条件的商户并推送订单消息 start =================
        //        PushUtil.sendPushBusinessYIsWorkAlias(ms);
        List<PageData> businessList = businessService.findBusinessIsWorkList(pd);
        if(businessList!=null && businessList.size()>0) {
            for (PageData business : businessList) {
                PushUtil.sendPushBussinessAlias(ms, business.get("session_id").toString());
            }
        }
        //===========   查询符合条件的商户并推送订单消息 end =================
    }

    /**
     * 跳转回访信息添加界面
     */
    @RequestMapping(value = "/toAddVisitingByCall")
    public ModelAndView toAddVisitingByCall(HttpServletRequest request, HttpSession session, Page page) throws Exception{
        pd = new PageData(request);
        //获取设备基本信息
        PageData customerPd = new PageData();
        customerPd.put("sn",pd.get("sn"));
        PageData customer = customerService.findInfo(customerPd);
        if(Verify.verifyIsNotNull(customer.getString("head_address"))){
            customer.put("head_address", FileUpload.findLocalFileUrl(customer.getString("head_address")));
        }else{
            customer.put("head_address","");
        }
        mv = getBaseMv(session, pd);
        mv.addObject("pd", pd);
        mv.addObject("customer", customer);
        mv.addObject("power",getRoleButton(session));
        mv.setViewName("order/order/visiting_addByCall");
        return mv;
    }
    /**
     * 根据通话记录添加回访记录
     */
    @RequestMapping(value = "/saveVisitingByCall")
    public void saveVisitingByCall(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
        pd = new PageData(request);
        //判断一下电话是否被受理完成了，受理过的电话记录不能重复受理
        PageData call = callRecordService.findInfo(pd.get("callRecordId").toString());
        Boolean isTrue = false;
        if(call!=null && !"Y".equals(call.get("remove_logo").toString())) {
            this.callRecordAccepted(pd.get("callRecordId").toString(),"visiting",pd.get("remark").toString());//电话记录处理
            //保存回访记录
            PageData user = (PageData) session.getAttribute(Const.SESSION_USER);
            pd.put("operator_id",user.get("id"));
            pd.put("operator_name",user.get("user_name"));
            pd.put("call_record_id",pd.get("callRecordId"));
            pd.put("type","call");//电话记录回访
            visitingService.save(pd);//保存回访记录
            isTrue = true;
        }
        this.writeJson(response, isTrue);
    }

    /**
     *
     */

    @RequestMapping(value = "/saveVisitingByCustomer")
    public void saveVisitingByCustomer(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception {
        pd = new PageData(request);
        //保存回访记录
        PageData user = (PageData) session.getAttribute(Const.SESSION_USER);
        pd.put("operator_id",user.get("id"));
        pd.put("operator_name",user.get("user_name"));
        pd.put("call_record_id",pd.get("callRecordId"));
        pd.put("type","customer");//电话记录回访
        visitingService.save(pd);//保存回访记录
        this.writeJson(response, true);
    }
}
