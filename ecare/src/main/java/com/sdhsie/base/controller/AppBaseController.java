package com.sdhsie.base.controller;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sdhsie.base.util.Log;
import com.sdhsie.base.util.PageData;
import com.sdhsie.base.util.Verify;
import com.sdhsie.base.util.parameterUtil;
import com.sdhsie.web.system.service.MainService;


public class AppBaseController {
	
	protected Logger logger = Log.getInstance(getClass());
	@Autowired
	protected MainService mainService;
	
	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		
		return request;
	}
	
	/**
	 *  判断分页
	 */
	public PageData getPage(PageData pd){
		if(Verify.verifyIsNotNull(pd.getString("index"))){
			String index = pd.getString("index");
			int startPage=(Integer.parseInt(index)-1)*parameterUtil.app_showCount;
			pd.put("startPage", startPage);
			pd.put("endPage", parameterUtil.app_showCount);
		}else{
			pd.put("index", 1);
			pd.put("startPage", 0);
			pd.put("endPage", parameterUtil.app_showCount);
		}
		return pd;
	}
	
	public static void logBefore(Logger logger, String interfaceName){
		logger.info("");
		logger.info("start");
		logger.info(interfaceName);
	}
	
	public static void logAfter(Logger logger){
		logger.info("end");
		logger.info("");
	}
	
	public void writeJson(HttpServletResponse response,Object object) throws IOException{
		response.setContentType("text/html;charset=utf-8");
		ObjectMapper objMapper = new ObjectMapper();
		JsonGenerator jsonGenerator = objMapper.getJsonFactory()
				.createJsonGenerator(response.getOutputStream(),
						JsonEncoding.UTF8);
		
		jsonGenerator.writeObject(object);
		jsonGenerator.flush();
		jsonGenerator.close();
	}
	
}
