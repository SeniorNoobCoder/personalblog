package com.sdhsie.base.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class RequestParamUtil {

	
	public static PageData getRequestPd(HttpServletRequest request,BufferedReader br){
		//Header部分
        System.out.print(request.getHeaderNames());
        Enumeration<?> enum1 = request.getHeaderNames();
        while (enum1.hasMoreElements()) {
            String key = (String) enum1.nextElement();
            String value = request.getHeader(key);
            System.out.println(key + "\t" + value);
        }
        //body部分
        String inputLine;
        String str = "";
        try {
            while ((inputLine = br.readLine()) != null) {
                str += inputLine;
            }
            br.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
        System.out.println("str:" + str);
        
        PageData param = JsonToMap.parseJSON2Pd(str);
    	
    	PageData pd = JsonToMap.parseJSON2Pd(param.get("body").toString());
    	System.out.println(pd.getString("login_name"));
		return pd;
        
	}
	
}
