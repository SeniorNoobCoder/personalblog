package com.sdhsie.base.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 
  * @ClassName: WeatherUtil
  * @Description: 天气
  * @author xiaol
  * @date 2016-8-4 下午02:54:58
  *
 */
public class WeatherUtil {

	//查询城市列表URL--cityname
	public static String citylistUrl = "http://apis.baidu.com/apistore/weatherservice/citylist";
	//查询前七天后四天--cityname&cityid
	public static String qian7hou4WeatherUrl = "http://apis.baidu.com/apistore/weatherservice/recentweathers";
	//根据拼音查询城市天气--citypinyin
	public static String pinyinWeatherUrl = "http://apis.baidu.com/apistore/weatherservice/weather";
	//根据名称查询城市天气--cityname
	public static String nameWeatherUrl = "http://apis.baidu.com/apistore/weatherservice/cityname";
	//查询城市信息详情--cityname
	public static String cityinfoUrl = "http://apis.baidu.com/apistore/weatherservice/cityinfo";
	//根据城市代码查询天气--cityid
	public static String cityidUrl = "http://apis.baidu.com/apistore/weatherservice/cityid";
	
	
	private static String apikey = "493c512708971a4a0917c194282eefbc";
	/*private static String httpArg = "cityname=山东";

	public static void main(String[] args) {
		String jsonResult = request(nameWeatherUrl, httpArg);
		System.out.println(jsonResult);
	}*/
	
	/**
	 * @param urlAll
	 *            :请求接口
	 * @param httpArg
	 *            :参数
	 * @return 返回结果
	 */
	public static String request(String httpUrl, String httpArg) {
	    BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();
	    httpUrl = httpUrl + "?" + httpArg;

	    try {
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setRequestMethod("GET");
	        // 填入apikey到HTTP header
	        connection.setRequestProperty("apikey", apikey);
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        result = sbf.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}

	
	
	public static String getImgByWeather(String weather) {
	    String imgName = "undefined";
	    if ("晴".equals(weather)) {
	        imgName = "00";
	    } else if ("多云".equals(weather)) {
	        imgName = "01";
	    } else if ("阴".equals(weather)) {
	        imgName = "02";
	    } else if ("阵雨".equals(weather)) {
	        imgName = "03";
	    } else if ("雷阵雨".equals(weather)) {
	        imgName = "04";
	    } else if ("雷阵雨伴有冰雹".equals(weather)) {
	        imgName = "05";
	    } else if ("雨夹雪".equals(weather)) {
	        imgName = "06";
	    } else if ("小雨".equals(weather)) {
	        imgName = "07";
	    } else if ("中雨".equals(weather)) {
	        imgName = "08";
	    } else if ("大雨".equals(weather)) {
	        imgName = "09";
	    } else if ("暴雨".equals(weather)) {
	        imgName = "10";
	    } else if ("大暴雨".equals(weather)) {
	        imgName = "11";
	    } else if ("特大暴雨".equals(weather)) {
	        imgName = "12";
	    } else if ("阵雪".equals(weather)) {
	        imgName = "13";
	    } else if ("小雪".equals(weather)) {
	        imgName = "14";
	    } else if ("中雪".equals(weather)) {
	        imgName = "15";
	    } else if ("大雪".equals(weather)) {
	        imgName = "16";
	    } else if ("暴雪".equals(weather)) {
	        imgName = "17";
	    } else if ("雾".equals(weather)) {
	        imgName = "18";
	    } else if ("冻雨".equals(weather)) {
	        imgName = "19";
	    } else if ("沙尘暴".equals(weather)) {
	        imgName = "20";
	    } else if ("小到中雨".equals(weather)) {
	        imgName = "21";
	    } else if ("中到大雨".equals(weather)) {
	        imgName = "22";
	    } else if ("大到暴雨".equals(weather)) {
	        imgName = "23";
	    } else if ("暴雨到大暴雨".equals(weather)) {
	        imgName = "24";
	    } else if ("大暴雨到特大暴雨".equals(weather)) {
	        imgName = "25";
	    } else if ("小到中雪".equals(weather)) {
	        imgName = "26";
	    } else if ("中到大雪".equals(weather)) {
	        imgName = "27";
	    } else if ("大到暴雪".equals(weather)) {
	        imgName = "28";
	    } else if ("浮尘".equals(weather)) {
	        imgName = "29";
	    } else if ("扬沙".equals(weather)) {
	        imgName = "30";
	    } else if ("强沙尘暴".equals(weather)) {
	        imgName = "31";
	    } else if ("霾".equals(weather)) {
	        imgName = "53";
	    }

	    return imgName + ".png";
	}
	
	
}
