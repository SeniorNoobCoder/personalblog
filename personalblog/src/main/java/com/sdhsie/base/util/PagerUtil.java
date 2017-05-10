/**    
 * 文件名：DateTimeUtil.java    
 *    
 * 版本信息：    
 * 日期：2014-2-17    
 * Copyright 足下 Corporation 2014     
 * 版权所有    
 *    
 */
package com.sdhsie.base.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/** 
 * 
* @ClassName: DateTimeUtil
* @Description: 
* @author anxingtao
* @date 2014-8-27 下午6:09:42
*
 */
public class PagerUtil {
	
	/**
	 * 
	* @Title: getPager
	* @Description: TODO
	* @param @param index 第几页
	* @param @param count 总数据大小
	* @param @param size 每页大小
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	public static String getPager(int index,int count,int size){
		int totalPage = (int) Math.ceil((double)count/size);
//		System.out.println(totalPage);
		
		StringBuffer sb = new StringBuffer();
		sb.append("<div class=\"jogger\">");
		
		if(totalPage<=10){
			if(index<=1){
				for (int i = 1; i <= totalPage; i++) {
					if(index==i){
						sb.append("<span class=\"current\">"+i+"</span>");
					}else{
						sb.append("<a onclick=\"onclickPager("+i+");\">"+i+"</a>");
					}
				}
				sb.append("<a onclick=\"onclickPager("+index+");\">下一页</a>");
				sb.append("<a onclick=\"onclickPager("+totalPage+");\">最后一页</a>");
			}else if(index<=5&&index>1){
				sb.append("<a onclick=\"onclickPager(1);\">首页</a>");
				sb.append("<a onclick=\"onclickPager("+(index-1)+");\">上一页</a>");
				for (int i = 1; i <= totalPage; i++) {
					if(index==i){
						sb.append("<span class=\"current\">"+i+"</span>");
					}else{
						sb.append("<a onclick=\"onclickPager("+i+");\">"+i+"</a>");
					}
				}
				sb.append("<a onclick=\"onclickPager("+(index+1)+");\">下一页</a>");
				sb.append("<a onclick=\"onclickPager("+totalPage+");\">最后一页</a>");
			}
		}else{
			if(index<=1){
				for (int i = 1; i <= 10; i++) {
					if(index==i){
						sb.append("<span class=\"current\">"+i+"</span>");
					}else{
						sb.append("<a onclick=\"onclickPager("+i+");\">"+i+"</a>");
					}
				}
				sb.append("<a onclick=\"onclickPager("+(index+1)+");\">下一页</a>");
				sb.append("<a onclick=\"onclickPager("+totalPage+");\">最后一页</a>");
			}else if(index<=5&&index>1){
				sb.append("<a onclick=\"onclickPager(1);\">首页</a>");
				sb.append("<a onclick=\"onclickPager("+(index-1)+");\">上一页</a>");
				for (int i = 1; i <= 10; i++) {
					if(index==i){
						sb.append("<span class=\"current\">"+i+"</span>");
					}else{
						sb.append("<a onclick=\"onclickPager("+i+");\">"+i+"</a>");
					}
				}
				sb.append("<a onclick=\"onclickPager("+(index+1)+");\">下一页</a>");
				sb.append("<a onclick=\"onclickPager("+totalPage+");\">最后一页</a>");
			}else if(index<totalPage&&index>(totalPage-5)){
				sb.append("<a onclick=\"onclickPager(1);\">首页</a>");
				sb.append("<a onclick=\"onclickPager("+(index-1)+");\">上一页</a>");
				for (int i = totalPage-9; i <= totalPage; i++) {
					if(index==i){
						sb.append("<span class=\"current\">"+i+"</span>");
					}else{
						sb.append("<a onclick=\"onclickPager("+i+");\">"+i+"</a>");
					}
				}
				sb.append("<a onclick=\"onclickPager("+(index+1)+");\">下一页</a>");
				sb.append("<a onclick=\"onclickPager("+totalPage+");\">最后一页</a>");
			}else if(index>=totalPage){
				sb.append("<a onclick=\"onclickPager(1);\">首页</a>");
				sb.append("<a onclick=\"onclickPager("+(index-1)+");\">上一页</a>");
				for (int i = totalPage-9; i <= totalPage; i++) {
					if(index==i){
						sb.append("<span class=\"current\">"+i+"</span>");
					}else{
						sb.append("<a onclick=\"onclickPager("+i+");\">"+i+"</a>");
					}
				}
			}else{
				sb.append("<a onclick=\"onclickPager(1);\">首页</a>");
				sb.append("<a onclick=\"onclickPager("+(index-1)+");\">上一页</a>");
				for (int i = index-5; i <= index+4; i++) {
					if(index==i){
						sb.append("<span class=\"current\">"+i+"</span>");
					}else{
						sb.append("<a onclick=\"onclickPager("+i+");\">"+i+"</a>");
					}
				}
				sb.append("<a onclick=\"onclickPager("+(index+1)+");\">下一页</a>");
				sb.append("<a onclick=\"onclickPager("+totalPage+");\">最后一页</a>");
			}
		}
		sb.append("</div>");
		return sb.toString();
	}
	
	
	/**
	 * 
	* @Title: getPagerAll
	* @Description: 包含上一页下一页首页最后一页
	* @param @param index
	* @param @param count
	* @param @param size
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	public static String getPagerAll(int index,int count,int size){
		int totalPage = (int) Math.ceil((double)count/size);
//		System.out.println(totalPage);
		
		StringBuffer sb = new StringBuffer();
		sb.append("<div class=\"jogger\">");
		sb.append("<a onclick=\"onclickPager(1);\">首页</a>");
		if(totalPage<=10){
			if(index<=1){
				sb.append("<a onclick=\"onclickPager("+index+");\">上一页</a>");
				for (int i = 1; i <= totalPage; i++) {
					if(index==i){
						sb.append("<span class=\"current\">"+i+"</span>");
					}else{
						sb.append("<a onclick=\"onclickPager("+i+");\">"+i+"</a>");
					}
				}
				if(totalPage>index){
					sb.append("<a onclick=\"onclickPager("+(index+1)+");\">下一页</a>");
				}else{
					sb.append("<a onclick=\"onclickPager("+index+");\">下一页</a>");
				}
			}else if(index<=totalPage&&index>1){
				sb.append("<a onclick=\"onclickPager("+(index-1)+");\">上一页</a>");
				for (int i = 1; i <= totalPage; i++) {
					if(index==i){
						sb.append("<span class=\"current\">"+i+"</span>");
					}else{
						sb.append("<a onclick=\"onclickPager("+i+");\">"+i+"</a>");
					}
				}
				if(totalPage>index){
					sb.append("<a onclick=\"onclickPager("+(index+1)+");\">下一页</a>");
				}else{
					sb.append("<a onclick=\"onclickPager("+index+");\">下一页</a>");
				}
			}
		}else{
			if(index<=1){
				sb.append("<a onclick=\"onclickPager("+index+");\">上一页</a>");
				for (int i = 1; i <= 10; i++) {
					if(index==i){
						sb.append("<span class=\"current\">"+i+"</span>");
					}else{
						sb.append("<a onclick=\"onclickPager("+i+");\">"+i+"</a>");
					}
				}
				sb.append("<a onclick=\"onclickPager("+(index+1)+");\">下一页</a>");
			}else if(index<=5&&index>1){
				sb.append("<a onclick=\"onclickPager("+(index-1)+");\">上一页</a>");
				for (int i = 1; i <= 10; i++) {
					if(index==i){
						sb.append("<span class=\"current\">"+i+"</span>");
					}else{
						sb.append("<a onclick=\"onclickPager("+i+");\">"+i+"</a>");
					}
				}
				sb.append("<a onclick=\"onclickPager("+(index+1)+");\">下一页</a>");
			}else if(index<totalPage&&index>(totalPage-5)){
				sb.append("<a onclick=\"onclickPager("+(index-1)+");\">上一页</a>");
				for (int i = totalPage-9; i <= totalPage; i++) {
					if(index==i){
						sb.append("<span class=\"current\">"+i+"</span>");
					}else{
						sb.append("<a onclick=\"onclickPager("+i+");\">"+i+"</a>");
					}
				}
				sb.append("<a onclick=\"onclickPager("+(index+1)+");\">下一页</a>");
			}else if(index>=totalPage){
				sb.append("<a onclick=\"onclickPager("+(index-1)+");\">上一页</a>");
				for (int i = totalPage-9; i <= totalPage; i++) {
					if(index==i){
						sb.append("<span class=\"current\">"+i+"</span>");
					}else{
						sb.append("<a onclick=\"onclickPager("+i+");\">"+i+"</a>");
					}
				}
				sb.append("<a onclick=\"onclickPager("+index+");\">下一页</a>");
			}else{
				sb.append("<a onclick=\"onclickPager("+(index-1)+");\">上一页</a>");
				for (int i = index-5; i <= index+4; i++) {
					if(index==i){
						sb.append("<span class=\"current\">"+i+"</span>");
					}else{
						sb.append("<a onclick=\"onclickPager("+i+");\">"+i+"</a>");
					}
				}
				sb.append("<a onclick=\"onclickPager("+(index+1)+");\">下一页</a>");
			}
		}
		sb.append("<a onclick=\"onclickPager("+totalPage+");\">最后一页</a>");
		sb.append("</div>");
		return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(getPager(12,46,2));
	}
}
