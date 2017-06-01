package com.sdhsie.base.util;

import com.cloopen.rest.sdk.CCPRestSDK;

import java.util.HashMap;
import java.util.Set;

public class SMSUtil {

	
	public static String templateId = "54185"; //正式
	public static String templateId1 = "1"; //测试
	
	/**
	 * mobileNumber手机号多个手机号以,分割
	 * templateId短信模板的ID
	 * str模板ID对应的内容
	 */
	public static HashMap<String, Object> sendSMS(String mobileNumber,String templateId,String[] str){
		HashMap<String, Object> result = null;

		CCPRestSDK restAPI = new CCPRestSDK();
		restAPI.init("sandboxapp.cloopen.com", "8883");// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
		restAPI.setAccount("aaf98f895147cd2a01516ae8cd17558a", "7d9fd50f002f4c538cc125c68628a3b0");// 初始化主帐号和主帐号TOKEN
		restAPI.setAppId("8a48b5515147eb6d01516b582aeb53b9");// 初始化应用ID正式
//		restAPI.setAppId("aaf98f895147cd2a01516b80557356d2");// 初始化应用ID测试
		
		result = restAPI.sendTemplateSMS(mobileNumber,templateId ,str);

		System.out.println("SDKTestSendTemplateSMS result=" + result);
		if("000000".equals(result.get("statusCode"))){
			//正常返回输出data包体信息（map）aaf98f895147cd2a01516ae8cd17558a
			HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
			Set<String> keySet = data.keySet();
			for(String key:keySet){
				Object object = data.get(key);
				System.out.println(key +" = "+object);
			}
		}else{
			//异常返回输出错误码和错误信息
			System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
		}
		return result;
	}
	
	
	
	public static void main(String[] args) {
		String[] str = {"123455","5"};
		SMSUtil.sendSMS("13506408036", SMSUtil.templateId, str);
	}

}
