package com.sdhsie.web.system.controller;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.sdhsie.base.util.*;
import com.sdhsie.base.util.database.DatabaseUtil;
import com.sdhsie.web.common.service.MessageService;
import com.sdhsie.web.customer.service.CustomerService;
import com.sdhsie.web.order.service.OrderService;
import com.sdhsie.web.partners.service.PartnersInfoService;
import com.sdhsie.web.visiting.service.VisitingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.sdhsie.base.controller.BaseController;
import com.sdhsie.web.system.service.DictionaryService;
import com.sdhsie.web.system.service.UserService;

@Controller
@RequestMapping(value = "/system/main")
public class MainController extends BaseController {
	private PageData pd;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private UserService userService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private VisitingService visitingService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private PartnersInfoService partnersInfoService;
	/**
	 * 
	  * @Title: toLogin
	  * @Description: 跳转到登录页面
	  * @param @param session
	  * @param @param request
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping(value="/index")
	public ModelAndView toLogin(HttpSession session,HttpServletRequest request,Page page)throws Exception{
		pd = new PageData(request);
		PageData userPd = (PageData) session.getAttribute(Const.SESSION_USER);
		pd.put("remove_logo", "N");
		pd.put("user_id", userPd.get("id"));
		pd.put("status", "N");
		Date date = new Date();
		pd.put("create_time", DateTimeUtil.getDateStr(date,"yyyy-MM"));
		mv = getBaseMv(session, pd);
//		page.setPd(pd);
		List<PageData> messageList = messageService.findListByUser(pd);
		int customerNum = customerService.findNum(pd);
		int orderNum = orderService.findNum(pd);
		int visitingNum = visitingService.findNum(pd);
		int partnersInfoNum = partnersInfoService.findNum(pd);
		mv.addObject("messageList", messageList);
		mv.addObject("customerNum", customerNum);
		mv.addObject("orderNum", orderNum);
		mv.addObject("visitingNum", visitingNum);
		mv.addObject("partnersInfoNum", partnersInfoNum);
		mv.addObject("pd", pd);
		mv.setViewName("system/main/index");
		return mv;
	}
	
	/**
	 * 
	  * @Title: getWeather
	  * @Description: 查询天气
	  * @param @param cityName
	  * @param @param mv
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
//	public void getWeather(String cityName,ModelAndView mv) throws Exception{
//		String httpArg = new String("cityname="+URLEncoder.encode(cityName, "UTF-8").replaceAll("\\+","%20"));
//		String jsonResult = WeatherUtil.request(WeatherUtil.qian7hou4WeatherUrl,httpArg);
//		PageData p = WeatherParseJSON2Pd(jsonResult);
//		//请求成功后 再进行解析
//		if("0".equals(p.get("errNum").toString())){
//			PageData p1 = WeatherParseJSON2Pd(p.get("retData").toString());
//			mv.addObject("weather",p1);
//			mv.addObject("todayWeather",WeatherParseJSON2Pd(p1.get("today").toString()));
//		}else{
//			getWeather(cityName,mv);
//		}
//	}
	
	/**
	 * 
	  * @Title: updateRole
	  * @Description: 切换角色
	  * @param @param session
	  * @param @param request
	  * @param @param response
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/updateRole")
	public void updateRole(HttpSession session,HttpServletRequest request,HttpServletResponse response)throws Exception{
		pd = new PageData(request);
		session.setAttribute(Const.SESSION_ROLE, pd.get("role_id"));
		this.writeJson(response, true);
	}
	
	
	/**
	 * 
	  * @Title: WeatherParseJSON2Pd
	  * @Description: 解析json2pd
	  * @param @param jsonStr
	  * @param @return    设定文件
	  * @return PageData    返回类型
	  * @throws
	 */
//	@SuppressWarnings("unchecked")
//	public static PageData WeatherParseJSON2Pd(String jsonStr){
//    	PageData pd = new PageData();
//        //最外层解析
//        JSONObject json = JSONObject.fromObject(jsonStr);
//        for(Object k : json.keySet()){
//            Object v = json.get(k);
//            String imgName = "";
//            if("type".equals(k)){
//            	imgName = WeatherUtil.getImgByWeather(v.toString());
//        	}
//            //如果内层还是数组的话，继续解析
//            if(v instanceof JSONArray){
//                List<PageData> list = new ArrayList<PageData>();
//                Iterator<JSONObject> it = ((JSONArray)v).iterator();
//                while(it.hasNext()){
//                    JSONObject json2 = it.next();
//                    list.add(WeatherParseJSON2Pd(json2.toString()));
//                }
//                pd.put(k.toString(), list);
//            } else {
//            	pd.put("icon", imgName);
//            	pd.put(k.toString(), v);
//            }
//        }
//        return pd;
//    }

	/**
	 * 
	  * @Title: updateWeatherCity
	  * @Description: 修改天气城市
	  * @param @param session
	  * @param @param request
	  * @param @param response
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value = "/updateWeatherCity")
	public void updateWeatherCity(HttpSession session,HttpServletRequest request,HttpServletResponse response)throws Exception{
		pd = new PageData(request);
		PageData userPd = (PageData) session.getAttribute(Const.SESSION_USER);
		pd.put("id", userPd.get("id"));
		pd.put("update_time", DateTimeUtil.getDateTimeStr());
		userService.update(pd);
		userPd.put("wearth_city_id", pd.get("wearth_city_id"));
		session.removeAttribute(Const.SESSION_USER);
		session.setAttribute(Const.SESSION_USER, userPd);
		this.writeJson(response, true);
	}
	
}
