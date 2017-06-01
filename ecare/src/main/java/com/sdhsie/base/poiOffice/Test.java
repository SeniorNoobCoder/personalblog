package com.sdhsie.base.poiOffice;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Picture;

public class Test {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		String savePath= "H:\\";  
		String docFile= savePath+ "w.doc";  
		String imgFile= savePath+ "2.jpg";  
		   
		HWPFDocument poiDoc = new HWPFDocument(new FileInputStream(docFile));  
		  List picList=poiDoc.getPicturesTable().getAllPictures();  
		  Picture picture=(Picture)picList.get(0); 
		    try {  
		            picture.writeImageContent(new FileOutputStream(imgFile));  
		      } catch (FileNotFoundException e) {  
		              e.printStackTrace();      
		}  
	}
	
}
