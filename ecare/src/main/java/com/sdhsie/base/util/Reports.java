package com.sdhsie.base.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

@SuppressWarnings("rawtypes")
public class Reports {

/**
 * 生成HTML报表显示
 */
public void selectHtmlReport(List list,HttpServletResponse response,String url,Map map){
response.setContentType("text/html;charset=utf-8");
File reportFile = new File(url);
JasperReport jasperReport = null;
System.out.println(reportFile.getPath());
try {
jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
} catch (JRException e) {
e.printStackTrace();
}
JRBeanCollectionDataSource jrbean = new JRBeanCollectionDataSource(list);
JasperPrint jasperPrint = null;
try {
jasperPrint = JasperFillManager.fillReport(jasperReport,map,jrbean);
} catch (JRException e) {
e.printStackTrace();
}
JRHtmlExporter exporter = new JRHtmlExporter();
PrintWriter out = null;
try {
out = response.getWriter();
} catch (IOException e1) {
e1.printStackTrace();
}
exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);
try {
	exporter.exportReport();
} catch (JRException e) {
	e.printStackTrace();
}
}



/**
 * 导出EXCEL报表
 */
public void selectExcelReport(List list,HttpServletResponse response,String url,Map map,String reportName){
try{
//加载jasper文件
File reportFile = new File(url);
JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());

//装载数据
JRBeanCollectionDataSource jrbean = new JRBeanCollectionDataSource(list);

//构造jasperPrint对象
JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, jrbean);
//输出流
ByteArrayOutputStream oStream = new ByteArrayOutputStream();

//构造输出对象
JExcelApiExporter exporter = new JExcelApiExporter();
 exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,"./image?image=");
 exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
 exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, oStream);
 exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE); // 删除记录最下面的空行
 exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,Boolean.FALSE);// 删除多余的ColumnHeader
 exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,Boolean.FALSE);// 显示边框
 exporter.exportReport();
 
 //excel文件名
 String fileName = reportName + ".xls";
 response.reset();
 response.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(fileName,"utf-8") + "\"");
//写输出流
 byte[] bytes = oStream.toByteArray();
 if(bytes != null && bytes.length > 0) {
 response.setContentType("application/vnd.ms-excel;charset=utf-8");
 response.setContentLength(bytes.length); 
 ServletOutputStream ouputStream = response.getOutputStream(); 
 ouputStream.write(bytes,0,bytes.length); 
 ouputStream.flush(); 
 ouputStream.close();
 }else{
 }
}catch(Exception ex){
ex.printStackTrace();
 } 
}

/**
 * 功能：生成默认图形报表的处理方法
 * 方法名:selectHtmlReport
 * @param List list 数据集
 * @param HttpServletResponse response
 * @param HttpServletRequest request 
* @param String url 解析的报表文件
 * @param Map map 报表用的参数
 * @author郭洪治 2010-03-15
 * @return void
 */
public void selectHtmlReport(List list,HttpServletResponse response,HttpServletRequest request,String url,Map map){
response.setContentType("text/html;charset=utf-8");
File reportFile = new File(url);
JasperReport jasperReport = null;
try {
jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
} catch (JRException e) {
e.printStackTrace();
}
JRBeanCollectionDataSource jrbean = new JRBeanCollectionDataSource(list);
JasperPrint jasperPrint = null;
//设置报表图形默认存放物理目录
String outPutDir = request.getSession().getServletContext().getRealPath("/")+"/reportimg/";
//加载图形报表的相对目录
String sourceDir = request.getContextPath()+"/reportimg/";
try {
File file = new File(outPutDir);
//判断此目录是否存在
if(!file.exists()){
file.mkdir();
}
jasperPrint = JasperFillManager.fillReport(jasperReport,map,jrbean);
} catch (JRException e) {
e.printStackTrace();
}
JRHtmlExporter exporter = new JRHtmlExporter();
PrintWriter out = null;
try {
out = response.getWriter();
} catch (IOException e) {
e.printStackTrace();
}

exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME,outPutDir);
exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,sourceDir);
exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.TRUE);
exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);
try {
exporter.exportReport();
} catch (JRException e) {
e.printStackTrace();
}
}
/**
 * 功能：生成指定目录图形报表查询的处理方法
 * @param List list 数据集
 * @param HttpServletResponse response
 * @param HttpServletRequest request 
 * @param String url 解析的报表文件
 * @param Map map 报表用的参数
 * @param String imgDir 图形生成的目录
 * @author 郭洪治2010-01-20
 * @return void
 */
