package com.sdhsie.base.util;


public class parameterUtil {

	
	//app端分页 每页显示的条数
	public static int app_showCount = 20;
	
	//配置超级管理员
	public static String superAdministrator="0";
	/*
	 * 配置超级管理员和普通管理员
	 */
	//管理员类型||系统用户
	public static String admin_type="0";
	//普通类型||施工单位
	public static String ordinary_type="1";


	/**
	 * 用户分类
	 */
	//系统用户
	public static String system_user="system";
	//客服用户
	public static String telemarketer_user="telemarketer";
	//合作商
	public static String partners_user="partners";
	//合作商户
	public static String server_user="server";
	//家人
	public static String  fmily_user="family";

	/**
	 * 服务分类
	 */
	public static String server_category_detail = "detail";//服务详情分类
	public static String server_category_category = "category";//服务分类

	/**
	 * 收费标准及工作详情类型
	 */
	public static String price_approve = "approve";//官方价格
	public static String price_private = "private";//私人价格


	/**
	 * 订单来源
	 */
	//客服填写
	public static String order_source_telemarketer="telemarketer";
	//合作商发布
	public static String order_source_partners="partners";
	//家人填写
	public static String  order_source_family="family";

	/**
	 * 组织
	 */
	//养护部
	public static String ministry="ministry";
	//分公司
	public static String branchoffice="branchoffice";
	//养护所
	public static String place="place";
	
	//字典ID
	public static final String DICTIONARY_UTIL_ID = "49";//单位ID
	
	public static final String DICTIONARY_SOURCE_ID = "53";//信息来源
	public static final String DICTIONARY_DIRECTION_ID = "164";//方向
	public static final String DICTIONARY_INSPECT_TYPE = "80";//巡检检查类型
	public static final String DICTIONARY_TEMPERATURE = "86";//气温
	public static final String DICTIONARY_WEATHER = "84";//天气
	public static final String DICTIONARY_INSPECT_FG = "110";//派工单规格
	public static final String DICTIONARY_DISPATCH_YQ = "111";
	
	

	public static final String p_notice = "p_notice";//派工单类型
	
	//组织
	public static String unit_organize="75";
	
	/*
	 * 字典表相关配置
	 */
	//工程类别
	public static String project_type = "45";
	//资产类别
	public static String assets_type = "48";
	//道路类型
	public static String road_type="123";
	
	//组织 分公司
	public static String org_branch ="26";
	
	
	/*
	 * 菜单表相关配置
	 */
	//一级菜单父级id
	public static String menu_one = "0";
	
	
	
	
	
	/**
	 * 基础数据
	 */
	public static String base_technical_grade = "123"; //技术等级
	public static String base_road_type = "129"; //路面类型
	public static String base_lane_type = "134"; //车道类型
	public static String service_type="143";	//服务类别
	
	
	public static String base_culvert_type = "148"; //涵洞类型
	public static String base_import_form = "155"; //进口形式
	public static String base_export_form = "156"; //出口形式
	public static String base_use_type = "157"; //用途分类
	public static String base_facility_type = "202"; //安全设施类型
	public static String base_position_type = "215"; //安全设施位置
	public static String base_color_type = "220"; //安全设施颜色
	public static String base_shap_type = "238"; //安全设施形状
	public static String base_fuse_type = "244"; //安全设施用途
	
	public static String base_direction_type = "164"; //方向
	public static String base_slope_type = "165"; //边坡类型
	public static String base_defend_type = "166"; //防护类型
	public static String base_level_type = "255"; //隧道线路等级
	public static String base_type = "260"; //隧道分类
	public static String base_line_type = "265"; //隧道照明
	public static String base_lf_type = "269"; //隧道左右幅
	public static String base_jg_type = "273"; //隧道结构形式
	public static String base_fan_type = "276"; //隧道通风
}
