package com.sdhsie.base.util;

public class BasicUtil {

		/**
		 * 
		* @Title: delTagsFContent
		* @Description: TODO
		* @param @param content
		* @param @return    设定文件
		* @return String    返回类型
		* @throws
		 */
	    public static String delTagsFContent(String content){
			String strClear=content.replaceAll( ".*?(.*?)<\\/body>", "$1"); //读出body内里所有内容
			strClear=strClear.replaceAll("</?[^/?(br)|(p)][^><]*>","");//保留br标签和p标签
			System.out.println(strClear);//输出结果
			return strClear;
		}

}
