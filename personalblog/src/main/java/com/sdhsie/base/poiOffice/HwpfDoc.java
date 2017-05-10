package com.sdhsie.base.poiOffice;

import java.io.InputStream;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

public class HwpfDoc {

	
	  public static WordExtractor testReadByExtractor(InputStream in) throws Exception {
	      WordExtractor extractor = new WordExtractor(in);
	      return extractor;
	   }
	  
	  
	  
	  public static HWPFDocument testReadByDoc(InputStream in) throws Exception {
	      HWPFDocument doc = new HWPFDocument(in);
	      return doc;
	   }
	  
}
