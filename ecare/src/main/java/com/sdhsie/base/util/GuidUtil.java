package com.sdhsie.base.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 获得Id等字符串
 * @author anxingtao
 */
public class GuidUtil {

	private static final Random ran = new Random();
	
	public static String getGuid()
	  {
		StringBuffer now = new StringBuffer(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
	    int n = (int)(Math.random() * 90000.0D + 10000.0D);
	    return now.append(n)+"";
	  }
	public static String geOrderNo()
	{
		StringBuffer now = new StringBuffer(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
		int n = (int)(Math.random() * 90000.0D + 10000.0D);
		return now.append(n)+"";
	}
	public static String geToken()
	{
		StringBuffer now = new StringBuffer(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
		int n = (int)(Math.random() * 90000.0D + 10000.0D);
		return now.append(n)+"";
	}
	public static String getSmailGuid() {
		StringBuffer cc = new StringBuffer();
		cc.append("6");
		String code = "0123456789";
		for(int i=0;i<8;i++) {
			cc.append(code.charAt(ran.nextInt(code.length())));
		}
		return cc.toString();
	}
	
	public static String getCodeNum() {
		StringBuffer cc = new StringBuffer();
		String code = "0123456789";
		cc.append("1");
		for(int i=0;i<6;i++) {
			cc.append(code.charAt(ran.nextInt(code.length())));
		}
		return cc.toString();
	}
	
	public static String getStrGuid(){
		StringBuffer sb = new StringBuffer();
//		StringBuffer now = new StringBuffer(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
//		sb.append(now);
		String code = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for(int i=0;i<32;i++) {
			sb.append(code.charAt(ran.nextInt(code.length())));
		}
		return sb.toString();
	}
	
	public static String getTableName() {
		String prefix = "builder_tablename_";
		StringBuffer now = new StringBuffer(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
	    int n = (int)(Math.random() * 90000.0D + 10000.0D);
	    String table = now.append(n)+"";
		return prefix+table;
	}

	/**
	 * 生成短信验证码
	 * @param charCount
     * @return
     */
	public static String getRandNum(int charCount) {
		String charValue = "";
		for (int i = 0; i < charCount; i++) {
			char c = (char) (randomInt(0, 10) + '0');
			charValue += String.valueOf(c);
		}
		return charValue;
	}
	public static int randomInt(int from, int to) {
		Random r = new Random();
		return from + r.nextInt(to - from);
	}
}
