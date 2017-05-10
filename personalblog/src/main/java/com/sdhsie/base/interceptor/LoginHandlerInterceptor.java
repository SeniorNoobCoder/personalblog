package com.sdhsie.base.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sdhsie.base.util.Const;
import com.sdhsie.base.util.DateTimeUtil;
import com.sdhsie.base.util.PageData;
import com.sdhsie.base.util.Verify;
import com.sdhsie.base.util.parameterUtil;
import com.sdhsie.web.system.service.LogService;
import com.sdhsie.web.system.service.LoginService;
import com.sdhsie.web.system.service.MainService;

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
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter{

	@Autowired
	private LogService logService;
	@Autowired
	private LoginService loginService;
	@Autowired
	protected MainService mainService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		boolean bol = false;
		String path = request.getServletPath();
		System.out.println(path);
		if(path.matches(Const.NO_INTERCEPTOR_PATH)){
			bol = true;
		}else{
			HttpSession session = request.getSession();
			PageData user = (PageData)session.getAttribute(Const.SESSION_USER);
			if(user!=null){
				if(path.matches(Const.LOG_PATH)){
					bol = true;
				}else{
					PageData p = new PageData();
					p.put("user_id", user.get("id"));
					p.put("role_id", session.getAttribute(Const.SESSION_ROLE));
					List<Integer> userRoleList = mainService.findRoleList(p);
					StringBuffer sb = new StringBuffer();
					for (Integer integer : userRoleList) {
						sb.append(integer).append(",");
					}
					if(sb.length()>0){
						sb.substring(0, sb.length()-1);
					}
					//查询访问的链接是否拥有权限 根据path+登录用户进行审查
					System.out.println(path.substring(1, path.lastIndexOf("do")+2));
					p.put("path", path.substring(1, path.lastIndexOf("do")+2));
					//查询该链接是否有用户没有则是异常链接
					p.put("user_id", user.get("id"));
					p.put("role_ids", sb);
					String user_ids = loginService.findUserPath(p);
					//如果user_ids为空则跳过，不在权限系统内
					//如果user_ids不为空且不包含用户id 说明非法没权限 
					//如果user_ids不为空且包含用户id 说明有权限
					if(Verify.verifyIsNotNull(user_ids)){
						if((user_ids+",").contains(user.get("id").toString()+",")){
							bol = true;
						}else{
							//登陆过滤
							response.sendRedirect(request.getContextPath() + Const.LOGIN);
							bol = false;
						}
					}else{
						bol = true;
					}
				}
				
				PageData pd = new PageData();
				
				pd.put("user_id", user.get("id"));
				pd.put("username", user.get("user_name"));
				if(path.matches(".*/((save)).*")){
					pd.put("type", "添加");
				}
				if(path.matches(".*/((del)).*")){
					pd.put("type", "删除");
				}
				if(path.matches(".*/((update)).*")){
					pd.put("type", "修改");
				}
				if(path.matches(".*/((clear)).*")){
					pd.put("type", "清空回收站");
				}
				if(path.matches(".*/((revert)).*")){
					pd.put("type", "还原回收站");
				}
				pd.put("content", user.get("user_name")+"于"+DateTimeUtil.getDateTimeStr()+"进行了"+pd.get("type"));
				pd.put("path", path);
				pd.put("createtime", DateTimeUtil.getDateTime());
				logService.save(pd);
			}else{
				//登陆过滤
				response.sendRedirect(request.getContextPath() + Const.LOGIN);
				bol = false;		
			}
		}
		return bol;
	}
	
	
	
	 /** 
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作    
     * 可在modelAndView中加入数据，比如当前时间 
     */  
    public void postHandle(HttpServletRequest request,    
            HttpServletResponse response, Object handler,    
            ModelAndView modelAndView) throws Exception {     
        System.out.println("==============执行顺序: 2、postHandle================");  
        
    }    
    
    /**  
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等   
     *   
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()  
     */    
    public void afterCompletion(HttpServletRequest request,    
            HttpServletResponse response, Object handler, Exception ex)    
            throws Exception {    
        System.out.println("==============执行顺序: 3、afterCompletion================");    
    }  
    
    
    /**
     * isAjaxRequest:判断请求是否为Ajax请求. <br/>
     * @param request 请求对象
     * @return boolean
     * @since JDK 1.6
     */
    public boolean isAjaxRequest(HttpServletRequest request){
        String header = request.getHeader("X-Requested-With");
        boolean isAjax = "XMLHttpRequest".equals(header) ? true:false;
        return isAjax;
    }
	
}