public void selectHtmlReport(List list,HttpServletResponse response,HttpServletRequest request,String url,Map map,String imgDir){
response.setContentType("text/html;charset=utf-8");
File reportFile = new File(url);
JasperReport jasperReport = null;
//生成图形目录
String outPutDir = request.getSession().getServletContext().getRealPath("/")+"/"+imgDir;
//图形源目录
String sourceDir = request.getContextPath()+"/"+imgDir;
try {
jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
} catch (JRException e) {
e.printStackTrace();
}

JRBeanCollectionDataSource jrbean = new JRBeanCollectionDataSource(list);
JasperPrint jasperPrint = null;
try {
jasperPrint = JasperFillManager.fillReport(jasperReport,map,jrbean);
} catch (JRException e) {
e.printStackTrace();
}
JRHtmlExporter exporter = new JRHtmlExporter();
PrintWriter out = null;
try {
out = response.getWriter();
} catch (IOException e) {
e.printStackTrace();
}
//设置图片存入的物理目录
exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME,outPutDir);
//设置图片显示的目录
exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,sourceDir);
//生成图片到目录
exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.TRUE);
exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);
try {
exporter.exportReport();
} catch (JRException e) {
e.printStackTrace();
}
}
/**
 * 功能：导出多sheet报表的处理方法
 * 方法名:selectAllExcelReport
 * @param List list 数据集
 * @param HttpServletResponse response
 * @param String url 解析的报表文件
 * @param String reportName 生成excel保存的名字
 * @author郭洪治
 * 创建时间：Nov 3, 2009 2:08:31 PM
 */
@SuppressWarnings("unchecked")
public void selectAllExcelReport(List list,HttpServletResponse response,String url,String reportName){
try{
//加载jasper文件
File reportFile = new File(url);
JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
List jasperPrintList = new ArrayList();

int listSize = list.size();
String sheetNamesArray[] = new String[listSize];
for(int i = 0; i < listSize; i++){
Map eachMap = (Map)list.get(i);
List eachList = (List)eachMap.get("ls");
sheetNamesArray[i] = (String)eachMap.get("title");
//装载数据
JRBeanCollectionDataSource jrbean = new JRBeanCollectionDataSource(eachList);
//构造jasperPrint对象
JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, eachMap, jrbean);
jasperPrintList.add(jasperPrint);
}

 //输出流
ByteArrayOutputStream oStream = new ByteArrayOutputStream();

//构造输出对象
JExcelApiExporter exporter = new JExcelApiExporter();
 exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,"./image?image=");
 exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
 exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, oStream);
 exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE); // 删除记录最下面的空行
 exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,Boolean.FALSE);// 删除多余的ColumnHeader
 exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,Boolean.FALSE);// 显示边框
 exporter.setParameter(JRXlsExporterParameter.SHEET_NAMES, sheetNamesArray);
 exporter.exportReport();
 
//写输出流
byte[] bytes = oStream.toByteArray();
 if(bytes != null && bytes.length > 0) {
 response.reset();
 response.setContentType("application/vnd.ms-excel;charset=utf-8");
 response.setContentLength(bytes.length); 
 //excel文件名
 String fileName = reportName + ".xls";
 response.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(fileName,"utf-8") + "\"");
 ServletOutputStream ouputStream = response.getOutputStream(); 
 ouputStream.write(bytes,0,bytes.length); 
 ouputStream.flush(); 
 ouputStream.close();
 }
}catch(Exception ex){
ex.printStackTrace();
 } 
}
}

