

package com.sdhsie.base.util;



public class Verify {

	/**
	 * 
	* @Title: verifyIsNull
	* @Description: true不为空，false为空
	* @param @param str
	* @param @return    设定文件
	* @return boolean    返回类型
	* @throws
	 */
	public static boolean verifyIsNotNull(String str){
		if(str==null||str.equals("")){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 
	* @Title: verifyIsNull
	* @Description: 验证对象
	* @param @param obj
	* @param @return    设定文件
	* @return boolean    返回类型
	* @throws
	 */
	public static boolean verifyIsNotNull(Object obj){
		if(obj==null||obj.equals("")){
			return false;
		}else{
			return true;
		}
	}
	
	
}
