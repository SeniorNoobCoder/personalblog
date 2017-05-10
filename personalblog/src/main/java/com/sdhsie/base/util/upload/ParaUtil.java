package com.sdhsie.base.util.upload;

import com.sdhsie.base.util.Config;

public class ParaUtil {

	/**
	 * 本地基本信息配置
	 */
//	public static String cloudfile = "http://42.96.194.204/pic/";
	public static String cloudfile = "http://"+Config.getValue("FTPHOST");
	public static String localName = Config.getValue("IMAGES");
	
	/**
	 * 一级文件夹
	 */
	//系统文件夹
//	public static String system = "web/system/";
//	//小修管理
//	public static String maint = "web/maint/";
//	//公共
//	public static String common = "web/common/";
//	//基础数据
//	public static String basedata = "web/basedata/";
//
//	//调查表管理
//	public static String draft = "web/draft/";
//
//	//app家人般首页top图片
//	public  static  String appFamily= "appFamily/";
//	//app家人般首页top图片
//	public  static  String appServer= "appServer/";
	public static  String business = Config.getValue("business");
	public static  String family = Config.getValue("family");
	public static  String partners = Config.getValue("partners");
	public static  String system = Config.getValue("system");
	/**
	 * 二级文件夹
	 */
	//====================================================后台============
	//用户信息
	public static  String apk = "apk/";
	public static String user = "user/";
	public static String customer = "customer/";
	public  static  String authent = "authent";
//	//合同管理
//	public static String dcontract = "dcontract/";
	//通知
	public static String message = "message/";
	//服务
	public static String service = "service/";
	//服务分类
	public static String serverCategory = "serverCategory/";
	//app家人版商城头部图片
	public static String topImages = "topImages/";
//	//安全
//	public static String facility = "facility/";

	
	//自定义屏蔽删除的路径
	public static String zdyPath = "web/system/";
}
