package com.sdhsie.web.system.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sdhsie.base.controller.BaseController;
import com.sdhsie.base.freemarkerOffice.ExportDoc;
import com.sdhsie.base.util.DateTimeUtil;
import com.sdhsie.base.util.FileUtil;
import com.sdhsie.base.util.Page;
import com.sdhsie.base.util.PageData;
import com.sdhsie.base.util.PageModel;
import com.sdhsie.base.util.Verify;

@Controller
@RequestMapping("/test")
public class TestController extends BaseController {

	
	
	
	
	
	
	
	
	public static void main(String[] args) throws ParseException {
		
//		PageData pd = new PageData();
//		pd.put("name"+1, "zhangsan"+1);
//		int i=1;
//		PageData p = findUser(pd,i);
//		System.out.println(p);
		
		
//		List<Integer> ls = new ArrayList<Integer>();
//		
//		int id = 11;
//		int pid=1;
//		ls.add(id);
//		List<Integer> list = findUser(ls, pid);
//		
//		
//		for (Integer sid : list) {
//			PageData p = new PageData();
//			p.put("scbs", "N");
//			p.put("xgsj", DateTimeUtil.getDateTime());
//			p.put("id", sid);
//			//更新方法。
//		}
//		ExportDoc.getInstance().createDoc();
	}


	private static List<Integer> findUser(List<Integer> ls, int pid) {
		PageData p = new PageData();
		p.put("id", pid);
		PageData zdPd =  p;  //数据库查询出来的数据
		
		if(Verify.verifyIsNotNull(zdPd)){
			ls.add(Integer.parseInt(zdPd.get("id").toString()));
			findUser(ls, Integer.parseInt(zdPd.get("pid").toString()));
		}
		
		return ls;
	}
	
	
	
	
	@RequestMapping(value = "/createDoc", method = RequestMethod.GET)
	public void createDoc(HttpServletResponse response,HttpSession session) throws Exception {
		PageData pd = new PageData(this.getRequest());
		pd.put("username", "张三");
//		ExportDoc.getInstance().createDoc();
		System.out.println("============");
//		ExportDoc.getInstance().createDoc(session, response, "tt.xml", "demo.doc", pd);
		
		
		List<PageData> list = new ArrayList<PageData>();
		for (int i = 0; i < 10; i++) {
			PageData p = new PageData();
			p.put("name", "张三"+i);
			p.put("age", "2"+i);
			list.add(p);
		}
		
		pd.put("dateList", list);
		
		ExportDoc.getInstance().createDoc(session, response, "dabei.xml", "list列表.doc", pd);
//		ExportDoc.getInstance().createExcel(session, response, "q.xml", "1.xlsx", pd);
		
//		ExportDoc.getInstance().createExcel(session, response, "demo.ftl", "列表.xlsx", pd);
		
		
	}
	
	
	
	
	
	@RequestMapping(value="/exportCfwfxwtj",method=RequestMethod.POST)
	public void exportCfwfxwtj(HttpSession session, Page page,HttpServletResponse response) throws Exception{
		PageData pd = new PageData(this.getRequest());
		
		
		Map<String, Object> beans = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dcr", "zs");
		map.put("dcsj", DateTimeUtil.getDate());
		beans.put("record_jbxx", map);
		
		String tempPath = session.getServletContext().getRealPath("/")+"excelExport/cfwfxwtj.xls";
		String toFile = session.getServletContext().getRealPath("/")+"upload_log/cfwfxwtj.xls";
		
		XLSTransformer transformer = new XLSTransformer();
		transformer.transformXLS(tempPath, beans, toFile);
		FileUtil.downFile(response,toFile,"处罚违法行为统计.xls");
		File file = new File(toFile);
		file.delete();
		file.deleteOnExit();
	}
	
	
	
	public void dr(HttpServletRequest request) throws IOException{
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
        MultipartFile multipartFile = multipartRequest.getFile("zp");  
		Workbook book = new XSSFWorkbook(multipartFile.getInputStream());
		Sheet sheet = book.getSheetAt(0);
		int totalRows = sheet.getPhysicalNumberOfRows();
		int totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
		String[] objs = new String[totalCells];
		for (int i = 0; i < totalRows; i++) {
			for (int j = 0; j < totalCells; j++) {
				if (totalRows >= 1 && sheet.getRow(0) != null) {
					System.out.println(sheet.getRow(i).getCell(j));
					if(sheet.getRow(i).getCell(j)==null||sheet.getRow(i).getCell(j).equals("null")){
						objs[j]="";
					}else{
						objs[j] = sheet.getRow(i).getCell(j).toString();
					}
				}
			}
		}
	}
	
}
