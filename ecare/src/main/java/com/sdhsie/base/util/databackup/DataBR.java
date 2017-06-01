package com.sdhsie.base.util.databackup;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import com.sdhsie.base.util.DateTimeUtil;
import com.sdhsie.base.util.GuidUtil;
import com.sdhsie.base.util.PageData;

public class DataBR {

	
	public static boolean backupData(HttpSession session){
		//读取配置文件
		Properties pps = PropertyUtil.readPts("properties/data.properties");
		String backupCmd = pps.getProperty("backupCmd");
		String savePath = pps.getProperty("savePath");
		System.out.println(backupCmd);
		System.out.println(savePath);
		System.out.println(session.getServletContext().getContextPath());
		System.out.println(session.getServletContext().getContextPath()+savePath+GuidUtil.getGuid()+".sql");
		File file = new File(session.getServletContext().getContextPath()+savePath);
		if(!file.exists()){
			file.mkdirs();
		}
		boolean b = MySQLDumpUtil.sqlDump(backupCmd, session.getServletContext().getContextPath()+savePath+GuidUtil.getSmailGuid()+"("+DateTimeUtil.getDate()+")"+".sql");
		System.out.println(b);
		return b;
	}
	
	
	public static List<PageData> findData(HttpSession session){
		//读取配置文件
		File[] fs ;
		Properties pps = PropertyUtil.readPts("properties/data.properties");
		String savePath = pps.getProperty("savePath");
		System.out.println(savePath);
		File file = new File(session.getServletContext().getContextPath()+savePath);
		if(!file.exists()){
			file.mkdirs();
		}
		fs = file.listFiles();
		
		List<PageData> ls = new ArrayList<PageData>();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		for (File f : fs) {
			PageData p = new PageData();
			p.put("code", f.hashCode());
			p.put("fileName", f.getName());
			p.put("filePath", f.getPath());
			p.put("fileSize", (int)Math.ceil(f.length()/1024)+"KB");
			Calendar cal = Calendar.getInstance();  
		    long time = f.lastModified();  
		    cal.setTimeInMillis(time);
		    p.put("time", sdf.format(cal.getTime()));
			ls.add(p);
			System.out.println(f.getName()+"#"+f.getPath()+"#"+f.length());
		}
		return ls;
	}
	
	
	
	public static boolean revertData(HttpSession session,String filePath){
		//读取配置文件
		boolean b=false;
		Properties pps = PropertyUtil.readPts("properties/data.properties");
		String restoreCmd = pps.getProperty("restoreCmd");
		File file = new File(filePath);
		if(file.exists()){
			b=true;
			MySQLDumpUtil.sqlLoad(restoreCmd, filePath);
		}
		return b;
	}
	
	
	public static void main(String[] args) {
		System.out.println(176252/1024);
		System.out.println(Math.ceil(176252/1024));
	}
	
	
}
