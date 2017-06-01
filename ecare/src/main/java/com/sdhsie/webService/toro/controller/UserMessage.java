package com.sdhsie.webService.toro.controller;
import com.sdhsie.base.controller.BaseController;
import com.sdhsie.base.util.*;
import com.sdhsie.web.customer.service.CustomerService;
import com.sdhsie.web.order.service.OrderFlowService;
import com.sdhsie.web.order.service.OrderService;
import com.sdhsie.web.system.service.LoginService;
import com.sdhsie.webService.toro.service.UserMessageService;
import com.sdhsie.webService.toro.utils.WebUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zcx on 2016/11/21.
 */
@Controller
@RequestMapping(value = "/toro/userMessage")
public class UserMessage extends BaseController {
    private PageData pd;
    @Autowired
    private LoginService loginService;
    @Autowired
    private UserMessageService userMessageService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderFlowService orderFlowService;
    /**
     * 设备端登录获取token
     */
    @RequestMapping(value = "/getToken")
    public void getToken(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws Exception {
        pd = new PageData(request);
        Map jsonMap = new HashMap<String, Object>();
        String code = "0";// 0发送失败  1 发送成功
        String message = "请求失败";
        String token =  "";//设备登录需要的登录token值
        pd.put("login_name",pd.get("name"));
        pd.put("login_password",pd.get("key"));
        if(!Verify.verifyIsNotNull(pd.getString("login_name"))){
            message="登录名称不可为空。";
        }else if(!Verify.verifyIsNotNull(pd.getString("login_password"))){
            message="登录密码不可为空。";
        }else {
            PageData uPd = loginService.findUserInfo(pd);
            if (Verify.verifyIsNotNull(uPd)) {//如果密码和账号正确
                if(uPd.getString("login_password").equals(MD5Util.MD5(pd.getString("login_password")))) {
                    code = "1";
                    token = MD5Util.MD5(GuidUtil.geToken());
                    message = "请求成功";
                }else {
                    message = "请求失败,key不正确！";
                }
            }else {
                message = "请求失败,账号不正确！";
            }
        }
        jsonMap.put("code",code);
        jsonMap.put("message",message);
        jsonMap.put("token",token);
        this.writeJson(response,jsonMap);
    }


    /**
     * 获取设备通话纪录
     * @param response
     * 格式{"token":"token",“SN”:"12323","callPhone":"13502100000","time":"2016-01-01 13:23:22"}
     * @throws IOException
     */
    @RequestMapping(value="phoneRecord" , method = RequestMethod.POST)
    public void phoneRecord(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException {
        pd = new PageData(request);
        String code = "0";// 0发送失败  1 发送成功 2token过期
        String message = "添加失败！";
        Map jsonMap = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(pd);
        if(jsonObject != null ) {
                pd.put("sn",jsonObject.get("SN"));
                PageData customer = customerService.findInfo(pd);
//            if (token!=null && token.equals(jsonObject.get("token").toString())) {
                pd = new PageData();
                pd.put("customer_sn", jsonObject.get("SN"));

                pd.put("create_time", jsonObject.get("time"));
                pd.put("callPhone", jsonObject.get("callPhone"));
                if(customer!=null&&customer.size()>0) {
                    pd.put("customer_id", customer.get("id"));
                }
                userMessageService.saveCallRecord(pd);
                code = "1";
                message = "添加成功！";
//            } else {
//                code = "2";
//                message = "token已过期请重新获取！";
//            }
        }
        jsonMap.put("code",code);
        jsonMap.put("message",message);
        this.writeJson(response,jsonMap);
    }

    /**
     * 获取客户满意度
     * @param request
     * @param response
     * {"token":"token",”SN”:"12323","orderNo":"33502100000","time":"2016-01-01 13:23:22",”score”:”1”}
     * @throws IOException
     */
    @RequestMapping(value="getScore" , method = RequestMethod.POST)
    public void getScore(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException {
        pd = new PageData(request);
        String code = "0";// 0发送失败  1 发送成功 2token过期
        String message = "添加失败！";
        Map jsonMap = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(pd);
        if(jsonObject != null ) {
//          if (token!=null && token.equals(jsonObject.get("token").toString())) {
                pd = new PageData();
                pd.put("order_no", jsonObject.get("orderNo"));
                 int num = userMessageService.findAssessmentNum(pd);
                if(num>0){
                    code = "0";
                    message = "此订单已评价，不能重复评价！";
                }else {
                    String score = jsonObject.get("score").toString();
                    if (score.equals("1")) {
                        pd.put("is_punctuality", "Y");
                        pd.put("service_quality", "5");
                        pd.put("service_attitude", "5");
                        pd.put("count", "满意");
                    } else {
                        pd.put("is_punctuality", "N");
                        pd.put("service_quality", "2");
                        pd.put("service_attitude", "2");
                        pd.put("count", "一般");
                    }
                    pd.put("order_no", jsonObject.get("orderNo"));
                    pd.put("create_time", jsonObject.get("time"));
                    userMessageService.saveAssessment(pd);
                    pd.put("status","6");
                    orderService.updateStatusByOrderNo(pd);
                    pd.put("sn",jsonObject.get("SN"));
                    this.saveOrderFlow(pd);
                    code = "1";
                    message = "添加成功！";
                }
//            } else {
//                code = "2";
//                message = "token已过期请重新获取！";
//            }
        }
        jsonMap.put("code",code);
        jsonMap.put("message",message);
        this.writeJson(response,jsonMap);
    }

    public  void saveOrderFlow(PageData pd){
        PageData customerPd = customerService.findInfo(pd);
        //保存订单流程
        PageData flowPd = new PageData();
        flowPd.put("order_no",pd.get("order_no"));
        flowPd.put("status",pd.get("status"));
        flowPd.put("operator_id",customerPd.get("id"));
        flowPd.put("operator_name","设备用户："+customerPd.get("name"));
        orderFlowService.save(flowPd);
    }
    /**
     * 客户进出安全岛
     * @param request
     * @param response
     * {”SN”:"12323","securityCode":"0","time":"2016-01-01 13:23:22"}
     * @throws IOException
     */
    @RequestMapping(value="securityReminder" , method = RequestMethod.POST)
    public void securityReminder(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException {
        pd = new PageData(request);
        String code = "0";// 0发送失败  1 发送成功 2token过期
        String message = "添加失败！";
        Map jsonMap = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(pd);
        if(jsonObject != null ) {
            pd.put("sn",jsonObject.get("SN"));
            PageData customer = customerService.findInfo(pd);
//          if (token!=null && token.equals(jsonObject.get("token").toString())) {
            pd = new PageData();
            pd.put("customer_sn", jsonObject.get("SN"));
            if("0".equals(jsonObject.get("securityCode"))){
                pd.put("type", "IN");
            }else if("1".equals(jsonObject.get("securityCode"))){
                pd.put("type", "OUT");
            }
            pd.put("create_time",jsonObject.get("time"));
            if(customer!=null&&customer.size()>0) {
                pd.put("customer_id", customer.get("id"));
            }
            userMessageService.saveInOutSafeArea(pd);
            code = "1";
            message = "添加成功！";
//            } else {
//                code = "2";
//                message = "token已过期请重新获取！";
//            }
        }
        jsonMap.put("code",code);
        jsonMap.put("message",message);
        this.writeJson(response,jsonMap);
    }
//{"token":"token",“SN”:"12323","callPhone":"13502100000","time":"2016-01-01 13:23:22"}
    public static final String APPID = "yunrong";
    public static final String SECRET = "eeSNANEz5dKhgZU1gsPi7YnUCrvekMW";
    public static final String GET_TOKEN_URL = "https://open.e5ex.com/cgi/token";
    public static final String GET_TOKEN_LOCRL = "http://localhost:8080/toro/userMessage/getScore.do";
//    {”SN”:"12323","securityCode":"0","time":"2016-01-01 13:23:22"}
//{"token":"token",”SN”:"12323","orderNo":"33502100000","time":"2016-01-01 13:23:22",”score”:”1”}
    @RequestMapping(value = "/toroClict")
    public  void  toroClict(HttpServletResponse response) throws IOException {
        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("appid", APPID);
//        params.put("secret", SECRET);
        params.put("score", "1");
        params.put("SN", "5062000159");
        params.put("time", "2017-02-16 15:08:16");
        params.put("orderNo","S-666");
        params.put("securityCode","0");
        // 请求token 获取
        System.out.println(params);
        String result = WebUtils.postURL(GET_TOKEN_LOCRL, params);
        System.out.println("请求结果:" + result);
        this.writeJson(response,result);
    }
}
