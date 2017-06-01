package com.sdhsie.base.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UploadifyUtil {

	
	// 地址
	public static String officeCon = "C:/cloudsrich/bd";
	
	public static String baodian = "http://www.baodian.cn/pic";
	public static String cloudfile = "http://www.baodian.cn/pic";
	public static String image = "/cloudsrich/bd";
	public static String tubiao="/tubiao";
	public static String user="/user";
	public static String setting="/setting";
	public static String radio="/radio";
	public static String jbsz="/jbsz";
	public static String mwyd="/mwyd";
	public static String msjzw="/msjzw";
	public static String splj="/splj";
	public static String papers = "/papers";

	java.text.SimpleDateFormat dfs = new java.text.SimpleDateFormat("yyyyMM");
	public String times = dfs.format(new java.util.Date());
	



	//删除指定文件夹下所有文件
	//param path 文件夹完整绝对路径
	   public static boolean delAllFile(String path) {
	       boolean flag = false;
	       File file = new File(path);
	       if (!file.exists()) {
	         return flag;
	       }
	       if (!file.isDirectory()) {
	         return flag;
	       }
	       String[] tempList = file.list();
	       File temp = null;
	       for (int i = 0; i < tempList.length; i++) {
	          if (path.endsWith(File.separator)) {
	             temp = new File(path + tempList[i]);
	          } else {
	              temp = new File(path + File.separator + tempList[i]);
	          }
	          if (temp.isFile()) {
	             temp.delete();
	          }
	          if (temp.isDirectory()) {
	             delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
	             delFolder(path + "/" + tempList[i]);//再删除空文件夹
	             flag = true;
	          }
	       }
	       return flag;
	     }
	


//删除文件夹
//param folderPath 文件夹完整绝对路径

   public static void delFolder(String folderPath) {
   try {
      delAllFile(folderPath); //删除完里面所有内容
      String filePath = folderPath;
      filePath = filePath.toString();
//      java.io.File myFilePath = new java.io.File(filePath);
//      myFilePath.delete(); //删除空文件夹
   } catch (Exception e) {
     e.printStackTrace(); 
   }
   }
   
   //重命名
	// 重命名上传文件（非必须）
	public String generateFileName(String fileName) {
		String formatDate = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
		int random = new Random().nextInt(10000);
		int position = fileName.lastIndexOf(".");
		String extension = fileName.substring(position);

		return formatDate + random + extension;
	}
   
	
	/**
	 * 出去String 中的 html
	 */
	public static String Html2Text(String inputString) {   
        String htmlStr = inputString; //含html标签的字符串   
        String textStr ="";   
        java.util.regex.Pattern p_script;   
        java.util.regex.Matcher m_script;   
        java.util.regex.Pattern p_style;   
        java.util.regex.Matcher m_style;   
        java.util.regex.Pattern p_html;   
        java.util.regex.Matcher m_html;   
         
        java.util.regex.Pattern p_html1;   
        java.util.regex.Matcher m_html1;   
      
       try {   
         String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> }   
         String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style> }   
            String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式   
            String regEx_html1 = "<[^>]+";   
            p_script = Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);   
            m_script = p_script.matcher(htmlStr);   
            htmlStr = m_script.replaceAll(""); //过滤script标签   

            p_style = Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);   
            m_style = p_style.matcher(htmlStr);   
            htmlStr = m_style.replaceAll(""); //过滤style标签   
         
            p_html = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);   
            m_html = p_html.matcher(htmlStr);   
            htmlStr = m_html.replaceAll(""); //过滤html标签   
             
            p_html1 = Pattern.compile(regEx_html1,Pattern.CASE_INSENSITIVE);   
            m_html1 = p_html1.matcher(htmlStr);   
            htmlStr = m_html1.replaceAll(""); //过滤html标签   
         
             
         textStr = htmlStr;   
         
        }catch(Exception e) {   
                 System.err.println("Html2Text: " + e.getMessage());   
        }   
      
       return textStr;//返回文本字符串   
    }
	
	  public static String abbreviate(String str, int width, String ellipsis) {  
	        if (str == null || "".equals(str)) {  
	            return "";  
	         }  
	  
	        int d = 0; // byte length  
	        int n = 0; // char length  
	        for (; n < str.length(); n++) {  
	             d = (int) str.charAt(n) > 256 ? d + 2 : d + 1;  
	            if (d > width) {  
	                break;  
	             }  
	         }  
	  
	        if (d > width) {  
	             n = n - ellipsis.length() / 2;  
	            return str.substring(0, n > 0 ? n : 0) + ellipsis;  
	         }  
	  
	        return str = str.substring(0, n);  
	     }   
	
	  /**
	   * 去除\r \n
	   */
	  public  String replaceBlank(String str) {
   	        String dest = "";
   	        if (str!=null) {
   	            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
   	            Matcher m = p.matcher(str);
   	            dest = m.replaceAll("");
   	        }
   	        return dest;
   	    }
	  
	  
	  /**
	   * 将一个文件考到另一个文件
	   */
	  
	  
	  
}