package com.sdhsie.base.freemarkerOffice;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;

import com.sdhsie.base.util.FileUtil;
import com.sdhsie.base.util.PageData;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 导出Word文档
 * @author anxingtao
 */
public class ExportDoc {
	
	  private static ExportDoc exportDoc;
	  
	  public static ExportDoc getInstance() {
			if(exportDoc==null) exportDoc = new ExportDoc();
			return exportDoc;
		}
	
	private Configuration configuration = null;

	public ExportDoc() {
		configuration = new Configuration();
		configuration.setDefaultEncoding("UTF-8");
	}

	// 2个参数分别是：模板的名称，导出文件的路径
	public void createDoc(HttpSession session,HttpServletResponse response,String modelName,String fileName,PageData pd) {
		String path = session.getServletContext().getRealPath("")+File.separator+"docTemp";
		Template t = null;
		// 1、导入模板
		configuration.setClassForTemplateLoading(this.getClass(), "/docTemp");
		try {
			// test.ftl为要装载的模板
			t = configuration.getTemplate(modelName); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 3、导出文件
		// 输出文档路径及名称
		String lsPath = path+"/upload";
		File file = new File(lsPath);
        if(!file.exists()){
        	file.mkdirs();
        }
		Writer out = null;
		String toPath = lsPath+File.separator+pd.getString("id")+".doc";
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(toPath)), "utf-8"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
//			pd.put("rznr", this.delHtml(pd.getString("rznr")).replace("&nbsp;", ""));
			t.process(pd, out);
			out.flush();
			out.close();
			FileUtil.downFile(response ,toPath,fileName );
			File f = new File(toPath);
			f.delete();
			f.deleteOnExit();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// 2个参数分别是：模板的名称，导出文件的路径
	public void createExcel(HttpSession session,HttpServletResponse response,String modelName,String fileName,PageData pd) {
		String path = session.getServletContext().getRealPath("")+File.separator+"excelTemp";
		Template t = null;
		// 1、导入模板
		configuration.setClassForTemplateLoading(this.getClass(), "/excelTemp");
		try {
			// test.ftl为要装载的模板
			t = configuration.getTemplate(modelName); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 3、导出文件
		// 输出文档路径及名称
		String lsPath = path+"/upload";
		File file = new File(lsPath);
        if(!file.exists()){
        	file.mkdirs();
        }
		Writer out = null;
		String toPath = lsPath+File.separator+pd.getString("id")+".xlsx";
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(toPath)), "utf-8"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
//			pd.put("rznr", this.delHtml(pd.getString("rznr")).replace("&nbsp;", ""));
			t.process(pd, out);
			out.flush();
			out.close();
			FileUtil.downFile(response ,toPath,fileName );
			File f = new File(toPath);
			f.delete();
			f.deleteOnExit();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void createDocTest() {
		//要填入模本的数据文件
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("username", "zhangsan");
		//设置模本装置方法和路径,FreeMarker支持多种模板装载方法。可以重servlet，classpath，数据库装载，
		//这里我们的模板是放在com.havenliu.document.template包下面
		configuration.setClassForTemplateLoading(this.getClass(), "/docTemp");
		Template t=null;
		try {
			//test.ftl为要装载的模板
			t = configuration.getTemplate("tt.ftl");
		} catch (IOException e) {
			e.printStackTrace();
		}
		//输出文档路径及名称
		File outFile = new File("H:/tt.doc");
		Writer out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		 
        try {
			t.process(dataMap, out);
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static String delHtml(String inputString) {
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;
        try {
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            String regEx_script = "<[/s]*?script[^>]*?>[/s/S]*?<[/s]*?//[/s]*?script[/s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[/s/S]*?<//script>
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签
            textStr = htmlStr;
        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }
        return textStr;// 返回文本字符串
    }

}
