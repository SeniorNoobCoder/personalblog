package com.sdhsie.port.partner.controller;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sdhsie.base.controller.BaseController;
import com.sdhsie.base.util.DateTimeUtil;
import com.sdhsie.base.util.GuidUtil;
import com.sdhsie.base.util.Json;
import com.sdhsie.base.util.PageData;
import com.sdhsie.base.util.RequestParamUtil;
import com.sdhsie.base.util.Verify;
import com.sdhsie.port.partner.service.PartnerUserService;

@Controller
@RequestMapping("/port/partner/partnerUser")
public class PartnerUserController extends BaseController {

	@Autowired
	private PartnerUserService userService;
	
	@RequestMapping(value="/login.html")
	public void findList(HttpSession session,HttpServletRequest request,HttpServletResponse response,BufferedReader br)throws Exception{
        PageData pd = RequestParamUtil.getRequestPd(request, br);
		Json json = new Json();
		try {
			if(Verify.verifyIsNotNull(pd.getString("login_name"))){
				if(Verify.verifyIsNotNull(pd.getString("login_password"))){
					
					PageData userPd = userService.findLoginInfo(pd);
					if(Verify.verifyIsNotNull(userPd)){
						//更新session_id
						String session_id = GuidUtil.getGuid();
						pd.put("id", userPd.get("id"));
						pd.put("session_id",session_id);
						pd.put("update_time", DateTimeUtil.getDateTimeStr());
						userService.updateUser(pd);
						
						json.setSessionId(session_id);
						json.setSuccess(true);
						json.setMsg("登录成功。");
						json.setMyobject(userPd);
					}else{
						json.setMsg("登录名称或登录密码不正确。");
						json.setSuccess(false);
					}
				}else{
					json.setMsg("登录密码不能为空。");
					json.setSuccess(false);
				}
			}else{
				json.setMsg("登录名称不能为空。");
				json.setSuccess(false);
			}
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("数据异常。");
		}
		this.writeJson(response, json);
	}
	
	
	
}
