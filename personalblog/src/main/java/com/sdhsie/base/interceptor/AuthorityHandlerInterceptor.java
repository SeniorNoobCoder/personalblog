package com.sdhsie.base.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.sdhsie.base.util.Const;

/**
 * 
* 类名称：LoginHandlerInterceptor.java
* 类描述： 
* @author FUHANG
* 作者单位： 
* 联系方式：
* 创建时间：2014年6月27日
* @version 1.0
 */
public class AuthorityHandlerInterceptor extends HandlerInterceptorAdapter{
	@Autowired
//	private MainService mainService;
	
	public String preHandle(HttpServletRequest request, HttpServletResponse response, Object handler,ActionInvocation invocation) throws Exception {
		// TODO Auto-generated method stub
		String path = request.getServletPath();
		if(path.matches(Const.NO_INTERCEPTOR_PATH)){
			return invocation.invoke();
		}else{
			HttpSession session = request.getSession();
			boolean bol = false;
			if(bol){
				return invocation.invoke();
			}else{
        		ActionContext.getContext().getSession().put("authInfo", "toNoAuth");
        		return null;
//				return "toNoAuth";
			}
		}
	}
	
}
