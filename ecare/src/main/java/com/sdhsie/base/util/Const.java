package com.sdhsie.base.util;

import org.springframework.context.ApplicationContext;
/**
 * 项目名称：newOracle
 * @author:fuhang
 * 
*/
public class Const {
	public static final String SESSION_SECURITY_CODE = "sessionSecCode";
	public static final String SESSION_USER = "loginUser";
	public static final String SESSION_USER_RIGHTS = "sessionUserRights";
	public static final String SESSION_ROLE_RIGHTS = "sessionRoleRights";
	public static final String TRUE = "T";
	public static final String FALSE = "F";
	public static final String LOGIN = "/system/login/toLogin.do";
	public static final String AUTHOR = "/pages/base/author.jsp";
	
	public static final String SESSION_USER_ORG = "sessionUserOrganizeId";
	
	//级联id
	public static final String SESSION_ORG_CASCADE = "sessionOrganizeCascade";
	
	
	//角色切换
	public static final String SESSION_ROLE = "sessionRole";
	
	//接口token
	public static final String SESSION_USER_Token = "sessionUserToken";

	public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(to_login)|(out_login)|(code)|(getCode)|(api)|(app)|(lost_login)|(getToken)|(phoneRecord)|(getScore)|(getScore)|(securityReminder)).*";	//不对匹配该值的访问路径拦截（正则）

	public static final String LOG_PATH = ".*/((save)|(update)|(del)|(clear)|(revert)).*";	//对匹配该值的访问路径进行日志处理（正则）

	
	public static ApplicationContext WEB_APP_CONTEXT = null; //该值会在web容器启动时由WebAppContextListener初始化
	
	/**
	 * APP Constants
	 */
	//app注册接口_请求协议参数)
	public static final String[] APP_REGISTERED_PARAM_ARRAY = new String[]{"countries","uname","passwd","title","full_name","company_name","countries_code","area_code","telephone","mobile"};
	public static final String[] APP_REGISTERED_VALUE_ARRAY = new String[]{"国籍","邮箱帐号","密码","称谓","名称","公司名称","国家编号","区号","电话","手机号"};
	
	//app登录接口_请求协议中的参数
	public static final String[] APP_LOGIN_PARAM_ARRAY = new String[]{"uname","passwd"};
	public static final String[] APP_LOGIN_VALUE_ARRAY = new String[]{"邮箱账号","密码"};
	
	//app登录状态接口_请求协议中的参数
	public static final String[] APP_LOGINSTATUS_PARAM_ARRAY = new String[]{"app_id","status"};
	public static final String[] APP_LOGINSTATUS_VALUE_ARRAY = new String[]{"app登录用户ID","登录状态"};	
	
	//忘记密码,查找用户账户是否存在接口_请求协议中的参数
	public static final String[] APP_FORGOTPASSWORD_PARAM_ARRAY = new String[]{"uname"};
	public static final String[] APP_FORGOTPASSWORD_VALUE_ARRAY = new String[]{"邮箱账号"};
	

	//删除标识
	public static final String SCBS_N = "N";//未删除
	public static final String SCBS_Y = "Y";//删除
	
	
	//消息通知 类型
	public static final String NEWS_TYPE_PLAN_SEND = "sendPlan";// 下发计划
	public static final String NEWS_TYPE_FORM_TIP = "form";// 调查表到期提醒
	
}
