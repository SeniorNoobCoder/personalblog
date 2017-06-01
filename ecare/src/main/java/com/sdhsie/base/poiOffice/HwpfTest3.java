package com.sdhsie.base.poiOffice;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.junit.Test;

public class HwpfTest3 {
	  
	   @Test
	   public void testWrite() throws Exception {
	      String templatePath = "H:\\ww.doc";
	      InputStream is = new FileInputStream(templatePath);
	      HWPFDocument doc = new HWPFDocument(is);
	      Range range = doc.getRange();
	      //把range范围内的${reportDate}替换为当前的日期
	      range.replaceText("${reportDate}", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	      range.replaceText("${appleAmt}", "100.00");
	      range.replaceText("${bananaAmt}", "200.00");
	      range.replaceText("${totalAmt}", "300.00");
	      OutputStream os = new FileOutputStream("H:\\w.doc");
	      //把doc输出到输出流中
	      doc.write(os);
	      this.closeStream(os);
	      this.closeStream(is);
	   }
	  
	   /**
	    * 关闭输入流
	    * @param is
	    */
	   private void closeStream(InputStream is) {
	      if (is != null) {
	         try {
	            is.close();
	         } catch (IOException e) {
	            e.printStackTrace();
	         }
	      }
	   }
	 
	   /**
	    * 关闭输出流
	    * @param os
	    */
	   private void closeStream(OutputStream os) {
	      if (os != null) {
	         try {
	            os.close();
	         } catch (IOException e) {
	            e.printStackTrace();
	         }
	      }
	   }
	  


	   
	   
	   public static byte[] inputStream2ByteArray(InputStream in,boolean isClose){  
	        byte[] byteArray = null;  
	        try {  
	            int total = in.available();  
	            byteArray = new byte[total];  
	            in.read(byteArray);  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }finally{  
	            if(isClose){  
	                try {  
	                    in.close();  
	                } catch (Exception e2) {  
	                    System.out.println("关闭流失败");  
	                }  
	            }  
	        }  
	        return byteArray;  
	    }  
	}