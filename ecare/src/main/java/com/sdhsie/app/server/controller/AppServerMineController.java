package com.sdhsie.app.server.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sdhsie.app.common.controller.AppParameter;
import com.sdhsie.app.common.service.AppMessageService;
import com.sdhsie.app.server.service.AppLoginService;
import com.sdhsie.app.server.service.AppOrderService;
import com.sdhsie.app.server.service.AppServerMineService;
import com.sdhsie.base.controller.AppBaseController;
import com.sdhsie.base.util.Json;
import com.sdhsie.base.util.PageData;
import com.sdhsie.base.util.Verify;
import com.sdhsie.base.util.ecareParameter;

@Controller
@RequestMapping(value = "/app/servermine")
public class AppServerMineController extends AppBaseController{
	@Autowired
	private AppServerMineService appServerMineService;
	@Autowired
	private AppOrderService appOrderService;
	@Autowired
	private AppMessageService appMessageService;
	@Autowired
	private AppLoginService appLoginService;
	
	/**
	 * 
	  * @Title: saveFeedback
	  * @Description: 商户的意见反馈
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/saveFeedback.html", method = RequestMethod.POST)
	public void saveFeedback(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception{
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String content=bodyMap.get("content").toString();//反馈内容
		try {
			if(Verify.verifyIsNotNull(session_id)){
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当用户信息
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					PageData pd=new PageData();
					pd.put("create_user", userPd.get("id"));
					pd.put("status", "N");
					pd.put("remove_logo", "N");
					pd.put("content", content);
					pd.put("type", "server");//商户类型
					appServerMineService.saveFeedback(pd);
					
					json.setMsg("意见反馈成功。");
					json.setCode("0");
					json.setVerifySessionId(true);
					json.setSuccess(true);
					
				}else{
					json.setMsg("session_id失效，请重新登录");
					json.setCode("-6");
					json.setSuccess(false);
				}
			}else{
				json.setMsg("session_id为空");
				json.setCode("-6");
				json.setSuccess(false);
			}
			this.writeJson(response, json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	  * @Title: findUserInfo
	  * @Description: 商家信息
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findUserInfo.html", method = RequestMethod.POST)
	public void findUserInfo(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception{
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		String id=bodyMap.get("id").toString();//服务信息id
		try {
					PageData pd=new PageData();
					pd.put("id", id);
					PageData pp=appServerMineService.findUserInfo(pd);
					
					json.setMsg("查询商家信息成功。");
					json.setMyobject(pp);
					json.setCode("0");
					json.setVerifySessionId(true);
					json.setSuccess(true);
			this.writeJson(response, json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	  * @Title: findRank
	  * @Description: 等级信息
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findRank.html", method = RequestMethod.POST)
	public void findRank(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception{
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		
		try {
			if(Verify.verifyIsNotNull(session_id)){
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当用户信息
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					PageData pd=new PageData();
					pd.put("server_user_id", userPd.get("id"));
					int num=appServerMineService.findPraiseNum(pd);//查询好评数量
					double numd=0;
					double  sum=0;
					int sumd=0;
					double current=0;
					int nextNum=0;
					int need=0;
					if(num!=0){
						numd = (double) num;// 好评数量转化为double类型
						sum = numd / 50;
						sumd = num / 50;
						 current=Math.ceil(sum);//当前等级
						int currentInt=(int)current;//转化为int类型
						 nextNum=currentInt*50;//升到下一级所需要的好评数
						 need=nextNum-num;//需要多少好评数升到下一级
					}else{
						num=0;
						sumd=0;
						nextNum=50;
						need=50;
					}
					//等级规则
					PageData pv = new PageData();
					pv.put("user_type", ecareParameter.server_user);
					pv.put("info_type", "level");
					PageData level = appMessageService.findUsInfo(pv);
					
					level.put("num", num);//好评数量
					level.put("current", sumd);//当前等级的百分比
					level.put("nextNum", nextNum);//升到下一级所需要的好评数
					level.put("need", need);//需要多少好评数升到下一级
					json.setMsg("查询成功！");
					json.setMyobject(level);
					json.setCode("0");
					json.setVerifySessionId(true);
					json.setSuccess(true);
					
				}else{
					json.setMsg("session_id失效，请重新登录");
					json.setCode("-6");
					json.setSuccess(false);
				}
			}else{
				json.setMsg("session_id为空");
				json.setCode("-6");
				json.setSuccess(false);
			}
			this.writeJson(response, json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	  * @Title: findTwo
	  * @Description: 查询我的订单完成量与我的收入（登陆后的）
	  * @param @param map
	  * @param @param request
	  * @param @param response
	  * @param @param session
	  * @param @throws Exception    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	@RequestMapping(value="/findTwo.html", method = RequestMethod.POST)
	public void findTwo(@RequestBody Map<String, Object> map,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception{
		Json json = new Json();
		String session_id = AppParameter.getSessionId(map);//手机用户session_id
		Map<String, Object> bodyMap = AppParameter.getBody(map);//内容传参
		try {
			if(Verify.verifyIsNotNull(session_id)){
				PageData p = new PageData();
				p.put("session_id", session_id);
				//获取当用户信息
				PageData userPd = appOrderService.findSessionInfo(p);
				if(Verify.verifyIsNotNull(userPd)){
					//我的收入与完成量
					PageData pp = new PageData();
					SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM");
					String date = "";
					java.util.Date dd = Calendar.getInstance().getTime();
					date = sdf.format(dd);
					
					pp.put("server_user_id", userPd.get("id"));
					pp.put("month", date);
					double pdds = appLoginService.findMany(pp);// 查询我的收入
					int nums = appLoginService.findNum(pp);// 查询我的完成量

					PageData pi = new PageData();
					pi.put("pdds", pdds);
					pi.put("nums", nums);
					
					json.setMsg("查询成功");
					json.setMyobject(pi);
					json.setCode("0");
					json.setVerifySessionId(true);
					json.setSuccess(true);
					
				}else{
					json.setMsg("session_id失效，请重新登录");
					json.setCode("-6");
					json.setSuccess(false);
				}
			}else{
				json.setMsg("session_id为空");
				json.setCode("-6");
				json.setSuccess(false);
			}
			this.writeJson(response, json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
