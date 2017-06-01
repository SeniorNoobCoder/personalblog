package com.sdhsie.base.util.databackup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * 
  * @ClassName: MySQLDump
  * @Description: MYSQL数据库备份
  * @author Administrator
  * @date 2016-3-21 下午01:50:01
  *
 */
public class MySQLDumpUtil {

	
	
	public static void backup(String cmd, String filePath) {
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			Process proc = Runtime.getRuntime().exec(cmd);
			br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			bw = new BufferedWriter(new FileWriter(filePath));
			System.out.println(filePath);
			String str = null;
			while((str=br.readLine())!=null) {
				bw.write(str);
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(br!=null) br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(bw!=null) bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	  * @Title: sqlDump
	  * @Description: 备份数据库
	  * @param @param cmd
	  * @param @param filePath
	  * @param @return    设定文件
	  * @return boolean    返回类型
	  * @throws
	 */
	public static boolean sqlDump(String cmd, String filePath) {
		boolean falg = false;
		try {
			Runtime run = Runtime.getRuntime();
			Process p = run.exec(cmd);
			InputStream is = p.getInputStream();// 控制台的输出信息作为输入流
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");// 设置输入流编码格式
			BufferedReader br = new BufferedReader(isr);
			// 将控制台输入信息写入到文件输出流中
			FileOutputStream fos = new FileOutputStream(filePath);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos,
					"UTF-8"));

			String temp = null;
			while ((temp = br.readLine()) != null) {
				bw.write(temp);
				bw.newLine();
			}
			bw.flush();
			bw.close();
			br.close();
			falg = true;
			System.out.println("/* Dump SQL File " + filePath + " OK! */");
		} catch (IOException e) {
			throw new RuntimeException("请将mysql命令添加到path中!", e);
		}
		return falg;
	}

	/**
	 * 
	  * @Title: sqlLoad
	  * @Description: 恢复数据库
	  * @param @param cmd
	  * @param @param sqlPath    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public static void sqlLoad(String cmd, String sqlPath) {
		try {
			Runtime rt = Runtime.getRuntime();
			Process child = rt.exec(cmd);
			OutputStream out = child.getOutputStream();// 控制台的输入信息作为输出流
			// 输入流
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(sqlPath), "utf-8"));
			// 输出流
			OutputStreamWriter writer = new OutputStreamWriter(out, "utf-8");
			String inStr;
			while ((inStr = br.readLine()) != null) {
				writer.write(inStr);
				writer.write("\n\r");
			}
			writer.flush();
			// 别忘记关闭输出流
			out.close();
			br.close();
			writer.close();
			System.out.println("/* Load SQL File " + sqlPath + " OK!*/");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
